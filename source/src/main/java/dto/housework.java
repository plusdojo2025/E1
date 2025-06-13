package dto;

import java.io.Serializable;

public class housework implements Serializable {
	private int housework_id;
	private String housework_name;
	private String family_id;
	private int category_id;
	private int housework_level;
	private int noti_flag;
	private String noti_time;
	private int frequency;
	private String manual;
	private String fixed_role;
	private String variable_role;
	private int log;

		//今日の家事一覧表示用
		public housework(int housework_id, String housework_name, String family_id, int category_id,
				int housework_level, int noti_flag, String noti_time, int frequency, String manual, String fixed_role,
				String variable_role, int log) {
			this.housework_id = housework_id;
			this.housework_name = housework_name;
			this.family_id = family_id;
			this.category_id = category_id;
			this.housework_level = housework_level;
			this.noti_flag = noti_flag;
			this.noti_time = noti_time;
			this.frequency = frequency;
			this.manual = manual;
			this.fixed_role = fixed_role;
			this.variable_role = variable_role;
			this.log = log;
		}

public int getHousework_id() {
	return housework_id;
}
public void setHousework_id(int housework_id) {
	this.housework_id = housework_id;
}
public String getHousework_name() {
	return housework_name;
}
public void setHousework_name(String housework_name) {
	this.housework_name = housework_name;
}
public String getFamily_id() {
	return family_id;
}
public void setFamily_id(String family_id) {
	this.family_id = family_id;
}
public int getCategory_id() {
	return category_id;
}
public void setCategory_id(int category_id) {
	this.category_id = category_id;
}
public int getHousework_level() {
	return housework_level;
}
public void setHousework_level(int housework_level) {
	this.housework_level = housework_level;
}
public int getNoti_flag() {
	return noti_flag;
}
public void setNoti_flag(int noti_flag) {
	this.noti_flag = noti_flag;
}
public String getNoti_time() {
	return noti_time;
}
public void setNoti_time(String noti_time) {
	this.noti_time = noti_time;
}
public int getFrequency() {
	return frequency;
}
public void setFrequency(int frequency) {
	this.frequency = frequency;
}
public String getManual() {
	return manual;
}
public void setManual(String manual) {
	this.manual = manual;
}
public String getFixed_role() {
	return fixed_role;
}
public void setFixed_role(String fixed_role) {
	this.fixed_role = fixed_role;
}
public String getVariable_role() {
	return variable_role;
}
public void setVariable_role(String variable_role) {
	this.variable_role = variable_role;
}
public int getLog() {
	return log;
}
public void setLog(int log) {
	this.log = log;
}


}
