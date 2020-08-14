package com.hurry.led.web;

import java.io.Serializable;

import com.google.gson.Gson;
import com.hurry.led.util.LedUtil;

/**
 * 队列
 * 
 * @author ZhouHao
 * 
 */
public class Queue implements Serializable{

	private static final long serialVersionUID = -7652912756263828120L;

	public static final String STAUTS_WATING = "等待中";

	public static final String STAUTS_STARTING = "制作中";

	public static final String STAUTS_OVER = "已完成";

	private int id;

	private String keyId;

	private String status = STAUTS_WATING;

	private String name;

	public Queue() {
	}

	public Queue(String name, String keyId) {
		this.name = name;
		this.keyId = keyId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toJson() {
		return new Gson().toJson(this);
	}

	public void changeStatus() {
		if (getStatus().equals(STAUTS_WATING)) {
			setStatus(STAUTS_OVER);
			LedUtil.addMessage(getKeyId());
		} else if (getStatus().equals(STAUTS_STARTING)) {
			setStatus(STAUTS_OVER);
		} else if (getStatus().equals(STAUTS_OVER)) {
			QueueManager.remove(name, id);
			LedUtil.removeMessage(getKeyId());
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName();
	}
}
