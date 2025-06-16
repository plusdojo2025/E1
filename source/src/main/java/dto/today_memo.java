package dto;

import java.io.Serializable;

public class today_memo implements Serializable {
private int memo_id;
private String family_id;
private String memo;
private String date;

public today_memo(int memo_id, String family_id, String memo) {
	this.memo_id = memo_id;
	this.family_id = family_id;
	this.memo = memo;
}

public today_memo(int memo_id, String family_id, String memo, String date) {
	this.memo_id = memo_id;
	this.family_id = family_id;
	this.memo = memo;
	this.date = date;
}

public int getMemo_id() {
	return memo_id;
}
public void setMemo_id(int memo_id) {
	this.memo_id = memo_id;
}
public String getFamily_id() {
	return family_id;
}
public void setFamily_id(String family_id) {
	this.family_id = family_id;
}
public String getMemo() {
	return memo;
}
public void setMemo(String memo) {
	this.memo = memo;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}

}
