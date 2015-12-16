package cn.cjp.demo.hibernate.dao;

import java.util.ArrayList;

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
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/spring-context.xml" })
public class UserDaoTest extends TestCase {

	@Autowired
	HibernateTemplate hibernateTemplate;

	@Autowired
	UserDao userDao;

	@Before
	public void before() {
		System.out.println("============== Begin ==============");
	}

	@Test
	public void testSave() {

		UserModel sucre = new UserModel(1);
		sucre.setAddr("北京市");
		sucre.setName("Sucre");

		UserModel lucy = new UserModel();
		lucy.setAddr("北京市");
		lucy.setName("lucy");

		userDao.saveOrUpdate(sucre);

		if (lucy.getFriends() == null) {
			lucy.setFriends(new ArrayList<UserModel>());
		}
		lucy.getFriends().add(sucre);
		userDao.saveOrUpdate(lucy);

		long count = userDao.count();
		System.out.println(count);
	}

}
