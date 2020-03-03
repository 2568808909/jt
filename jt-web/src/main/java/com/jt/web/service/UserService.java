package com.jt.web.service;

import com.jt.web.pojo.User;

public interface UserService {
	
	/**
	 * 注册
	 * @param user
	 */
	void userRegister(User user);
	
	/**
	 * 登录
	 */
	String doLogin(User user);
}
