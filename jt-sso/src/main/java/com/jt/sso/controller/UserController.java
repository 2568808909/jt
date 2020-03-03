package com.jt.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.sso.pojo.User;
import com.jt.sso.service.UserService;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JedisCluster JedisCluster;


    /**
     * 使用jsonp解决跨域问题，以验证用户信息
     *
     * @param value
     * @param type
     * @param callback
     * @return
     */
    @RequestMapping("/check/{value}/{type}")
    @ResponseBody
    public MappingJacksonValue check(@PathVariable String value, @PathVariable Integer type, String callback) {
        //System.out.println(value+"/"+type+"/"+callback);
        MappingJacksonValue jacksonValue = new MappingJacksonValue(SysResult.oK(userService.findUser(value, type)));
        jacksonValue.setJsonpFunction(callback);
        return jacksonValue;
    }

    /**
     * 接收前台发来的注册请求
     *
     * @param user
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public SysResult doRegister(User user) {
        userService.saveUser(user);
        return SysResult.oK();
    }

    @RequestMapping("/login")
    @ResponseBody
    public SysResult doLogin(User user) {
        String token = userService.findUserByUP(user);
        return SysResult.oK(token);
    }

    @RequestMapping("/query/{token}")
    @ResponseBody
    public MappingJacksonValue query(@PathVariable String token, String callback) {
        MappingJacksonValue jacksonValue;
        String userJSON = JedisCluster.get(token);
        try {
            jacksonValue = new MappingJacksonValue(SysResult.oK(userJSON));
        } catch (Exception e) {
            jacksonValue = new MappingJacksonValue(SysResult.build(201, "failure"));
        }
        jacksonValue.setJsonpFunction(callback);
        return jacksonValue;
    }

}
