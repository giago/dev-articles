package com.devarticles.cms.client.view.widget;

import com.devarticles.cms.client.model.LinkDto;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class LinkInput extends BaseInputField<LinkDto> {

	private FieldInput description;
	private FieldInput title;
	private FieldInput url;
	private Anchor a;
	
	public LinkInput() {
		super();
	}
	
	public LinkDto getValue() {
		LinkDto link = new LinkDto();
		link.setDescription(description.getValue());
		link.setUrl(url.getValue());
		link.setTitle(title.getValue());
		return link;
	}
	
	public void setValue(LinkDto link) {
		description.setValue(link.getDescription());
		url.setValue(link.getUrl());
		title.setValue(link.getTitle());
	}

	@Override
	protected void initContent(FlowPanel contentPanel) {
		HorizontalPanel hp = new HorizontalPanel();
		hp.setStyleName("da-linkInputTitleAndDescription");
		title = new FieldInput("Title", "da-twoThirdInputField", new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				String value = description.getValue();
				if(value == null || value.length() == 0) {
					description.setValue(title.getValue());
				}
			}
		});
		hp.add(title);

		description = new FieldInput("Description", "da-twoThirdInputField");
		hp.add(description);
		contentPanel.add(hp);
		
		url = new FieldInput("Url", "da-longInputField");
		contentPanel.add(url);
		
		a = new Anchor();
		a.setVisible(false);
		contentPanel.add(a);
	}

	@Override
	protected void initToolbar(FlowPanel toolbar) {
	}

	@Override
	protected void collapse() {
		if(a.isVisible()) {
			a.setVisible(false);
			title.setVisible(true);
			description.setVisible(true);
			url.setVisible(true);
		} else {
			a.setText(title.getValue());
			a.setHref(url.getValue());
			a.setVisible(true);
			title.setVisible(false);
			description.setVisible(false);
			url.setVisible(false);
		}
	}
	
}
