package com.hurry.led.server.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.hurry.led.server.HTTPServerAPI;
import com.hurry.led.util.ImageUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class IndexController implements HttpHandler {
	String[] img = { "jpeg", "jpg", "gif", "bmp", "png" };
	String[] text = { "js", "css", "html", "xhtml", "xml" };

	private static final Map<String, String> CONTENT_TYPES = new HashMap<String, String>();
	private static final String _404 = "<h1>404 NOT FOUND</h1>";

	static {
		CONTENT_TYPES.put("jpg", "image/jpeg");
		CONTENT_TYPES.put("jpeg", "image/jpeg");
		CONTENT_TYPES.put("gif", "image/gif");
		CONTENT_TYPES.put("png", "image/png");
		CONTENT_TYPES.put("bmp", "application/x-bmp");

		CONTENT_TYPES.put("js", "application/x-javascript");
		CONTENT_TYPES.put("css", "text/css");
		CONTENT_TYPES.put("html", "text/html");
		CONTENT_TYPES.put("htm", "text/html");

	}

	public void handle(HttpExchange t) throws IOException {
		try {
			String uri = t.getRequestURI().getPath();
			uri = uri.substring(HTTPServerAPI.ROOT.length(), uri.length());
			if (uri.trim().equals("")) {
				uri = HTTPServerAPI.INDEXPAGE;
			}
			String filePath = HTTPServerAPI.WEB_ROOT + uri;
			File file = new File(filePath);
			String ls = uri.substring(uri.lastIndexOf(".") + 1, uri.length());
			if (!file.canRead()) {
				BufferedOutputStream stream = new BufferedOutputStream(t.getResponseBody());
				t.sendResponseHeaders(404, _404.length());
				stream.write(_404.getBytes());
				stream.close();
				return;
			}
			t.getResponseHeaders().add("Content-Type", CONTENT_TYPES.get(ls));
			// 读取图片 2进制
			if (Arrays.<String> asList(img).contains(ls)) {
				writeByte(t, file);
			} else {
				writeText(t, file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writeByte(HttpExchange t, File file) throws Exception {
		// t.getResponseHeaders().add("Content-Type", "application/octet-stream");
		BufferedOutputStream stream = new BufferedOutputStream(t.getResponseBody());
		BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		try {
			byte b[] = ImageUtils.toByteArray(file);
			long l = b.length;
			t.sendResponseHeaders(200, l);
			stream.write(b);
			stream.flush();
		} finally {
			stream.close();
			inputStream.close();
		}
	}

	public void writeText(HttpExchange t, File file) throws Exception {
		// t.getResponseHeaders().add("Content-type", "text/html");
		PrintWriter writer = new PrintWriter(t.getResponseBody());
		BufferedReader reader = new BufferedReader(new FileReader(file));
		try {
			long l = 0;
			StringBuilder builder = new StringBuilder();
			while (reader.ready()) {
				String line = reader.readLine() + "\n";
				l += line.getBytes().length;
				builder.append(line);
			}
			if (file.getName().contains("script.js")) {
				System.out.println(builder);
			}
			t.sendResponseHeaders(200, l);
			writer.write(builder.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			writer.close();
			reader.close();
		}
	}
}