package com.hurry.led.server.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import com.hurry.led.server.DoRequest;
import com.hurry.led.server.HTTPServerAPI;
import com.hurry.led.util.PropertiesUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class APIController implements HttpHandler {

	@SuppressWarnings("unchecked")
	public void handle(HttpExchange t) throws IOException {
		Map<String, Object> params = (Map<String, Object>) t.getAttribute("parameters");
		try {
			String response = PropertiesUtil.getProperty("onSuccess", "0");
			String uri = t.getRequestURI().getPath();
			uri=uri.substring(HTTPServerAPI.ROOT.length(),uri.length());
			try {
				DoRequest.initData(params.get("data").toString());
			} catch (Exception e) {
				response = "exception:" + e.toString();
			}
			t.sendResponseHeaders(200, response.length());
			PrintWriter writer = new PrintWriter(t.getResponseBody());
			writer.print(response);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}