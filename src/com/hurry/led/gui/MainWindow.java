package com.hurry.led.gui;

import java.awt.AWTEvent;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.hurry.led.util.LedUtil;

public class MainWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8188160608577376500L;

	private boolean fullScrean = false;

	public MainWindow(String title) {
		try {
			this.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					System.out.println("退出系统..");
					try {
						ButtonManager.SerializableMap();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					System.exit(1);
				}
			});
			this.addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent e) {
					resize();
				}
			});
			init(title);
			initMsg();
			CustomButton button = new CustomButton("", "");
			button.setLocation(200, 200);
			button.init();
			add(button);
			// 添加键盘事件
			Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
				int count = 1, count2 = 1;
				int old_w = getWidth();
				int old_h = getHeight();

				@Override
				public void eventDispatched(AWTEvent event) {
					MouseEvent e = (MouseEvent) event;
					if (event.getSource() instanceof MainWindow) {
						if (e.getClickCount() == 2) {
							if (count % 3 == 0)
								if (fullScrean) {
									UIController.fullScreen(null);
									fullScrean = false;
									setSize(old_w, old_h);
								} else {
									old_w = getWidth();
									old_h = getHeight();
									UIController.fullScreen(UIController.mainWindow);
									fullScrean = true;
								}
							count++;
						}
					}
					if (event.getSource() instanceof CustomButton) {
						CustomButton button = (CustomButton) event.getSource();
						if (e.getClickCount() == 1) {
							if (count2 % 2 == 0){
								button.doClick();
								count2=0;
							}
							count2++;
						}
					}

					// if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					// UIController.fullScreen(null);
					// }
					// if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					// UIController.fullScreen(UIController.mainWindow);
					// }
				}
			}, AWTEvent.MOUSE_EVENT_MASK);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initMsg() {
		String[] names = ButtonManager.keySet();
		for (String name : names) {
			List<CustomButton> list = ButtonManager.getButton(0, UIController.table_size, name);
			if (list.size() != 0) {
				for (CustomButton button : list) {
					if (button.getStatus().equals(CustomButton.STATUS_OVER)) {
						LedUtil.addMessage(button.getRealKey());
					}
				}
			}
		}
		try {
			LedUtil.showMessageToLED();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void init(String title) throws Exception {
		this.setLayout(null);
		this.setTitle(title);
		this.setSize(800, 600);
		this.setLocationRelativeTo(this);
		// this.setUndecorated(true);
		// UIController.mediaTracker = new MediaTracker(this);
		// ImageUtils.initImage(this.getClass());
		// bG_img = ImageUtils.getImage("111.jpg");
		setAlwaysOnTop(true);
		UIController.mainWindow = this;
	}

	private Font titleFont = new Font("微软雅黑", Font.BOLD, 30);
	private String title = "已点菜品";

	/**
	 * 按钮缓存区
	 */
	private List<JButton> ljt = new LinkedList<JButton>();

	@Override
	public void paint(Graphics g) {
		// update(g);

		g.setColor(ColorManager.BACKGROUND_COLOR);
		g.fillRect(0, 0, getWidth(), getHeight());
		// g.clearRect(0, 0, getWidth(), getHeight());
		g.setFont(titleFont);
		g.setColor(ColorManager.BUTTON_COLOR2);
		g.drawString(title, getWidth() / 2 - (title.length() / 2 * titleFont.getSize()), 80);
		g.fillRoundRect(0, 100, getWidth(), 2, 10, 1);
		int width_n = 100;
		g.fillRoundRect(200, width_n, 2, getHeight(), 10, 1);
		g.setColor(ColorManager.BUTTON_COLOR2);
		g.setFont(FontManager.name_text);

		String[] names = ButtonManager.keySet();
		int table_x = 210;
		int table_y = UIController.BUTTON_HEIGHT +10;
		// 清空旧的按钮信息
		for (JButton button : ljt) {
			remove(button);
		}
		ljt.clear();
		// 创建表格
		for (String name : names) {
			List<CustomButton> list = ButtonManager.getButton(0, UIController.table_size, name);
			if (list.size() != 0) {
				for (CustomButton button : list) {
					button.setLocation(table_x, table_y+10);
					ljt.add(button);
					add(button);
					button.repaint();
					button.init();
					table_x += UIController.BUTTON_WIDTH + 30;
				}
				
				g.fillRoundRect(0, UIController.BUTTON_HEIGHT + table_y + 45, getWidth(), 2, 10, 1);
				g.drawString(name, 210 / 2 - (FontManager.name_text.getSize() * name.length() / 2), UIController.BUTTON_HEIGHT + table_y);
				String size = ButtonManager.getButton(name).size() + "份";
				g.drawString(size, 210 / 2 - (FontManager.name_text.getSize() * size.length() / 2), UIController.BUTTON_HEIGHT + table_y
						+ FontManager.name_text.getSize());
				table_x = 210;
				table_y += UIController.BUTTON_HEIGHT + 12;
			}
		}
	}

	private void resize() {
		// bG_img = createImage(getWidth(), getHeight());
		repaint();
	}

}
