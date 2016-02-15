package com.crocobaby.frame.log;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import com.crocobaby.frame.common.CommonUtil;

public class LogManager {

	/** 配置文件. */
	private static final String LOG_CONFIG_PROPERTIES = "log4j.properties";

	/** 数据库连接相关的配置文件. */
	static Properties logConfig = CommonUtil.initProperty(LOG_CONFIG_PROPERTIES);

	public static void initLog() throws Exception{
		PropertyConfigurator.configure(logConfig);
	}
}
