package com.hurry.led.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class ConfigManager {
	public static String TITLE="涵睿科技 LED控制系统";
	private static final String authorizeFile = "authorize/.authorize";

	public static void addRegistCode(String code) throws Exception {
		File file = new File(authorizeFile);
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(code);
		writer.close();
	}

	public static String getRegistCode() throws Exception {
		File file = new File(authorizeFile);
		String code = "";
		BufferedReader reader = null;
		if (file.canRead()) {
			try {
				reader = new BufferedReader(new FileReader(file));
				code = reader.readLine();
			} finally {
				reader.close();
			}
		}
		return code;
	}
}
