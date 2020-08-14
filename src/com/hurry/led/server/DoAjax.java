package com.hurry.led.server;

import java.util.Map;

import com.google.gson.Gson;
import com.hurry.led.web.Queue;
import com.hurry.led.web.QueueManager;

public class DoAjax {

	public static final String TYPE_GETNAME = "getNames";

	public static final String TYPE_GETALLQUEUE = "getAllQueues";

	public static final String TYPE_CHANGE = "changeQueue";

	public static final String TYPE_HASNEWMSG = "hasNewMsg";

	public static String initData(Map<String, Object> params) {
		try {
			String type = params.get("type").toString();
			if (TYPE_GETNAME.equals(type)) {
				return new Gson().toJson(QueueManager.getNames());
			}
			if (TYPE_GETALLQUEUE.equals(type)) {
				String json = QueueManager.getAll().toJson();
				return json;
			}
			if (TYPE_CHANGE.equals(type)) {
				String id = params.get("id").toString();
				String name = params.get("name").toString();
				Queue queue = null;
				if (null == name) {
					queue = QueueManager.get(Integer.parseInt(id));
				} else {
					queue = QueueManager.get(name, Integer.parseInt(id));
				}
				if (queue == null)
					return "error";
				queue.changeStatus();
				String json = queue.toJson();
				QueueManager.saveQueue();
				return json;
			}
			if (TYPE_HASNEWMSG.equals(type)) {
				boolean bh = DoRequest.hasNewMsg();
				DoRequest.hasNewMsg(false);
				return bh + "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}
	
	
}
