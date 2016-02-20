package com.crocobaby.frame.db;

import java.beans.PropertyDescriptor;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseDAO {

	/** 日志管理. */
	private static Logger log = LoggerFactory.getLogger(BaseDAO.class);

	/**
	 * 创建QueryRunner工具类.
	 * 
	 * 大量操作都可以通过这个对象搞定。
	 * 
	 * 如果想执行事务性操作，则要将withConnection置为false，自己通过DataSourceManager申请可回滚性连接。
	 * 
	 * @param withConnection
	 *            是否自带一个数据库连接
	 * @return the query runner
	 */
	public QueryRunner createQueryRunner(boolean withConnection) {
		if (withConnection)
			return new QueryRunner(RelationDataBaseManager.getDataSource());
		return new QueryRunner();
	}

	/**
	 * 将Bean的数据列表，以Insert方式插入数据库.
	 * 
	 * @param <T>
	 * 
	 * @param beanList
	 *            Bean数据列表
	 * @param tableName
	 *            表名
	 * @return true, if successful
	 */
	public <T> boolean insert(List<T> beanList, String tableName) {

		if (beanList == null || beanList.size() == 0) {
			log.error("The beanList is null", new NullPointerException());
			return false;
		}
		if (StringUtils.isBlank(tableName)) {
			log.error("The tableName is null", new NullPointerException());
			return false;
		}
		PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(beanList.get(0).getClass());

		String sql = buildInsertSql(descriptors, tableName);
		Object[][] params = buildParamList(descriptors, beanList);

		try {
			createQueryRunner(true).batch(sql, params);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 根据Bean的属性信息，Bean数据列表，构建参数列表.
	 * 
	 * @param <T>
	 * 
	 * @param descriptors
	 *            Bean的属性信息
	 * @param beanList
	 *            Bean数据列表
	 * @return the object[][]
	 */
	private <T> Object[][] buildParamList(PropertyDescriptor[] descriptors, List<T> beanList) {
		Object[][] values = new Object[descriptors.length][];
		try {
			for (int i = 0; i < beanList.size(); i++) {
				Object bean = beanList.get(i);
				Object[] record = new Object[descriptors.length];
				for (int j = 0; j < descriptors.length; j++) {
					record[j] = descriptors[j].getReadMethod().invoke(bean, new Object[0]);
				}
				values[i] = record;
			}
			return values;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 根据Bean的属性信息构建Insert语句.
	 * 
	 * @param descriptors
	 *            Bean的属性信息
	 * @param tableName
	 *            表名
	 * @return SQL语句
	 */
	private String buildInsertSql(PropertyDescriptor[] descriptors, String tableName) {

		StringBuffer colSentence = new StringBuffer();
		colSentence.append("insert into ");
		colSentence.append(tableName);
		colSentence.append(" (");

		StringBuffer paraSentence = new StringBuffer();
		paraSentence.append("values (");

		for (PropertyDescriptor desc : descriptors) {
			colSentence.append(desc.getDisplayName().toUpperCase());
			colSentence.append(",");
			paraSentence.append("?,");
		}
		colSentence.deleteCharAt(colSentence.lastIndexOf(","));
		colSentence.append(") ");
		paraSentence.deleteCharAt(paraSentence.lastIndexOf(","));
		paraSentence.append(") ");

		return colSentence.append(paraSentence).toString();
	}

}
