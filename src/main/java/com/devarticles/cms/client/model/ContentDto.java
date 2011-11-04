package com.devarticles.cms.client.model;

import java.util.HashMap;


public class ContentDto extends ModelDto {
	
	private static final String TEXT_TYPE = "lang-text";
	private static final String OLD_CODE_TYPE = "lang-old-code";
	private static final String JAVA_TYPE = "lang-java";
	private static final String CSS_TYPE = "lang-css";
	private static final String JAVA_SCRIPT_TYPE = "lang-js";
	private static final String XML_TYPE = "lang-xml";
	private static final String HTML_TYPE = "lang-html";
	private static final String SCALA_TYPE = "lang-scala";
	private static final String GO_TYPE = "lang-go";
	private static final String SQL_TYPE = "lang-sql";
	private static final String GENERIC_TYPE = "lang-generic";
	private static final String IMAGE_TYPE = "lang-image";
	private static final String VIDEO_TYPE = "lang-video";
	private static final String NOTE_TYPE = "note";
	private static final String WARNING_TYPE = "warning";
	private static final String LIST_TYPE = "list";
	private static final String TITLE_TYPE = "title";
	private static final String SUMMARY_TYPE = "summary";
	private static final String IMPORTANT_TYPE = "important";
	private static final String YOUTUBE_TYPE = "youtube";
	private static final String ADS_TYPE = "ads";
	private static final String MOBILE_ADS_TYPE = "mobile-ads";
	
	private static final int NONE = 0;
	private static final int CODE = 1;
	private static final int JAVA = 2;
	private static final int CSS = 3;
	private static final int JAVA_SCRIPT = 4;
	private static final int XML = 5;
	private static final int HTML = 6;
	private static final int SCALA = 7;
	private static final int GO = 8;
	private static final int SQL = 9;
	private static final int GENERIC = 10;
	private static final int IMAGE = 11;
	private static final int VIDEO = 12;
	private static final int NOTE = 13;
    private static final int WARNING = 14;
    private static final int LIST = 15;
    private static final int TITLE = 16; 
    private static final int SUMMARY = 17;
    private static final int IMPORTANT = 18;
    private static final int YOUTUBE = 19;
    private static final int ADS = 20;
    private static final int MOBILE_ADS = 21;
	
	public static final HashMap<String, Integer> ENTRIES = new HashMap<String, Integer>(); 
	static {
	    ENTRIES.put(TEXT_TYPE, NONE);
	    ENTRIES.put(OLD_CODE_TYPE, CODE);
	    ENTRIES.put(JAVA_TYPE, JAVA);
	    ENTRIES.put(CSS_TYPE, CSS);
	    ENTRIES.put(JAVA_SCRIPT_TYPE, JAVA_SCRIPT);
	    ENTRIES.put(XML_TYPE, XML);
	    ENTRIES.put(HTML_TYPE, HTML);
	    ENTRIES.put(SCALA_TYPE, SCALA);
	    ENTRIES.put(GO_TYPE, GO);
	    ENTRIES.put(SQL_TYPE, SQL);
	    ENTRIES.put(GENERIC_TYPE, GENERIC);
	    ENTRIES.put(IMAGE_TYPE, IMAGE);
	    ENTRIES.put(VIDEO_TYPE, VIDEO);
	    ENTRIES.put(NOTE_TYPE, NOTE);
        ENTRIES.put(WARNING_TYPE, WARNING);
        ENTRIES.put(LIST_TYPE, LIST);
        ENTRIES.put(TITLE_TYPE, TITLE);
        ENTRIES.put(SUMMARY_TYPE, SUMMARY);
        ENTRIES.put(IMPORTANT_TYPE, IMPORTANT);
        ENTRIES.put(YOUTUBE_TYPE, YOUTUBE);
        ENTRIES.put(ADS_TYPE, ADS);
        ENTRIES.put(MOBILE_ADS_TYPE, MOBILE_ADS);
	}

	private int order;
	private String data;
	private int type;
	
	public ContentDto() {}

	public ContentDto(String content, String type) {
		this.setData(content);
		setType(type);
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getOrder() {
		return order;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getData() {
		return data;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
	
	public void setType(String type) {
	    this.type = convertType(type);
	}
	
	public static final int convertType(String type) {
	    return ENTRIES.get(type);
	}
	
	public static final String convertType(int type) {
	    String classType;
	    switch (type) {
            case NONE:
                classType = TEXT_TYPE;
                break;
            case CODE:
                classType = OLD_CODE_TYPE;
                break;
            case JAVA:
                classType = JAVA_TYPE;
                break;
            case CSS:
                classType = CSS_TYPE;
                break;
            case JAVA_SCRIPT:
                classType = JAVA_SCRIPT_TYPE;
                break;
            case XML:
                classType = XML_TYPE;
                break;
            case HTML:
                classType = HTML_TYPE;
                break;
            case SCALA:
                classType = SCALA_TYPE;
                break;
            case GO:
                classType = GO_TYPE;
                break;
            case SQL:
                classType = SQL_TYPE;
                break;
            case GENERIC:
                classType = GENERIC_TYPE;
                break;
            case IMAGE:
                classType = IMAGE_TYPE;
                break;
            case VIDEO:
                classType = VIDEO_TYPE;
                break;
            case NOTE:
                classType = NOTE_TYPE;
                break;
            case LIST:
                classType = LIST_TYPE;
                break;
            case WARNING:
                classType = WARNING_TYPE;
                break;
            case TITLE:
                classType = TITLE_TYPE;
                break;
            case SUMMARY:
                classType = SUMMARY_TYPE;
                break;
            case IMPORTANT:
                classType = IMPORTANT_TYPE;
                break;
            case YOUTUBE:
                classType = YOUTUBE_TYPE;
                break;
            case ADS:
                classType = ADS_TYPE;
                break;
            case MOBILE_ADS:
                classType = MOBILE_ADS_TYPE;
                break;
            default:
                throw new RuntimeException("Type : " + type + " is not implemented");
        }
	    return classType;
	}
	
}
