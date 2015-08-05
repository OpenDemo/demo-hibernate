package cn.cjp.demo.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import cn.cjp.demo.hibernate.model.UserModel;

/**
 * 数据访问层基类
 * 
 * @author SucreCui
 * 
 * @param <UserModel>
 */
@Component
public class UserDao {

	@Resource
	private HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	public List<Object> query(String hql) {
		return this.getHibernateTemplate().find(hql);
	}

	/**
	 * 如果存在Id，则update，否则save
	 * 
	 * @param entity
	 */
	public void saveOrUpdate(UserModel entity) {
		if (entity.getId() == 0) {
			hibernateTemplate.save(entity);
		} else {
			hibernateTemplate.update(entity);
		}
	}

	public UserModel findById(Serializable id) {
		SessionFactory sessionFactory = this.getHibernateTemplate()
				.getSessionFactory();
		Session session = sessionFactory.openSession();


		System.out.println("findById(" + id + ")");
		UserModel model = (UserModel) session.get(UserModel.class, id);
		System.out.println("find end");
		// session.close();
		return model;
		// return this.getHibernateTemplate().get(UserModel.class, id);
	}

	public long count() {
		return (Long) this.getHibernateTemplate()
				.find("select count(*) from UserModel").get(0);
	}

	@SuppressWarnings("unchecked")
	public List<UserModel> findAll() {
		return this.getHibernateTemplate().find("from UserModel");
	}

	@SuppressWarnings("unchecked")
	public List<UserModel> findAll(int pageNum, int pageSize) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		Criteria criteria = session.createCriteria(UserModel.class);

		criteria.setFirstResult((pageNum - 1) * pageSize);
		criteria.setMaxResults(pageSize);

		return criteria.list();
	}

	public void delete(int id) {
		try {
			this.getHibernateTemplate().delete(new UserModel(id));
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param entity
	 */
	public void update(UserModel entity) {
		hibernateTemplate.update(entity);
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

}
