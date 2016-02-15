package com.crocobaby.web.mobile.views;

import com.crocobaby.beans.ProductInfo;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.SwipeView;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by maeagle on 15/12/19.
 */
public class ProductDetailView extends NavigationView {

    /**
     * 日志管理.
     */
    private static Logger log = LoggerFactory.getLogger(InputView.class);

    /**
     * 页面主布局.
     */
    private VerticalLayout layout = new VerticalLayout();

    /**
     * 产品介绍标签.
     */
    private Label productIntroLabel = new Label();

    public ProductDetailView() {

        this.setCaption("产品详情");
        this.setWidth("100%");
        this.setHeight("100%");
        this.getNavigationBar().setWidth("100%");

        initComponent();
        this.setContent(layout);
    }

    /**
     * 初始化布局.
     */
    private void initComponent() {

        // 主布局配置
        layout.setStyleName("inputView");
        layout.setSpacing(true);

        VerticalLayout productIntroArea = buildProductIntroArea();
        layout.addComponent(productIntroArea);

    }

    /**
     * 构建产品介绍面板区域.
     *
     * @return the vertical layout
     */
    private VerticalLayout buildProductIntroArea() {
        VerticalLayout titleArea = new VerticalLayout();
        titleArea.setSpacing(true);

        productIntroLabel.setStyleName("scoreDashboard_productIntroContent");
        productIntroLabel.setContentMode(ContentMode.HTML);
        titleArea.addComponent(productIntroLabel);
        titleArea.setComponentAlignment(productIntroLabel, Alignment.MIDDLE_CENTER);
        titleArea.setExpandRatio(productIntroLabel, 1);

        return titleArea;
    }

    /**
     * 设置数据
     *
     * @param productInfo
     */
    public void setData(ProductInfo productInfo) {

        productIntroLabel.setValue(productInfo.getPro_desc());

    }


}
