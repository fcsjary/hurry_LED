package com.hurry.led.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.hurry.led.main.ConfigManager;
import com.hurry.led.main.Start;
import com.hurry.led.util.MacUtil;

@SuppressWarnings("serial")
public class RegistDialog extends JFrame implements ActionListener {

	private Container contentPane;

	private JLabel jlabel_input;

	private JLabel mac_input;

	private JButton ok;

	private JTextField contentField;

	private JTextField macField;

	// -----最大点击次数，消失
	// -----最大坐标
	private static int W;

	private static int H;

	// -----当前坐标
	private static int xp;

	private static int yp;

	public RegistDialog(int w, int h) {
		W = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - w;
		H = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - h;
		xp = W / 2;
		yp = H / 2;
		this.setLocation(xp, yp);
		this.setAlwaysOnTop(true); // 窗体总在最前面
		this.setResizable(false); // 窗体不能改变大小
		// this.setUndecorated(true); //窗体不要边框
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		contentPane = this.getContentPane();
		contentPane.setLayout(null);
		jlabel_input = new JLabel();
		jlabel_input.setText("授权码：");
		jlabel_input.setFont(new Font("黑体", 15, 20));
		jlabel_input.setBounds(17, 0, 120, 40);

		contentField = new JTextField();
		contentField.setBounds(112, 7, 145, 25);

		mac_input = new JLabel();
		mac_input.setText("标  识：");
		mac_input.setFont(new Font("黑体", 15, 20));
		mac_input.setBounds(17, 50, 120, 40);

		macField = new JTextField();
		macField.setBounds(112, 55, 145, 25);
		macField.setText(MacUtil.getMACAddress());

		ok = new JButton("确认");
		ok.setBounds(120, 100, 60, 50);
		ok.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				try {
					String content = getContent();
					if (null != content && !content.equals("")) {
							ConfigManager.addRegistCode(content);
							setVisible(false);
							Start.main(null);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		// contentPane.add(jlabel_code1);
		contentPane.add(ok);
		contentPane.add(jlabel_input);
		contentPane.add(contentField);
		contentPane.add(mac_input);
		contentPane.add(macField);
		// contentPane.add(jlabel_code);
		// contentPane.add(ImageJLabel);
		this.setTitle("软件授权");
		this.setSize(w, h);
		Toolkit t = Toolkit.getDefaultToolkit();
		Dimension srcSize = t.getScreenSize();
		Dimension frmSize = this.getSize();
		this.setLocation((srcSize.width - frmSize.width) / 2, (srcSize.height - frmSize.height) / 2);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {

	}

	/**
	 * 获取输入框内容
	 * 
	 * @return 输入框内容
	 */
	private String getContent() {
		synchronized (this) {
			return contentField.getText().trim();
		}
	}

}
