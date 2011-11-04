package com.devarticles.cms.client.plugin;

import com.devarticles.cms.client.ArticleEditorEntryPoint;
import com.devarticles.cms.client.ArticleServiceAsync;
import com.devarticles.cms.client.view.widget.LimitedTextArea;
import com.devarticles.cms.shared.Profile;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class TwitterPlugin extends OptionalPlugin {
    
    private LimitedTextArea twittMessage;

    public TwitterPlugin(ArticleServiceAsync articleService, final Profile profile) {
        super(articleService, profile, "Twitter plugin");
    }
    
    @Override
    protected void init() {
        Button enableTwitter = new Button("Enable Twitter");
        enableTwitter.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                initTwitter(profile);
            }
        });
        content.add(enableTwitter);
    }
    
    private void initTwitter(Profile profile){
        content.clear();
        if(!profile.isTwitterAccountSet()) {
            service.getTwitterUrl(new AsyncCallback<String>() {
                @Override
                public void onFailure(Throwable caught) {
                    Window.alert("problem getting twitter url!");
                }

                @Override
                public void onSuccess(String result) {
                    ArticleEditorEntryPoint.goToPage(result, "twitter");
                    content.add(new TwitterPinForm() {
                        @Override
                        public void savePin(String pin) {
                            if(ArticleEditorEntryPoint.isDevMode()) {
                                Window.confirm("Dev mode, mocking twitter");
                                showTwittForm();
                            } else {
                                service.registerTwitterPin(pin, new AsyncCallback<Void>() {
                                    @Override
                                    public void onFailure(Throwable caught) {
                                        Window.alert("problem saving twitter pin!");
                                    }
                                    
                                    @Override
                                    public void onSuccess(Void result) {
                                        Window.confirm("Great you can twitt now");
                                        showTwittForm();
                                    }
                                });
                            }
                        }
                    });
                }
            });
        } else {
            showTwittForm();
        }
    }
    
    private void showTwittForm() {
        content.clear();
        FlowPanel form = new FlowPanel();
        twittMessage = new LimitedTextArea(160);
        Button submitTwitt = new Button("submit twitt");
        submitTwitt.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                service.twitt(twittMessage.getValue(), new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("sending message to twitter failed!");
                    }
                    @Override
                    public void onSuccess(Void result) {
                        Window.alert("message sent to twitter successful");
                    }
                });
            }
        });
        form.add(twittMessage);
        form.add(submitTwitt);
        content.add(form);
    }
    
    public abstract class TwitterPinForm extends Composite {

        private FlowPanel panel;
        private TextBox pin;
        private Button submit;
        
        public TwitterPinForm() {
            panel = new FlowPanel();
            panel.add(new Label("Please enter the twitter pin, if you have some kind of popup blocker please make sure to allow it"));
            pin = new TextBox();
            panel.add(pin);
            submit = new Button();
            submit.setText("Save the twitter pin");
            submit.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    savePin(pin.getText());
                }
            });
            panel.add(submit);
            initWidget(panel);
        }
        
        public abstract void savePin(String pin);
    }

    
}
