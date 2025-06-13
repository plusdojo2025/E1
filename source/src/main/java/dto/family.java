package dto;

import java.io.Serializable;

public class family implements Serializable {
	private String family_id;
	private String fami_pass;
	
	public String getFamily_id() {
		return family_id;
	}
	public void setFamily_id(String family_id) {
		this.family_id = family_id;
	}
	public String getFami_pass() {
		return fami_pass;
	}
	public void setFami_pass(String fami_pass) {
		this.fami_pass = fami_pass;
	}
	public family(String family_id, String fami_pass) {
		super();
		this.family_id = family_id;
		this.fami_pass = fami_pass;
	}
	public family() {
		super();
		this.family_id ="";
		this.fami_pass ="";
	}
	
	
}
