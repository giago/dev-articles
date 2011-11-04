package com.devarticles.cms.server.model;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.giago.appengine.commons.http.url.DashWithoutIdUrlGenerator;
import com.giago.appengine.commons.sitemap.introspector.Sitemap;

@Sitemap(title="tag", urlGeneratorClass=DashWithoutIdUrlGenerator.class, relativeUrlPrefix="/tag/")
@PersistenceCapable
public class Tag extends Model {

    public interface Property {
        String tag = "tag";
    }
    
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
    
    @Persistent private String tag;
    @Persistent private Long counter;
    @Persistent private Date createdDate;
    @Persistent private Date modified;
    
    public Tag() {}
    
    public Tag(String tag){
        this.tag = tag;
        this.counter = Long.valueOf(1L);
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public String getTag() {
        return tag;
    }
    public void setCounter(Long counter) {
        this.counter = counter;
    }
    public Long getCounter() {
        return counter;
    }

    public void increase() {
        counter++;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Date getModified() {
		return modified;
	}
    
}
