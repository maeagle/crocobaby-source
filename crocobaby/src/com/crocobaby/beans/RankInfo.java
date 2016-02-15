/*
 * @Author: maeagle
 * @Date: 2015-11-15
 */
package com.crocobaby.beans;

/**
 * The Class RankInfo.
 */
public class RankInfo {

	/** 产品编号. */
	private String pro_code;

    private String pro_name;

	private String rak_rank;

	private String rate_permium;

	public String getPro_code() {
		return pro_code;
	}

	public void setPro_code(String pro_code) {
		this.pro_code = pro_code;
	}

	public String getPro_name() {
		return pro_name;
	}

	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}

	public String getRak_rank() {
		return rak_rank;
	}

	public void setRak_rank(String rak_rank) {
		this.rak_rank = rak_rank;
	}

    public String getRate_permium() {
        return rate_permium;
    }

    public void setRate_permium(String rate_permium) {
        this.rate_permium = rate_permium;
    }
}
