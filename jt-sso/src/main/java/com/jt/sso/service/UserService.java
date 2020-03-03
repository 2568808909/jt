package com.jt.sso.service;

import com.jt.sso.pojo.User;

public interface UserService {

	boolean findUser(String value,Integer type);
	
	void saveUser(User user);
	
	String findUserByUP(User user);
}
