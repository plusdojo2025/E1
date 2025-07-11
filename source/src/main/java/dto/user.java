package dto;

import java.io.Serializable;

public class user implements Serializable {
	private String user_id;
	private String family_id;
	private String password;
	private Float share_goal;
	
	//分担ガチャ用変数
	private float user_level;
	
	public void addUser_level(int user_level) {
		this.user_level-=user_level;
	}
	
	public float getUser_level() {
		return user_level;
	}

	public void setUser_level(float user_level) {
		this.user_level = user_level;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Float getShare_goal() {
		return share_goal;
	}

	public void setShare_goal(Float share_goal) {
		this.share_goal = share_goal;
	}

	public user(String user_id, String family_id, String password, Float share_goal) {
		super();
		this.user_id = user_id;
		this.family_id = family_id;
		this.password = password;
		this.share_goal = share_goal;
	}
	
    public user(String user_id, String password) {
        super();
        this.user_id = user_id;
        this.password = password;
    }

    //分析用
    public user(String user_id, Float share_goal) {
    	this.user_id = user_id;
    	this.share_goal = share_goal;
    }

    
    //登録用
    public user(String user_id) {
        this.user_id = user_id;
    }
    
	public user() {
		super();
		this.user_id = "";
		this.family_id = "";
		this.password = "";
		this.share_goal = null;
	}
	
}
