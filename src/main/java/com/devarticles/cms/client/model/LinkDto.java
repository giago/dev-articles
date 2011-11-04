package com.devarticles.cms.client.model;

public class LinkDto extends ModelDto {
	
	private String url;
	private String title;
	private String description;
	
	public LinkDto() {
	}
	public LinkDto(String url, String title, String description, int type) {
		this.url = url;
		this.title = title;
		this.description = description;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl() {
		return url;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}

}
