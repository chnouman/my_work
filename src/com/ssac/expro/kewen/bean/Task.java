package com.ssac.expro.kewen.bean;

import java.util.Map;

public class Task {
	private int taskID;
	private Map<String, Object> taskParam;

	public int getTaskID() {
		return taskID;
	}

	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}

	public Map<String, Object> getTaskParam() {
		return taskParam;
	}

	public void setTaskParam(Map<String, Object> taskParam) {
		this.taskParam = taskParam;
	}

	public Task(int tsid, Map<String, Object> hm) {
		this.taskID = tsid;
		this.taskParam = hm;
	}
}
