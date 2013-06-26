package com.ssac.expro.kewen.bean;
/**
 * 演出类
 * @author poe
 *
 */
public class ShowInfo {

	private String dramaID;
	
	private String dramaName;
	
	private String hashFolderName;
	
	private String titleImage;
	
	private String showTime;
	
	private String price;
	
	private String dramaType;
	
	private String linkAddress;
	
	private String publishCompany;//出品公司
	
	private String desc;

	public String getPublishCompany() {
		return publishCompany;
	}

	public void setPublishCompany(String publishCompany) {
		this.publishCompany = publishCompany;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDramaID() {
		return dramaID;
	}

	public void setDramaID(String dramaID) {
		this.dramaID = dramaID;
	}

	public String getDramaName() {
		return dramaName;
	}

	public void setDramaName(String dramaName) {
		this.dramaName = dramaName;
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

	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDramaType() {
		return dramaType;
	}

	public void setDramaType(String dramaType) {
		this.dramaType = dramaType;
	}

	public String getLinkAddress() {
		return linkAddress;
	}

	public void setLinkAddress(String linkAddress) {
		this.linkAddress = linkAddress;
	}
	
}
