package com.avit.ads.requestads.cache.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * TBsmpUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_bsmp_user")
public class TBsmpUser implements java.io.Serializable {

	// Fields
	@Transient
	public Long getArea4() {
		return area4;
	}

	public void setArea4(Long area4) {
		this.area4 = area4;
	}

	private Integer userid;
	private String usersn;
	private String locationCity;
	private Long locationcodevalue;
	private Long area1;
	private Long area2;
	private Long area3;
	private Long area4;
	private String userlevel;
	private String industrycategory;

	// Constructors

	/** default constructor */
	public TBsmpUser() {
	}

	/** minimal constructor */
	public TBsmpUser(Integer userid) {
		this.userid = userid;
	}

	/** full constructor */
	public TBsmpUser(Integer userid, String usersn, Long locationcodevalue,
			String userlevel, String industrycategory) {
		this.userid = userid;
		this.usersn = usersn;
		this.locationcodevalue = locationcodevalue;
		this.userlevel = userlevel;
		this.industrycategory = industrycategory;
	}

	// Property accessors
	@Id
	@Column(name = "USERID", unique = true, nullable = false)
	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Column(name = "USERSN", length = 20)
	public String getUsersn() {
		return this.usersn;
	}

	public void setUsersn(String usersn) {
		this.usersn = usersn;
	}
	
	@Column(name = "LOCATIONCODE", length = 20)
	public String getLocationCity() {
		return locationCity;
	}

	public void setLocationCity(String locationCity) {
		this.locationCity = locationCity;
	}

	
	@Column(name = "LOCATIONCODEVALUE", precision = 14, scale = 0)
	public Long getLocationcodevalue() {
		return this.locationcodevalue;
	}

	public void setLocationcodevalue(Long locationcodevalue) {
		this.locationcodevalue = locationcodevalue;
	}

	@Column(name = "USERLEVEL", length = 20)
	public String getUserlevel() {
		return this.userlevel;
	}

	public void setUserlevel(String userlevel) {
		this.userlevel = userlevel;
	}

	@Column(name = "INDUSTRYCATEGORY", length = 20)
	public String getIndustrycategory() {
		return this.industrycategory;
	}

	public void setIndustrycategory(String industrycategory) {
		this.industrycategory = industrycategory;
	}

	@Transient
	public Long getArea1() {
		return area1;
	}

	public void setArea1(Long area1) {
		this.area1 = area1;
	}

	@Transient
	public Long getArea2() {
		return area2;
	}

	public void setArea2(Long area2) {
		this.area2 = area2;
	}

	@Transient
	public Long getArea3() {
		return area3;
	}

	public void setArea3(Long area3) {
		this.area3 = area3;
	}

}