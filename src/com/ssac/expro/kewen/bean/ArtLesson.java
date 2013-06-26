package com.ssac.expro.kewen.bean;

public class ArtLesson {

	private String ContentID;
	private String ContentTitle;
	private String HashFolderName;
	private String TitleImageName;
	private String Summary;
	private String CreateTime;
	private String Customlinks;
	
	//detail
	private String Desc;
	private String EffectTime;
	
	public String getDesc() {
		return Desc;
	}
	public void setDesc(String desc) {
		Desc = desc;
	}
	public String getEffectTime() {
		return EffectTime;
	}
	public void setEffectTime(String effectTime) {
		EffectTime = effectTime;
	}
	public String getContentID() {
		return ContentID;
	}
	public void setContentID(String contentID) {
		ContentID = contentID;
	}
	public String getContentTitle() {
		return ContentTitle;
	}
	public void setContentTitle(String contentTitle) {
		ContentTitle = contentTitle;
	}
	public String getHashFolderName() {
		return HashFolderName;
	}
	public void setHashFolderName(String hashFolderName) {
		HashFolderName = hashFolderName;
	}
	public String getTitleImageName() {
		return TitleImageName;
	}
	public void setTitleImageName(String titleImageName) {
		TitleImageName = titleImageName;
	}
	public String getSummary() {
		return Summary;
	}
	public void setSummary(String summary) {
		Summary = summary;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	public String getCustomlinks() {
		return Customlinks;
	}
	public void setCustomlinks(String customlinks) {
		Customlinks = customlinks;
	}
}
