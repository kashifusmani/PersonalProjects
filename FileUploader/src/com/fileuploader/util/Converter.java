package com.fileuploader.util;

import com.fileuploader.businessobjects.User;
import com.fileuploader.servlets.beans.RegistrationBean;

/**
 * Adapter class to provide conversion between object types.
 * 
 * @author kashifu
 * 
 */
public class Converter {

	public static User toUser(RegistrationBean registerBean) {
		User user = new User();
		user.setEmail(registerBean.getEmail());
		user.setFirstname(registerBean.getFirstname());
		user.setLastname(registerBean.getLastname());
		user.setRegistrationType(registerBean.getRegistrationType());
		return user;
	}

}
