package com.hurry.led.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 配置文件管理类
 * 
 * @author ZhouHao
 * 
 */
public class PropertiesUtil {
	private static Properties properties = null;
	private static final String FILENAME = "config.properties";
	static {
		properties = new Properties();
		try {
			properties.load(new FileInputStream(new File(FILENAME)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据key 获取默认配置对应值
	 * 
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {
		return properties.getProperty(key);
	}
	
	public static String getProperty(String key,String defaultValue) {
		String value = properties.getProperty(key);
		if(value==null){
			System.err.println("使用默认值:"+key+"="+defaultValue);
			value=defaultValue;
		}
		return value;
	}

	/**
	 * 设置配置属性
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public static void setProperty(String key, String value) {
		properties.setProperty(key, value);
		try {
			properties.store(new FileOutputStream(new File(FILENAME)), "");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
