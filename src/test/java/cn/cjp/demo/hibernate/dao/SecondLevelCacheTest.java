package cn.cjp.demo.hibernate.dao;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.cjp.demo.hibernate.model.UserModel;

/**
 * 二级缓存测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/spring-context.xml" })
public class SecondLevelCacheTest extends TestCase {

	@Autowired
	HibernateTemplate hibernateTemplate;

	@Autowired
	UserDao userDao;

	@Before
	public void before() {
		System.out.println("============== Begin ==============");
	}

	/**
	 * 测试二级缓存
	 */
	@Test
	public void test() {

		UserModel model = new UserModel(1);
		
		System.out.println("===============get1");
		model = hibernateTemplate.get(UserModel.class, model.getId());
		System.out.println(model);
		
		System.out.println("===============get2"); // 得到二级缓存
		model = hibernateTemplate.get(UserModel.class, model.getId());
		System.out.println(model);

		System.out.println("===============find");
		model = (UserModel) hibernateTemplate.find(
				"from UserModel where name='1438769000024'").get(0);
		System.out.println(model);

		System.out.println("===============find"); // 得到查询缓存
		model = (UserModel) hibernateTemplate.find(
				"from UserModel where name='1438769000024'").get(0);
		System.out.println(model);

	}

}
