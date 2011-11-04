package com.devarticles.cms.client.view.widget;

import java.util.List;

import com.devarticles.cms.client.model.ModelDto;
import com.devarticles.cms.shared.Correction;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public abstract class BaseInputField<M extends ModelDto> extends Composite {
	
	protected FlowPanel toolbar;
	private FlowPanel panel;
	private Warning warning;
	
	//private boolean collapsed = false;
	
	public BaseInputField() {
		panel = new FlowPanel();
		
		toolbar = new FlowPanel();
		toolbar.setStyleName("da-contentInputToolBar");
		initToolbar(toolbar);
		panel.add(toolbar);
		
		prepareStandardControls();
		
		warning = new Warning();
		panel.add(warning);
		
		FlowPanel contentPanel = new FlowPanel();
		contentPanel.setStyleName("da-cotentInputContent");
		initContent(contentPanel);
		panel.add(contentPanel);
		
		initWidget(panel);
	}
	
	private void prepareStandardControls() {
		final Button collapse = new Button("Collapse");
		collapse.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if("Collapse".equals(collapse.getText())) {
					collapse.setText("Expand");
				} else {
					collapse.setText("Collapse");	
				}
				collapse();
			}
		});
		toolbar.add(collapse);
	}

	public abstract M getValue();

	public abstract void setValue(M model);
	
	public void setWarningId(String id) {
		warning.setId(id);
	}
	
	public void updateWarning(List<Correction> corrections) {
		warning.update(corrections);
	}
	
	protected abstract void initToolbar(FlowPanel toolbar);
	
	protected abstract void initContent(FlowPanel contentPanel);

	protected abstract void collapse();
	
}
