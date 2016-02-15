/**
 * @ClassName：RelationDataBaseManager
 * @Description�?
 * 
 * @author：马�?�?
 * @修改时间�?2015-9-18 14:08:45
 * @修改内容�?
 */
package com.crocobaby.frame.db;

import java.sql.Connection;

import javax.sql.DataSource;

import org.logicalcobwebs.proxool.ProxoolDataSource;

/**
 * 关系型数据库连接管理工具
 */
public class RelationDataBaseManager {

	/** 数据源 */
	private static DataSource dataSource = null;

	/**
	 * 初始化RDS连接
	 */
	static void initRds() {
		if (dataSource == null) {
			String dbConnectType = DataBaseManager.dbConfig.get("rds.type").toString();
			if ("jdbcd".equalsIgnoreCase(dbConnectType)) {
				JdbcDataSource jdbcDs = new JdbcDataSource();
				jdbcDs.setDriverClass(DataBaseManager.dbConfig.get("rds.driver-class").toString());
				jdbcDs.setUrl(DataBaseManager.dbConfig.get("rds.driver-url").toString());
				jdbcDs.setUserName(DataBaseManager.dbConfig.get("rds.username").toString());
				jdbcDs.setPassword(DataBaseManager.dbConfig.get("rds.password").toString());
				dataSource = jdbcDs;
			} else if ("proxool".equalsIgnoreCase(dbConnectType)) {
				ProxoolDataSource proxoolDs = new ProxoolDataSource();
				proxoolDs.setDriver(DataBaseManager.dbConfig.get("rds.driver-class").toString());
				proxoolDs.setDriverUrl(DataBaseManager.dbConfig.get("rds.driver-url").toString());
				proxoolDs.setUser(DataBaseManager.dbConfig.get("rds.username").toString());
				proxoolDs.setPassword(DataBaseManager.dbConfig.get("rds.password").toString());
				proxoolDs.setHouseKeepingTestSql(
						DataBaseManager.dbConfig.get("rds.proxool.house-keeping-test-sql").toString());
				proxoolDs.setMaximumConnectionCount(Integer
						.parseInt(DataBaseManager.dbConfig.get("rds.proxool.maximum-connection-count").toString()));
				dataSource = proxoolDs;
			} else {
				throw new RuntimeException("The dbConnectType is invalid!");
			}
		}
	}

	/**
	 * 获取当前连接的数据源.
	 *
	 * @return the data source
	 */
	public static DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * 根据当前数据源，获取数据库连接
	 *
	 * @return the connection
	 * @throws Exception
	 *             the exception
	 */
	public static Connection getConnection() throws Exception {
		return dataSource.getConnection();
	}

}
