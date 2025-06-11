package dto;

import java.io.Serializable;

public class loginuser implements Serializable {
	static private String user_id;
	static private String family_id;
	
	public loginuser(String user_id, String family_id) {
		loginuser.user_id = user_id;
		loginuser.family_id = family_id;
	}
	
	public static String getUser_id() {
		return user_id;
	}
	public static void setUser_id(String user_id) {
		loginuser.user_id = user_id;
	}
	public static String getFamily_id() {
		return family_id;
	}
	public static void setFamily_id(String family_id) {
		loginuser.family_id = family_id;
	}
	
	
}
