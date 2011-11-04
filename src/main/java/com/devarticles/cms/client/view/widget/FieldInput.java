package com.devarticles.cms.client.view.widget;

import com.devarticles.cms.client.ArticleEditorEntryPoint.Style;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class FieldInput extends Composite {
	
	private FlowPanel panel;
	
	private Label label;
	private Label warning;
	private FlowPanel warningPanel;
	private TextBox field;
	
	public FieldInput(String name) {
		this(name, null);
	}
	
	public FieldInput(String name, String styleClass) {
		this(name, styleClass, null);
	}
	
	public FieldInput(String name, String styleClass, ChangeHandler changeHandler) {
	    panel = new FlowPanel();
        label = new Label(name + " : ");
        warningPanel = new FlowPanel();
        warningPanel.addStyleName(Style.correction);
        warning = new Label();
        warningPanel.add(warning);
        warningPanel.setVisible(false);
        field = new TextBox();
        if(changeHandler != null) {        	
        	field.addChangeHandler(changeHandler);
        }
        if(styleClass != null) {
            panel.setStyleName(styleClass);
        }
        panel.add(label);
        panel.add(warningPanel);
        panel.add(field);
        initWidget(panel);
	}
	
	public void setValue(String value) {
		field.setValue(value);
	}
	
	public String getValue() {
		return field.getValue();
	}

	public void showWarning(String correction) {
		if(correction != null && correction.length() > 0) {
			warning.setText(correction);
			warningPanel.setVisible(true);
		}
	}

}
