package com.hurry.led.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.hurry.led.gui.UIController;

public class ImageUtils {

	/**
	 * 图片库
	 */
	private static final HashMap<String, Image> IMAGE_MAP = new HashMap<String, Image>();

	/**
	 * 图片格式
	 */
	private static final Object IMAGE_LAYOUT[] = { "jpg", "png", "bmp", "gif", "JPG", "PNG", "BMP", "GIF" };

	/**
	 * 指定路径加载图片初始化图片 并加载
	 * 
	 * @param imagePath
	 * @throws Exception
	 */
	public static void initImage(String imagePath) throws Exception {
		System.out.println("初始化图片数据" + imagePath);
		for (File file : FileUtils.getAllFiles(imagePath)) {
			String fileName = file.getAbsolutePath();
			if (ClassUtils.arrayContains(IMAGE_LAYOUT, fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()))) {
				String key = fileName.substring(imagePath.length() + 1).replace("\\", "/");
				System.out.println(key + "=" + fileName);
				IMAGE_MAP.put(key,toImage(toByteArray(fileName)) );
				initImage(IMAGE_MAP.get(key));
			}
		}
		System.out.println("初始化图片数据完成");
	}

	/**
	 * 根据文件名获取图片
	 * 
	 * 
	 * @param name
	 *            文件名<br>
	 *            如： 图片路径为 d:/game/image/boss/1.gif <br>
	 *            加载时路径为d:/game/image ,则传入boss/1.gif即可获取该图片对象
	 * @return 图片对象
	 * @throws Exception
	 */
	public static Image getImage(String name) throws Exception {
		synchronized (IMAGE_MAP) {
			return IMAGE_MAP.get(name);
		}
	}

	/**
	 * 指定类加载 类同目录下图片初始化图片 并加载
	 * 
	 * @param imageClass
	 * @throws Exception
	 * @throws URISyntaxException
	 */
	public static void initImage(Class<?> imageClass) throws URISyntaxException, Exception {
		String pakage = imageClass.getPackage().toString();
		pakage = pakage.substring(8);
		pakage = pakage.replace(".", "/");// 获取类目录
		String path = imageClass.getResource("/").toURI().getPath() + pakage;// 完整类目录
		if (path.indexOf("/") == 0) { // 替换第一个/符号
			path = path.substring(1, path.length());
		}
		initImage(path);
	}

	/**
	 * 加载多个图片到mediaTracker
	 * 
	 * @param images
	 */
	public static void initImage(Image[] images) {
		for (int i = 0; i < images.length; i++) {
			UIController.mediaTracker.addImage(images[i], IMAGE_MAP.size());
		}
	}

	/**
	 * 加载单个图片到mediaTracker
	 * 
	 * @param mediaTracker
	 * @param image
	 */
	private static void initImage(Image image) {
		UIController.mediaTracker.addImage(image, IMAGE_MAP.size());
	}
	
	 public static byte[] toByteArray(String imageFile) throws Exception {  
		 return toByteArray(new File(imageFile));
	 }
	
	 public static Image toImage(byte [] b) throws IOException{
		 ByteArrayInputStream imageStream = new ByteArrayInputStream(b);  
		 return ImageIO.read(imageStream);
	 }
	 
	 
	  public static byte[] toByteArray(File imageFile) throws Exception {  
	        BufferedImage img = ImageIO.read(imageFile);  
	        ByteArrayOutputStream buf = new ByteArrayOutputStream((int) imageFile.length());  
	        try {
	        	String name =  imageFile.getName();
	            ImageIO.write(img,name.substring(name.lastIndexOf(".")+1, name.length()), buf);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	            return null;  
	        }  
	        return buf.toByteArray();  
	    }  
	  
}
