package com.hurry.led.util;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

	/**
	 * 获取指定目录的所有文件(包括子目录)
	 * @param path 指定目录
	 * @return 文件列表
	 * @throws Exception
	 */
	public synchronized static List<File> getAllFiles(String path) throws Exception {
		return getFiles(path, true,"");
	}
	
	/**
	 * 获取指定目录的文件
	 * @param path
	 * @return 文件列表
	 * @throws Exception
	 */
	public synchronized static List<File> getFiles(String path) throws Exception {
		return getFiles(path, false,"");
	}
	
	/**
	 * 获取指定目录的文件
	 * @param path 目录
	 * @param allFile 是否获取子目录
	 * @return 文件对象集合
	 * @throws Exception
	 */
	public synchronized static List<File> getFiles(String path,boolean allFile,String flt) throws Exception {
		List<File> fileList = new ArrayList<File>();
		File file = new File(path);
		if(!file.canRead()){
			return fileList;
		}
		File files[] = file.listFiles();
		if(files==null){
			return fileList;
		}
		for (int i = 0; i < files.length; i++) {
			File pFile = files[i];
			if (pFile.canRead()) {
				if (pFile.isFile()&&pFile.canRead()&&pFile.getPath().contains(flt)) {
					fileList.add(pFile);
				}
				if (pFile.isDirectory()&&allFile) {
					fileList.addAll(getFiles(pFile.getAbsolutePath(),allFile,flt));
				}
			}
		}
		return fileList;
	}
	
}
