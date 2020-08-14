package com.hurry.led.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import com.hurry.led.gui.UIController;
import com.hurry.led.server.controller.APIController;
import com.hurry.led.server.controller.AjaxController;
import com.hurry.led.server.controller.IndexController;
import com.hurry.led.util.LedUtil;
import com.hurry.led.web.Queue;
import com.hurry.led.web.QueueManager;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class HTTPServerAPI {
	public static final String ROOT = "/";

	public static final String DO_REQUEST = "/hurry";

	public static final String DO_AJAX = "/ajax";

	public static final String INDEXPAGE = "index.htm";

	public static final String WEB_ROOT = System.getProperty("user.dir") + "/webRoot/";

	private static HashMap<String, HttpHandler> MAP = new HashMap<String, HttpHandler>();
	static {
		MAP.put(ROOT, new IndexController());
		MAP.put(DO_REQUEST, new APIController());
		MAP.put(DO_AJAX, new AjaxController());
	}

	public static void start(int port) {
		try {
			List<Queue> queues = QueueManager.getAll();
			for(Queue queue:queues){
				if(queue.getStatus().equals(Queue.STAUTS_OVER))
					LedUtil.addMessage(queue.getKeyId());
			}
			
			HttpServer hs = HttpServer.create(new InetSocketAddress(port), 0);
			for (String k : MAP.keySet()) {
				System.out.println("创建HttpContent:"+k);
				HttpContext context = hs.createContext(k, MAP.get(k));
				hs.setExecutor(null);
				context.getFilters().add(new ParameterFilter());
			}
			hs.start();
			System.out.println("开启服务器成功");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(UIController.mainWindow, "开启服务失败!\r\n错误码:" + e.getMessage());
			e.printStackTrace();
		}
	}

}
