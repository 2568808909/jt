package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.JedisCluster;


public class Tester {
	
	private AbstractApplicationContext ctx;
	
	@Before
	public void init() {
		ctx=new ClassPathXmlApplicationContext("spring/*.xml");
	}
	
	@After
	public void close() {
		ctx.close();
	}
	
	@Test
	public void TestEx() {
		JedisCluster jedisCluster=ctx.getBean("jedisCluster",JedisCluster.class);
		jedisCluster.setex("test", 10, "ex");
		System.out.println(jedisCluster.get("test"));
		
		while(true) {
			
		}
	}
}
