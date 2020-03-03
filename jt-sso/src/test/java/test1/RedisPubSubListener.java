package test1;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.JedisSentinelPool;

public class RedisPubSubListener extends JedisPubSub {  
    // 取得订阅的消息后的处理  
    public void onMessage(String channel, String message) {
        //TODO:接收订阅频道消息后，业务处理逻辑
    	System.out.println("onMessage");
        System.out.println(channel + "=" + message);  
    }  

    // 初始化订阅时候的处理  
    public void onSubscribe(String channel, int subscribedChannels) {  
        // System.out.println(channel + "=" + subscribedChannels);  
    }  

    // 取消订阅时候的处理  
    public void onUnsubscribe(String channel, int subscribedChannels) {  
        // System.out.println(channel + "=" + subscribedChannels);  
    }  

    // 初始化按表达式的方式订阅时候的处理  
    public void onPSubscribe(String pattern, int subscribedChannels) {  
        // System.out.println(pattern + "=" + subscribedChannels);  
    }  

    // 取消按表达式的方式订阅时候的处理  
    public void onPUnsubscribe(String pattern, int subscribedChannels) {  
        // System.out.println(pattern + "=" + subscribedChannels);  
    }  

    // 取得按表达式的方式订阅的消息后的处理  
    public void onPMessage(String pattern, String channel, String message) { 
    	HostAndPort hp=new HostAndPort("192.168.163.135", 6379);
		Jedis jedis=new Jedis(hp);
		Random random=new Random();
		jedis.set("callback"+random.nextInt(1000), "expire");
    	System.out.println("onPMessage");
        System.out.println(pattern + "=" + channel + "=" + message);  
    }  
}