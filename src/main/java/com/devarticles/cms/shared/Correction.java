package com.devarticles.cms.shared;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Correction implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static final String ARTICLE_ID = "articleId";
	
	public interface Type {
		String content = "content";
		String title = "title";
		String description = "description";
	}
	
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
	
	@Persistent
	private Long articleId;
	
	@Persistent
	private String source;
	
	@Persistent
	private String correction;
	
	@Persistent
	private String type;
	
	public Correction() {
	}
	
	public Correction(Long articleId, String source) {
		this(articleId, source, Type.content);
	}
	
	public Correction(Long articleId, String source, String type) {
		this.source = source;
		this.type = type;
		this.articleId = articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setCorrection(String correction) {
		this.correction = correction;
	}

	public String getCorrection() {
		return correction;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSource() {
		return source;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

}
