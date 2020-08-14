package com.hurry.led.gui;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MediaTracker;

import javax.swing.JFrame;


import com.hurry.led.util.PropertiesUtil;

public class UIController {
	public static int BUTTON_WIDTH=92,BUTTON_HEIGHT=60;
	
	public static final GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	
	public volatile static MediaTracker mediaTracker = null;

	public volatile static MainWindow mainWindow = null;
	
	public static  int table_size=5;
	
	static{
		table_size  = Integer.parseInt(PropertiesUtil.getProperty("maxButtonNumber", table_size+""));
	}
	public static void fullScreen(JFrame frame) {
		graphicsDevice.setFullScreenWindow(frame);
	}
}
