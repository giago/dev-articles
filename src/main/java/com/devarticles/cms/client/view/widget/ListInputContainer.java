package com.devarticles.cms.client.view.widget;

import java.util.ArrayList;
import java.util.List;

import com.devarticles.cms.client.model.ModelDto;
import com.devarticles.cms.shared.Correction;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public abstract class ListInputContainer<V extends BaseInputField<M>, M extends ModelDto> extends Composite {

	private FlowPanel panel;
	private List<InputWrapper<V>> inputWrappers = new ArrayList<InputWrapper<V>>();
	
	public ListInputContainer(String title) {
		FlowPanel container = new FlowPanel();
		container.setStyleName("da-listInputContainer");
		Label titleLabel = new Label(title);
		titleLabel.setStyleName("da-listInputContainerTitle");
		container.add(titleLabel);
		panel = new FlowPanel();
		container.add(panel);
		container.add(createToolbar(title));
		initWidget(container);
	}

	public void init() {
		panel.clear();
		inputWrappers = new ArrayList<InputWrapper<V>>();
		panel.add(createInputField());
	}
	
	
	public List<M> getValues() {
		List<M> models = new ArrayList<M>();
		for(InputWrapper<V> w : inputWrappers) {
			models.add(w.getValue());
		}
		return models;
	}
	
	public void setValues(List<M> models) {
		if(models == null || models.isEmpty()) {
			return;
		}
		panel.clear();
		inputWrappers = new ArrayList<InputWrapper<V>>();
		for(M m : models) {
			InputWrapper<V> wrapper = createInputFieldWrapper();
			wrapper.setValue(m);
			wrapper.setWarningId(getWarningId(m));
			add(wrapper);
		}
	}
	
	protected String getWarningId(M m) {
		return null;
	}

	private InputWrapper<V> createInputFieldWrapper() {
		V v = createInputField();
		InputWrapper<V> wrapper = new InputWrapper<V>(v) {
			public void moveDown(InputWrapper<V> w) {
				int index = inputWrappers.lastIndexOf(w);
				if(inputWrappers.size() > index) {
					inputWrappers.add(index+1, w);
				}
			};
			public void moveUp(InputWrapper<V> w) {
				int index = inputWrappers.lastIndexOf(w);
				inputWrappers.add(index-1, w);
			};
			public void remove(InputWrapper<V> w) {
				inputWrappers.remove(w);
			};
		};
		return wrapper;
	}
	
	public abstract V createInputField();
	
	public void updateWarnings(List<Correction> corrections) {
		for(InputWrapper<V> v : inputWrappers) {
			v.updateWarnings(corrections);
		}
	}
	
	private Widget createToolbar(String title) {
		FlowPanel toolbar = new FlowPanel();
		toolbar.setStyleName("da-listInputToolbar");
		Button addButton = new Button("Add " + title);
		addButton.setStyleName("da-listInputAddButton");
		addButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				add(createInputFieldWrapper());
			}
		});
		toolbar.add(addButton);
		return toolbar;
	}
	
	private void add(InputWrapper<V> w) {
		panel.add(w);
		inputWrappers.add(w);
	}

	@SuppressWarnings("hiding")
	public abstract class InputWrapper<V extends BaseInputField<M>> extends Composite {
		private FlowPanel panel;
		private V v;
		public InputWrapper(V v) {
			this.v = v;
			panel = new FlowPanel();
			panel.setStyleName("da-inputWrapper");
			FlowPanel toolbar = new FlowPanel();
			toolbar.setStyleName("da-inputWrapperToolbar");
			//toolbar.add(getMoveUpButton());
			//toolbar.add(getMoveDownButton());
			toolbar.add(getRemoveButton());
			panel.add(toolbar);
			panel.add(v);
			initWidget(panel);
		}
		
//		private Widget getMoveDownButton() {
//			Button btn = new Button("move down");
//			btn.addClickHandler(new ClickHandler() {
//				@Override
//				public void onClick(ClickEvent event) {
//					moveDown(InputWrapper.this);
//				}
//			});
//			return btn;
//		}
//
//		private Widget getMoveUpButton() {
//			Button btn = new Button("move up");
//			btn.addClickHandler(new ClickHandler() {
//				@Override
//				public void onClick(ClickEvent event) {
//					moveUp(InputWrapper.this);
//				}
//			});
//			return btn;
//		}

		private Widget getRemoveButton() {
			Button btn = new Button("remove");
			btn.setStyleName("da-listInputButton");
			btn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					remove(InputWrapper.this);
					ListInputContainer.this.panel.remove(InputWrapper.this);
				}
			});
			return btn;
		}

		public M getValue() {
			return v.getValue();
		}
		
		public void setValue(M m) {
			v.setValue(m);
		}
		
		public void setWarningId(String id) {
			v.setWarningId(id);
		}
		
		public void updateWarnings(List<Correction> corrections) {
			v.updateWarning(corrections);
		}
		
		public abstract void remove(InputWrapper<V> w);
		
		public abstract void moveUp(InputWrapper<V> w); 

		public abstract void moveDown(InputWrapper<V> w);
	}
	
}
