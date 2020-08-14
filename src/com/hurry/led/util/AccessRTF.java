package com.hurry.led.util;

import java.io.*;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;
import javax.swing.text.rtf.*;

public class AccessRTF {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		writRTF("RTF测试123123\r\n大苏打我去", "TestRtf.rtf");
		System.out.println(readRtf("TestRtf.rtf"));
	}

	public static String readRtf(String filePath) {
		String result = null;
		File file = new File(filePath);
		try {
			DefaultStyledDocument styledDoc = new DefaultStyledDocument();
			InputStream is = new FileInputStream(file);
			new RTFEditorKit().read(is, styledDoc, 0);
			result = new String(styledDoc.getText(0, styledDoc.getLength()));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void writRTF(String msg,String fileName) {
		DefaultStyledDocument outDoc = new DefaultStyledDocument();
		try {
			JTextPane textPane = new JTextPane();
			StyledDocument doc = (StyledDocument) textPane.getDocument();
			Style style = doc.addStyle("StyleName", null);
			// StyleConstants.setForeground(style, Color.RED);
			// StyleConstants.setFontSize(style, 12);
			// StyleConstants.setItalic(style, true);
			// StyleConstants.setUnderline(style, true);
			// StyleConstants.setBold(style, true);
			// StyleConstants.setFontFamily(style, "黑体");
			outDoc.insertString(outDoc.getLength(), msg, style);
			// 最后一个参数应该是控制写入内容的格式，暂时不懂用，用null代替
			OutputStream os = new FileOutputStream(fileName);
			new RTFEditorKit().write(os, outDoc, 0, outDoc.getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}