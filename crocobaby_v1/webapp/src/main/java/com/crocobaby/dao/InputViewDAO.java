package com.crocobaby.dao;

import java.util.List;

import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crocobaby.frame.db.BaseDAO;
import com.crocobaby.frame.db.DataBaseManager;

/**
 * The Class ProductInfoDAO.
 */
public class InputViewDAO {

    /**
     * 日志管理.
     */
    private static Logger log = LoggerFactory.getLogger(InputViewDAO.class);

    /**
     * 基础SQL操作类.
     */
    private static BaseDAO dao = new BaseDAO();

    /**
     * 获取产品列表信息的SQL.
     */
    private static final String GET_COMPANY_SELECT_INFO = "SELECT CPBI.COM_CODE,CPBI.COM_NAME FROM "
            + DataBaseManager.DB_INSTANCE + "CB_COMPANY_INFO CPBI where " +
            "exists(select T.PRO_CODE from " + DataBaseManager.DB_INSTANCE + "CB_PRO_BASE_INFO T" +
            " where T.COM_CODE = CPBI.COM_CODE and T.PRO_ON_SALE='Y')";


    /**
     * 获取产品列表信息的SQL.
     */
    private static final String GET_PRODUCT_SELECT_INFO = "SELECT CPBI.PRO_CODE,CPBI.PRO_NAME FROM "
            + DataBaseManager.DB_INSTANCE + "CB_PRO_BASE_INFO CPBI where CPBI.PRO_ON_SALE='Y'";

    /**
     * 获取产品列表信息的SQL.
     */
    private static final String GET_PRODUCT_SELECT_COM_INFO = "SELECT CPBI.PRO_CODE,CPBI.PRO_NAME FROM "
            + DataBaseManager.DB_INSTANCE + "CB_PRO_BASE_INFO CPBI where CPBI.PRO_ON_SALE='Y' and CPBI.COM_CODE=?";

    /**
     * 获取缴费期间，保险期间描述的SQL.
     */
    private static final String GET_PERIOD_SELECT_INFO = "select concat(ifnull(CPPI.PERIOD_VALUE,''), CPPI.PERIOD_UNIT) as id,"
            + " if(CDI.DIC_CODE='TO_Y',replace(CDI.DIC_DESC,'#',ifnull(CPPI.PERIOD_VALUE,'')), concat(ifnull(CPPI.PERIOD_VALUE,''), CDI.DIC_DESC)) as name"
            + " from " + DataBaseManager.DB_INSTANCE + "CB_PRO_PERIOD_INFO CPPI inner join "
            + DataBaseManager.DB_INSTANCE
            + "CB_DICT_INFO CDI on CPPI.PERIOD_UNIT= CDI.DIC_CODE and CDI.DIC_GROUP= 'PERIOD_UNIT'"
            + " where CPPI.PRO_CODE=? and CPPI.PERIOD_TYPE=? order by CDI.SEQ, (CPPI.PERIOD_VALUE+0)";


    public static List<Object[]> getCompanySelectInfo() {
        try {
            return dao.createQueryRunner(true).query(GET_COMPANY_SELECT_INFO, new ArrayListHandler());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 获取产品列表信息.
     *
     * @return the product select info
     */
    public static List<Object[]> getProductSelectInfo(String companyCode) {
        try {
            return dao.createQueryRunner(true).query(GET_PRODUCT_SELECT_COM_INFO, new ArrayListHandler(), companyCode);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 获取产品列表信息.
     *
     * @return the product select info
     */
    public static List<Object[]> getProductSelectInfo() {
        try {
            return dao.createQueryRunner(true).query(GET_PRODUCT_SELECT_INFO, new ArrayListHandler());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 获取缴费期间，保险期间描述.
     *
     * @param productCode 产品编号
     * @param periodType  期间类型
     * @return the period select info
     */
    public static List<Object[]> getPeriodSelectInfo(String productCode, String periodType) {
        try {
            return dao.createQueryRunner(true).query(GET_PERIOD_SELECT_INFO, new ArrayListHandler(), productCode,
                    periodType);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}