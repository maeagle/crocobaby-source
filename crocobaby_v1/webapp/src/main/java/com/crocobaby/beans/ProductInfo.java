package com.crocobaby.beans;

/**
 * @ClassName：ProductInfoBean
 * 
 * @author：马一博
 */
public class ProductInfo {

	/** 产品编号. */
	private String pro_code;

	/** 产品名称. */
	private String pro_name;

	/** 产品描述. */
	private String pro_desc;

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

	public String getPro_desc() {
		return pro_desc;
	}

	public void setPro_desc(String pro_desc) {
		this.pro_desc = pro_desc;
	}
}
