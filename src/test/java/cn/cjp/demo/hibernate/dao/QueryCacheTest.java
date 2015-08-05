package cn.cjp.demo.hibernate.dao;

import junit.framework.TestCase;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.cjp.demo.hibernate.model.UserModel;

/**
 * 查询缓存测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/spring-context.xml" })
public class QueryCacheTest extends TestCase {

	@Autowired
	HibernateTemplate hibernateTemplate;

	@Autowired
	UserDao userDao;

	@Before
	public void before() {
		System.out.println("============== Begin ==============");
	}

	/**
	 * 对查询缓存测试 <br>
	 * 1. 只有B处起作用，不可以跨session; <br>
	 * 2. 配置文件中打开query_cache的前提下，setCacheable 是起作用的
	 */
	@Test
	public void testCriteriaInCache() {
		System.out.println("=============");

		Session session = hibernateTemplate.getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(UserModel.class);
		criteria.setCacheable(true);
		criteria.add(Restrictions.eq("name", "Sucre"));
		System.out.println("=============1" + criteria.list());
		System.out.println("=============2" + criteria.list()); // B
		criteria.add(Restrictions.eq("id", 1));
		System.out.println("=============" + criteria.list());

		criteria = session.createCriteria(UserModel.class);
		criteria.setCacheable(true);
		criteria.add(Restrictions.eq("id", 1));
		System.out.println("=============" + criteria.list());

		session = hibernateTemplate.getSessionFactory().openSession();
		criteria = session.createCriteria(UserModel.class);
		criteria.setCacheable(true);
		criteria.add(Restrictions.eq("id", 1));
		System.out.println("=============" + criteria.list());
	}

	/**
	 * 测试查询缓存 <br>
	 * 1. BC两处都是使用的A产生的缓存，可以跨session <br>
	 * 2. 配置文件中打开query_cache的前提下，setCacheable 是起作用的
	 */
	@Test
	public void testQueryInCache() {
		System.out.println("=============");

		Session session = hibernateTemplate.getSessionFactory().openSession();
		Query query = session
				.createQuery("select u.name from UserModel as u where u.id=?");
		query.setParameter(0, 1);
		query.setCacheable(true);
		String name = (String) query.list().get(0);
		System.out.println("=============A" + name); // A

		query = session
				.createQuery("select u.name from UserModel as u where u.id=?");
		query.setParameter(0, 1);
		query.setCacheable(true);
		name = (String) query.list().get(0);
		System.out.println("=============B" + name); // B

		session = hibernateTemplate.getSessionFactory().openSession();
		query = session
				.createQuery("select u.name from UserModel as u where u.id=?");
		query.setParameter(0, 1);
		query.setCacheable(true);
		name = (String) query.list().get(0);
		System.out.println("=============C" + name); // C
	}

}
