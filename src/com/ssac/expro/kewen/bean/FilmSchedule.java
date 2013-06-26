package com.ssac.expro.kewen.bean;
/**
 * 电影播出的计划时间
 * @author poe.Cai
 *
 */
public class FilmSchedule {

	private String time;
	private String link;//购票链接
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
}
