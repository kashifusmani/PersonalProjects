package com.addressbook.businessobjects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AUTHENTICATION")
public class Auth {
	
	@Id
	@Column(name = "userId")
	private int userId;

	@Column(name = "pwdhash")
	private String pwdhash;  

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPwdhash() {
		return pwdhash;
	}

	public void setPwdhash(String pwdhash) {
		this.pwdhash = pwdhash;
	}

}
