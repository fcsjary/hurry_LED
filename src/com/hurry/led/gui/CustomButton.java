package com.hurry.led.gui;

import static com.hurry.led.gui.ColorManager.*;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import java.io.Serializable;

import javax.swing.JButton;

import com.hurry.led.util.LedUtil;

public class CustomButton extends JButton implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8591217599563829780L;
	// public static final Color BUTTON_COLOR1 = new Color(125, 161, 237);
	// public static final Color BUTTON_COLOR2 = new Color(91, 118, 173);
	public static final Color BUTTON_FOREGROUND_COLOR = Color.WHITE;
	private boolean hover;

	public static final String STATUS_WAITING = "等待中";

	public static final String STATUS_STARTING = "制作中";

	public static final String STATUS_OVER = "取餐";

	private String status = "";//STATUS_WAITING;

	private String key;

	private String name;

	private int index;

	public CustomButton(String key, String name) {
		this.key = key;
		this.name = name;
		// init();

	}

	private JButton my = this;

	public void initColor() {
		if (status.equals(STATUS_WAITING)) {
			setForeground(BUTTON_COLOR2);
		} else if (status.equals(STATUS_STARTING)) {
			setForeground(BUTTON_COLOR_STARTING2);
		} else if (status.equals(STATUS_OVER)) {
			setForeground(BUTTON_COLOR_OVER2);
		}
	}
	
	public void doClick(){
		if (status.equals(STATUS_WAITING)) {
			status = STATUS_STARTING;
		} else if (status.equals(STATUS_STARTING)) {
			status = STATUS_OVER;
			initMsg();
		} else if (status.equals(STATUS_OVER)) {
			ButtonManager.removeButton(getName(), getKey());
			UIController.mainWindow.remove(my);
			UIController.mainWindow.repaint();
			LedUtil.removeMessage(key);
			try {
				LedUtil.showMessageToLED();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		setText("<html>" + getRealKey() + "<br>" + status + "</html>");
		// 开启线程，对map进行序列化
		new Thread() {
			public void run() {
				try {
					ButtonManager.SerializableMap();
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	}

	public void initMsg() {
		if (status.equals(STATUS_OVER)) {
			LedUtil.addMessage(getRealKey());
			try {
				LedUtil.showMessageToLED();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String getRealKey() {
		if (key.contains("$")) {
			return key.substring(0, key.indexOf("$"));
		}
		return key;
	}

	public void init() {
		setText("<html>" + getRealKey() + "<br>" + status + "</html>");
		setFont(FontManager.button_text);
		setBorderPainted(false);
		setForeground(BUTTON_COLOR2);
		setFocusPainted(false);
		setContentAreaFilled(false);
		setMargin(new Insets(0, 0, 0, 0));
		setSize(UIController.BUTTON_WIDTH, UIController.BUTTON_HEIGHT);
		initColor();
		// 移除所有事件
		for (MouseListener listener : this.getMouseListeners()) {
			removeMouseListener(listener);
		}

		// 添加新事件
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setForeground(BUTTON_FOREGROUND_COLOR);
				hover = true;
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				initColor();
				hover = false;
				repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g.create();
		int h = getHeight();
		int w = getWidth();
		float tran = 1F;
		if (!hover) {
			tran = 0.3F;
		}

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		GradientPaint p1;
		GradientPaint p2;
		if (getModel().isPressed()) {
			p1 = new GradientPaint(0, 0, new Color(0, 0, 0), 0, h - 1, new Color(100, 100, 100));
			p2 = new GradientPaint(0, 1, new Color(0, 0, 0, 50), 0, h - 3, new Color(255, 255, 255, 100));
		} else {
			p1 = new GradientPaint(0, 0, new Color(100, 100, 100), 0, h - 1, new Color(0, 0, 0));
			p2 = new GradientPaint(0, 1, new Color(255, 255, 255, 100), 0, h - 3, new Color(0, 0, 0, 50));
		}
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, tran));
		RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(0, 0, w - 1, h - 1, 20, 20);
		Shape clip = g2d.getClip();
		g2d.clip(r2d);
		GradientPaint gp = new GradientPaint(0.0F, 0.0F, BUTTON_COLOR1, 0.0F, h, BUTTON_COLOR2, true);
		g2d.setPaint(gp);
		g2d.fillRect(0, 0, w, h);
		g2d.setClip(clip);
		g2d.setPaint(p1);
		g2d.drawRoundRect(0, 0, w - 1, h - 1, 20, 20);
		g2d.setPaint(p2);
		g2d.drawRoundRect(1, 1, w - 3, h - 3, 18, 18);
		g2d.dispose();
		super.paintComponent(g);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
