package com.devarticles.cms.client.plugin;

import java.util.List;

import com.devarticles.cms.client.model.ArticleDto;
import com.devarticles.cms.client.view.widget.FieldInput;
import com.devarticles.cms.shared.Correction;
import com.devarticles.cms.shared.Profile;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class ArticleMetadataSubPlugin extends Composite {
    
    private FlowPanel panel;
    
    private FieldInput titleField;
    private FieldInput descriptionField;
    private FieldInput tagsField;
    private FieldInput authorField;
    
    public ArticleMetadataSubPlugin(Profile profile) {
        panel = new FlowPanel();
        
        titleField = new FieldInput("Title", "da-longInputField");
        descriptionField = new FieldInput("Description", "da-longInputField");
        
        HorizontalPanel p = new HorizontalPanel();
        p.setStyleName("da-horizontalInputContainer");
        tagsField = new FieldInput("Tags(comma separated)", "da-twoThirdInputField");
        authorField = new FieldInput("Author", "da-oneThirdInputField");
        
        panel.add(titleField);
        panel.add(descriptionField);
        p.add(tagsField);
        p.add(authorField);
        panel.add(p);
        initWidget(panel);
        setAuthor(profile.getNickname());
    }
    
    public void setTitle(ArticleDto article) {
        titleField.setValue(article.getTitle());
    }
    
    public void setDescription(ArticleDto article) {
        descriptionField.setValue(article.getDescription());        
    }
    
    public void setTags(ArticleDto article) {
        tagsField.setValue(article.getKeywords());
    }
    
    public void setAuthor(ArticleDto article) {
        authorField.setValue(article.getAuthor());
    }

    public void setAuthor(String author) {
        authorField.setValue(author);
    }

    public String getTitle() {
        return titleField.getValue();
    }
    
    public String getDescription() {
        return descriptionField.getValue();
    }
    
    public String getKeywords() {
        return tagsField.getValue();
    }
    
    public String getAuthor() {
        return authorField.getValue();
    }

	public void setWarnings(List<Correction> corrections) {
		for(Correction c : corrections) {
			if(Correction.Type.description.equals(c.getType())) {
				if(c.getCorrection() != null && c.getSource()!= null &&
						c.getSource().equals(descriptionField.getValue())) {
					descriptionField.showWarning(c.getCorrection());
				}
			} else if(Correction.Type.title.equals(c.getType())) {
				if(c.getCorrection() != null && c.getSource()!= null &&
						c.getSource().equals(titleField.getValue())) {
					titleField.showWarning(c.getCorrection());
				}
			}
		}
	}
}
