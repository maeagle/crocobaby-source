package com.crocobaby.web.desktop.root;

import com.vaadin.addon.touchkit.ui.NumberField;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

//@Theme("crocobaby")
@PreserveOnRefresh
@Title("鳄鱼保保")
public class RootUI extends UI {

	private VerticalLayout layout;

	private ComboBox productInfoCombo;

	private NumberField ageField;

	@Override
	protected void init(VaadinRequest request) {
		initComponent();
		this.setContent(layout);
	}

	private void initComponent() {

		layout = new VerticalLayout();
		layout.setSizeFull();

		ageField = new NumberField("输入年龄");
		layout.addComponent(ageField);

		productInfoCombo = new ComboBox("选择产品");
		productInfoCombo.addItem("The Sun");
		productInfoCombo.addItem("The Moon");
		layout.addComponent(productInfoCombo);
	}
}
