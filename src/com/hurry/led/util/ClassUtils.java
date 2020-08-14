package com.hurry.led.util;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClassUtils {

	/**
	 * 获取指定类同目录下的所有类（不进行实例化）
	 * @param class1 指定的类
	 * @return 同目录下的类
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<Object> getAllClass(Class class1)
			throws Exception {
		List<Object> objects = new ArrayList<Object>();
		String path = class1.getResource("/").toURI().getPath();
		String package1 = class1.getPackage().toString();
		path = path.replace("file:/", "").replace("%20", " ");
		package1 = package1.substring(8);
		String fileName = new String((path + (package1.replace(".", "/"))).getBytes("GBK"));
		List<File> files = FileUtils.getAllFiles(fileName);
		for (int i = 0; i < files.size(); i++) {
			File file2 = files.get(i);
			String name = file2.getName();
			if (file2.canRead() && name.lastIndexOf(".class") != -1) {
				name = name.substring(0, name.length() - 6);
				objects.add(Class.forName(package1 + "." + name));
			}
		}
		return objects;
	}
	
	/**
	 * 对象是否存在与数组
	 * @param objects 对象数组
	 * @param object 是否存在的对象
	 * @return
	 */
	public static boolean arrayContains(Object[] objects, Object object) {
		for (Object object2 : objects) {
			if (object.equals(object2)) {
				return true;
			}
		}
		return false;
	}
}
