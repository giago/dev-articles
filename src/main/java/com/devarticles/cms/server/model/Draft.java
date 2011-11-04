package com.devarticles.cms.server.model;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.devarticles.cms.client.model.ArticleDto;

@PersistenceCapable
public class Draft extends Article {
    
    public Draft(){
    }
    
    public Draft(ArticleDto article) {
        super(article);
    }
    
    @Persistent
    private Integer status;

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

}
