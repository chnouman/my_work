package com.ssac.expro.kewen.bean;

public class AD {
	
	private String imgName;
	private String imgPath;
	private String type;//0:首页  1：演出 、、、
	private String ResourceContent;//资源路径
	private String LinkAddress;//图片点击后的跳转路径
	
	public String getResourceContent() {
		return ResourceContent;
	}
	public void setResourceContent(String resourceContent) {
		ResourceContent = resourceContent;
	}
	public String getLinkAddress() {
		return LinkAddress;
	}
	public void setLinkAddress(String linkAddress) {
		LinkAddress = linkAddress;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
