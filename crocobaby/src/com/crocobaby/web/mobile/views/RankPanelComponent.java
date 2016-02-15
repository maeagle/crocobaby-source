package com.crocobaby.web.mobile.views;

import java.util.List;

import com.crocobaby.dao.EvaluationDashboardDAO;
import com.crocobaby.web.mobile.root.RootUI;
import com.vaadin.data.Property;
import com.vaadin.ui.*;
import com.vaadin.ui.Table.CellStyleGenerator;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.crocobaby.beans.RankInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Table.ColumnHeaderMode;

public class RankPanelComponent extends VerticalLayout {

    private Table table = new Table();

    private List<RankInfo> rankList;

    public RankPanelComponent(String titleString) {

        this.setWidth("100%");
        this.setHeightUndefined();
        this.setSpacing(true);
        this.setStyleName("scoreDashboard_layout");

        if (StringUtils.isNotBlank(titleString)) {
            Label title = new Label(titleString);
            title.setStyleName("scoreDashboard_rateCommentTitle");
            this.addComponent(title);
            this.setComponentAlignment(title, Alignment.MIDDLE_CENTER);
        }

        table.setSelectable(true);
        table.setImmediate(true);
        table.addValueChangeListener(e -> selectRow(e));
        table.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
        table.addContainerProperty("Rank", String.class, null);
        table.addContainerProperty("ProductName", String.class, null);
        table.addContainerProperty("Permium", String.class, null);
        table.setWidth("100%");
        table.setHeight("100px");
        table.setCellStyleGenerator((table, itemId, propertyId) -> {
            return "cell";
        });
        this.addComponent(table);
    }

    private void selectRow(Property.ValueChangeEvent e) {
        String productCode = null;
        try {
            productCode = rankList.get(Integer.parseInt(table.getValue().toString())).getPro_code();
            table.setValue(null);
        } catch (Exception e1) {
            return;
        }
        if(StringUtils.isNotEmpty(productCode)){
            RootUI ui = (RootUI) UI.getCurrent();
            ui.productDetailView.setData(EvaluationDashboardDAO.getProductDescription(productCode));
            ui.navManager.navigateTo(ui.productDetailView);
            ui.push();
        }
    }


    public void setData(List<RankInfo> rankList) {

        table.removeAllItems();
        this.rankList = null;
        if (rankList == null)
            return;
        this.rankList = rankList;
        for (int i = 0; i < rankList.size(); i++) {
            RankInfo rank = rankList.get(i);
            table.addItem(new Object[]{rank.getRak_rank(), rank.getPro_name(), rank.getRate_permium()}, i);
        }
        table.setPageLength(table.size());
        table.setHeightUndefined();
    }
}
