package com.crocobaby.beans;

/**
 * The Class PeriodInfo.
 */
public class PeriodInfo {

	/** 主键. */
	private String pkid;

	/** 产品编号. */
	private String pro_code;

	/** 期间类型. */
	private String period_type;

	/** 值. */
	private String period_value;

	/** 单位. */
	private String period_unit;

	public String getPkid() {
		return pkid;
	}

	public void setPkid(String pkid) {
		this.pkid = pkid;
	}

	public String getPro_code() {
		return pro_code;
	}

	public void setPro_code(String pro_code) {
		this.pro_code = pro_code;
	}

	public String getPeriod_type() {
		return period_type;
	}

	public void setPeriod_type(String period_type) {
		this.period_type = period_type;
	}

	public String getPeriod_value() {
		return period_value;
	}

	public void setPeriod_value(String period_value) {
		this.period_value = period_value;
	}

	public String getPeriod_unit() {
		return period_unit;
	}

	public void setPeriod_unit(String period_unit) {
		this.period_unit = period_unit;
	}

}
