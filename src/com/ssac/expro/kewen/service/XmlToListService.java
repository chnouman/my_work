package com.ssac.expro.kewen.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.ssac.expro.kewen.bean.AD;
import com.ssac.expro.kewen.bean.Art;
import com.ssac.expro.kewen.bean.ArtLesson;
import com.ssac.expro.kewen.bean.ArtitleForeign;
import com.ssac.expro.kewen.bean.Constants;
import com.ssac.expro.kewen.bean.Film;
import com.ssac.expro.kewen.bean.FilmSchedule;
import com.ssac.expro.kewen.bean.Huizhan;
import com.ssac.expro.kewen.bean.ShowInfo;
import com.ssac.expro.kewen.bean.Theatre;
import com.ssac.expro.kewen.bean.UserSina;
import com.ssac.expro.kewen.bean.WeiboSina;

/**
 * 采用 pull解析
 * 
 * @author poe
 * 
 */
public class XmlToListService {

	/**
	 * 获取首页的广告数据
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static List<AD> GetAD(String str) throws Exception {

		if (str == null || "".equals(str))
			return null;

		List<AD> ads = null;
		AD ad = null;
		XmlPullParser parser = Xml.newPullParser();
		InputStream inputStream = new ByteArrayInputStream(str.getBytes());
		parser.setInput(inputStream, "UTF-8");
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				ads = new ArrayList<AD>();
				break;
			case XmlPullParser.START_TAG:

				String name = parser.getName();

				if ("ds".equals(name)) {

					ad = new AD();

				}

				if (ad != null) {

					if ("ResourceContent".equals(name)) {
						String content = parser.nextText();

						if (null != content) {

							ad.setResourceContent(content);

							if (content.contains("/")) {
								String ss = content.substring(content.indexOf("/") + 1,	content.length());
								ad.setImgName(ss);
								ad.setImgPath(Constants.RESOURCE_PREFIX
										+ content);
							}

						}

					}
					if ("LinkAddress".equals(name)) {
						ad.setLinkAddress(parser.nextText());
					}
				}

				break;

			case XmlPullParser.END_TAG:

				if ("ds".equals(parser.getName())) {

					ads.add(ad);

					ad = null;
				}

				break;

			}

			eventType = parser.next();
		}

		return ads;
	}

	public static List<ShowInfo> GetShowInfo(String str)  {

		if (str == null || "".equals(str))
			return null;

		List<ShowInfo> showList = null;
		ShowInfo sinfo = null;
		
		try{
		XmlPullParser parser = Xml.newPullParser();
		InputStream inputStream = new ByteArrayInputStream(str.getBytes());
		parser.setInput(inputStream, "UTF-8");
		int eventType = parser.getEventType();
		
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				showList = new ArrayList<ShowInfo>();
				break;
			case XmlPullParser.START_TAG:

				String name = parser.getName();

				if ("detail".equals(name)) {

					sinfo = new ShowInfo();

				}

				if (sinfo != null) {

					if ("DramaID".equals(name)) {
						sinfo.setDramaID(parser.nextText());
					} else if ("DramaName".equals(name)) {
						sinfo.setDramaName(parser.nextText());
					} else if ("HashFolderName".equals(name)) {
						sinfo.setHashFolderName(parser.nextText());
					} else if ("TitleImageName".equals(name)) {
						sinfo.setTitleImage(parser.nextText());
					} else if ("ShowTime".equals(name)) {
						sinfo.setShowTime(parser.nextText());
					} else if ("Price".equals(name)) {
						sinfo.setPrice(parser.nextText());
					} else if ("DramaTypeText".equals(name)) {
						sinfo.setDramaType(parser.nextText());
					} else if ("Link".equals(name)) {
						sinfo.setLinkAddress(parser.nextText());
					}
				}

				break;

			case XmlPullParser.END_TAG:

				if ("detail".equals(parser.getName())) {
					if(null!=sinfo){
					sinfo.setTitleImage(Constants.RESOURCE_PREFIX
								+ sinfo.getHashFolderName()+"/"+sinfo.getTitleImage());
					showList.add(sinfo);
					sinfo = null;
					}
				}
				break;
			}
			eventType = parser.next();
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return showList;
	}
	
	public static ShowInfo GetShowInfoDetail(String str)  {

		if (str == null || "".equals(str))
			return null;
		ShowInfo sinfo = null;
		try{
		XmlPullParser parser = Xml.newPullParser();
		InputStream inputStream = new ByteArrayInputStream(str.getBytes());
		parser.setInput(inputStream, "UTF-8");
		int eventType = parser.getEventType();
		
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				break;
			case XmlPullParser.START_TAG:

				String name = parser.getName();

				if ("CMSDramaInfo".equals(name)) {

					sinfo = new ShowInfo();

				}

				if (sinfo != null) {

					if ("_dramaid".equals(name)) {
						sinfo.setDramaID(parser.nextText());
					} else if ("_dramaname".equals(name)) {
						sinfo.setDramaName(parser.nextText());
					} else if ("_hashfoldername".equals(name)) {
						sinfo.setHashFolderName(parser.nextText());
					} else if ("TitleImageName".equals(name)) {
						sinfo.setTitleImage(parser.nextText());
					} else if ("ShowTime".equals(name)) {
						sinfo.setShowTime(parser.nextText());
					} else if ("Price".equals(name)) {
						sinfo.setPrice(parser.nextText());
					} else if ("DramaTypeText".equals(name)) {
						sinfo.setDramaType(parser.nextText());
					} else if ("_link".equals(name)) {
						sinfo.setLinkAddress(parser.nextText());
					} else if("_repertoire".equals(name)){
						sinfo.setDesc(parser.nextText());
					} else if("_company".equals(name)){
						sinfo.setPublishCompany(parser.nextText());
					}
				}

				break;

			case XmlPullParser.END_TAG:

				if ("CMSDramaInfo".equals(parser.getName())) {
					if(null!=sinfo){
					sinfo.setTitleImage(Constants.RESOURCE_PREFIX
								+ sinfo.getHashFolderName()+"/"+sinfo.getTitleImage());
					}
				}
				break;
			}
			eventType = parser.next();
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return sinfo;
	}

	/**
	 * 剧院活动
	 * @param str
	 * @return
	 */
	public static List<Theatre> GetTheatre(String str)  {

		if (str == null || "".equals(str))
			return null;

		List<Theatre> showList = null;
		Theatre sinfo = null;
		try{
		XmlPullParser parser = Xml.newPullParser();
		InputStream inputStream = new ByteArrayInputStream(str.getBytes());
		parser.setInput(inputStream, "UTF-8");
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				showList = new ArrayList<Theatre>();
				break;
			case XmlPullParser.START_TAG:

				String name = parser.getName();

				if ("detail".equals(name)) {

					sinfo = new Theatre();

				}

				if (sinfo != null) {

					if ("ContentID".equals(name)) {
						sinfo.setContentID(parser.nextText());
					} else if ("ContentTitle".equals(name)) {
						sinfo.setContentTitle(parser.nextText());
					} else if ("HashFolderName".equals(name)) {
						sinfo.setHashFolderName(parser.nextText());
					} else if ("TitleImageName".equals(name)) {
						sinfo.setTitleImage(parser.nextText());
					} else if ("Summary".equals(name)) {
						sinfo.setSummary(parser.nextText());
					} else if ("ActivityBeginTime".equals(name)) {
						sinfo.setBeginTime(parser.nextText());
					} else if ("activityendtime".equals(name)) {
						sinfo.setEndTime(parser.nextText());
					} else if ("CreateTime".equals(name)) {
						sinfo.setCreateTime(parser.nextText());
					}
				}

				break;

			case XmlPullParser.END_TAG:

				if ("detail".equals(parser.getName())) {
					if(sinfo!=null){
						sinfo.setTitleImage(Constants.RESOURCE_PREFIX
								+ sinfo.getHashFolderName()+"/"+sinfo.getTitleImage());
						showList.add(sinfo);
						sinfo = null;
					}
				}
				break;
			}
			eventType = parser.next();
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return showList;
	}
	
	/**
	 * 获取剧院活动的详情
	 * @param str
	 * @return
	 */
	public static Theatre GetTheatreDetail(String str)  {

		if (str == null || "".equals(str))
			return null;
		Theatre sinfo = null;
		try{
		XmlPullParser parser = Xml.newPullParser();
		InputStream inputStream = new ByteArrayInputStream(str.getBytes());
		parser.setInput(inputStream, "UTF-8");
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				break;
			case XmlPullParser.START_TAG:

				String name = parser.getName();

				if ("CMSContentInfo".equals(name)) {

					sinfo = new Theatre();

				}

				if (sinfo != null) {

					if ("ContentID".equals(name)) {
						sinfo.setContentID(parser.nextText());
					} else if ("ContentTitle".equals(name)) {
						sinfo.setContentTitle(parser.nextText());
					} else if ("HashFolderName".equals(name)) {
						sinfo.setHashFolderName(parser.nextText());
					} else if ("TitleImageName".equals(name)) {
						sinfo.setTitleImage(parser.nextText());
					} else if ("Summary".equals(name)) {
						sinfo.setSummary(parser.nextText());
					} else if ("ActivityBeginTime".equals(name)) {
						sinfo.setBeginTime(parser.nextText());
					} else if ("activityendtime".equals(name)) {
						sinfo.setEndTime(parser.nextText());
					} else if ("CreateTime".equals(name)) {
						sinfo.setCreateTime(parser.nextText());
					} else if ("_description".equals(name)) {
						sinfo.setDesc(parser.nextText());
					}
				}

				break;

			case XmlPullParser.END_TAG:

				if ("CMSContentInfo".equals(parser.getName())) {
					if(sinfo!=null){
						sinfo.setTitleImage(Constants.RESOURCE_PREFIX
								+ sinfo.getHashFolderName()+"/"+sinfo.getTitleImage());
					}
				}
				break;
			}
			eventType = parser.next();
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return sinfo;
	}
	
	public static List<ArtitleForeign> GetArticleForeign(String str) throws Exception {

		if (str == null || "".equals(str))
			return null;

		List<ArtitleForeign> showList = null;
		ArtitleForeign sinfo = null;
		XmlPullParser parser = Xml.newPullParser();
		InputStream inputStream = new ByteArrayInputStream(str.getBytes());
		parser.setInput(inputStream, "UTF-8");
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				showList = new ArrayList<ArtitleForeign>();
				break;
			case XmlPullParser.START_TAG:

				String name = parser.getName();

				if ("detail".equals(name)) {

					sinfo = new ArtitleForeign();

				}

				if (sinfo != null) {

//					if ("ContentID".equals(name)) {
//						sinfo.setContentID(parser.nextText());
//					} else if ("HashFolderName".equals(name)) {
//						sinfo.setHashFolderName(parser.nextText());
//					} else if ("TitleImageName".equals(name)) {
//						sinfo.setTitleImage(parser.nextText());
//					} else if ("Summary".equals(name)) {
//						sinfo.setSummary(parser.nextText());
//					} else if ("ActivityBeginTime".equals(name)) {
//						sinfo.setBeginTime(parser.nextText());
//					} else if ("activityendtime".equals(name)) {
//						sinfo.setEndTime(parser.nextText());
//					} else if ("CreateTime".equals(name)) {
//						sinfo.setCreateTime(parser.nextText());
//					}
				}

				break;

			case XmlPullParser.END_TAG:

				if ("detail".equals(parser.getName())) {
					showList.add(sinfo);
					sinfo = null;
				}
				break;
			}
			eventType = parser.next();
		}
		return showList;
	}

	
	public static List<Film> GetFilms(String str)  {

		if (str == null || "".equals(str))
			return null;

		List<Film> showList = null;
		Film sinfo = null;
		try{	
		XmlPullParser parser = Xml.newPullParser();
		InputStream inputStream = new ByteArrayInputStream(str.getBytes());
		parser.setInput(inputStream, "UTF-8");
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				showList = new ArrayList<Film>();
				break;
			case XmlPullParser.START_TAG:

				String name = parser.getName();

				if ("detail".equals(name)) {

					sinfo = new Film();

				}

				if (sinfo != null) {

					if ("FilmID".equals(name)) {
						sinfo.setFilmID(parser.nextText());
					} else if ("FilmName".equals(name)) {
						sinfo.setFilmName(parser.nextText());
					} else if ("HashFolderName".equals(name)) {
						sinfo.setHashFolderName(parser.nextText());
					} else if ("TitleImageName".equals(name)) {
						sinfo.setTitleImageName(parser.nextText());
					} else if ("ReleaseDate".equals(name)) {
						sinfo.setReleaseDte(parser.nextText());
					} else if ("Property1".equals(name)) {
						sinfo.setProperty1(parser.nextText());
					} else if ("Property2".equals(name)) {
						sinfo.setProperty2(parser.nextText());
					} else if ("_repertoire".equals(name)) {
						sinfo.setFilmDesc(parser.nextText());
					} else if ("_sort".equals(name)) {//片长 N分钟
						sinfo.setTotalTime(parser.nextText());
					} else if ("_summary".equals(name)) {//评分 4颗星
						sinfo.setStar(parser.nextText());
					} 
				}

				break;

			case XmlPullParser.END_TAG:

				if ("detail".equals(parser.getName())) {
					if(sinfo!=null){
						sinfo.setTitleImageName(Constants.RESOURCE_PREFIX
								+ sinfo.getHashFolderName()+"/"+sinfo.getTitleImageName());
						showList.add(sinfo);
						sinfo = null;
					}
				}
				break;
			}
			eventType = parser.next();
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return showList;
	}
	
	
	public static Film GetFilmDetail(String str)  {

		if (str == null || "".equals(str))
			return null;

		Film sinfo = null;
		try{	
		XmlPullParser parser = Xml.newPullParser();
		InputStream inputStream = new ByteArrayInputStream(str.getBytes());
		parser.setInput(inputStream, "UTF-8");
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				break;
			case XmlPullParser.START_TAG:

				String name = parser.getName();

				if ("CMSFilmInfo".equals(name)) {

					sinfo = new Film();

				}

				if (sinfo != null) {

					if ("FilmID".equals(name)) {
						sinfo.setFilmID(parser.nextText());
					} else if ("FilmName".equals(name)) {
						sinfo.setFilmName(parser.nextText());
					} else if ("HashFolderName".equals(name)) {
						sinfo.setHashFolderName(parser.nextText());
					} else if ("TitleImageName".equals(name)) {
						sinfo.setTitleImageName(parser.nextText());
					} else if ("ReleaseDate".equals(name)) {
						sinfo.setReleaseDte(parser.nextText());
					} else if ("Property1".equals(name)) {
						sinfo.setProperty1(parser.nextText());
					} else if ("Property2".equals(name)) {
						sinfo.setProperty2(parser.nextText());
					} else if ("_repertoire".equals(name)) {
						sinfo.setFilmDesc(parser.nextText());
					} else if ("_length".equals(name)) {//片长 N分钟
						sinfo.setTotalTime(parser.nextText());
					} else if ("_summary".equals(name)) {//评分 4颗星
						sinfo.setStar(parser.nextText());
					} else if ("_filmtype".equals(name)) {//评分 4颗星
						sinfo.setType(parser.nextText());
					} 
				}

				break;

			case XmlPullParser.END_TAG:

				if ("detail".equals(parser.getName())) {
					if(sinfo!=null){
						sinfo.setTitleImageName(Constants.RESOURCE_PREFIX
								+ sinfo.getHashFolderName()+"/"+sinfo.getTitleImageName());
					}
				}
				break;
			}
			eventType = parser.next();
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return sinfo;
	}
	
	public static Art GetArt(String str)  {

		if (str == null || "".equals(str))
			return null;

		Art sinfo = null;
		try{	
		XmlPullParser parser = Xml.newPullParser();
		InputStream inputStream = new ByteArrayInputStream(str.getBytes());
		parser.setInput(inputStream, "UTF-8");
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				sinfo = new Art();
				break;
			case XmlPullParser.START_TAG:
				String name = parser.getName();

				if (sinfo != null) {
					if ("string".equals(name)) {
						sinfo.setImgPath(Constants.MONTH_ART_PIC_FILE+parser.nextText());
					} 
				}

				break;
			case XmlPullParser.END_TAG:
				break;
			}
			eventType = parser.next();
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return sinfo;
	}
	

	/**
	 * 获取会展列表
	 * @param str
	 * @return
	 */
	public static List<Huizhan> GetHuiZhan(String str)  {

		if (str == null || "".equals(str))
			return null;

		List<Huizhan> showList = null;
		Huizhan sinfo = null;
		try{	
		XmlPullParser parser = Xml.newPullParser();
		InputStream inputStream = new ByteArrayInputStream(str.getBytes());
		parser.setInput(inputStream, "UTF-8");
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				showList = new ArrayList<Huizhan>();
				break;
			case XmlPullParser.START_TAG:

				String name = parser.getName();

				if ("detail".equals(name)) {

					sinfo = new Huizhan();

				}

				if (sinfo != null) {

					if ("ShowID".equals(name)) {
						sinfo.setShowID(parser.nextText());
					} else if ("ShowTitle".equals(name)) {
						sinfo.setShowTitle(parser.nextText());
					} else if ("BeginTime".equals(name)) {
						sinfo.setBeginTime(parser.nextText());
					} else if ("EndTime".equals(name)) {
						sinfo.setEndTime(parser.nextText());
					} else if ("Address".equals(name)) {
						sinfo.setAddress(parser.nextText());
					} else if ("ShowImage1".equals(name)) {
						sinfo.setShowImg(parser.nextText());
					} else if ("ShowTypeName".equals(name)) {
						sinfo.setShowType(parser.nextText());
					} 
				}

				break;

			case XmlPullParser.END_TAG:

				if ("detail".equals(parser.getName())) {
					if(sinfo!=null){
						sinfo.setShowImg(Constants.RESOURCE_PREFIX
								+sinfo.getShowImg());
						showList.add(sinfo);
						sinfo = null;
					}
				}
				break;
			}
			eventType = parser.next();
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return showList;
	}
	
	
	public static Huizhan GetHuiZhanDetail(String str)  {

		if (str == null || "".equals(str))
			return null;

		Huizhan sinfo = null;
		try{	
		XmlPullParser parser = Xml.newPullParser();
		InputStream inputStream = new ByteArrayInputStream(str.getBytes());
		parser.setInput(inputStream, "UTF-8");
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				break;
			case XmlPullParser.START_TAG:

				String name = parser.getName();

				if ("detail".equals(name)) {

					sinfo = new Huizhan();

				}

				if (sinfo != null) {

					if ("ShowID".equals(name)) {
						sinfo.setShowID(parser.nextText());
					} else if ("ShowTitle".equals(name)) {
						sinfo.setShowTitle(parser.nextText());
					} else if ("BeginTime".equals(name)) {
						sinfo.setBeginTime(parser.nextText());
					} else if ("EndTime".equals(name)) {
						sinfo.setEndTime(parser.nextText());
					} else if ("Address".equals(name)) {
						sinfo.setAddress(parser.nextText());
					} else if ("ShowImage1".equals(name)) {
						sinfo.setShowImg(parser.nextText());
					} else if ("ShowTypeName".equals(name)) {
						sinfo.setShowType(parser.nextText());
					} else if ("Sponsor".equals(name)) {
						sinfo.setZhuban(parser.nextText());
					} else if ("ShowImage2".equals(name)) {
						sinfo.setDisplayImg(parser.nextText());
					} else if ("Phone".equals(name)) {
						sinfo.setPhone(parser.nextText());
					} else if ("Exhibitor".equals(name)) {
						sinfo.setMembers(parser.nextText());
					} else if ("Description".equals(name)) {
						sinfo.setDesc(parser.nextText());
					} else if ("ShowTypeName".equals(name)) {
						sinfo.setShowType(parser.nextText());
					} 
				}

				break;

			case XmlPullParser.END_TAG:

				if ("detail".equals(parser.getName())) {
					if(sinfo!=null){
						sinfo.setShowImg(Constants.RESOURCE_PREFIX
								+sinfo.getShowImg());
						sinfo.setDisplayImg(Constants.RESOURCE_PREFIX
								+sinfo.getDisplayImg());
					}
				}
				break;
			}
			eventType = parser.next();
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return sinfo;
	}
	
	public static List<ArtLesson> GetYiwenKetang(String str)  {

		if (str == null || "".equals(str))
			return null;

		List<ArtLesson> showList = null;
		ArtLesson sinfo = null;
		try{	
		XmlPullParser parser = Xml.newPullParser();
		InputStream inputStream = new ByteArrayInputStream(str.getBytes());
		parser.setInput(inputStream, "UTF-8");
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				showList = new ArrayList<ArtLesson>();
				break;
			case XmlPullParser.START_TAG:

				String name = parser.getName();

				if ("detail".equals(name)) {

					sinfo = new ArtLesson();

				}

				if (sinfo != null) {
					
					if ("ContentID".equals(name)) {
						sinfo.setContentID(parser.nextText());
					} else if ("ContentTitle".equals(name)) {
						sinfo.setContentTitle(parser.nextText());
					} else if ("HashFolderName".equals(name)) {
						sinfo.setHashFolderName(parser.nextText());
					} else if ("TitleImageName".equals(name)) {
						sinfo.setTitleImageName(parser.nextText());
					} else if ("Summary".equals(name)) {
						sinfo.setSummary(parser.nextText());
					} else if ("CreateTime".equals(name)) {
						sinfo.setCreateTime(parser.nextText());
					} else if ("Customlinks".equals(name)) {
						sinfo.setCustomlinks(parser.nextText());
					} 
				}

				break;

			case XmlPullParser.END_TAG:

				if ("detail".equals(parser.getName())) {
					if(sinfo!=null){
						sinfo.setTitleImageName(Constants.RESOURCE_PREFIX
								+ sinfo.getHashFolderName()+"/"+sinfo.getTitleImageName());
						showList.add(sinfo);
						sinfo = null;
					}
				}
				break;
			}
			eventType = parser.next();
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return showList;
	}
	
	
	public static ArtLesson GetYiwenKetangDetail(String str)  {

		if (str == null || "".equals(str))
			return null;

		ArtLesson sinfo = null;
		try{	
		XmlPullParser parser = Xml.newPullParser();
		InputStream inputStream = new ByteArrayInputStream(str.getBytes());
		parser.setInput(inputStream, "UTF-8");
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				break;
			case XmlPullParser.START_TAG:

				String name = parser.getName();

				if ("CMSContentInfo".equals(name)) {

					sinfo = new ArtLesson();

				}

				if (sinfo != null) {
					
					if ("ContentID".equals(name)) {
						sinfo.setContentID(parser.nextText());
					} else if ("ContentTitle".equals(name)) {
						sinfo.setContentTitle(parser.nextText());
					} else if ("HashFolderName".equals(name)) {
						sinfo.setHashFolderName(parser.nextText());
					} else if ("TitleImageName".equals(name)) {
						sinfo.setTitleImageName(parser.nextText());
					} else if ("Summary".equals(name)) {
						sinfo.setSummary(parser.nextText());
					} else if ("CreateTime".equals(name)) {
						sinfo.setCreateTime(parser.nextText());
					} else if ("Customlinks".equals(name)) {
						sinfo.setCustomlinks(parser.nextText());
					}  else if ("_createtime".equals(name)) {
						sinfo.setCreateTime(parser.nextText());
					} else if ("_description".equals(name)) {
						sinfo.setDesc(parser.nextText());
					} else if ("_effectdate".equals(name)) {
						sinfo.setEffectTime(parser.nextText());
					} else if ("_createtime".equals(name)) {
						sinfo.setCreateTime(parser.nextText());
					} 
				}

				break;

			case XmlPullParser.END_TAG:
				break;
			}
			eventType = parser.next();
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return sinfo;
	}
	
	
	/**
	 * parse the content of <string></string>
	 * @param str
	 * @return
	 */
	public static String getString(String str)  {

		String result=null;
		
		if (str == null || "".equals(str))
			return null;
		
		try{	
		XmlPullParser parser = Xml.newPullParser();
		InputStream inputStream = new ByteArrayInputStream(str.getBytes());
		parser.setInput(inputStream, "UTF-8");
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				break;
			case XmlPullParser.START_TAG:
				String name = parser.getName();
					if ("string".equals(name)) {
						result=parser.nextText();
					} 
				break;

			case XmlPullParser.END_TAG:
				break;
			}
			eventType = parser.next();
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result ;
	}
	
	/**
	 * 电影演出的时刻表
	 * @param str
	 * @return
	 */
	public static List<FilmSchedule> GetFilmScheduleList(String str)  {

		if (str == null || "".equals(str))
			return null;

		List<FilmSchedule> showList = null;
		FilmSchedule sinfo = null;
		try{	
		XmlPullParser parser = Xml.newPullParser();
		InputStream inputStream = new ByteArrayInputStream(str.getBytes());
		parser.setInput(inputStream, "UTF-8");
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				showList = new ArrayList<FilmSchedule>();
				break;
			case XmlPullParser.START_TAG:

				String name = parser.getName();

				if ("Schedul".equals(name)) {

					sinfo = new FilmSchedule();

				}

				if (sinfo != null) {
					if ("Time".equals(name)) {
						sinfo.setTime(parser.nextText());
					} else if ("Link".equals(name)) {
						sinfo.setLink(parser.nextText());
					} 
				}

				break;

			case XmlPullParser.END_TAG:

				if ("Schedul".equals(parser.getName())) {
					if(sinfo!=null){
						showList.add(sinfo);
						sinfo = null;
					}
				}
				break;
			}
			eventType = parser.next();
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return showList;
	}
	
	
	/**
	 * 微博信息电影json解析 
	 * @param str
	 * @return
	 */
	public static List<WeiboSina> GetWeiboList(String str)  {

		if (str == null || "".equals(str))
			return null;

		List<WeiboSina> showList = new ArrayList<WeiboSina>();
		WeiboSina weibo =null;
		UserSina user=null;
		
		try {
			
			JSONObject jsonAll =new JSONObject(str);
			
			JSONArray jsonarray=jsonAll.getJSONArray("statuses");
			
			if(jsonarray.length()>0){
				
				for(int i=0;i<jsonarray.length();i++	){
					JSONObject json=jsonarray.getJSONObject(i);
					
					//防止 结构变化
					try{
						json=jsonarray.getJSONObject(i).getJSONObject("retweeted_status");
					}catch(JSONException e){
						e.printStackTrace();
					}
					JSONObject json_user=json.getJSONObject("user");
					
					user=new UserSina();
					user.setBi_followers_count(json_user.getString("bi_followers_count"));
					user.setCreated_at(json_user.getString("created_at"));
					user.setLocation(json_user.getString("location"));
					user.setDescription(json_user.getString("description"));
					user.setGender(json_user.getString("gender"));
					user.setProfile_image_url(json_user.getString("profile_image_url"));
					user.setScreen_name(json_user.getString("screen_name"));
					
					weibo =new WeiboSina();
					weibo.setUser(user);
					weibo.setId(json.getString("id"));
					weibo.setCreate_time(json.getString("created_at"));
					weibo.setText(json.getString("text"));
					weibo.setSource(json.getString("source"));
					weibo.setThumbnail_pic(json.getString("thumbnail_pic"));
					weibo.setBmiddle_pic(json.getString("bmiddle_pic"));
					weibo.setOriginal_pic(json.getString("original_pic"));
					
					showList.add(weibo);
				}
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return showList;
	}
	
	/**
	 * 微博信息剧院json解析 
	 * @param str
	 * @return
	 */
	public static List<WeiboSina> GetWeiboTheatreList(String str)  {

		if (str == null || "".equals(str))
			return null;

		List<WeiboSina> showList = new ArrayList<WeiboSina>();
		WeiboSina weibo =null;
		UserSina user=null;
		
		try {
			
			JSONObject jsonAll =new JSONObject(str);
			
			JSONArray jsonarray=jsonAll.getJSONArray("statuses");
			
			if(jsonarray.length()>0){
				
				for(int i=0;i<jsonarray.length();i++	){
					
					JSONObject json=jsonarray.getJSONObject(i);
					//防止 结构变化
					try{
						json=jsonarray.getJSONObject(i).getJSONObject("retweeted_status");
					}catch(JSONException e){
						e.printStackTrace();
					}
					
					JSONObject json_user=json.getJSONObject("user");
					
					user=new UserSina();
					user.setBi_followers_count(json_user.getString("bi_followers_count"));
					user.setCreated_at(json_user.getString("created_at"));
					user.setLocation(json_user.getString("location"));
					user.setDescription(json_user.getString("description"));
					user.setGender(json_user.getString("gender"));
					user.setProfile_image_url(json_user.getString("profile_image_url"));
					user.setScreen_name(json_user.getString("screen_name"));
					
					weibo =new WeiboSina();
					weibo.setUser(user);
					weibo.setId(json.getString("id"));
					weibo.setCreate_time(json.getString("created_at"));
					weibo.setText(json.getString("text"));
					weibo.setSource(json.getString("source"));
					weibo.setThumbnail_pic(json.getString("thumbnail_pic"));
					weibo.setBmiddle_pic(json.getString("bmiddle_pic"));
					weibo.setOriginal_pic(json.getString("original_pic"));
					
					showList.add(weibo);
				}
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return showList;
	}
}
