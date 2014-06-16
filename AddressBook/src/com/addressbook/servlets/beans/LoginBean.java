package com.addressbook.servlets.beans;

/**
 * This bean is used to transfer user supplied data from "login" form.
 * @author kashifu
 *
 */
public class LoginBean {

	private String email;
	private String password;
	private String registrationType;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRegistrationType() {
		return registrationType;
	}
	public void setRegistrationType(String registrationType) {
		this.registrationType = registrationType;
	}
	
	
}
