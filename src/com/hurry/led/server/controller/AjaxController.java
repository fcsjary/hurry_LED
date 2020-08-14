package com.hurry.led.server.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import com.hurry.led.server.DoAjax;
import com.hurry.led.util.PropertiesUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class AjaxController implements HttpHandler {

	@SuppressWarnings("unchecked")
	public void handle(HttpExchange t) throws IOException {
		Map<String, Object> params = (Map<String, Object>) t.getAttribute("parameters");
		try {
			String response = PropertiesUtil.getProperty("onSuccess", "0");
			try {
				response = DoAjax.initData(params);
			} catch (Exception e) {
				response = "error";
			}
			t.sendResponseHeaders(200, response.getBytes().length);
			PrintWriter writer = new PrintWriter(t.getResponseBody());
			writer.print(response);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}