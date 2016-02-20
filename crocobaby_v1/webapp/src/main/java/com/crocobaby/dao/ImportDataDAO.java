package com.crocobaby.dao;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crocobaby.beans.EvaluationInfo;
import com.crocobaby.beans.PeriodInfo;
import com.crocobaby.beans.RateInfo;
import com.crocobaby.frame.db.BaseDAO;
import com.crocobaby.frame.db.DataBaseManager;

/**
 * The Class ImportDataDAO.
 */
public class ImportDataDAO {

	/** 日志管理. */
	private static Logger log = LoggerFactory.getLogger(ImportDataDAO.class);

	/** 基础SQL操作类. */
	private static BaseDAO dao = new BaseDAO();

	/** 删除EVALUATION_INFO数据. */
	private static final String DELETE_EVALUATION_INFO = "delete from " + DataBaseManager.DB_INSTANCE
			+ "CB_EVALUATION_INFO where PRO_CODE = ? and VAL_TYPE = ?";

	/** 插入EVALUATION_INFO数据. */
	private static final String INSERT_EVALUATION_INFO = "INSERT INTO " + DataBaseManager.DB_INSTANCE
			+ "CB_EVALUATION_INFO"
			+ " (PKID, PRO_CODE, VAL_AGE, VAL_INS_PERIOD, VAL_PAY_PERIOD, VAL_SEX, VAL_TYPE, VAL_RATE)"
			+ " values (?, ?, ?, ?, ?, ?, ?, ?)";

	/** 删除RATE_INFO数据. */
	private static final String DELETE_RATE_INFO = "delete from " + DataBaseManager.DB_INSTANCE
			+ "cb_pro_rate_info where PRO_CODE = ?";

	/** 插入RATE_INFO数据. */
	private static final String INSERT_RATE_INFO = "INSERT INTO " + DataBaseManager.DB_INSTANCE + "cb_pro_rate_info"
			+ " (RATE_PKID, PRO_CODE, RATE_AGE, RATE_SEX, RATE_INS_PERIOD, RATE_PAY_PERIOD, RATE_PERMIUM)"
			+ " values (?, ?, ?, ?, ?, ?, ?)";

	/** 删除PERIOD_INFO数据. */
	private static final String DELETE_PERIOD_INFO = "delete from " + DataBaseManager.DB_INSTANCE
			+ "CB_PRO_PERIOD_INFO where PRO_CODE = ?";

	/** 插入CB_PRO_PERIOD_INFO数据. */
	private static final String INSERT_PERIOD_INFO = "INSERT INTO " + DataBaseManager.DB_INSTANCE + "CB_PRO_PERIOD_INFO"
			+ " (PKID, PRO_CODE, PERIOD_TYPE, PERIOD_VALUE, PERIOD_UNIT) VALUES (?,?,?,?,?)";

	/**
	 * 保存期间信息.
	 *
	 * @param periodInfoList
	 *            the period info list
	 */
	public static void savePeriodInfo(List<PeriodInfo> periodInfoList) {

		Object[][] params = new Object[periodInfoList.size()][5];
		for (int i = 0; i < periodInfoList.size(); i++) {
			PeriodInfo info = periodInfoList.get(i);
			params[i][0] = info.getPkid();
			params[i][1] = info.getPro_code();
			params[i][2] = info.getPeriod_type();
			params[i][3] = info.getPeriod_value();
			params[i][4] = info.getPeriod_unit();
		}
		try {
			QueryRunner qr = dao.createQueryRunner(true);
			qr.update(DELETE_PERIOD_INFO, periodInfoList.get(0).getPro_code());
			qr.batch(INSERT_PERIOD_INFO, params);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 批量插入评分数据.
	 *
	 * @param evaluationInfoList
	 *            the evaluation info list
	 */
	public static void saveEvaluationInfo(List<EvaluationInfo> evaluationInfoList) {
		Object[][] params = new Object[evaluationInfoList.size()][8];
		for (int i = 0; i < evaluationInfoList.size(); i++) {
			EvaluationInfo info = evaluationInfoList.get(i);
			params[i][0] = info.getPkid();
			params[i][1] = info.getPro_code();
			params[i][2] = info.getVal_age();
			params[i][3] = info.getVal_ins_period();
			params[i][4] = info.getVal_pay_period();
			params[i][5] = info.getVal_sex();
			params[i][6] = info.getVal_type();
			params[i][7] = info.getVal_rate();
		}
		try {
			QueryRunner qr = dao.createQueryRunner(true);
			qr.update(DELETE_EVALUATION_INFO, evaluationInfoList.get(0).getPro_code(),
					evaluationInfoList.get(0).getVal_type());
			qr.batch(INSERT_EVALUATION_INFO, params);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 批量插入费率数据.
	 *
	 * @param rateInfoList
	 *            the rate info list
	 */
	public static void saveRateInfo(List<RateInfo> rateInfoList) {
		Object[][] params = new Object[rateInfoList.size()][7];
		for (int i = 0; i < rateInfoList.size(); i++) {
			RateInfo info = rateInfoList.get(i);
			params[i][0] = info.getRate_pkid();
			params[i][1] = info.getPro_code();
			params[i][2] = info.getRate_age();
			params[i][3] = info.getRate_sex();
			params[i][4] = info.getRate_ins_period();
			params[i][5] = info.getRate_pay_period();
			params[i][6] = info.getRate_permium();
		}
		try {
			QueryRunner qr = dao.createQueryRunner(true);
			qr.update(DELETE_RATE_INFO, rateInfoList.get(0).getPro_code());
			qr.batch(INSERT_RATE_INFO, params);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
