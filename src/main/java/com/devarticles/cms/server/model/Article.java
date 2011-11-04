
package com.devarticles.cms.server.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.devarticles.cms.client.model.ArticleDto;
import com.devarticles.cms.client.model.ContentDto;
import com.devarticles.cms.client.model.LinkDto;
import com.giago.appengine.commons.http.url.DashUrlGenerator;
import com.giago.appengine.commons.sitemap.introspector.Sitemap;

@PersistenceCapable
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE, customStrategy="complete-table")
@Sitemap(urlGeneratorClass = DashUrlGenerator.class)
public class Article extends Model {

    public interface Property {
        String tags = "tags";
        String title = "title";
    }

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private String title;

    @Persistent
    private String description;

    @Persistent
    private List<String> tags;

    @Persistent
    @Element(dependent = "true")
    private List<Content> contents;

    @Persistent
    @Element(dependent = "true")
    private List<Link> links;

    @Persistent
    private Date createdDate;

    @Persistent
    private Date modifiedDate;

    @Persistent
    private String author;

    @Persistent
    private String userId;

    @Persistent
    private Long counter;
    
    @Persistent
    private Integer year;

    public Article() {
    }

    public Article(ArticleDto article) {
        setId(article.getId());
        this.author = article.getAuthor();
        this.description = article.getDescription();
        setKeywords(article.getKeywords());
        this.contents = convertContents(article.getContents());
        this.createdDate = article.getCreatedDate();
        this.modifiedDate = article.getModifiedDate();
        this.title = article.getTitle();
        this.links = convertLinks(article.getLinks());
    }

    public void setKeywords(String keywords) {
        tags = new ArrayList<String>();
        if (keywords != null) {
            for (String tag : keywords.split(",")) {
                tags.add(tag.trim());
            }
        }
    }

    public String getKeywords() {
        if (tags == null || tags.isEmpty()) {
            return null;
        }
        StringBuilder keys = new StringBuilder();
        for (String tag : tags) {
            keys.append(tag);
            keys.append(",");
        }
        keys.deleteCharAt(keys.length() - 1);
        return keys.toString();
    }

    public ArticleDto asDto() {
        ArticleDto article = new ArticleDto();
        article.setId(getId());
        article.setAuthor(getAuthor());
        article.setContents(getContentsAsDto());
        article.setCreatedDate(getCreatedDate());
        article.setDescription(getDescription());
        article.setKeywords(getKeywords());
        article.setLinks(getLinksAsDto());
        article.setTitle(getTitle());
        article.setCounter(getCounter());
        article.setModifiedDate(getModifiedDate());
        return article;
    }

    private List<ContentDto> getContentsAsDto() {
        List<ContentDto> dtos = new ArrayList<ContentDto>();
        if (this.contents == null) {
            return dtos;
        }
        for (Content content : this.contents) {
            ContentDto dto = new ContentDto();
            dto.setData(content.getData());
            dto.setType(content.getType());
            dtos.add(dto);
        }
        return dtos;
    }

    private List<LinkDto> getLinksAsDto() {
        List<LinkDto> dtos = new ArrayList<LinkDto>();
        if (links == null) {
            return dtos;
        }
        for (Link link : links) {
            LinkDto dto = new LinkDto();
            dto.setUrl(link.getUrl());
            dto.setTitle(link.getTitle());
            dto.setDescription(link.getDescription());
            dtos.add(dto);
        }
        return dtos;
    }

    private List<Content> convertContents(List<ContentDto> contentJdos) {
        List<Content> contents = new ArrayList<Content>();
        if (contentJdos == null) {
            return contents;
        }
        for (ContentDto contentJdo : contentJdos) {
            Content content = new Content();
            content.setData(contentJdo.getData());
            content.setType(contentJdo.getType());
            contents.add(content);
        }
        return contents;
    }

    private List<Link> convertLinks(List<LinkDto> linkJdos) {
        List<Link> links = new ArrayList<Link>();
        if (linkJdos == null) {
            return links;
        }
        for (LinkDto linkJdo : linkJdos) {
            Link link = new Link();
            link.setUrl(linkJdo.getUrl());
            link.setTitle(linkJdo.getTitle());
            link.setDescription(linkJdo.getDescription());
            links.add(link);
        }
        return links;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public String getFormattedCreatedDate() {
        if (createdDate != null) {
            return new SimpleDateFormat("dd/MM/yyyy").format(createdDate);
        }
        return "no date";
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void addContent(String content, String type) {
        if (this.contents == null) {
            this.contents = new ArrayList<Content>();
        }
        contents.add(new Content(content, type));
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
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
    	if(counter == null) {
    		return new Long(0);
    	}
        return counter;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getYear() {
		return year;
	}
}
