package com.hurry.led.main;

import com.hurry.led.server.HTTPServerAPI;
import com.hurry.led.util.LEDProperties;
import com.hurry.led.util.PropertiesUtil;

public class Start {

	public static void main(String[] args) {
		try {
				LEDProperties.init();
				// new MainWindow(ConfigManager.TITLE).setVisible(true);
				HTTPServerAPI.start(Integer.parseInt(PropertiesUtil.getProperty("serverPort", "1101")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
