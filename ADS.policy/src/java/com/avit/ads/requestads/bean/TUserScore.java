package com.avit.ads.requestads.bean;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TUserScore entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_user_score", catalog = "ads")
public class TUserScore implements java.io.Serializable {

	// Fields

	private Integer id;
	private String usersn;
	private Long score;

	// Constructors

	/** default constructor */
	public TUserScore() {
	}

	/** minimal constructor */
	public TUserScore(Integer id, String usersn) {
		this.id = id;
		this.usersn = usersn;
	}

	/** full constructor */
	public TUserScore(Integer id, String usersn, Long score) {
		this.id = id;
		this.usersn = usersn;
		this.score = score;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getUserid() {
		return this.id;
	}

	public void setUserid(Integer userid) {
		this.id = userid;
	}

	@Column(name = "USERSN", nullable = false, length = 20)
	public String getUsersn() {
		return this.usersn;
	}

	public void setUsersn(String usersn) {
		this.usersn = usersn;
	}

	@Column(name = "SCROE", precision = 14, scale = 0)
	public Long getScore() {
		return this.score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

}