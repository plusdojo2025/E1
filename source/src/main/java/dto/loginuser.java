package dto;

import java.io.Serializable;

public class loginuser implements Serializable {
	private String user_id;
	private String family_id;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getFamily_id() {
		return family_id;
	}
	public void setFamily_id(String family_id) {
		this.family_id = family_id;
	}
	public loginuser(String user_id, String family_id) {
		super();
		this.user_id = user_id;
		this.family_id = family_id;
	}
	public loginuser(String user_id) {
		super();
		this.user_id = user_id;
	}
	
}
