package com.crocobaby.beans;

import java.io.Serializable;
import java.util.List;

/**
 * The Class EvaluationInfo.
 */
public class EvaluationInfo implements Serializable {

	private String pkid;

	/** 产品编号. */
	private String pro_code;

	/** 年龄. */
	private String val_age;

	/** 保险期间. */
	private String val_ins_period;

	/** 缴费期间. */
	private String val_pay_period;

	/** 性别. */
	private String val_sex;

	/** 评分类型. */
	private String val_type;

	/** 评分类型描述. */
	private String val_type_desc;

	/** 评分解读. */
	private String val_rate_desc;

	private String val_rate;

	private List<RankInfo> rankList;

	public String getPro_code() {
		return pro_code;
	}

	public void setPro_code(String pro_code) {
		this.pro_code = pro_code;
	}

	public String getVal_age() {
		return val_age;
	}

	public void setVal_age(String val_age) {
		this.val_age = val_age;
	}

	public String getVal_ins_period() {
		return val_ins_period;
	}

	public void setVal_ins_period(String val_ins_period) {
		this.val_ins_period = val_ins_period;
	}

	public String getVal_pay_period() {
		return val_pay_period;
	}

	public void setVal_pay_period(String val_pay_period) {
		this.val_pay_period = val_pay_period;
	}

	public String getVal_sex() {
		return val_sex;
	}

	public void setVal_sex(String val_sex) {
		this.val_sex = val_sex;
	}

	public String getVal_type() {
		return val_type;
	}

	public void setVal_type(String val_type) {
		this.val_type = val_type;
	}

	public String getVal_type_desc() {
		return val_type_desc;
	}

	public void setVal_type_desc(String val_type_desc) {
		this.val_type_desc = val_type_desc;
	}

	public String getVal_rate_desc() {
		return val_rate_desc;
	}

	public void setVal_rate_desc(String val_rate_desc) {
		this.val_rate_desc = val_rate_desc;
	}

	public String getPkid() {
		return pkid;
	}

	public void setPkid(String pkid) {
		this.pkid = pkid;
	}

	public List<RankInfo> getRankList() {
		return rankList;
	}

	public void setRankList(List<RankInfo> rankList) {
		this.rankList = rankList;
	}

	public String getVal_rate() {
		return val_rate;
	}

	public void setVal_rate(String val_rate) {
		this.val_rate = val_rate;
	}
}
