package com.jt.web.thread;

import com.jt.web.pojo.User;

public class UserThreadLocal {
	
	private final static ThreadLocal<User> THREAD_LOCAL=new ThreadLocal<>();
	
	public static void set(User user) {
		THREAD_LOCAL.set(user);
	}
	
	public static User get() {
		return THREAD_LOCAL.get();
	}
	
	public static void remove() {
		THREAD_LOCAL.remove();
	}
}
