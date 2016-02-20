package com.crocobaby.web.mobile.views;

import com.crocobaby.beans.EvaluationInfo;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class EvaluationItemComponent extends VerticalLayout {

	private EvaluationInfo data;

	private Label scoreLabel = new Label();

	private Label titleLabel = new Label();

	private boolean isChecked = false;

	private LayoutClickListener onClickListener;

	public EvaluationItemComponent(LayoutClickListener onClickListener) {

		super();
		this.onClickListener = onClickListener;

		this.setWidth("100%");
		this.setHeight("100px");
		this.setSpacing(true);
		this.setStyleName("scoreDetail_uncheck");
		this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		this.addLayoutClickListener(e -> onClickhandler(e));

		scoreLabel.setStyleName("scoreDetail_scoreLabel_uncheck");
		scoreLabel.setImmediate(true);
		this.addComponent(scoreLabel);

		titleLabel.setStyleName("scoreDetail_titleLabel_uncheck");
		titleLabel.setImmediate(true);
		this.addComponent(titleLabel);

	}

	private void onClickhandler(LayoutClickEvent e) {

		if (isChecked) {
			uncheck();
		} else {
			this.setStyleName("scoreDetail_check");
			scoreLabel.setStyleName("scoreDetail_scoreLabel_check");
			titleLabel.setStyleName("scoreDetail_titleLabel_check");
			isChecked = true;
			onClickListener.layoutClick(e);
		}
	}

	public void uncheck() {
		this.setStyleName("scoreDetail_uncheck");
		scoreLabel.setStyleName("scoreDetail_scoreLabel_uncheck");
		titleLabel.setStyleName("scoreDetail_titleLabel_uncheck");
		isChecked = false;
	}

	public void setData(EvaluationInfo data) {
		this.data = data;
		for (int i = 0; i < data.getRankList().size(); i++) {
			if (data.getRankList().get(i).getPro_code().equals(data.getPro_code())) {
				scoreLabel.setValue(data.getRankList().get(i).getRak_rank());
				break;
			}
		}
		titleLabel.setValue(data.getVal_type_desc());
	}

	public EvaluationInfo getData() {
		return data;
	}
}
