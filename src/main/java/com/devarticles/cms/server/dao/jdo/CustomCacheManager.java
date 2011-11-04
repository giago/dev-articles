package com.devarticles.cms.server.dao.jdo;

import java.util.Collections;
import java.util.List;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheManager;

import com.devarticles.cms.server.model.jsp.ArticleLink;

public class CustomCacheManager {
	
	private Cache cache;
	private static final String RECENT_ARTICLE_KEY = "recentArticleKey";
	private static final String POPULAR_ARTICLE_KEY = "popularArticleKey";
	private static final String RELATIVE_URL_KEY = "relativeUrlKey";
	
	public CustomCacheManager() {
		try {
	        cache = CacheManager.getInstance().getCacheFactory().createCache(Collections.emptyMap());
	        cache.clear();
	    } catch (CacheException e) {
	        throw new RuntimeException("Impossible to get the cache from the manager");
	    }
	}
	
	public void expireArticlesUrl() {
		cache.remove(RECENT_ARTICLE_KEY);
		cache.remove(POPULAR_ARTICLE_KEY);
		cache.remove(RELATIVE_URL_KEY);
	}

	@SuppressWarnings("unchecked")
	public List<ArticleLink> getRecentArticlesUrl() {
		return (List<ArticleLink>)cache.get(RECENT_ARTICLE_KEY);
	}

	public void setRecentArticlesUrl(List<ArticleLink> urls) {
		cache.put(RECENT_ARTICLE_KEY, urls);
	}

	@SuppressWarnings("unchecked")
	public List<ArticleLink> getPopularArticlesUrl() {
		return (List<ArticleLink>)cache.get(POPULAR_ARTICLE_KEY);
	}

	public void setPopularArticlesUrl(List<ArticleLink> urls) {
		cache.put(POPULAR_ARTICLE_KEY, urls);
	}

	@SuppressWarnings("unchecked")
	public List<ArticleLink> getRelativeUrls() {
		return (List<ArticleLink>)cache.get(RELATIVE_URL_KEY);
	}
	
	public void setRelativeUrls(List<ArticleLink> urls) {
		cache.put(RELATIVE_URL_KEY, urls);
	}

}
