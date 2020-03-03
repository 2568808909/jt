package com.jt.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.util.CookieUtils;
import com.jt.web.pojo.User;
import com.jt.web.thread.UserThreadLocal;

import redis.clients.jedis.JedisCluster;

public class UserInterceptor implements HandlerInterceptor{

	@Autowired
	private JedisCluster jedisCluster;
	
	private final static ObjectMapper MAPPER=new ObjectMapper();
	
	/**
	 * 1.在调用controller方法之前拦截
	 * boolean 代表   
	 * 		true代表放行  false表示拦截
	 * 拦截器使用用户登陆校验
	 * 1.获取客户端端的Cookie
	 * 2.判断cookie是否有token数据
	 * 3.判断redis中是否有用户json数据
	 * 如果用户都满足要求则放行.否则跳转登录页面
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token=CookieUtils.getCookieValue(request, "JT_TICKET");
		if(!StringUtils.isEmpty(token)) {
			String userJSON=jedisCluster.get(token);
			if(!StringUtils.isEmpty(userJSON)) {
				User user=MAPPER.readValue(userJSON, User.class);
				UserThreadLocal.set(user);
				return true;
			}
		}
		//无用户信息，则重定向至登录页面
		response.sendRedirect("/user/login.html");
		return false;
	}
	
	//在业务逻辑执行完成后拦截
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	//在业务逻辑执行完之后返回给客户端之前拦截
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//为了防止内存泄漏移除Threadlocal内数据
		UserThreadLocal.remove();
	}

}
