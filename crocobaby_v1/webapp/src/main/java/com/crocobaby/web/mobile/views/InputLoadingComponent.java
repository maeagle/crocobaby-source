package com.crocobaby.web.mobile.views;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;

public class InputLoadingComponent extends AbsoluteLayout {

	/** logo. */
	private Image logoImage = new Image();

	private Label blankLabel = new Label();

	private ComponentPosition imagePos = new ComponentPosition();

	private ComponentPosition backgroundPos = new ComponentPosition();

	private int step = 17;

	public InputLoadingComponent() {
		initComponent();
	}

	private void initComponent() {

		this.setWidth("66px");
		this.setHeight("136px");

		logoImage.setSource(new ThemeResource("img/input_logo.png"));
		logoImage.setWidth("66px");
		logoImage.setHeight("136px");
		this.addComponent(logoImage);
		imagePos.setCSSString("left:0px;top:0px;z-index:1");
		this.setPosition(logoImage, imagePos);

		blankLabel.setWidth("66px");
		blankLabel.addStyleName("inputView_loadingBack");
		blankLabel.setHeight("0px");
		blankLabel.setImmediate(true);
		this.addComponent(blankLabel);
		backgroundPos.setCSSString("left:0px;top:136px;z-index:0");
		this.setPosition(blankLabel, backgroundPos);
	}

	public boolean loading() {

		if (blankLabel.getHeight() + step > 136)
			return false;

		backgroundPos.setTop(backgroundPos.getTopValue() - step, backgroundPos.getTopUnits());
		blankLabel.setHeight(blankLabel.getHeight() + step, blankLabel.getHeightUnits());
		blankLabel.markAsDirty();

		if (blankLabel.getHeight() + step >= 136)
			return false;

		return true;
	}

	public void resetComponent() {
		backgroundPos.setTop(136f, backgroundPos.getTopUnits());
		this.setPosition(blankLabel, backgroundPos);
		blankLabel.setHeight(0f, blankLabel.getHeightUnits());
	}

}
