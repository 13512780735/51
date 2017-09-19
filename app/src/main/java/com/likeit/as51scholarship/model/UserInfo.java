package com.likeit.as51scholarship.model;

/**
 * 用户信息实体类
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-9-9 下午5:02:52
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class UserInfo {

	private String ukey;
	private String nickname;
	private String avatar;
	private String lng;// 经度
	private String lat;// 纬度

	public UserInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUkey() {
		return ukey;
	}

	public void setUkey(String ukey) {
		this.ukey = ukey;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}
}
