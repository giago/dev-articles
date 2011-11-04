package com.devarticles.cms.server.model;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.devarticles.cms.client.model.ContentDto;
import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Content extends Model {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent private String data;
	@Persistent private int type;
	
	public Content() {}

	public Content(String content, String type) {
		this.data = content;
		setType(type);
	}

	public void setData(String data) {
		this.data = data;
	}
	public String getData() {
		return data;
	}
	public String getHtmlData() {
		return data.replace("<", "&lt;").replace(">", "&gt;");
	}
	public void setType(String type) {
		this.type = ContentDto.convertType(type);
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getType() {
		return type;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Key getKey() {
		return key;
	}
	
	public String getCssClass() {
	    return ContentDto.convertType(type);
	}
}
