package com.devarticles.cms.client.plugin;

import java.util.Date;
import java.util.List;

import com.devarticles.cms.client.ArticleEditorEntryPoint;
import com.devarticles.cms.client.ArticleServiceAsync;
import com.devarticles.cms.client.ArticleEditorEntryPoint.Constants;
import com.devarticles.cms.client.ArticleEditorEntryPoint.Style;
import com.devarticles.cms.client.model.ArticleDto;
import com.devarticles.cms.client.model.ContentDto;
import com.devarticles.cms.client.model.LinkDto;
import com.devarticles.cms.client.view.widget.ContentInput;
import com.devarticles.cms.client.view.widget.LinkInput;
import com.devarticles.cms.client.view.widget.ListInputContainer;
import com.devarticles.cms.shared.Correction;
import com.devarticles.cms.shared.Profile;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

public class ArticlePlugin extends Composite {
	
    private ArticleDto article;
    private FlowPanel panel;
    private Button saveButton;
    private Button saveDraftButton;

    private ListInputContainer<LinkInput, LinkDto> linkListInput;
    private ListInputContainer<ContentInput, ContentDto> contentListInput;
    private Label messageLabel;
    private FlowPanel messagePanel;
    private ArticleServiceAsync articleService;
    private ArticleMetadataSubPlugin articleMetadata;
    private Profile profile;
    
    private boolean isDraft = false;
    
    public ArticlePlugin(ArticleServiceAsync articleService, Profile profile, RootPanel mainToolBar) {
    	this.articleService = articleService;
        this.profile = profile;
        panel = new FlowPanel();
        panel.setStyleName(Style.articleEditor);
        
        initWidget(panel);
        
        initMessagePanel();
        initArticle();
        initToolbar(mainToolBar);
        initArticleMetadata();
        initLinkListInput();
        initContentListInput();
    }
    
    private void initMessagePanel() {
        messagePanel = new FlowPanel();
        messagePanel.setStyleName(Style.messagePanel);
        messagePanel.setVisible(false);
        messageLabel = new Label();
        messageLabel.setStyleName(Style.messageLabel);
        messagePanel.add(messageLabel);
        panel.add(messagePanel);
    }

    private void initArticleMetadata() {
        articleMetadata = new ArticleMetadataSubPlugin(profile);
        panel.add(articleMetadata);
    }

    private void initArticle() {
        String articleUrl = Window.Location.getParameter(Constants.articleUrlParameter);
        String draftUrl = Window.Location.getParameter(Constants.draftUrlParameter);
        if(articleUrl != null && articleUrl.length()>0) {
            articleService.get(articleUrl, new AsyncCallback<ArticleDto>() {
                @Override
                public void onSuccess(ArticleDto result) {
                    if(result == null) {
                        setUserMessage("Article retrieved is null, the id can be wrong or you can't edit this specific article. " +
                                "Only article you have created can be edited by you."); 
                        article = new ArticleDto();
                        article.setCreatedDate(new Date()); 
                        article.setModifiedDate(new Date());    
                    }
                    setValue(result);
                }
                
                @Override
                public void onFailure(Throwable caught) {
                    article = new ArticleDto();
                    article.setCreatedDate(new Date()); 
                    article.setModifiedDate(new Date());
                    setValue(article);
                }
            });
        } else if (draftUrl != null && draftUrl.length() > 0) {
            articleService.getDraft(draftUrl, new AsyncCallback<ArticleDto>() {
                @Override
                public void onSuccess(ArticleDto result) {
                    if(result == null) {
                        setUserMessage("Draft retrieved is null, the id can be wrong or you can't edit this specific article. " +
                                "Only article you have created can be edited by you."); 
                        result = new ArticleDto();
                    }
                    result.setCreatedDate(new Date()); 
                    result.setModifiedDate(new Date());    
                    setValue(result);
                    isDraft = true;
                }
                
                @Override
                public void onFailure(Throwable caught) {
                    article = new ArticleDto();
                    article.setCreatedDate(new Date()); 
                    article.setModifiedDate(new Date());
                    setValue(article);
                }
            });
        } else {
            article = new ArticleDto();
            article.setCreatedDate(new Date()); 
        }
    }
    
    private void initLinkListInput() {
        linkListInput = new ListInputContainer<LinkInput, LinkDto>("Links"){
            @Override
            public LinkInput createInputField() {
                return new LinkInput();
            }
        };
        panel.add(linkListInput);
    }
    
    private void initContentListInput() {
        contentListInput = new ListInputContainer<ContentInput, ContentDto>("Content"){
            @Override
            public ContentInput createInputField() {
                return new ContentInput();
            }

			@Override
			protected String getWarningId(ContentDto m) {
				return m.getData();
			}
        };
        panel.add(contentListInput);
    }
    
    private void initToolbar(RootPanel panel) {
        FlowPanel editorToolBar = new FlowPanel();
        editorToolBar.setStyleName(Style.toolbar);
        final TextBox articleIdLoader = new TextBox();
        articleIdLoader.setText("ID to load");
        articleIdLoader.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (event.getCharCode() == KeyCodes.KEY_ENTER) {
                    ArticleEditorEntryPoint.goToPage(Constants.editorPageWithArticleParam + articleIdLoader.getValue());
                }
            }
        });
        editorToolBar.add(articleIdLoader);
        Button loadButton = new Button("Load");
        loadButton.setStyleName(Style.toolbarButton);
        loadButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                ArticleEditorEntryPoint.goToPage(Constants.editorPageWithArticleParam + articleIdLoader.getValue());
            }
        
        });
        editorToolBar.add(loadButton);
        
        saveButton = new Button("Save");
        saveButton.setStyleName(Style.toolbarButton);
        saveButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                saveButton.setEnabled(false);
                ArticleDto article = getValue();
                final Long draftId = article.getId();
                if(isDraft) {
                	article.setId(null);
                }
                articleService.save(article, new AsyncCallback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                    	if(isDraft && draftId != null) {
                        	articleService.deleteDraft(draftId, new AsyncCallback<Void>() {
								@Override
								public void onFailure(Throwable caught) {
									Window.alert("problem deleting the draft");
								}
								@Override
								public void onSuccess(Void result) {									
									ArticleEditorEntryPoint.goToPage(Constants.editorPage);
								}
                        	});
                        }
                    }
                    @Override
                    public void onFailure(Throwable caught) {
                        saveButton.setEnabled(true);
                        throw new RuntimeException("Problem saving ... " + caught.getMessage());
                    }
                });
            }
        });
        editorToolBar.add(saveButton);
        
        Button loadDraftButton = new Button("Load Draft");
        loadDraftButton.setStyleName(Style.toolbarButton);
        loadDraftButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                ArticleEditorEntryPoint.goToPage(Constants.editorPageWithDraftParam + articleIdLoader.getValue());
            }
        });
        editorToolBar.add(loadDraftButton);
        saveDraftButton = new Button("Save Draft");
        saveDraftButton.setStyleName(Style.toolbarButton);
        saveDraftButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                saveDraftButton.setEnabled(false);
                articleService.saveAsDraft(getValue(), new AsyncCallback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        ArticleEditorEntryPoint.goToPage(Constants.editorPage);
                    }
                    @Override
                    public void onFailure(Throwable caught) {
                        saveDraftButton.setEnabled(true);
                        throw new RuntimeException("Problem saving ... " + caught.getMessage());
                    }
                });
            }
        });
        editorToolBar.add(saveDraftButton);
        panel.add(editorToolBar);
    }
    
    public void setValue(ArticleDto article) {
        this.article = article;
        articleMetadata.setTitle(article);
        articleMetadata.setDescription(article);
        articleMetadata.setTags(article);
        articleMetadata.setAuthor(article);
        linkListInput.setValues(article.getLinks());
        contentListInput.setValues(article.getContents());
        getCorrections(article.getId());
    }
    
    private void getCorrections(final Long id) {
		if(id == null) {
			return;
		}
		articleService.getCorrections(id, new AsyncCallback<List<Correction>>() {
			@Override
			public void onFailure(Throwable caught) {
				throw new RuntimeException("Problem getting the corrections ... " + caught.getMessage());
			}
			@Override
			public void onSuccess(List<Correction> result) {
				contentListInput.updateWarnings(result);
				articleMetadata.setWarnings(result);
			}
		});
	}

	public ArticleDto getValue() {
        article.setTitle(articleMetadata.getTitle());
        article.setDescription(articleMetadata.getDescription());
        article.setKeywords(articleMetadata.getKeywords());
        article.setAuthor(articleMetadata.getAuthor());
        article.setLinks(linkListInput.getValues());
        article.setContents(contentListInput.getValues());
        return article;
    }
    
    private void setUserMessage(String message) {
        messagePanel.setVisible(true);
        messageLabel.setText(message);
    }

}
