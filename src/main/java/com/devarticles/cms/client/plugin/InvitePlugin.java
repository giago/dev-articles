package com.devarticles.cms.client.plugin;

import com.devarticles.cms.client.ArticleServiceAsync;
import com.devarticles.cms.client.view.widget.FieldInput;
import com.devarticles.cms.shared.Profile;
import com.devarticles.cms.shared.ServerException;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;

public class InvitePlugin extends OptionalPlugin {

    private FieldInput email;
    
    private FieldInput message;
    
    public InvitePlugin(ArticleServiceAsync articleService, final Profile profile) {
        super(articleService, profile, "Invite Sender plugin");
    }

    @Override
    protected void init() {
        email = new FieldInput("Email", "da-longInputField");
        content.add(email);

        message = new FieldInput("Message", "da-longInputField");
        content.add(message);
        
        Button button = new Button("Invite");
        button.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                try {
                    service.sendInvite(email.getValue(), message.getValue(), new AsyncCallback<Void>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            // TODO Auto-generated method stub
                        }
    
                        @Override
                        public void onSuccess(Void result) {
                            email.setValue("");
                            message.setValue("");
                        }
                    });
                } catch(ServerException se) {
                    //TODO
                }
            }
        });
        content.add(button);
    }
}
