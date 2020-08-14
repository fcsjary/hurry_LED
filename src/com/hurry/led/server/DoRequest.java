package com.hurry.led.server;

import com.hurry.led.gui.UIController;
import com.hurry.led.util.PropertiesUtil;
import com.hurry.led.web.Queue;
import com.hurry.led.web.QueueManager;

public class DoRequest {

	private static Boolean HASNEWMSG = false;

	public static boolean hasNewMsg() {
		synchronized (HASNEWMSG) {
			return HASNEWMSG;
		}
	}

	public static void hasNewMsg(boolean hasNewMsg) {
		synchronized (HASNEWMSG) {
			HASNEWMSG = hasNewMsg;
		}
	}

	/**
	 * 初始化参数数据，数据以 id:name,id2:name2...格式
	 * 
	 * @param data
	 */
	public static synchronized void initData(String data) {
		String numberNameSplit = PropertiesUtil.getProperty("numberNameSplit", ":");
		String namesSplit = PropertiesUtil.getProperty("namesSplit", ":");
		String[] datas = data.split(numberNameSplit);
		boolean autoRefresh = false;
		for (String str : datas) {
			String[] strs = str.split(namesSplit);
			String id = strs[0];
			String name = strs[1];
			Queue queue = new Queue(name, id);
			QueueManager.put(queue);
			hasNewMsg(true);
			QueueManager.saveQueue();
			// ButtonManager.putButton(id, name);
			// if(ButtonManager.getButton(name).size()<UIController.table_size){
			// autoRefresh = true;
			// }
		}
		if (autoRefresh) {
			if (UIController.mainWindow != null) {
				UIController.mainWindow.repaint();
			}
		}
	}
}
