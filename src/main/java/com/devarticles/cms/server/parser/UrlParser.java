package com.devarticles.cms.server.parser;


/**
 * This class is responsible to parse url requests
 * like 
 * 
 * SEO-Google-Ranking-Factors
 * /article/Cheap-architecture-for-big-system-5001
 * and get useful information
 */
public class UrlParser {
    
	private static final String EMPTY = "";
    private static final String DASH = "-";
    private static final String URL_SEPARATOR = "/";
    private static final String ENCODED_SPACES = "%20";
    private static final String SPACE = " ";
    private static final String ARTICLE_TOKEN = "/article/";
    private static final String ARTICLE_TOKEN_PATTERN = ".*/article/";
    private static final String ARCHIVE_TOKEN = "/archive/";
    private static final String ARCHIVE_TOKEN_PATTERN = ".*/archive/";
	
	public static final Long getId(String url) {
		if(url == null) {
			return null;
		}
		int index = url.lastIndexOf(DASH);
		if(index <= 0) {
			return null;
		}
		try {
			String possibleNumber = url.substring(index + 1);
			return Long.valueOf(possibleNumber);
		} catch(Exception e) {
			return null;
		}
	}

	public static final String getArticleTitle(String url) {
		return getCleandRelevantUrlPart(url, ARTICLE_TOKEN, ARTICLE_TOKEN_PATTERN);
	}
	
	public static final Integer getArchiveYear(String url) {
		String year =  getCleandRelevantUrlPart(url, ARCHIVE_TOKEN, ARCHIVE_TOKEN_PATTERN);
		try {
			return Integer.parseInt(year);
		} catch(Throwable e) {}
		return null;
	}
	
	private static final String getCleandRelevantUrlPart(String url, String token, String tokenPattern) {
		if(url == null) {
			return null;
		}
		if(!url.contains(token)) {
			return null;
		}
		String cleand = url.replaceAll(tokenPattern, EMPTY);
		return replaceDashes(cleand).trim();
	}
	
    public static final String getLastSegmentWitoutId(String url) {
        if(url == null) {
            return null;
        }
        String lastSegment = getLastSegment(url);
        Long id = getId(lastSegment);
        if(id != null) {
            lastSegment = lastSegment.replace(DASH + id, "");
        }
        return replaceDashes(lastSegment);
    }
    
    public static final String cleanUpFromEncodedSpaces(String segment) {
        return segment.replace(ENCODED_SPACES, SPACE);
    }
    
    public static final String getLastSegment(String url) {
        String[] segment = url.split(URL_SEPARATOR);
        return segment[segment.length-1];
    }
	
    private static final String replaceDashes(String source) {
        return source.replaceAll(DASH, SPACE);
    }
    
}
