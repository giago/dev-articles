package com.devarticles.cms.server.model;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class ArticleTest {
	
	@Test
	public void shouldConvertTagsToKeywords() {
		Article article = new Article();
		article.setTags(Arrays.asList("test", "google", "SEO"));
		Assert.assertEquals("test,google,SEO", article.getKeywords());
	}

}
