package com.jt.web.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.ServiceException;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;
import com.jt.web.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private HttpClientService httpClientService;
	
	private final static ObjectMapper MAPPER=new ObjectMapper();
	
	@Override
	public void userRegister(User user) {
		String url="http://sso.jt.com/user/register";
		Map<String, String> params=new HashMap<>();
		params.put("username", user.getUsername());
		params.put("password", user.getPassword());
		params.put("phone", user.getPhone());
		try {
			String result=httpClientService.doPost(url, params);
			SysResult sysResult=MAPPER.readValue(result, SysResult.class);
			if(sysResult.getStatus()!=200) {
				throw new ServiceException("注册失败");
			}
		}catch (Exception e) {
			throw new RuntimeException();
		}
	}

	@Override
	public String doLogin(User user) {
		String url="http://sso.jt.com/user/login";
		Map<String, String> params=new HashMap<>();
		params.put("username", user.getUsername());
		params.put("password", user.getPassword());
		String result=null;
		try {
			String tokenJSON=httpClientService.doPost(url,params);
			SysResult sysResult=MAPPER.readValue(tokenJSON, SysResult.class);
			if(sysResult.getStatus()==200) {
				result=(String)sysResult.getData();
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return result;
	}

}
