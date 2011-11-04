package com.devarticles.cms.client.plugin;

import com.devarticles.cms.client.ArticleEditorEntryPoint;
import com.devarticles.cms.client.ArticleServiceAsync;
import com.devarticles.cms.shared.Profile;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public abstract class OptionalPlugin extends Composite {

    protected ArticleServiceAsync service;
    
    protected Profile profile;
    
    protected FlowPanel content;
    
    public OptionalPlugin(ArticleServiceAsync articleService, Profile profile, String title) {
        this.service = articleService;
        this.profile = profile;
        FlowPanel panel = new FlowPanel();
        panel.setStyleName("da-plugin");
        if(ArticleEditorEntryPoint.isDevMode()) {
            title = title + " dev mode";
        }
        Label titleLabel = new Label(title);
        titleLabel.setStyleName("da-pluginTitle");
        panel.add(titleLabel);
        content = new FlowPanel();
        panel.add(content);
        init();
        initWidget(panel);
    }

    protected abstract void init();
    
}
