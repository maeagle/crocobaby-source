/*
 * @Author: maeagle
 * @Date: 2015-11-15
 */
package com.crocobaby.dao;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crocobaby.beans.EvaluationInfo;
import com.crocobaby.beans.ProductInfo;
import com.crocobaby.beans.RankInfo;
import com.crocobaby.frame.db.BaseDAO;
import com.crocobaby.frame.db.DataBaseManager;

/**
 * The Class EvaluationInfoDAO.
 */
public class EvaluationDashboardDAO {

    /**
     * 日志管理.
     */
    private static Logger log = LoggerFactory.getLogger(EvaluationDashboardDAO.class);

    /**
     * 基础SQL操作类.
     */
    private static BaseDAO dao = new BaseDAO();

    /**
     * 获取产品描述信息的SQL.
     */
    private static String GET_PRODUCT_INFO = "SELECT CPBI.PRO_NAME,CPBI.PRO_DESC FROM " + DataBaseManager.DB_INSTANCE
            + "CB_PRO_BASE_INFO CPBI where CPBI.PRO_CODE=?";

    /**
     * 查询排名的描述信息的SQL.
     */
    private static String GET_RANK_DESC = "select CDI.DIC_DESC from " + DataBaseManager.DB_INSTANCE
            + "CB_DICT_INFO CDI where"
            + " CDI.DIC_GROUP='EVAL_DESC' and (?+0) >= (CDI.DIC_CODE + 0) order by CDI.DIC_CODE desc limit 1";

    /**
     * 获取评估维度列表的SQL.
     */
    private static String GET_EVALUATION_ITEMS = "select CEI.PRO_CODE, CEI.VAL_AGE, CEI.VAL_SEX, CEI.VAL_INS_PERIOD, CEI.VAL_PAY_PERIOD, CEI.VAL_TYPE,"
            + " CDI.DIC_DESC as val_type_desc, CDI_PAR.DIC_DESC as val_rate_desc from " + DataBaseManager.DB_INSTANCE
            + "CB_EVALUATION_INFO CEI inner join " + DataBaseManager.DB_INSTANCE
            + "CB_DICT_INFO CDI on CEI.VAL_TYPE= CDI.DIC_CODE and CDI.DIC_GROUP= 'EVAL_TYPE' inner join "
            + DataBaseManager.DB_INSTANCE
            + "CB_DICT_INFO CDI_PAR on CEI.VAL_TYPE= CDI_PAR.DIC_CODE and CDI_PAR.DIC_GROUP= 'EVAL_TYPE_DESC' "
            + "where CEI.PRO_CODE=? and CEI.VAL_SEX=? and CEI.VAL_AGE=? and CEI.VAL_INS_PERIOD=? and CEI.VAL_PAY_PERIOD=?";

    /**
     * 获取产品的各个评分类型的排名情况.
     */
    private static String GET_PRODUCT_RANK_LIST = "select CRI_1.PRO_CODE, CPBI.PRO_NAME, CRI_1.RAK_RANK, CONCAT(CPRI.RATE_PERMIUM,'元') as RATE_PERMIUM from "
            + DataBaseManager.DB_INSTANCE + "CB_RANK_INFO CRI_1" + " inner join " + DataBaseManager.DB_INSTANCE
            + "CB_RANK_INFO CRI_2 on CRI_1.RAK_AGE= CRI_2.RAK_AGE and CRI_1.RAK_SEX= CRI_2.RAK_SEX and CRI_2.RAK_TYPE is null"
            + " and CRI_1.RAK_INS_PERIOD=CRI_2.RAK_INS_PERIOD and CRI_1.RAK_PAY_PERIOD=CRI_2.RAK_PAY_PERIOD"
            + " inner join " + DataBaseManager.DB_INSTANCE + "CB_PRO_BASE_INFO CPBI on CRI_1.PRO_CODE=CPBI.PRO_CODE"
            + " inner join " + DataBaseManager.DB_INSTANCE
            + "CB_PRO_RATE_INFO CPRI on CPRI.PRO_CODE= CRI_1.PRO_CODE"
            + " and CPRI.RATE_AGE= CRI_2.RAK_AGE and CPRI.RATE_SEX= CRI_2.RAK_SEX and CPRI.RATE_INS_PERIOD= CRI_2.RAK_INS_PERIOD and CPRI.RATE_PAY_PERIOD= CRI_2.RAK_PAY_PERIOD"
            + " where CRI_2.PRO_CODE=? and CRI_2.RAK_SEX=? and CRI_2.RAK_AGE=? and CRI_2.RAK_INS_PERIOD=? and CRI_2.RAK_PAY_PERIOD=? and CRI_1.RAK_TYPE is null"
            + " order by CRI_1.RAK_RANK";

    /**
     * 获取产品的各个评分类型的排名情况.
     */
    private static String GET_PRODUCT_SUB_RANK_LIST = "select CRI_1.PRO_CODE, CPBI.PRO_NAME, CRI_1.RAK_RANK, CONCAT(CPRI.RATE_PERMIUM,'元') as RATE_PERMIUM from "
            + DataBaseManager.DB_INSTANCE + "CB_RANK_INFO CRI_1 inner join " + DataBaseManager.DB_INSTANCE
            + "CB_RANK_INFO CRI_2 on CRI_1.RAK_AGE= CRI_2.RAK_AGE and CRI_1.RAK_SEX= CRI_2.RAK_SEX"
            + " and CRI_1.RAK_INS_PERIOD=CRI_2.RAK_INS_PERIOD and CRI_1.RAK_PAY_PERIOD=CRI_2.RAK_PAY_PERIOD"
            + " and CRI_1.RAK_TYPE=CRI_2.RAK_TYPE and CRI_1.RAK_RANK<=CRI_2.RAK_RANK inner join "
            + DataBaseManager.DB_INSTANCE + "CB_PRO_BASE_INFO CPBI" + " on CRI_1.PRO_CODE=CPBI.PRO_CODE"
            + " inner join " + DataBaseManager.DB_INSTANCE
            + "CB_PRO_RATE_INFO CPRI on CPRI.PRO_CODE= CRI_1.PRO_CODE"
            + " and CPRI.RATE_AGE= CRI_2.RAK_AGE and CPRI.RATE_SEX= CRI_2.RAK_SEX and CPRI.RATE_INS_PERIOD= CRI_2.RAK_INS_PERIOD and CPRI.RATE_PAY_PERIOD= CRI_2.RAK_PAY_PERIOD"
            + " where CRI_2.PRO_CODE=? and CRI_2.RAK_TYPE=? and CRI_2.RAK_SEX=? and CRI_2.RAK_AGE=? and CRI_2.RAK_INS_PERIOD=? and CRI_2.RAK_PAY_PERIOD=?"
            + " order by CRI_1.RAK_RANK limit 10";

    /**
     * 获取产品总排名.（全量）
     *
     * @param info the info
     * @return 排在该产品前面的排名列表
     */
    public static List<RankInfo> getProductRank(EvaluationInfo info) {
        try {

            return dao.createQueryRunner(true).query(GET_PRODUCT_RANK_LIST,
                    new BeanListHandler<RankInfo>(RankInfo.class), info.getPro_code(), info.getVal_sex(),
                    info.getVal_age(), info.getVal_ins_period(), info.getVal_pay_period());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 获取产品各项指标排名.（排在该产品前面的排名列表）
     *
     * @param info the info
     * @return 排在该产品前面的排名列表
     */
    public static List<RankInfo> getProductSubRank(EvaluationInfo info) {
        try {
            return dao.createQueryRunner(true).query(GET_PRODUCT_SUB_RANK_LIST,
                    new BeanListHandler<RankInfo>(RankInfo.class), info.getPro_code(), info.getVal_type(),
                    info.getVal_sex(), info.getVal_age(), info.getVal_ins_period(), info.getVal_pay_period());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 获取评估指标的列表
     *
     * @param info 查询条件封装
     * @return the score list
     */
    public static List<EvaluationInfo> getEvaluationItems(EvaluationInfo info) {
        try {
            return dao.createQueryRunner(true).query(GET_EVALUATION_ITEMS,
                    new BeanListHandler<EvaluationInfo>(EvaluationInfo.class), info.getPro_code(), info.getVal_sex(),
                    info.getVal_age(), info.getVal_ins_period(), info.getVal_pay_period());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 获取产品的描述.
     *
     * @param pro_code the pro_code
     * @return the score description
     */
    public static ProductInfo getProductDescription(String pro_code) {
        try {
            return dao.createQueryRunner(true).query(GET_PRODUCT_INFO, new BeanHandler<ProductInfo>(ProductInfo.class),
                    pro_code);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 获取排名的描述.
     *
     * @param rankNo 排名
     * @return the score description
     */
    public static String getRankDescription(String rankNo) {
        try {
            return dao.createQueryRunner(true).query(GET_RANK_DESC, new ScalarHandler<String>(1), rankNo);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
