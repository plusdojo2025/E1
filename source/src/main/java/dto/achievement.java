package dto;

import java.io.Serializable;

public class achievement implements Serializable {
private int achieve_id;             /*実績ID*/
private String user_id;             /*ユーザーID*/
private String date;                /*日付*/
private int achieve_history;        /*実績*/

public achievement(int achieve_id, String user_id, String date, int achieve_history) {
	this.achieve_id = achieve_id;
	this.user_id = user_id;
	this.date = date;
	this.achieve_history = achieve_history;
}

public int getAchieve_id() {
	return achieve_id;
}
public void setAchieve_id(int achieve_id) {
	this.achieve_id = achieve_id;
}
public String getUser_id() {
	return user_id;
}
public void setUser_id(String user_id) {
	this.user_id = user_id;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public int getAchieve_history() {
	return achieve_history;
}
public void setAchieve_history(int achieve_history) {
	this.achieve_history = achieve_history;
}

}

