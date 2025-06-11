package dto;

import java.io.Serializable;

public class today_housework implements Serializable {
private int today_housework_id;
private int housework_id;
private String date;

public today_housework(int today_housework_id, int housework_id, String date) {
	this.today_housework_id = today_housework_id;
	this.housework_id = housework_id;
	this.date = date;
}

public int getToday_housework_id() {
	return today_housework_id;
}

public void setToday_housework_id(int today_housework_id) {
	this.today_housework_id = today_housework_id;
}

public int getHousework_id() {
	return housework_id;
}

public void setHousework_id(int housework_id) {
	this.housework_id = housework_id;
}

public String getDate() {
	return date;
}

public void setDate(String date) {
	this.date = date;
}



}
