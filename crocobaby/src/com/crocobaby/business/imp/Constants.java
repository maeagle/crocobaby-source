package com.crocobaby.business.imp;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Constants {

	@SuppressWarnings("serial")
	public static final Map<String, String> SHEET_DATASET = new HashMap<String, String>() {
		{
			put(SHEET_ZEREN, "2");
			put(SHEET_XINGJIABI, "3");
			put(SHEET_GANGGAN, "4");
		}
	};
	public static final String SHEET_XINGJIABI = "性价比得分";

	public static final String SHEET_ZEREN = "责任得分";

	public static final String SHEET_GANGGAN = "杠杆得分";

	public static final String SHEET_RATE = "表定费率";

	public static final Pattern REG_PERIOD = Pattern.compile("(\\d+)(.+)");

	public static final String CELL_INS_PERIOD_ALL_LIFE = "ALL";

	public static final String CELL_PAY_PERIOD_ONCE = "ONCE";

}
