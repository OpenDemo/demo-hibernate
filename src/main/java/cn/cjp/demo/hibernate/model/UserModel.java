package cn.cjp.demo.hibernate.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 
 */
@Entity
@Table(name = "t_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7995028372726879751L;

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String name;

	private String addr;

	@ManyToMany(fetch = FetchType.LAZY)
//	@Fetch(value = FetchMode.SUBSELECT)
//	@ManyToMany(fetch = FetchType.EAGER)
	private List<UserModel> friends;

	public UserModel() {
	}

	public UserModel(int id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the addr
	 */
	public String getAddr() {
		return addr;
	}

	/**
	 * @param addr
	 *            the addr to set
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}

	public List<UserModel> getFriends() {
		return friends;
	}

	public void setFriends(List<UserModel> friends) {
		this.friends = friends;
	}

}
