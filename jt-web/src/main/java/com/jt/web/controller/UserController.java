package com.jt.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.util.CookieUtils;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;
import com.jt.web.service.UserService;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JedisCluster JedisCluster;
	
	/**
	 * 登录/注册页面的跳转
	 * @param model
	 * @return
	 */
	@RequestMapping("/{model}")
	public String operation(@PathVariable String model) {
		return model;
	}
	
	/**
	 * 注册
	 * @param user
	 * @return
	 */
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult doRegister(User user) {
		try {
			userService.userRegister(user);
			return SysResult.oK();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"注册失败");
	}
	
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult doLogin(User user,HttpServletResponse response) {
		try {
			String token=userService.doLogin(user);
			Cookie cookie=new Cookie("JT_TICKET", token);
			cookie.setPath("/");
			cookie.setMaxAge(604800);
			response.addCookie(cookie);
			return SysResult.oK();
		}catch (Exception e) {
			return SysResult.build(201, e.getMessage());
		}
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		String token=CookieUtils.getCookieValue(request, "JT_TICKET");
		JedisCluster.del(token);
		CookieUtils.deleteCookie(request, response, "JT_TICKET");
		return "redirect:/index.html";
	}
}
