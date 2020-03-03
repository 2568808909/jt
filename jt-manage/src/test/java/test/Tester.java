package test;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jt.manage.mapper.ItemMapper;
import com.jt.manage.pojo.Item;

public class Tester {

	private AbstractApplicationContext ac;
	
	@Before
	public void init() {
		ac=new ClassPathXmlApplicationContext("spring/applicationContext.xml","spring/applicationContext-mybatis.xml");
	}
	
	@After
	public void close() {
		ac.close();
	}
	
	@Test
	public void ItemMapperDemo() {
		ItemMapper itemMapper=ac.getBean("itemMapper",ItemMapper.class);
		List<Item> items=itemMapper.findItemPage(0, 20);
		for (Item item : items) {
			System.out.println(item);
		}
		System.out.println(itemMapper.getItemCount());
	}

}
