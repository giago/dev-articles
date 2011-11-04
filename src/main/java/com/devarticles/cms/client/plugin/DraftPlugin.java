package com.devarticles.cms.client.plugin;

import java.util.List;

import com.devarticles.cms.client.ArticleEditorEntryPoint;
import com.devarticles.cms.client.ArticleEditorEntryPoint.Constants;
import com.devarticles.cms.client.ArticleServiceAsync;
import com.devarticles.cms.client.model.DraftDto;
import com.devarticles.cms.shared.Profile;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;

public class DraftPlugin extends OptionalPlugin {
    
    public DraftPlugin(ArticleServiceAsync articleService, final Profile profile) {
        super(articleService, profile, "Draft Plugin");
    }

    @Override
    protected void init() {
        service.getDrafts(new AsyncCallback<List<DraftDto>>() {
            @Override
            public void onFailure(Throwable caught) {  }

            @Override
            public void onSuccess(List<DraftDto> result) {
                for(final DraftDto link: result) {
                    Button load = new Button(link.getTitle() + " - " + link.getId());
                    load.addClickHandler(new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {                            
                            ArticleEditorEntryPoint.goToPage(Constants.editorPageWithDraftParam + link.getId());
                        }
                    });
                    content.add(load);
                }
            }
        });
    }
    
    

}
