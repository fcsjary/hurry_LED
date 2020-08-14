package com.hurry.led.util;

public class LEDProperties {

	// 1、网络通讯 2、232通讯 3、485通讯
	public  static int TransMode = 2;
	// 控制器型号(1、N1,N2,L1 /2、N3 /3、T1,T2,T3,T4,Q0,Q1)
	public static int ConType = 3;
	// 设置串口参数
	// 屏号
	public static int SerialPortPara_pno = 1;
	// 端口号
	public static int SerialPortPara_comno = 1;
	// 波特率
	public static int SerialPortPara_baud = 9600;

	// 添加显示屏
	// 屏幕号
	public static int Control_pno = 1;
	// 单双色(单色为1 ，双色为2,三基色3)
	public static int Control_DBColor = 1;

	// 添加节目
	// 屏幕号
	public static int Program_pno = 1;
	// 节目号
	public static int Program_jno = 1;
	// 播放时间
	public static int Program_playTime = 0;

	// 单行文本配置
	// 屏号 (>=1)
	public static int LnTxt_pno = 1;
	// 节目号 (>=1)
	public static int LnTxt_jno = 1;
	// 区域号 (>=1)
	public static int LnTxt_qno = 1;
	// 区域左上角顶点x坐标：8的倍数，单位：象素
	public static int LnTxt_left = 0;
	// 区域左上角顶点y坐标
	public static int LnTxt_top = 0;
	// 区域宽度
	public static int LnTxt_width = 64;
	// 区域高度
	public static int LnTxt_height = 32;
	// 字体名
	public static String LnTxt_fontname = "宋体";
	// 字体号
	public static int LnTxt_fontsize = 10;
	// 字体颜色 颜色的RGB值，如红色为 ：255
	public static int LnTxt_fontcolor = 255;
	// 是否粗体
	public static boolean LnTxt_bold = true;
	// 是否斜体
	public static boolean LnTxt_italic = false;
	// 是否下划线
	public static boolean LnTxt_underline = false;
	// 显示特技（支持左移、右移、上移、下移）
	public static int LnTxt_PlayStyle = 32;
	// 显示速度
	public static int LnTxt_Playspeed = 15;
	// 保留参数（暂未使用）
	public static int LnTxt_times = 0;

	// 发送控制参数
	// 屏号
	public static int SendControl_pno = 1;
	// 发送模式1为普通 2为SD卡发送
	public static int SendControl_SendType = 1;
	// 窗口句柄
	public static int SendControl_hwd = 0;

	public static void init() {
		System.out.println("加载LED 配置文件...");
		TransMode = Integer.parseInt(PropertiesUtil.getProperty("TransMode", TransMode + ""));
		ConType = Integer.parseInt(PropertiesUtil.getProperty("ConType", ConType + ""));
		SerialPortPara_pno = Integer.parseInt(PropertiesUtil.getProperty("SerialPortPara_pno", SerialPortPara_pno + ""));
		SerialPortPara_comno = Integer.parseInt(PropertiesUtil.getProperty("SerialPortPara_comno", SerialPortPara_comno + ""));
		SerialPortPara_baud = Integer.parseInt(PropertiesUtil.getProperty("SerialPortPara_baud", SerialPortPara_baud + ""));
		Control_pno = Integer.parseInt(PropertiesUtil.getProperty("Control_pno", Control_pno + ""));
		Control_DBColor = Integer.parseInt(PropertiesUtil.getProperty("Control_DBColor", Control_DBColor + ""));
		Program_pno = Integer.parseInt(PropertiesUtil.getProperty("Program_pno", Program_pno + ""));
		Program_jno = Integer.parseInt(PropertiesUtil.getProperty("Program_jno", Program_jno + ""));
		Program_playTime = Integer.parseInt(PropertiesUtil.getProperty("Program_playTime", Program_playTime + ""));
		LnTxt_pno = Integer.parseInt(PropertiesUtil.getProperty("LnTxt_pno", LnTxt_pno + ""));
		LnTxt_jno = Integer.parseInt(PropertiesUtil.getProperty("LnTxt_jno", LnTxt_jno + ""));
		LnTxt_qno = Integer.parseInt(PropertiesUtil.getProperty("LnTxt_qno", LnTxt_qno + ""));
		LnTxt_left = Integer.parseInt(PropertiesUtil.getProperty("LnTxt_left", LnTxt_left + ""));
		LnTxt_top = Integer.parseInt(PropertiesUtil.getProperty("LnTxt_top", LnTxt_top + ""));
		LnTxt_width = Integer.parseInt(PropertiesUtil.getProperty("LnTxt_width", LnTxt_width + ""));
		LnTxt_height = Integer.parseInt(PropertiesUtil.getProperty("LnTxt_height", LnTxt_height + ""));
		LnTxt_fontname = PropertiesUtil.getProperty("LnTxt_fontname", LnTxt_fontname);
		LnTxt_fontsize = Integer.parseInt(PropertiesUtil.getProperty("LnTxt_fontsize", LnTxt_fontsize + ""));
		LnTxt_fontcolor = Integer.parseInt(PropertiesUtil.getProperty("LnTxt_fontcolor", LnTxt_fontcolor + ""));
		LnTxt_bold = Boolean.parseBoolean(PropertiesUtil.getProperty("LnTxt_bold", LnTxt_bold + ""));
		LnTxt_italic = Boolean.parseBoolean(PropertiesUtil.getProperty("LnTxt_italic", LnTxt_italic + ""));
		LnTxt_underline = Boolean.parseBoolean(PropertiesUtil.getProperty("LnTxt_underline", LnTxt_underline + ""));
		LnTxt_PlayStyle = Integer.parseInt(PropertiesUtil.getProperty("LnTxt_PlayStyle", LnTxt_PlayStyle + ""));
		LnTxt_Playspeed = Integer.parseInt(PropertiesUtil.getProperty("LnTxt_Playspeed", LnTxt_Playspeed + ""));
		LnTxt_times = Integer.parseInt(PropertiesUtil.getProperty("LnTxt_times", LnTxt_times + ""));
		SendControl_pno = Integer.parseInt(PropertiesUtil.getProperty("SendControl_pno", SendControl_pno + ""));
		SendControl_SendType = Integer.parseInt(PropertiesUtil.getProperty("SendControl_SendType", SendControl_SendType + ""));
		SendControl_hwd = Integer.parseInt(PropertiesUtil.getProperty("SendControl_hwd", SendControl_hwd + ""));
		System.out.println("完成!");
	}
}
