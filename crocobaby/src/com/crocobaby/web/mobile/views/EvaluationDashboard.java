package com.crocobaby.web.mobile.views;

import com.crocobaby.beans.EvaluationInfo;
import com.crocobaby.beans.ProductInfo;
import com.crocobaby.beans.RankInfo;
import com.crocobaby.dao.EvaluationDashboardDAO;
import com.vaadin.addon.touchkit.ui.SwipeView;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class ScoreDashboard.
 */
public class EvaluationDashboard extends SwipeView {

	/** 页面主布局. */
	private VerticalLayout layout = new VerticalLayout();

	/** 产品名称标签. */
	private Label productNameLabel = new Label();

	/** 产品总排名标签. */
	private Label productRankLabel = new Label();

	/** 产品各指标项布局. */
	private RankPanelComponent productRankPanel = new RankPanelComponent("");

	/** 产品各指标项布局. */
	private RankPanelComponent subRankPanel = new RankPanelComponent("指标排名");

	/** 各指标项排名展示板. */
	private HorizontalLayout evalItemsLayout = new HorizontalLayout();

	/** 各指标项排名明细. */
	private List<EvaluationItemComponent> evalItemList = new ArrayList<EvaluationItemComponent>();

	/**
	 * 构造方法.
	 */
	public EvaluationDashboard() {
		super();
		initComponent();
		this.setContent(layout);
	}

	/**
	 * 初始化布局.
	 */
	private void initComponent() {

		layout.setSpacing(false);

		VerticalLayout titleArea = buildTitleArea();
		layout.addComponent(titleArea);

		layout.addComponent(productRankPanel);

		evalItemsLayout.setHeightUndefined();
		evalItemsLayout.setWidth("100%");
		evalItemsLayout.setImmediate(true);
		layout.addComponent(evalItemsLayout);
		layout.addComponent(subRankPanel);

	}

	/**
	 * 构建标题区域.
	 *
	 * @return the vertical layout
	 */
	private VerticalLayout buildTitleArea() {

		VerticalLayout titleArea = new VerticalLayout();
		titleArea.setStyleName("scoreDashboard_title");
		titleArea.setSpacing(true);

		productNameLabel.setValue("");
		productNameLabel.setStyleName("scoreDashboard_productLabel");
		titleArea.addComponent(productNameLabel);
		titleArea.setComponentAlignment(productNameLabel, Alignment.TOP_LEFT);

		HorizontalLayout totalScoreLayout = new HorizontalLayout();
		totalScoreLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		totalScoreLayout.setStyleName("scoreDashboard_scoreFrame");
		totalScoreLayout.setWidth("130px");
		totalScoreLayout.setHeight("130px");

		Label text_1_Label = new Label();
		text_1_Label.setValue("第");
		text_1_Label.setStyleName("scoreDashboard_scoreTextLabel");
		totalScoreLayout.addComponent(text_1_Label);

		productRankLabel.setValue("");
		productRankLabel.setStyleName("scoreDashboard_scoreLabel");
		totalScoreLayout.addComponent(productRankLabel);

		Label text_2_Label = new Label();
		text_2_Label.setValue("名");
		text_2_Label.setStyleName("scoreDashboard_scoreTextLabel");
		totalScoreLayout.addComponent(text_2_Label);

		titleArea.addComponent(totalScoreLayout);
		titleArea.setComponentAlignment(totalScoreLayout, Alignment.MIDDLE_CENTER);

		 Label blankLabel = new Label("", ContentMode.HTML);
		 titleArea.addComponent(blankLabel);
		return titleArea;
	}

	/**
	 * 填充数据.
	 *
	 * @param formData
	 *            the form data
	 * @return true, if successful
	 */
	public boolean setData(EvaluationInfo formData) {

		evalItemList.clear();
		evalItemsLayout.removeAllComponents();

		/*
		 * 查询产品综合排名列表，并对相应组件进行数据填充
		 */
		List<RankInfo> productRankList = EvaluationDashboardDAO.getProductRank(formData);
		productRankPanel.setData(productRankList);
		RankInfo productRankInfo = null;
		for (int i = 0; i < productRankList.size(); i++) {
			if (productRankList.get(i).getPro_code().equals(formData.getPro_code())) {
				productRankInfo = productRankList.get(i);
				break;
			}
		}
		productRankLabel.setValue(productRankInfo.getRak_rank());
		String val = Double
				.toString((Double.parseDouble(productRankInfo.getRak_rank()) / productRankList.size()) * 100);

		ProductInfo productBean = EvaluationDashboardDAO.getProductDescription(formData.getPro_code());
		productNameLabel.setValue(productBean.getPro_name());

		/*
		 * 查询产品指标列表
		 */
		List<EvaluationInfo> evalItems = EvaluationDashboardDAO.getEvaluationItems(formData);
		if (evalItems == null || evalItems.size() == 0)
			return false;

		for (EvaluationInfo info : evalItems) {
			List<RankInfo> rankList = EvaluationDashboardDAO.getProductSubRank(info);
			info.setRankList(rankList);
			EvaluationItemComponent scoreDetail = new EvaluationItemComponent(new LayoutClickListener() {
				@Override
				public void layoutClick(LayoutClickEvent event) {
					evaluationDetailOnClick(event);
				}
			});
			scoreDetail.setData(info);
			evalItemList.add(scoreDetail);
			evalItemsLayout.addComponent(scoreDetail);
		}
		return true;
	}

	/**
	 * 评估明细面板点击事件.
	 *
	 * @param e
	 *            the e
	 */
	private void evaluationDetailOnClick(LayoutClickEvent e) {
		EvaluationItemComponent selectCom = (EvaluationItemComponent) e.getSource();
		for (EvaluationItemComponent com : evalItemList) {
			if (com.getData().equals(selectCom.getData()))
				continue;
			com.uncheck();
		}
		subRankPanel.setData(selectCom.getData().getRankList());
	}
}
