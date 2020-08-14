package com.hurry.led.gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JButton;

public class ButtonManager implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7236215065560185845L;

	private static Map<String, Map<String, CustomButton>> BUTTON_MAP = new LinkedHashMap<String, Map<String, CustomButton>>();

	private static final String BACKUP_FILE_NAME =System.getProperty("user.dir")+"\\backup\\queue.map";

	static {
		try {
			deSerializableMap();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<CustomButton> getButton(int start, int size, String name) {
		List<CustomButton> list = null, list2 = new LinkedList<CustomButton>();
		synchronized (BUTTON_MAP) {
			if (BUTTON_MAP.get(name) != null)
				list = new LinkedList<CustomButton>(BUTTON_MAP.get(name).values());
			else
				list = new LinkedList<CustomButton>();
		}
		for (int i = start; i < size; i++) {
			if (list.size() > i)
				list2.add(list.get(i));
		}
		return list2;
	}

	public static List<JButton> getButton(String key) {
		synchronized (BUTTON_MAP) {
			return new LinkedList<JButton>(BUTTON_MAP.get(key).values());
		}
	}

	static Random random = new Random();

	public static void putButton(String key, CustomButton button) {
		synchronized (BUTTON_MAP) {
			Map<String, CustomButton> mp = BUTTON_MAP.get(key);
			if (mp == null)
				mp = new LinkedHashMap<String, CustomButton>();
			CustomButton od = mp.get(button.getKey());
			if (od != null) {
				button.setKey(button.getKey() + "$" + random.nextInt());
				mp.put(button.getKey(), button);
			} else {
				mp.put(button.getKey(), button);
			}
			BUTTON_MAP.put(key, mp);
		}
	}

	public static void removeButton(String key) {
		synchronized (BUTTON_MAP) {
			BUTTON_MAP.remove(key);
		}
	}

	public static CustomButton removeButton(String name, String id) {
		return BUTTON_MAP.get(name).remove(id);
	}

	public static String[] keySet() {
		synchronized (BUTTON_MAP) {
			return BUTTON_MAP.keySet().toArray(new String[] {});
		}
	}

	/**
	 * 序列化
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void SerializableMap() throws FileNotFoundException, IOException {
		ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(BACKUP_FILE_NAME));
		synchronized (BUTTON_MAP) {
			System.out.print("保存备份:" + BACKUP_FILE_NAME);
			outputStream.writeObject(BUTTON_MAP);
			outputStream.close();
			System.out.println("成功！");
		}

	}

	/**
	 * 反序列化
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public static void deSerializableMap() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(BACKUP_FILE_NAME));
		synchronized (BUTTON_MAP) {
			System.out.print("加载备份:" + BACKUP_FILE_NAME);
			BUTTON_MAP = (Map<String, Map<String, CustomButton>>) inputStream.readObject();
			inputStream.close();
			System.out.println("成功！");
		}
	}

	public static void test() {
		synchronized (BUTTON_MAP) {
			for (int i = 0; i < 20; i++) {
				putButton("鱼香肉丝", new CustomButton("000" + i, "鱼香肉丝"));
			}
			putButton("鱼香肉丝", new CustomButton("0001", "鱼香肉丝"));
			for (int i = 7; i < 25; i++) {
				putButton("宫爆鸡丁", new CustomButton("000" + i, "宫爆鸡丁"));
			}
			for (int i = 6; i < 18; i++) {
				putButton("酸菜鱼", new CustomButton("000" + i, "酸菜鱼"));
			}
			for (int i = 6; i < 55; i++) {
				putButton("番茄鸡蛋", new CustomButton("000" + i, "番茄鸡蛋"));
			}
			for (int i = 6; i < 47; i++) {
				putButton("肉末茄子", new CustomButton("000" + i, "肉末茄子"));
			}
			for (int i = 6; i < 22; i++) {
				putButton("红烧肉", new CustomButton("000" + i, "红烧肉"));
			}
			for (int i = 6; i < 28; i++) {
				putButton("烧白", new CustomButton("000" + i, "烧白"));
			}
			for (int i = 6; i < 30; i++) {
				putButton("红烧排骨", new CustomButton("000" + i, "红烧排骨"));
			}
		}
	}

	public static void putButton(String id, String name) {
		putButton(name, new CustomButton(id, name));
	}

	public static void main(String[] args) {
		test();
		try {
			SerializableMap();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (String key : keySet()) {
			System.out.print(key + "\t");
			for (CustomButton button : getButton(0, 10, key)) {
				System.out.print(button.getKey() + "\t");
			}
			System.out.println();
		}
	}
}
