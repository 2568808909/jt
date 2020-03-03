package test1;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;

public class ExDemo {
	
	@Test
	public void tester() {
		HostAndPort hp=new HostAndPort("192.168.163.135", 6379);
		Jedis jedis=new Jedis(hp);
		jedis.psubscribe(new RedisPubSubListener(), "__key*__:*");
	}
	
	@Test
	public void testDemo() {
		HostAndPort hp=new HostAndPort("192.168.163.135", 6379);
		Jedis jedis=new Jedis(hp);
		jedis.set("test", "tom");
		System.out.println(jedis.get("test"));
	}
}
