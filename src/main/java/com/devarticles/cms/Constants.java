package com.devarticles.cms;

public class Constants {

    public interface Parameter {
        String url = "url";
    }
    
    public interface Attribute {
        String article = "article";
        String popularArticleUrls = "popularArticleUrls";
        String recentArticleUrls = "recentArticleUrls";
        String relativeUrls = "relativeUrls";
        String relativeTags = "relativeTags";
        String tagList = "tagList";
        String yearList = "yearList";
        String articleTagList = "articleTagList";
        String archiveYearList = "archiveYearList";
        String tagName = "tagName";
        String yearName = "yearName";
		String canonicalUrl = "canonicalUrl";
		String relatedArticles = "relatedArticles";
    }
    
    public interface Page {
        String article = "/article.jsp";
        String m_article = "/m_article.jsp";
        String tag = "/tag.jsp";
        String archive = "/archive.jsp";
    }
    
}
