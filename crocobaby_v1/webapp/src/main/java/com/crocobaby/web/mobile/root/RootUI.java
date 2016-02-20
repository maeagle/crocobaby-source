package com.crocobaby.web.mobile.root;

import com.crocobaby.web.mobile.views.InputView;
import com.crocobaby.web.mobile.views.EvaluationDashboard;
import com.crocobaby.web.mobile.views.ProductDetailView;
import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.ui.UI;

@Theme("crocobaby")
@Widgetset("com.crocobaby.web.mobile.widgetsets.CrocobabyWidgetset")
@Title("鳄鱼保保")
@Push(PushMode.MANUAL)
public class RootUI extends UI {

    public NavigationManager navManager = new NavigationManager();

    public InputView inputView = new InputView();

    public EvaluationDashboard scoreDashboard = new EvaluationDashboard();

    public ProductDetailView productDetailView = new ProductDetailView();

    @Override
    protected void init(VaadinRequest request) {

        navManager.setCurrentComponent(inputView);
        navManager.setNextComponent(scoreDashboard);
        setContent(navManager);
    }

    private void showNonMobileNotification() {
    }
}
