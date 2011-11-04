package com.devarticles.cms.client.model;

public class DraftDto extends ModelDto {

    private String title;
    
    private Long id;

    public DraftDto(){}
    
    public DraftDto(String title, Long id){
        this.title = title;
        this.id = id;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    
}
