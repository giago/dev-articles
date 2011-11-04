package com.devarticles.cms.server.dao.jdo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import com.giago.appengine.commons.test.BaseDatastoreTestCase;
import org.junit.Test;

import com.devarticles.cms.server.dao.ArticleDao;
import com.devarticles.cms.server.model.Article;
import com.devarticles.cms.server.model.jsp.ArticleLink;

public class JdoArticleDaoTest extends BaseDatastoreTestCase {
    
    private ArticleDao dao = new JdoArticleDao();
    
    @Test
    public void shouldGetArticleLinksForTagsAlwaysWorksEvenWithoutTags() {
        List<ArticleLink> links = dao.getArticleLinksByTag("gwt");
        assertNotNull(links);
        assertEquals(0, links.size());
    }
    
    @Test
    public void shouldGetArticleLinksTagsAlwaysWorksEvenWithoutConsistentTag() {
        List<ArticleLink> links = dao.getArticleLinksByTag(null);
        assertNotNull(links);
        assertEquals(0, links.size());
    }
    
    @Test
    public void shouldGetProperTags() {
        Article article = new Article();
        article.setTitle("correct");
        article.setTags(Arrays.asList("gwt", "maven"));
        dao.save(article);
        article = new Article();
        article.setTitle("wrong");
        article.setTags(Arrays.asList("eclipse", "maven"));
        dao.save(article);
        
        List<ArticleLink> links = dao.getArticleLinksByTag("gwt");
        assertNotNull(links);
        assertEquals(1, links.size());
        assertEquals("correct", links.get(0).getTitle());
    }
    
    @Test
    public void shouldIncreaseTheViewCounterOnEveryGet() {
        Article article = new Article();
        article.setTitle("correct");
        article.setTags(Arrays.asList("gwt", "maven"));
        dao.save(article);
        
        List<ArticleLink> links = dao.getArticleLinksByTag("gwt");
        assertNotNull(links);
        assertEquals(1, links.size());
        
        
        Article a1 = dao.get(links.get(0).getUrl());
        assertEquals(Long.valueOf(1), a1.getCounter());
        
        Article a2 = dao.get(links.get(0).getUrl());
        assertEquals(Long.valueOf(2), a2.getCounter());
    }
    
}
