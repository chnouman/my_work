package com.ssac.expro.kewen.bean;

public class Film {

	private String filmID;
	private String filmName;
	private String hashFolderName;
	private String titleImageName;
	private String releaseDte;//上映时间
	private String property1;//导演
	private String property2;//主演
	
	private String filmDesc;//电影介绍
	private String star;//得分1-5：5颗星 等级最高
	private String totalTime;//片长 单位：分钟
	private String type;//影片类型 动作片：恐怖片
	
	
	public String getFilmDesc() {
		return filmDesc;
	}
	public void setFilmDesc(String filmDesc) {
		this.filmDesc = filmDesc;
	}
	public String getStar() {
		return star;
	}
	public void setStar(String star) {
		this.star = star;
	}
	public String getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFilmID() {
		return filmID;
	}
	public void setFilmID(String filmID) {
		this.filmID = filmID;
	}
	public String getFilmName() {
		return filmName;
	}
	public void setFilmName(String filmName) {
		this.filmName = filmName;
	}
	public String getHashFolderName() {
		return hashFolderName;
	}
	public void setHashFolderName(String hashFolderName) {
		this.hashFolderName = hashFolderName;
	}
	public String getTitleImageName() {
		return titleImageName;
	}
	public void setTitleImageName(String titleImageName) {
		this.titleImageName = titleImageName;
	}
	public String getReleaseDte() {
		return releaseDte;
	}
	public void setReleaseDte(String releaseDte) {
		this.releaseDte = releaseDte;
	}
	public String getProperty1() {
		return property1;
	}
	public void setProperty1(String property1) {
		this.property1 = property1;
	}
	public String getProperty2() {
		return property2;
	}
	public void setProperty2(String property2) {
		this.property2 = property2;
	}
	
}
