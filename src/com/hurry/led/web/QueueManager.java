package com.hurry.led.web;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;
import java.util.Map;


public class QueueManager {

	private static Integer id = 1;
	/**
	 * 总队列
	 */
	private static Map<String, Map<Integer, Queue>> MAP = new LinkedHashMap<String, Map<Integer, Queue>>();

	private static final String BACKUP_FILE_NAME = System.getProperty("user.dir") + "\\backup\\queue-web.map";

	static {
		try {
			deSerializableMap();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void put(Queue queue) {
		synchronized (id) {
			queue.setId(id++);
		}
		synchronized (MAP) {
			Map<Integer, Queue> tmp = MAP.get(queue.getName());
			if (tmp == null) {
				tmp = new LinkedHashMap<Integer, Queue>();
				MAP.put(queue.getName(), tmp);
			}
			tmp.put(queue.getId(), queue);
		}
	}
	
	public static void remove(String name,int id){
		synchronized (MAP) {
			MAP.get(name).remove(id);
			if(MAP.get(name).size()==0){
				MAP.remove(name);
			}
		}
	}

	public static QueueList get(String name) {
		synchronized (MAP) {
			return new QueueList(MAP.get(name).values());
		}
	}

	public static Queue get(String name, int id) {
		synchronized (MAP) {
			return MAP.get(name).get(id);
		}
	}

	public static Queue get(int id) {
		for (Queue queue : getAll()) {
			if (id == queue.getId()) {
				return queue;
			}
		}
		return null;
	}

	public static QueueList getAll() {
		synchronized (MAP) {
			QueueList queueList = new QueueList();
			for (String key : MAP.keySet()) {
				queueList.addAll(MAP.get(key).values());
			}
			return queueList;
		}
	}

	public static String[] getNames() {
		synchronized (MAP) {
			return MAP.keySet().toArray(new String[] {});
		}
	}

	public static void test() {
		Queue queue = new Queue("鱼香肉丝", "XC-001");
		Queue queue2 = new Queue("鱼香肉丝", "XC-002");

		Queue queue3 = new Queue("酸菜肉丝", "DC-001");
		put(queue);
		put(queue2);
		put(queue3);
	}

	/**
	 * 序列化
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void SerializableMap() throws FileNotFoundException, IOException {
		ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(BACKUP_FILE_NAME));
		synchronized (MAP) {
			System.out.print("保存备份:" + BACKUP_FILE_NAME);
			outputStream.writeObject(MAP);
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
		synchronized (MAP) {
			System.out.print("加载备份:" + BACKUP_FILE_NAME);
			MAP = (Map<String, Map<Integer, Queue>>) inputStream.readObject();
			inputStream.close();
			System.out.println("成功！");
		}
	}
	
	public static void saveQueue(){
		new Thread() {
			public void run() {
				try {
					QueueManager.SerializableMap();
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	}
}
