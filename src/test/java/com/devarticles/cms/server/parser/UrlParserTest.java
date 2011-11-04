package com.devarticles.cms.server.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class UrlParserTest {

	@Test
	public void shouldGetTheId() {
		assertEquals(5001L, UrlParser.getId("/article/Cheap-architecture-for-big-system-5001").longValue(), 0.01);
	}

	@Test
	public void shouldReturnNullInAnyOtherScenario() {
	    assertNull(UrlParser.getId("/article/Cheap-architecture-for-big-system"));
	    assertNull(UrlParser.getId("/article/Cheap-architecture-for-big-system-"));
		assertNull(UrlParser.getId(null));
		assertNull(UrlParser.getId(""));
		assertNull(UrlParser.getId("vlsdjf laskdkjf lasdj alsdkj asdjlasd "));
	}
	
	@Test
	public void shouldGetArticleTitle() {
		assertEquals("Cheap architecture for big system", UrlParser.getArticleTitle("/article/Cheap-architecture-for-big-system"));
		assertEquals("Cheap architecture", UrlParser.getArticleTitle("/article/Cheap-architecture-"));
	}

	@Test
	public void shouldGetArticleTitleWithAbsoluteUrl() {
		assertEquals("Cheap architecture for big system", UrlParser.getArticleTitle("http://localhost:8888/article/Cheap-architecture-for-big-system"));
		assertEquals("Cheap architecture", UrlParser.getArticleTitle("http://localhost:8888/article/Cheap-architecture-"));
	}
	
	@Test
	public void shouldGetArtcileTitleReturnStrangeCases() {
		assertNull(UrlParser.getArticleTitle("Cheap-architecture-for-big-system"));
		assertNull(UrlParser.getArticleTitle(null));
		assertNull(UrlParser.getArticleTitle(""));
		assertNull(UrlParser.getArticleTitle("vlsdjf laskdkjf lasdj alsdkj asdjlasd "));
	}
	
	@Test
	public void shouldGetDate() {
		assertEquals(Integer.valueOf(2011), UrlParser.getArchiveYear("http://localhost:8888/archive/2011"));
		assertEquals(Integer.valueOf(2010), UrlParser.getArchiveYear("http://localhost:8888/archive/2010"));
		assertNull(UrlParser.getArchiveYear("http://localhost:8888/aeef/2010"));
		assertNull(UrlParser.getArchiveYear("Cheap-architecture-for-big-system"));
		assertNull(UrlParser.getArchiveYear(null));
		assertNull(UrlParser.getArchiveYear(""));
		assertNull(UrlParser.getArchiveYear("2010"));
	}
	
	@Test
	public void shouldGetLastSegment() {
	    assertEquals("Cheap-architecture", UrlParser.getLastSegment("http://localhost:8888/tag/Cheap-architecture"));
	    assertEquals("Cheap-architecture", UrlParser.getLastSegment("Cheap-architecture"));
	    assertEquals("Cheap-architecture", UrlParser.getLastSegment("/Cheap-architecture"));
	}
	
	@Test
    public void shouldGetLastSegmentPartWithoutId() {
	    assertEquals("Cheap architecture", UrlParser.getLastSegmentWitoutId("http://localhost:8888/tag/Cheap-architecture"));
	    assertEquals("Cheap architecture", UrlParser.getLastSegmentWitoutId("http://localhost:8888/tag/Cheap-architecture-100"));
    }
	
	@Test
	public void shouldCleanUpEncodedSpaces() {
	    assertEquals("tag1 with spaces" , UrlParser.cleanUpFromEncodedSpaces("tag1%20with%20spaces"));
	}
	
}
