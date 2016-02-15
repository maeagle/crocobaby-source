package com.crocobaby.frame.db;

import java.util.Properties;

import com.crocobaby.frame.common.CommonUtil;

public class DataBaseManager {

	/** 配置文件. */
	private static final String DB_CONFIG_PROPERTIES = "db.properties";

	public static String DB_INSTANCE = "";

	/** 数据库连接相关的配置文件. */
	static Properties dbConfig = CommonUtil.initProperty(DB_CONFIG_PROPERTIES);

	public static void initDataBase() throws Exception {
		RelationDataBaseManager.initRds();
		DB_INSTANCE = dbConfig.getProperty("rds.instance");
	}
}
