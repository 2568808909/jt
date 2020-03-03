package com.jt.sso.service.impl;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.sso.mapper.UserMapper;
import com.jt.sso.pojo.User;
import com.jt.sso.service.UserService;

import redis.clients.jedis.JedisCluster;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JedisCluster jedisCluster;

    private final static ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public boolean findUser(String value, Integer type) {
        String column = "";
        switch (type) {
            case 1:
                column = "username";
                break;
            case 2:
                column = "phone";
                break;
            case 3:
                column = "email";
                break;
            default:
                break;
        }
        return userMapper.findUser(column, value) == 0;
    }

    @Override
    public void saveUser(User user) {
        String password = user.getPassword();
        password = DigestUtils.md5Hex(user.getPassword());
        user.setPassword(password);
        user.setCreated(new Date());
        user.setUpdated(user.getCreated());
        user.setEmail(user.getPhone());
        userMapper.insert(user);
    }

    /**
     * 通过账号密码查找用户，查找成功后，生成token，放入redis，再返回token
     */
    @Override
    public String findUserByUP(User user) {
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        User userDB = userMapper.findUserByUP(user);
        String token = DigestUtils.md5Hex("JT_TICKET" + System.currentTimeMillis() + userDB.getUsername());
        try {
            String userJSON = MAPPER.writeValueAsString(userDB);
            jedisCluster.setex(token, 604800, userJSON);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

}
