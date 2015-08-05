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
 * 懒加载、急加载测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/spring-context.xml" })
public class FetchTypeTest extends TestCase {

	@Autowired
	HibernateTemplate hibernateTemplate;

	@Autowired
	UserDao userDao;

	@Before
	public void before() {
		System.out.println("============== Begin ==============");
	}

	/**
	 * 测试懒加载、急加载
	 */
	@Test
	public void testFetchType() {
		UserModel model = userDao.findById(2);

		System.out.println("begin");
		for (UserModel model2 : model.getFriends()) {
			System.out.println(model2);
		}
		System.out.println("end");
	}

}
