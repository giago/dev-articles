package com.devarticles.cms.client.view.widget;


import com.devarticles.cms.client.HtmlConstants;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;

public class LimitedTextArea extends Composite {
	
	private FlowPanel panel;
	private TextArea textArea;
	private Label counter;
	private int limit;
	private boolean isValid = false;
	
	public LimitedTextArea(int limit) {
		this.limit = limit;
		panel = new FlowPanel();
		panel.setStyleName(HtmlConstants.LIMITED_TEXT_AREA_CONTAINER);
		textArea = new TextArea();
		textArea.setStyleName(HtmlConstants.LIMITED_TEXT_AREA);
		textArea.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				imposeLimit();
				updateCounter();
				checkStatus();
			}
		});
		textArea.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				imposeLimit();
				updateCounter();
				checkStatus();
			}
		});
		panel.add(textArea);

		counter = new Label(String.valueOf(this.limit));
		counter.setStyleName(HtmlConstants.LIMITED_TEXT_AREA_COUNTER);
		panel.add(counter);
		
		initWidget(panel);
	}
	
	private void imposeLimit(){
		if(getLenght() > limit) {
			textArea.setValue(getValue().substring(0, limit-1));
		}
	}
	
	private void updateCounter() {
		int charLeft = limit - getLenght();
		counter.setText(String.valueOf(charLeft));
	}
	
	private int getLenght() {
		return getValue().length();
	}
	
	public String getValue() {
		String value = textArea.getValue();
		if(value == null) {
			return "";
		}
		return value;
	}
	
	public void setValue(String value) {
		textArea.setValue(value);
	}
	
	public void reset() {
		textArea.setValue("");
		counter.setText(String.valueOf(limit));
	}
	
	private void setStatus(boolean isValid) {
		if(this.isValid == isValid) {
			return;
		}
		this.isValid = isValid;
	}
	
	private void checkStatus(){
		if(getLenght() > 0) {
			setStatus(true);
		} else {
			setStatus(false);
		}
	}

}
