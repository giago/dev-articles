package com.devarticles.cms.client.plugin;

import java.util.ArrayList;
import java.util.List;

import com.devarticles.cms.client.ArticleServiceAsync;
import com.devarticles.cms.client.ArticleEditorEntryPoint.Constants;
import com.devarticles.cms.client.ArticleEditorEntryPoint.Style;
import com.devarticles.cms.client.model.ArticleDto;
import com.devarticles.cms.client.model.ContentDto;
import com.devarticles.cms.client.view.widget.LimitedTextArea;
import com.devarticles.cms.shared.Correction;
import com.devarticles.cms.shared.Correction.Type;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class CorrectorPlugin extends Composite {
	
	private ArticleServiceAsync articleService;
	private FlowPanel source;
	private FlowPanel correction;
	private int currentCorrection = -1;
	private HTML sourceInput;
	private Label messageBoard;
	private Label status;
	private LimitedTextArea correctionInput;
	private List<Correction> corrections = new ArrayList<Correction>();
	
	public CorrectorPlugin(ArticleServiceAsync articleService) {
		this.articleService = articleService;
		FlowPanel panel = new FlowPanel();
        panel.setStyleName(Style.correctorPlugin);
        source = new FlowPanel();
        source.setStyleName(Style.correctorPluginSource);
        sourceInput = new HTML();
        source.add(sourceInput);
        correction = new FlowPanel();
        correction.setStyleName(Style.correctorPluginCorrection);
        correctionInput = new LimitedTextArea(500);
        correction.add(correctionInput);
        Button next = new Button("Next");
        next.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				next();
			}
        });
        Button finish = new Button("Finish");
        finish.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				save();
			}
        });
        panel.add(source);
        HorizontalPanel hp = new HorizontalPanel();
        hp.addStyleName(Style.correctorToolBar);
        hp.add(next);
        status = new Label();
        status.addStyleName(Style.correctorToolBarStatus);
        hp.add(status);
        hp.add(finish);
        panel.add(hp);
        panel.add(correction);
        messageBoard = new Label();
        panel.add(messageBoard);
        initWidget(panel);
        initArticle();
	}
	
	private void initArticle() {
        String draftUrl = Window.Location.getParameter(Constants.draftUrlParameter);
        if(draftUrl != null && draftUrl.length()>0) {
            articleService.getDraft("" + draftUrl, new AsyncCallback<ArticleDto>() {
                @Override
                public void onSuccess(ArticleDto result) {
                	extractInfoFromArticle(result);
                }
				@Override
                public void onFailure(Throwable caught) {
					messageBoard.setText("Problem loading article : " + caught.getMessage());
                }
            });
        }
        String articleUrl = Window.Location.getParameter(Constants.articleUrlParameter);
        if(articleUrl != null && articleUrl.length()>0) {
        	articleService.get("" + articleUrl, new AsyncCallback<ArticleDto>() {
                @Override
                public void onSuccess(ArticleDto result) {
                	extractInfoFromArticle(result);
                }
				@Override
                public void onFailure(Throwable caught) {
					messageBoard.setText("Problem loading article : " + caught.getMessage());
                }
            });
        }
	}
	
	private void extractInfoFromArticle(ArticleDto result) {
		if(result != null) {
        	corrections.clear();
        	corrections.add(new Correction(
        			result.getId(), result.getTitle(), Type.title));
        	corrections.add(new Correction(result.getId(), result.getDescription(), 
        			Type.description));
        	for(ContentDto c : result.getContents()) {
        		if(c.getType() == 0 || c.getType() == 13 || c.getType() == 14 ||
        				c.getType() == 17 || c.getType() == 16 || c.getType() == 18) {                    			
        			corrections.add(new Correction(result.getId(), c.getData()));
        		}
        	}
        	loadPreviousCorrections(result.getId());
        } else {
        	messageBoard.setText("Problem loading article, is null");
        }
	}
	
	private void loadPreviousCorrections(final Long articleId) {
		articleService.getCorrections(articleId, new AsyncCallback<List<Correction>>() {
			@Override
			public void onFailure(Throwable caught) {
				messageBoard.setText("Problem loading previous corrections : " + caught.getMessage());
			}

			@Override
			public void onSuccess(List<Correction> result) {
				for (Correction p : result) { 
					for(Correction c : corrections) {
						if(p.getSource().equals(c.getSource())) {
							c.setCorrection(p.getCorrection());
							c.setId(p.getId());
						}
					}
				}
				next();
			}
		});
	}
	
	private void next() {
		if(corrections.isEmpty()) {
			messageBoard.setText("Nothing to check here");
			return;
		}
		if(currentCorrection >= 0 && currentCorrection < corrections.size()) {
			Correction c = corrections.get(currentCorrection);
			c.setCorrection(correctionInput.getValue());
			currentCorrection++;
		} else {
			currentCorrection = 0;
		}
		loadCorrection(corrections.get(currentCorrection));
		status.setText((currentCorrection+1) + "/" + corrections.size());
	}
	
	private void loadCorrection(Correction c) {
		sourceInput.setHTML(c.getSource());
		correctionInput.setValue(c.getCorrection());
	}
	
	private void save() {
		articleService.save(corrections, new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				messageBoard.setText("Problem saving corrections : " + caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				messageBoard.setText("Corrections saved");
			}
		});
	}

}
