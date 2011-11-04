package com.devarticles.cms.client.dialog;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class CenteredDialog extends DialogBox {
    
    private FlowPanel content;
    private FlowPanel toolbar;
    
    public CenteredDialog(String debugId) {
        setGlassEnabled(Boolean.TRUE);
        setAnimationEnabled(Boolean.TRUE);
        FlowPanel panel = new FlowPanel();
        content = new FlowPanel();
        content.setStyleName("dialogContent");
        toolbar = new FlowPanel();
        toolbar.setStyleName("dialogToolbar");
        panel.add(content);
        panel.add(toolbar);
        super.add(panel);
        ensureDebugId(debugId);
    }
    
    public Button addToolbarButton(Button w) {
        toolbar.add(w);
        return w;
    }
        
    public Button addToolbarButton(Button w, String debugId) {
        toolbar.add(w);
        w.ensureDebugId(debugId);
        return w;
    }
    
    @Override
    public void add(Widget w) {
        content.add(w);
    }
    
}
