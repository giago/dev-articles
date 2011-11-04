package com.devarticles.cms.client.view.widget;

import java.util.List;

import com.devarticles.cms.client.ArticleEditorEntryPoint.Style;
import com.devarticles.cms.shared.Correction;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class Warning extends Composite {
	
	private FlowPanel panel;
	private String id;
	private Label correction;
	
	public Warning() {
		panel = new FlowPanel();
		panel.addStyleName(Style.correction);
		panel.setVisible(false);
		correction = new Label();
		panel.add(correction);
		initWidget(panel);
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public void update(List<Correction> corrections) {
		if(id == null) {
			return;
		}
		for(Correction c : corrections) {
			if(c.getCorrection() != null && c.getCorrection().length() > 0 
					&& id.equals(c.getSource())) {
				correction.setText(c.getCorrection());
				panel.setVisible(true);
				return;
			}
		}
	}
	
}
