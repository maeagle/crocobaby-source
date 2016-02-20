package com.crocobaby.beans;

/**
 * The Class RateInfo.
 */
public class RateInfo {

	/** The rate_pkid. */
	private String rate_pkid;

	/** 产品编号. */
	private String pro_code;

	/** 年龄. */
	private String rate_age;

	/** 性别 */
	private String rate_sex;

	/** 保险期间. */
	private String rate_ins_period;

	/** 缴费期间. */
	private String rate_pay_period;

	/** 保费. */
	private String rate_permium;

	public String getRate_pkid() {
		return rate_pkid;
	}

	public void setRate_pkid(String rate_pkid) {
		this.rate_pkid = rate_pkid;
	}

	public String getPro_code() {
		return pro_code;
	}

	public void setPro_code(String pro_code) {
		this.pro_code = pro_code;
	}

	public String getRate_age() {
		return rate_age;
	}

	public void setRate_age(String rate_age) {
		this.rate_age = rate_age;
	}

	public String getRate_sex() {
		return rate_sex;
	}

	public void setRate_sex(String rate_sex) {
		this.rate_sex = rate_sex;
	}

	public String getRate_permium() {
		return rate_permium;
	}

	public void setRate_permium(String rate_permium) {
		this.rate_permium = rate_permium;
	}

	public String getRate_ins_period() {
		return rate_ins_period;
	}

	public void setRate_ins_period(String rate_ins_period) {
		this.rate_ins_period = rate_ins_period;
	}

	public String getRate_pay_period() {
		return rate_pay_period;
	}

	public void setRate_pay_period(String rate_pay_period) {
		this.rate_pay_period = rate_pay_period;
	}

}
