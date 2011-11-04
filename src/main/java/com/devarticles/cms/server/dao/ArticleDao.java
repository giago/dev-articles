package com.devarticles.cms.server.dao;

import java.util.List;

import com.devarticles.cms.server.model.Article;
import com.devarticles.cms.server.model.jsp.ArticleLink;

public interface ArticleDao {

	Article get(String url);
	
	void save(Article article);
	
	List<ArticleLink> getRecentArticlesUrl();

	List<ArticleLink> getPopularArticlesUrl();

	Article getArticleWithUrlStartingWith(String url);

	List<ArticleLink> getReletiveUrls();

    List<ArticleLink> getArticleLinksByTag(String tagName);

	List<ArticleLink> getArticleLinksByYear(Integer year);

	List<ArticleLink> getRelatedArticles(String tag);
	
}
