package com.ssac.expro.kewen.bean;

public class Constants {

	public static String UPDATE_VERSION="http://www.sscac.com.cn:8080/fy/AndroidVersion";
	
	public static String LOCAPIC_NEWS_PATH="/expro/pic/home";
	
	public static final String RESOURCE_PREFIX="http://www.sscac.com.cn/cmsattachment/";//图片的存放路径
	
	public static final String HOME_AD="http://www.sscac.com.cn:8080/fy/AD/61";//首页 图片广告
	
	public static final String YANCHU_AD="http://www.sscac.com.cn:8080/fy/ad/65";//演出的 图片广告
	/**
	 * 	解析起止节点字段：detail
			参考字段：
		A.	DramaID：演出ID，用于点击查看详细
		B.	DramaName：演出名
		C.	HashFolderName：图片中间路径名
		D.	TitleImageName：图片名
		E.	ShowTime：上映时间
		F.	Price：票价
		G.	DramaTypeText：演出类型
		H.	Link：购票链接（链接内容可能需要修改）
			图片路径：Perfix/ HashFolderName/ TitleImageName
	 */
	public static final String YANCHU_ZIXUN="http://www.sscac.com.cn:8080/fy/CAShow/";//资讯的 图片广告
	
	public static final String ZIXUN_DETAIL="http://www.sscac.com.cn:8080/fy/CAShow/";//资讯详情
	
	public static final String JUYUAN_ACTIVITIES="http://www.sscac.com.cn:8080/fy/ContentList/3a089441-b219-49bc-8cfc-30efb8e12672/";//剧院活动
	
	public static final String JUYUAN_ACTIVITIES_DETAIL="http://www.sscac.com.cn:8080/fy/ContentDetail/";//剧院活动详情
	
	public static final String YIWEN_ZHOUKAN="http://www.sscac.com.cn:8080/fy/ContentList/3a089441-b219-49bc-8cfc-30efb8e12672/";
			
	public static final String DIANYING_LIST="http://www.sscac.com.cn:8080/fy/IMAXFilm/";//电影列表
	
	public static final String DIANYING_DETAIL="http://www.sscac.com.cn:8080/fy/IMAXFilm/";//影片详情
	
	public static final String DIANYING_SCHEDUL="http://www.sscac.com.cn:8080/fy/IMAXFilmSchedul/";//影片详情
	
	public static final String DIANYING_DONGTAI="http://www.sscac.com.cn:8080/fy/ContentList/a51a82e3-3236-4480-9bce-5c152f8cb8e4";
	//影城活动
	public static final String YINGCHENG_ACTIVITIES="http://www.sscac.com.cn:8080/fy/ContentList/cc440b27-c0e3-4ff4-903e-b2ae5b48392f/";
	
	public static final String YINGCHENG_DONGTAI="http://www.sscac.com.cn:8080/fy/ContentList/a51a82e3-3236-4480-9bce-5c152f8cb8e4/";
	
	public static final String PAIPIAN_INFO="http://www.sscac.com.cn:8080/fy/IMAXFilmSchedul/";//拍片信息
	
	public static final String MONTH_ART="http://www.sscac.com.cn:8080/fy/Artslink";
	
	public static final String MONTH_ART_PIC_FILE="http://www.sscac.com.cn/cmsattachment/";
	
//	public static final String ABOUNT_WENBO="http://www.sscac.com.cn/Description/ZTJS";
	
	public static final String HUIZHAN_LIST="http://www.sscac.com.cn:8080/fy/ShowList/";
	
	public static final String HUIZHAN_DETAIL="http://www.sscac.com.cn:8080/fy/ShowDetail/";
	
	public static final String YITAN_YISHU_KETANG="http://www.sscac.com.cn:8080/fy/ContentList/960a1b7e-3a45-4430-930d-cd6f7550f00d/";
	
	public static final String YITAN_YISHU_SHARE="http://www.sscac.com.cn:8080/fy/ContentList/12a5260c-0a96-4f8f-82b2-a65157566feb/";
	
	public static final String YITAN_WENHUA_SHIYE="http://www.sscac.com.cn:8080/fy/ContentList/71002643-6d6e-46ae-8c12-5d54a05149a0/";
	
	public static final String YITAN_MEIYU_CHUANBO="http://www.sscac.com.cn:8080/fy/ContentList/5a875146-b99a-4651-8980-3f5ed614362c/";
	
	public static final String YITAN_JUJIAO_MINGJIA="http://www.sscac.com.cn:8080/fy/ContentList/1adcd6fb-9f9d-40d7-afe3-c678ce0817da/";
	
	public static final String YITAN_YISHUHUI="http://www.sscac.com.cn:8080/fy/ContentList/33357009-b566-4384-b936-8c1915cdc4d9/";
	
	public static final String YITAN_SPECIAL_ACTIVIES="http://www.sscac.com.cn:8080/fy/ContentList/f5f8c22d-4636-4156-a9f7-d50716a3056e/";
	
	//描述文字
	public static final String YINGCHENG_DESCRIPTION="http://www.sscac.com.cn:8080/fy/Description/YCJS";
	
	public static final String ABOUNT_WENBO="http://www.sscac.com.cn:8080/fy/Description/ZTJS";
	
	
	//weibo string 
	//应用的key 请到官方申请正式的appkey替换APP_KEY
		public static final String APP_KEY="3983929670";//"1992416536";//"2045436852";
		//app secret
		public static final String APP_SECRET="1e1d6d877ce9962b4aee4dc3c14d7d43";//"3b8aca8963d083f6ab82ee4901875993";
		//替换为开发者REDIRECT_URL
		public static final String REDIRECT_URL = "http://www.cnsaas.com";//"http://www.sina.com";
		//新支持scope 支持传入多个scope权限，用逗号分隔
		public static final String SCOPE = "email,direct_messages_read,direct_messages_write," +
				"friendships_groups_read,friendships_groups_write,statuses_to_me_read," +
					"follow_app_official_microblog";
		
		public static final String API_SERVER="https://api.weibo.com/oauth2/access_token";
		
		public static final String CLIENT_ID = "client_id";
		
		public static final String CLIENT_SECRET="client_secret";
		
		public static final String GRANT_TYPE="grant_type";
		
		public static final String AUTHORIZATION_CODE="authorization_code";
		
		public static final String CODE="code";
		
		public static final String RESPONSE_TYPE = "response_type";
		
		public static final String USER_REDIRECT_URL = "redirect_uri";
		
		public static final String DISPLAY = "display";
		
		public static final String USER_SCOPE = "scope";
		
		public static final String PACKAGE_NAME = "packagename";
		
		public static final String KEY_HASH = "key_hash";
		
		public static final String URL_WEIBO_TIME_BACH="https://api.weibo.com/2/statuses/timeline_batch.json";
		
		public static final String UIDS="uids";
		
		public static final String  ACCESS_TOKEN="access_token";
		
		
		//shared weibo id
		public static final String WEIBO_SINA_THEATRE_ID="1914016804";//剧院
		public static final String WEIBO_SINA_FILM_ID="1849261382";//电影
}
