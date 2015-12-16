package cn.cjp.demo.hibernate.dao;

import junit.framework.TestCase;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.cjp.demo.hibernate.model.UserModel;

/**
 * 一级缓存测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/spring-context.xml" })
public class FirstLevelCacheTest extends TestCase {

	@Autowired
	HibernateTemplate hibernateTemplate;

	@Autowired
	UserDao userDao;

	@Before
	public void before() {
		System.out.println("============== Begin ==============");
	}
	
	/**
	 * 测试缓存更新策略
	 */
	@Test
	public void testClearWhileUpdate() {
		System.out.println(userDao.findAll());

		System.out.println(userDao.findAll());
		
		UserModel sucre = new UserModel(1);
		sucre.setAddr("北京市");
		sucre.setName("Sucre");
//		userDao.saveOrUpdate(sucre);

		System.out.println(userDao.findAll());
	}

	/**
	 * 测试一级缓存
	 */
	@Test
	public void testIteratorInCache() {
		System.out.println("=============");

		Session session = hibernateTemplate.getSessionFactory().openSession();
		Object object = session.createQuery("from UserModel where id=1")
				.iterate().next();
		System.out.println("===创建一个session：" + object);

		object = session.createQuery("from UserModel where id=1").iterate()
				.next();
		System.out.println("===同一个session：" + object); // 命中缓存，使用了懒加载的缓存，然而上面的hql查询，仍旧进行了一次查询

		session = hibernateTemplate.getSessionFactory().openSession();
		object = session.createQuery("from UserModel where id=1").iterate()
				.next();
		System.out.println("===新的session" + object); // 无缓存

		System.out.println("=============");
		System.out.println("=============");

		session = hibernateTemplate.getSessionFactory().openSession();
		object = session.createQuery("select name from UserModel where id=1")
				.iterate().next();
		System.out.println("===创建一个session：" + object);

		object = session.createQuery("select name from UserModel where id=1")
				.iterate().next();
		System.out.println("===同一个session：" + object);

		session = hibernateTemplate.getSessionFactory().openSession();
		object = session.createQuery("select name from UserModel where id=1")
				.iterate().next();
		System.out.println("===新的session" + object); // 无缓存
	}

	/**
	 * 对get、load进行一级缓存测试 <br>
	 * 在同一个session里，才可以使用一级缓存
	 */
	@Test
	public void testGetAndLoadInCache() {
		System.out.println("=============");

		Session session = hibernateTemplate.getSessionFactory().openSession();
		Object object = session.load(UserModel.class, 1);
		System.out.println("===创建一个session：" + object);

		object = session.load(UserModel.class, 1);
		System.out.println("===同一个session：" + object); // 成功命中缓存

		session = hibernateTemplate.getSessionFactory().openSession();
		object = session.load(UserModel.class, 1);
		System.out.println("===新的session" + object); // 无缓存
		System.out.println("=============");

		session = hibernateTemplate.getSessionFactory().openSession();
		object = session.get(UserModel.class, 1);
		System.out.println("===创建一个session：" + object);

		object = session.get(UserModel.class, 1);
		System.out.println("===同一个session：" + object); // 成功命中缓存

		session = hibernateTemplate.getSessionFactory().openSession();
		object = session.get(UserModel.class, 1);
		System.out.println("===新的session" + object); // 无缓存
	}

}
