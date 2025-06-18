package dto;

import java.io.Serializable;


public class notification implements Serializable{
	private int noti_id;		//通知ID
	private String user_id;		//ユーザーID
	private String noti_datetime;//通知時間
	private String noti_content;//通知内容
	
	public notification(int noti_id,String user_id,String noti_datetime,String noti_content) {
		this.noti_id=noti_id;
		this.user_id=user_id;
		this.noti_datetime=noti_datetime;
		this.noti_content=noti_content;
	}
	
	public notification() {
		this.noti_id=0;
		this.user_id="";
		this.noti_datetime="";
		this.noti_content="";
	}
	
	public int getNoti_id() {
		return noti_id;
	}
	public void setNoti_id(int noti_id) {
		this.noti_id=noti_id;
	}
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id=user_id;
	}
	
	public String getNoti_datetime() {
		return noti_datetime;
	}
	public void setNoti_datetime(String noti_datetime) {
		this.noti_datetime=noti_datetime;
	}
	
	public String getNoti_content() {
		return noti_content;
	}
	public void setNoti_content(String noti_content) {
		this.noti_content=noti_content;
	}
}
