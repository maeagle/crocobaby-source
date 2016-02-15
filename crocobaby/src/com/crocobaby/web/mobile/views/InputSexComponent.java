package com.crocobaby.web.mobile.views;

import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;

// TODO: Auto-generated Javadoc
/**
 * The Class SexChooseComponent.
 */
public class InputSexComponent extends HorizontalLayout {

	/** The male check source. */
	private static ThemeResource maleCheckSource = new ThemeResource("img/input_sex_male_check.png");

	/** The male uncheck source. */
	private static ThemeResource maleUncheckSource = new ThemeResource("img/input_sex_male_uncheck.png");

	/** The female check source. */
	private static ThemeResource femaleCheckSource = new ThemeResource("img/input_sex_female_check.png");

	/** The female uncheck source. */
	private static ThemeResource femaleUncheckSource = new ThemeResource("img/input_sex_female_uncheck.png");

	/** The male radio. */
	Image maleRadio = new Image();

	/** The female radio. */
	Image femaleRadio = new Image();

	/** The select sex. */
	private String selectSex = "M";

	/**
	 * Instantiates a new sex choose component.
	 */
	public InputSexComponent() {

		this.setHeightUndefined();
		this.setSpacing(true);
		this.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);

		maleRadio.setWidth("60px");
		maleRadio.setHeight("30px");
		femaleRadio.setAlternateText("男");
		maleRadio.setSource(maleCheckSource);
		maleRadio.addClickListener(new ClickListener() {
			@Override
			public void click(ClickEvent event) {
				radioOnclick();

			}
		});
		this.addComponent(maleRadio);
		femaleRadio.setWidth("60px");
		femaleRadio.setHeight("30px");
		femaleRadio.setAlternateText("女");
		femaleRadio.setSource(femaleUncheckSource);
		femaleRadio.addClickListener(new ClickListener() {
			@Override
			public void click(ClickEvent event) {
				radioOnclick();

			}
		});
		this.addComponent(femaleRadio);
	}

	/**
	 * Radio onclick.
	 */
	private void radioOnclick() {
		if (selectSex.equals("M")) {
			selectSex = "F";
			maleRadio.setSource(maleUncheckSource);
			femaleRadio.setSource(femaleCheckSource);
		} else {
			selectSex = "M";
			maleRadio.setSource(maleCheckSource);
			femaleRadio.setSource(femaleUncheckSource);
		}
	}

	/**
	 * Gets the select sex.
	 *
	 * @return the select sex
	 */
	public String getSelectValue() {
		return selectSex;
	}

}
