package com.crocobaby.frame.common;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Properties;
import java.util.UUID;

/**
 * The Class CommonUtil.
 */
public class CommonUtil {

	/**
	 * 初始化指定的配置文件.
	 *
	 * @param propertyPath
	 *            the property path
	 * @return the properties
	 */
	public static Properties initProperty(String propertyPath) {
		Properties p = null;
		InputStream inputStream = null;
		try {
			p = new Properties();
			inputStream = CommonUtil.class.getClassLoader().getResourceAsStream(propertyPath);
			p.load(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (Exception e) {
			}
		}
		return p;
	}

	/**
	 * 创建唯一ID.
	 *
	 * @return the string
	 */
	public static String createSID() {

		UUID uuid = UUID.randomUUID();
		String SID = uuid.toString().replace("-", "").toUpperCase();
		return SID;
	}

	public static String getDoubleValue(double value) {
		DecimalFormat decimalFormat = new DecimalFormat("#.00");
		return decimalFormat.format(value).replace(".00", "");
	}
}
