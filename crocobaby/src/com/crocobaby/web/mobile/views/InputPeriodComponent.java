package com.crocobaby.web.mobile.views;

import java.util.List;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.NativeSelect;

/**
 * 缴费期间，保险期间选择区域.
 */
public class InputPeriodComponent extends HorizontalLayout {

	/** 期间类型：1.保险期间；2.缴费期间. */
	private String periodType;

	/** 标题图片. */
	private Image titleImg = new Image();

	/** 信息选择. */
	private NativeSelect periodSelect = new NativeSelect();

	/**
	 * 构造方法.
	 *
	 * @param periodType
	 *            期间类型
	 */
	public InputPeriodComponent(String periodType) {
		super();
		this.periodType = periodType;
		this.setHeightUndefined();
		this.setSpacing(true);
		this.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		initComponent();
	}

	/**
	 * 组件初始化.
	 */
	private void initComponent() {

		if ("2".equals(periodType)) {
			titleImg.setAlternateText("缴费期间");
			titleImg.setSource(new ThemeResource("img/input_pay_period_title.png"));
		} else {
			titleImg.setAlternateText("保险期间");
			titleImg.setSource(new ThemeResource("img/input_ins_period_title.png"));
		}
		titleImg.setWidth("86px");
		titleImg.setHeight("20px");
		this.addComponent(titleImg);

		periodSelect.setMultiSelect(false);
		periodSelect.setWidth("140px");
		periodSelect.setHeight("30px");
		periodSelect.setStyleName("periodUnitSelect");
		periodSelect.setNullSelectionAllowed(false);
		periodSelect.setImmediate(true);
		this.addComponent(periodSelect);
	}

	/**
	 * 清除下拉框数据.
	 */
	public void clearData() {
		periodSelect.clear();
		periodSelect.removeAllItems();
	}

	/**
	 * 设置下拉框的启用状态.
	 *
	 * @param enable
	 *            the new select enabled
	 */
	public void setSelectEnabled(boolean enable) {
		periodSelect.setEnabled(enable);
	}

	/**
	 * 对下拉框中的数据进行赋值.
	 *
	 * @param dataSet
	 *            the new data
	 */
	public void setData(List<Object[]> dataSet) {

		if (dataSet == null)
			return;
		for (Object[] record : dataSet) {
			periodSelect.addItem(record[0]);
			periodSelect.setItemCaption(record[0], record[1].toString());
		}
		periodSelect.setValue(dataSet.get(0)[0]);
	}

	/**
	 * Gets the select value.
	 *
	 * @return the select value
	 */
	public String getSelectValue() {
		return periodSelect.getValue().toString();
	}
}
