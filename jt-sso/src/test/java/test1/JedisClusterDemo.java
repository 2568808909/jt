package test1;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sun.org.apache.xml.internal.security.Init;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class JedisClusterDemo {
	
	private AbstractApplicationContext ctx;
	
	private JedisCluster jedisCluster;
	
	@Before
	public void Init() {
		ctx=new ClassPathXmlApplicationContext("spring/*.xml");
		jedisCluster=ctx.getBean("jedisCluster",JedisCluster.class);
	}
	
	@Test
	public void close() {
		ctx.close();
	}
	
	@Test
	public void Tester() {
		jedisCluster.psubscribe(new RedisPubSubListener(),"__key*__:*");
	}
	
	@Test
	public void Tester02() {
		jedisCluster.setex("test", 10, "jedis");
		System.out.println(jedisCluster.get("test"));
	}
}
