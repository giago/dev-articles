package com.devarticles.cms.client;

import com.devarticles.cms.client.plugin.ArticlePlugin;
import com.devarticles.cms.client.plugin.CorrectorPlugin;
import com.devarticles.cms.client.plugin.DraftPlugin;
import com.devarticles.cms.client.plugin.InvitePlugin;
import com.devarticles.cms.client.plugin.TwitterPlugin;
import com.devarticles.cms.shared.Profile;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class ArticleEditorEntryPoint implements EntryPoint {
	
    public interface Style {
        String articleEditor = "da-articleEditor";
        String messageLabel = "da-messageLabel";
        String messagePanel = "da-messagePanel";
        String toolbarButton = "da-articleEditorToolbarButton";
        String toolbar = "da-articleEditorToolbar";
		String correctorPlugin = "correctorPlugin";
		String correctorPluginSource = "correctorPluginSource";
		String correctorPluginCorrection = "correctorPluginCorrection";
		String correctorToolBar = "correctorToolBar";
		String correctorToolBarStatus = "correctorToolBarStatus";
		String correction = "correction";
    }
    
    public interface Constants {
        String host1 = "localhost";
        String host2 = "127.0.0.1";
        String pageName = "_self";
        String popupDimension = "width=600,height=460";
        String gwtArticleEditorHook = "gwtArticleEditor";
        String gwtCorrecctionEditorHook = "gwtCorrectionEditor";
        String gwtPluginsHook = "gwtPlugins";
        String editorPageWithArticleParam = "/editor.jsp?articleUrl=";
        String editorPageWithDraftParam = "/editor.jsp?draftUrl=";
        String editorPage = "/editor.jsp";
        String homePage = "/index.jsp";
        String articleUrlParameter  = "articleUrl";
        String draftUrlParameter  = "draftUrl";
		String correctorUrlParameter = "corrector";
		String gwtMainToolBarHook = "gwtMainToolBar";
    }
    
    private ArticleServiceAsync articleService = GWT.create(ArticleService.class);
	
    public static final void goToPage(String page) {
        goToPage(page, Constants.pageName);
    }

    public static final void goToPage(String page, String name) {
        Window.open(page, name, Constants.popupDimension);
    }
    
    public static final boolean isDevMode() {
        String host = Window.Location.getHost();
        if(host.contains(Constants.host1) || host.contains(Constants.host2)) {
            return true;
        }
        return false;
    }
	
	@Override
	public void onModuleLoad() {
	    articleService.getProfile(new AsyncCallback<Profile>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("problem saving profile!");
            }

            @Override
            public void onSuccess(final Profile profile) {
            	RootPanel mainToolBar = RootPanel.get(Constants.gwtMainToolBarHook);
            	RootPanel articleEditor = RootPanel.get(Constants.gwtArticleEditorHook);
            	RootPanel plugins = RootPanel.get(Constants.gwtPluginsHook);
            	
            	if(profile == null) {
                    goToPage(Constants.homePage, Constants.pageName);
                } else if(isCorrectionMode()) {
                	articleEditor.add(new CorrectorPlugin(articleService));
                	mainToolBar.add(new Label("Corrector"));
                } else {
                	articleEditor.add(new ArticlePlugin(articleService, profile, mainToolBar));
                    FlowPanel panel = new FlowPanel();
                    if(profile.isInvitePluginEnabled()) {
                        panel.add(new InvitePlugin(articleService, profile));
                    }
                    if(profile.isTwitterPluginEnabled()) {
                        panel.add(new TwitterPlugin(articleService, profile));
                    }
                    panel.add(new DraftPlugin(articleService, profile));
                    plugins.add(panel);
                }
            }
        });
	}
	
	private boolean isCorrectionMode() {
		String corrector = Window.Location.getParameter(Constants.correctorUrlParameter);
		try {
			return Boolean.parseBoolean(corrector);
		} catch (Throwable t) {}
		return false;
	}
	
}
