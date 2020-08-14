package com.hurry.led.web;

import java.util.ArrayList;
import java.util.Collection;

import com.google.gson.Gson;

public class QueueList extends ArrayList<Queue> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -265500693336666491L;

	public QueueList(){}
	public QueueList(Collection<Queue> c) {
		super(c);
	}
	
	public String toJson() {
		return new Gson().toJson(this);
	}
}
