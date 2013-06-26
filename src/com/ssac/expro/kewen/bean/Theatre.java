package com.ssac.expro.kewen.bean;

public class Theatre {

	private String contentID;
	private String contentTitle;
	private String hashFolderName;
	private String titleImage;
	private String summary;
	private String beginTime;
	private String endTime;
	private String createTime;
	private String desc;//活动描述
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getContentID() {
		return contentID;
	}
	public void setContentID(String contentID) {
		this.contentID = contentID;
	}
	public String getContentTitle() {
		return contentTitle;
	}
	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}
	
	
	public String getHashFolderName() {
		return hashFolderName;
	}
	public void setHashFolderName(String hashFolderName) {
		this.hashFolderName = hashFolderName;
	}
	public String getTitleImage() {
		return titleImage;
	}
	public void setTitleImage(String titleImage) {
		this.titleImage = titleImage;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
}
