package com.crocobaby.web.mobile.views;

import java.util.List;

import com.vaadin.data.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crocobaby.beans.EvaluationInfo;
import com.crocobaby.dao.InputViewDAO;
import com.crocobaby.web.mobile.root.RootUI;
import com.vaadin.addon.touchkit.ui.NumberField;
import com.vaadin.addon.touchkit.ui.SwipeView;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * 信息录入页面.
 */
public class InputView extends SwipeView {

    /**
     * 日志管理.
     */
    private static Logger log = LoggerFactory.getLogger(InputView.class);

    /**
     * 页面主布局.
     */
    private VerticalLayout layout = new VerticalLayout();

    /**
     * 年龄输入框.
     */
    private NumberField ageField = new NumberField();

    /**
     * 公司选择下拉框.
     */
    private NativeSelect companySelect = new NativeSelect();

    /**
     * 产品选择下拉框.
     */
    private NativeSelect productSelect = new NativeSelect();

    /**
     * 性别输入组件.
     */
    private InputSexComponent sexChoose = new InputSexComponent();

    /**
     * 缴费期间选择框.
     */
    private InputPeriodComponent payPeriodInput = new InputPeriodComponent("2");

    /**
     * 保险期间选择框.
     */
    private InputPeriodComponent insPeriodInput = new InputPeriodComponent("1");

    /**
     * 按钮.
     */
    private Image startImage = new Image();

    /**
     * The loading logo.
     */
    private InputLoadingComponent loadingLogo = new InputLoadingComponent();

    Property.ValueChangeListener productSelectListener= (e -> selectProduct(e.getProperty().getValue().toString()));

    /**
     * 构造方法.
     */
    public InputView() {
        super();
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

        // 标题区域
        VerticalLayout titleArea = buildTitleArea();
        layout.addComponent(titleArea);
        layout.setComponentAlignment(titleArea, Alignment.TOP_CENTER);


        // 公司选择区域
        NativeSelect companySelectArea = buildCompanySelectArea();
        layout.addComponent(companySelectArea);
        layout.setComponentAlignment(companySelectArea, Alignment.TOP_CENTER);

        // 产品选择区域
        NativeSelect productSelectArea = buildProductSelectArea();
        layout.addComponent(productSelectArea);
        layout.setComponentAlignment(productSelectArea, Alignment.TOP_CENTER);

        // 保险期间选择区域
        layout.addComponent(insPeriodInput);
        layout.setComponentAlignment(insPeriodInput, Alignment.TOP_CENTER);

        // 缴费期间选择区域
        layout.addComponent(payPeriodInput);
        layout.setComponentAlignment(payPeriodInput, Alignment.TOP_CENTER);

        // 年龄录入区域
        HorizontalLayout ageInputArea = buildAgeInputArea();
        layout.addComponent(ageInputArea);
        layout.setComponentAlignment(ageInputArea, Alignment.TOP_CENTER);

        // 性别选择区域
        layout.addComponent(sexChoose);
        layout.setComponentAlignment(sexChoose, Alignment.TOP_CENTER);

        // 按钮区域
        buildStartButtonArea();
        layout.addComponent(startImage);
        layout.setComponentAlignment(startImage, Alignment.TOP_CENTER);

        // logo区域
        HorizontalLayout logoArea = buildLogoArea();
        layout.addComponent(logoArea);
        layout.setComponentAlignment(logoArea, Alignment.TOP_RIGHT);

    }

    /**
     * 构建标题区域.
     *
     * @return the vertical layout
     */
    private VerticalLayout buildTitleArea() {
        VerticalLayout titleArea = new VerticalLayout();
        titleArea.setHeight("40px");
        return titleArea;
    }

    /**
     * 构建公司选择区域.
     *
     * @return the native select
     */
    private NativeSelect buildCompanySelectArea() {

        companySelect.setStyleName("companySelect");
        companySelect.addItem("-1");
        companySelect.setItemCaption("-1", "选择保险公司");

        List<Object[]> companySelectInfo = InputViewDAO.getCompanySelectInfo();
        if (companySelectInfo != null) {
            for (Object[] arr : companySelectInfo) {
                companySelect.addItem(arr[0]);
                companySelect.setItemCaption(arr[0], arr[1].toString());
            }
        }
        companySelect.setNullSelectionAllowed(false);
        companySelect.setImmediate(true);
        companySelect.setWidth("270px");
        companySelect.setHeight("35px");
        companySelect.addValueChangeListener(e -> selectCompany(e.getProperty().getValue().toString()));
        companySelect.setValue("-1");

        return companySelect;
    }

    /**
     * 构建产品选择区域.
     *
     * @return the native select
     */
    private NativeSelect buildProductSelectArea() {


        productSelect.setStyleName("productSelect");
        productSelect.addItem("-1");
        productSelect.setItemCaption("-1", "选择保险产品");
        List<Object[]> productSelectInfo = InputViewDAO.getProductSelectInfo();
        if (productSelectInfo != null) {
            for (Object[] arr : productSelectInfo) {
                productSelect.addItem(arr[0]);
                productSelect.setItemCaption(arr[0], arr[1].toString());
            }
        }
        productSelect.setNullSelectionAllowed(false);
        productSelect.setImmediate(true);
        productSelect.setWidth("270px");
        productSelect.setHeight("35px");
        productSelect.addValueChangeListener(productSelectListener);
        productSelect.setValue("-1");
        return productSelect;
    }

    /**
     * 构建年龄录入区域.
     *
     * @return the horizontal layout
     */
    private HorizontalLayout buildAgeInputArea() {

        HorizontalLayout ageInputArea = new HorizontalLayout();
        ageInputArea.setHeightUndefined();
        ageInputArea.setSpacing(true);
        ageInputArea.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);

        Image ageTitleImage = new Image();
        ageTitleImage.setAlternateText("投保年龄:");
        ageTitleImage.setSource(new ThemeResource("img/input_age_title.png"));
        ageTitleImage.setWidth("86px");
        ageTitleImage.setHeight("20px");
        ageInputArea.addComponent(ageTitleImage);

        ageField.setWidth("60px");
        ageField.setHeight("30px");
        ageField.setMaxLength(3);
        ageField.setImmediate(true);
        ageInputArea.addComponent(ageField);

        Image ageUnitImage = new Image();
        ageUnitImage.setAlternateText("岁");
        ageUnitImage.setSource(new ThemeResource("img/input_age_unit.png"));
        ageUnitImage.setWidth("18px");
        ageUnitImage.setHeight("20px");
        ageInputArea.addComponent(ageUnitImage);

        Label blankLabel = new Label("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", ContentMode.HTML);
        ageInputArea.addComponent(blankLabel);

        return ageInputArea;
    }

    /**
     * 构建按钮区域.
     *
     * @return the image
     */
    private Image buildStartButtonArea() {

        startImage.setAlternateText("开始评测");
        startImage.setSource(new ThemeResource("img/input_start_button.png"));
        startImage.setWidth("80px");
        startImage.setHeight("80px");
        startImage.addClickListener(e -> startEvaluationOnClick());
        return startImage;
    }

    /**
     * 构建logo区域.
     *
     * @return the image
     */
    private HorizontalLayout buildLogoArea() {

        HorizontalLayout logoArea = new HorizontalLayout();
        logoArea.setHeightUndefined();
        logoArea.setSpacing(true);
        logoArea.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);

        logoArea.addComponent(loadingLogo);

        Label blankLabel = new Label("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", ContentMode.HTML);
        logoArea.addComponent(blankLabel);

        return logoArea;
    }

    /**
     * 选择公司时，触发的事件.
     *
     * @param value the value
     */
    private void selectCompany(String value) {

        List<Object[]> selectInfo = null;
        if ("-1".equals(value)) {
            selectInfo = InputViewDAO.getProductSelectInfo();

        } else {
            selectInfo = InputViewDAO.getProductSelectInfo(value);
        }
        if (selectInfo != null) {
            productSelect.removeValueChangeListener(productSelectListener);
            productSelect.removeAllItems();
            productSelect.addItem("-1");
            productSelect.setItemCaption("-1", "选择保险产品");
            for (Object[] arr : selectInfo) {
                productSelect.addItem(arr[0]);
                productSelect.setItemCaption(arr[0], arr[1].toString());
            }
            productSelect.setValue("-1");
            productSelect.addValueChangeListener(productSelectListener);
        }
    }

    /**
     * 选择产品时，触发的事件.
     *
     * @param value the value
     */
    private void selectProduct(String value) {

        insPeriodInput.clearData();
        payPeriodInput.clearData();
        ageField.clear();

        if ("-1".equals(value)) {
            setInputEnabled(false);
        } else {
            setInputEnabled(true);
            insPeriodInput.setData(InputViewDAO.getPeriodSelectInfo(value, "1"));
            payPeriodInput.setData(InputViewDAO.getPeriodSelectInfo(value, "2"));
        }
    }

    /**
     * 设定整体面板可用状态.
     *
     * @param enable the new input enabled
     */
    public void setInputEnabled(boolean enable) {
        ageField.setEnabled(enable);
        sexChoose.setEnabled(enable);
        insPeriodInput.setSelectEnabled(enable);
        payPeriodInput.setSelectEnabled(enable);
        startImage.setEnabled(enable);
    }

    /**
     * 重置整体面板.
     */
    public void resetComponent() {
        insPeriodInput.clearData();
        payPeriodInput.clearData();
        ageField.clear();
        startImage.setSource(new ThemeResource("img/input_start_button.png"));
        loadingLogo.resetComponent();
    }

    /**
     * 验证表单值的有效性.
     *
     * @return true, if successful
     */
    private boolean validateForm() {
        if ("-1".equals(productSelect.getValue())) {
            Notification.show("快选择一款保险产品吖...", Type.HUMANIZED_MESSAGE);
            return false;
        }
        if (insPeriodInput.getSelectValue() == null || "".equals(insPeriodInput.getSelectValue())) {
            Notification.show("保多久？选一个[保险期间]试试先...", Type.HUMANIZED_MESSAGE);
            return false;
        }

        if (payPeriodInput.getSelectValue() == null || "".equals(insPeriodInput.getSelectValue())) {
            Notification.show("想怎么交保费，选一个[缴费期间]试试先...", Type.HUMANIZED_MESSAGE);
            return false;
        }

        if (ageField.isEmpty()) {
            Notification.show("请输入[投保年龄]", Type.HUMANIZED_MESSAGE);
            return false;
        }
        if ("".equals(sexChoose.getSelectValue())) {
            Notification.show("请选择[性别]...", Type.HUMANIZED_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * 将页面数据构建为可查询的数据集.
     *
     * @return the evaluation info
     */
    private EvaluationInfo buildFormDataSet() {
        EvaluationInfo eval = new EvaluationInfo();
        eval.setPro_code(productSelect.getValue().toString());
        eval.setVal_age(ageField.getValue());
        eval.setVal_ins_period(insPeriodInput.getSelectValue());
        eval.setVal_pay_period(payPeriodInput.getSelectValue());
        eval.setVal_sex(sexChoose.getSelectValue());
        return eval;
    }

    /**
     * 开始评测.
     */
    private void startEvaluationOnClick() {

        if (!validateForm())
            return;
        Thread evalThread = new Thread(new StartEvaluationThread());
        evalThread.start();
    }

    /**
     * 评测后业务逻辑处理的子线程.
     */
    class StartEvaluationThread implements Runnable {

        /**
         * 当前面板.
         */
        private RootUI ui = (RootUI) UI.getCurrent();

        @Override
        public void run() {
            ui.access(new Runnable() {
                @Override
                public void run() {
                    setInputEnabled(false);
                    ui.push();
                    try {
                        // loading一下
                        loadingLogo.loading();
                        ui.push();
                        // 拼装查询参数
                        EvaluationInfo queryCon = buildFormDataSet();
                        // loading一下
                        loadingLogo.loading();
                        ui.push();
                        // 设置ScoreDashboard数据
                        if (!ui.scoreDashboard.setData(queryCon)) {
                            Notification.show("您的投保年龄无法购买该产品！", Type.HUMANIZED_MESSAGE);
                            loadingLogo.resetComponent();
                            setInputEnabled(true);
                            ui.push();
                            return;
                        }
                        // 执行查询
                        loadingLogo.loading();
                        ui.push();
                        // 全部loading掉
                        while (loadingLogo.loading()) {
                            ui.push();
                            Thread.sleep(500);
                        }
                    } catch (Exception e) {
                        Notification.show("评测出现错误。请重试...", Type.ERROR_MESSAGE);
                        loadingLogo.resetComponent();
                        setInputEnabled(true);
                        ui.push();
                        log.error(e.getMessage(), e);
                        return;
                    }
                    setInputEnabled(true);
                    loadingLogo.resetComponent();
                    ui.navManager.navigateTo(ui.scoreDashboard);
                    ui.push();
                }
            });
        }
    }
}
