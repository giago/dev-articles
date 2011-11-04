package com.devarticles.cms.client.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleDto extends ModelDto {

	private Long id;
	private String title;
	private String description;	
	private String keywords;
	private List<ContentDto> contents;
	private Date createdDate;
	private Date modifiedDate;
	private String author;
	private List<LinkDto> links;
	private Long counter;
	
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setContents(List<ContentDto> contents) {
		this.contents = contents;
	}
	public List<ContentDto> getContents() {
		return contents;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getAuthor() {
		return author;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	public void addContent(String content, String type) {
		if(this.contents == null) {
			this.contents = new ArrayList<ContentDto>();
		}
		contents.add(new ContentDto(content, type));
	}
	public void setLinks(List<LinkDto> links) {
		this.links = links;
	}
	public List<LinkDto> getLinks() {
		return links;
	}
	public void addLink(String url, String title, String description, int type) {
		if(this.links == null) {
			this.links = new ArrayList<LinkDto>();
		}
		links.add(new LinkDto(url, title, description, type));
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setCounter(Long counter) {
		this.counter = counter;
	}
	public Long getCounter() {
		return counter;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
}
