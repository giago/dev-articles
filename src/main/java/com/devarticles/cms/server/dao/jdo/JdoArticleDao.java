package com.devarticles.cms.server.dao.jdo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.JDOOptimisticVerificationException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.giago.appengine.commons.dao.jdo.BaseDaoImpl;
import com.giago.appengine.commons.util.DateUtils;

import com.devarticles.cms.server.dao.ArticleDao;
import com.devarticles.cms.server.model.Article;
import com.devarticles.cms.server.model.jsp.ArticleLink;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;


public class JdoArticleDao extends BaseDaoImpl<Article> implements ArticleDao {
	
    protected CustomCacheManager cacheManager = new CustomCacheManager();
    
    private static final String TITLE_CONDITION = "title == urlParam";
    private static final String TAG_CONDITION = "tags == tagParam";
    private static final String YEAR_CONDITION = "year == yearParam";
    private static final String TAG_PARAM_DECLARATION = "String tagParam";
    private static final String YEAR_PARAM_DECLARATION = "Integer yearParam";
    private static final String COUNTER_DESC = "counter desc";
    private static final String CREATED_DATE_DESC = "createdDate desc";
    private static final String DASH = "-";
    private static final int PAGE_SIZE = 10;
    private static final int LARGE_PAGE_SIZE = 25;

    public JdoArticleDao() {
        super(Article.class);
    }

	@Override
	public void save(Article article) {
		PersistenceManager pm = getPM();
		pm.currentTransaction().begin();
	    try {
	    	if(article.getId() != null) {
	    		Article toUpdate = getArticle(pm, article.getId());
	    		toUpdate.setAuthor(article.getAuthor());
	    		
	    		toUpdate.getContents().clear();
	    		toUpdate.setContents(article.getContents());
	    		
	    		toUpdate.setDescription(article.getDescription());
	    		toUpdate.setTags(article.getTags());
	    		
	    		toUpdate.getLinks().clear();
	    		toUpdate.setLinks(article.getLinks());

	    		toUpdate.setTitle(article.getTitle());
	    		toUpdate.setModifiedDate(new Date());
	    		setYear(article);
	    		pm.makePersistent(article);
	    	} else {
	    		article.setModifiedDate(new Date());
	    		setYear(article);
	    		pm.makePersistent(article);
	    	}
	        pm.currentTransaction().commit();
	    } catch (JDOOptimisticVerificationException e) {
	        //handleVersionConflict(e, article);
	    	throw new RuntimeException("JDOOptimisticVerificationException", e);
	    } finally {      
	    	cacheManager.expireArticlesUrl();
	        if (pm.currentTransaction().isActive()) {
	            pm.currentTransaction().rollback();
	        }
	        pm.close();
	    }
	}

	private void setYear(Article article) {
		Date createdDate = article.getCreatedDate();
		if(createdDate != null) {
			article.setYear(DateUtils.getYear(createdDate));
		}
	}

	@Override
	public Article get(String url) {
		PersistenceManager pm = getPM();
		Article article = getArticle(pm, getIdFromUrl(url));
		return article;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Article getArticleWithUrlStartingWith(String url) {
		PersistenceManager pm = getPM();
		Query q = pm.newQuery(Article.class);
		q.setFilter(TITLE_CONDITION);
		q.setRange(0, 1);
		List<Article> result = (List<Article>)executeAndClose(q, url);
		if(result == null || result.isEmpty()) {
			return new Article();
		}
		return result.get(0); 
	}
	
	protected Object executeAndClose(Query query, String url) {
		Object result =  query.execute(url);
		query.closeAll();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ArticleLink> getRecentArticlesUrl() {
		List<ArticleLink> urls = cacheManager.getRecentArticlesUrl();
		if(urls == null) {
			PersistenceManager pm = getPM();
			Query q = pm.newQuery(Article.class);
			q.setRange(0, PAGE_SIZE);
			q.setOrdering(CREATED_DATE_DESC);
			
			urls = new ArrayList<ArticleLink>();
			for(Article article : (List<Article>) q.execute()) {
				urls.add(new ArticleLink(article));
			}
			cacheManager.setRecentArticlesUrl(urls);
		}
		return urls;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ArticleLink> getPopularArticlesUrl() {
		List<ArticleLink> urls = cacheManager.getPopularArticlesUrl();
		if(urls == null) {
			PersistenceManager pm = getPM();
			Query q = pm.newQuery(Article.class);
			q.setRange(0, PAGE_SIZE);
			q.setOrdering(COUNTER_DESC);
			
			urls = new ArrayList<ArticleLink>();
			for(Article article : (List<Article>) q.execute()) {
				urls.add(new ArticleLink(article));
			}
			cacheManager.setPopularArticlesUrl(urls);
		}
		return urls;
	}

	private static final String COUNTER = "counter";
	
	private Article getArticle(PersistenceManager pm, Long id) {
		try {
			DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
			Entity e = ds.get(KeyFactory.createKey(Article.class.getSimpleName(), id));
			if(e != null) {
				Object counter = e.getProperty(COUNTER);
				if(counter != null) {
					e.setProperty(COUNTER, (((Long)counter) + 1));
				} else {
					e.setProperty(COUNTER, Long.valueOf(1));
				}
			}
			ds.put(e);
			return (Article) pm.getObjectById(Article.class, id);
		} catch (Exception e) {
			throw new RuntimeException("Article with id : " + id + " can't be found!");
		}
	}
	
	private Long getIdFromUrl(String url) {
		String id = url.substring(url.lastIndexOf(DASH) + 1);
		return Long.valueOf(id);
	}

	public Article getForEdit(String url, String userId) {
		Article article = get(url);
		if(!article.getUserId().equals(userId)) {
			return null;
		}
		return article;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ArticleLink> getReletiveUrls() {
		List<ArticleLink> urls = cacheManager.getRelativeUrls();
		if(urls == null) {
			PersistenceManager pm = getPM();
			Query q = pm.newQuery(Article.class);
			urls = new ArrayList<ArticleLink>();
			for(Article article : (List<Article>) q.execute()) {
				urls.add(new ArticleLink(article));
			}
			cacheManager.setRelativeUrls(urls);
		}
		return urls;
	}

	@Override
	@SuppressWarnings("unchecked")
    public List<ArticleLink> getArticleLinksByTag(String tagName) {
	    List<ArticleLink> links = new ArrayList<ArticleLink>();
	    if(tagName == null) {
	        return links;
	    }
        PersistenceManager pm = getPM();
        Query q = pm.newQuery(Article.class);
        q.setFilter(TAG_CONDITION);
        q.declareParameters(TAG_PARAM_DECLARATION);
        q.setOrdering(CREATED_DATE_DESC);
            
        for(Article article : (List<Article>) q.execute(tagName)) {
            links.add(new ArticleLink(article));
        }
        return links;
    }
	
	@Override
	@SuppressWarnings("unchecked")
    public List<ArticleLink> getArticleLinksByYear(Integer year) {
	    List<ArticleLink> links = new ArrayList<ArticleLink>();
	    if(year == null) {
	        return links;
	    }
        PersistenceManager pm = getPM();
        Query q = pm.newQuery(Article.class);
        q.setFilter(YEAR_CONDITION);
        q.declareParameters(YEAR_PARAM_DECLARATION);
        q.setOrdering(CREATED_DATE_DESC);
            
        for(Article article : (List<Article>) q.execute(year)) {
            links.add(new ArticleLink(article));
        }
        return links;
    }
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ArticleLink> getRelatedArticles(String tag) {
		PersistenceManager pm = getPM();
		Query q = pm.newQuery(Article.class);
		q.setFilter(TAG_CONDITION);
		q.declareParameters(TAG_PARAM_DECLARATION);
		q.setRange(0, 5);
		q.setOrdering(COUNTER_DESC);
		
		List<ArticleLink> urls = new ArrayList<ArticleLink>();
		for(Article article : (List<Article>) q.execute(tag)) {
			urls.add(new ArticleLink(article));
		}
		
		return urls;
	}
	
}
