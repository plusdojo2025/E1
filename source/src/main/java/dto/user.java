package dto;

import java.io.Serializable;

public class user implements Serializable {
	private String user_id;
	private String user_name;
	private String family_id;
	private String password;
	private float share_goal;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getFamily_id() {
		return family_id;
	}

	public void setFamily_id(String family_id) {
		this.family_id = family_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public float getShare_goal() {
		return share_goal;
	}

	public void setShare_goal(float share_goal) {
		this.share_goal = share_goal;
	}

	public user(String user_id, String user_name, String family_id, String password, float share_goal) {
		super();
		this.user_id = user_id;
		this.user_name = user_name;
		this.family_id = family_id;
		this.password = password;
		this.share_goal = share_goal;
	}
	
    public user(String user_id, String password) {
        super();
        this.user_id = user_id;
        this.password = password;
    }
    
	public user() {
		super();
		this.user_id = "";
		this.user_name = "";
		this.family_id = "";
		this.password = "";
		this.share_goal = 0;
	}
	
}
