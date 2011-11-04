package com.devarticles.cms.server.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.devarticles.cms.Constants;
import com.devarticles.cms.server.dao.jdo.JdoArticleDao;
import com.devarticles.cms.server.model.Article;
import com.devarticles.cms.server.model.jsp.ArticleLink;
import com.devarticles.cms.server.parser.UrlParser;

public class ArticleServlet extends DispatcherServlet {
	
	private static final long serialVersionUID = 1L;
	
	private JdoArticleDao articleDao = new JdoArticleDao();
	
    @Override
    protected String process(HttpServletRequest req) {
        StringBuffer url = req.getRequestURL();
        if(url != null) {
        	Article article = getArticleFromData(url.toString());
            req.setAttribute(Constants.Attribute.article, article);
            req.setAttribute(Constants.Attribute.canonicalUrl, 
            		article.getTitle().replaceAll(" ", "-") + "-" + article.getId());
            req.setAttribute(Constants.Attribute.relatedArticles, getRelatedArticles(article));
        }
        req.setAttribute(Constants.Parameter.url, url);
        if(isMobile(req)) {
        	return Constants.Page.m_article; 
        } else {        	
        	return Constants.Page.article;
        }
    }
    
    private List<ArticleLink> getRelatedArticles(Article article) {
    	List<String> tags = article.getTags();
    	if(tags == null || tags.isEmpty()) {
    		return null;
    	}
		return articleDao.getRelatedArticles(tags.get(0));
	}

	private Article getArticleFromData(String url) {
        Long id = UrlParser.getId(url);
        if(id != null) {
            return articleDao.get(url);
        }
        String startingUrl = UrlParser.getArticleTitle(url);
        if(startingUrl != null) {
            return articleDao.getArticleWithUrlStartingWith(url);
        }
        return articleDao.getArticleWithUrlStartingWith("");
    }

}
