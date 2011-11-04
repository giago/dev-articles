package com.devarticles.cms.client.view.widget;

import com.devarticles.cms.client.HtmlConstants;
import com.devarticles.cms.client.model.ContentDto;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

import java.util.Map.Entry;

public class ContentInput extends BaseInputField<ContentDto> {
	
	private LimitedTextArea textArea;
	private ListBox typeListBox;
	private Label collapsed;
	
	public ContentInput() {
		super();
		setStyleName(HtmlConstants.INPUT_CONTAINER);
	}
	
	@Override
	protected void initToolbar(FlowPanel toolbar) {
		typeListBox = new ListBox();
		typeListBox.setStyleName(HtmlConstants.SIGN_IN_INPUT);
		
		for(Entry<String, Integer> entry : ContentDto.ENTRIES.entrySet()) {
		    typeListBox.insertItem(entry.getKey(), entry.getKey(), entry.getValue());		    
		}
		typeListBox.setSelectedIndex(0);
		
		toolbar.add(typeListBox);
	}
	
	@Override
	protected void initContent(FlowPanel contentPanel) {
		textArea = new LimitedTextArea(500);
		contentPanel.add(textArea);
		collapsed = new Label();
		collapsed.addStyleName(HtmlConstants.PREVIEW_LABEL);
		collapsed.setVisible(false);
		contentPanel.add(collapsed);
	}
	
	public void addControl(Widget w) {
		toolbar.add(w);
	}

	@Override
	public ContentDto getValue() {
		String value = textArea.getValue(); 
		String typeValue = typeListBox.getValue(typeListBox.getSelectedIndex());
		return new ContentDto(value, typeValue);
	}
	
	@Override
	public void setValue(ContentDto model) {
		textArea.setValue(model.getData());
		typeListBox.setSelectedIndex(model.getType());
	}

	@Override
	protected void collapse() {
		if(textArea.isVisible()) {
			textArea.setVisible(false);
			collapsed.setText(textArea.getValue());
			collapsed.setVisible(true);
		} else {
			textArea.setVisible(true);
			collapsed.setVisible(false);
		}
	}

}
