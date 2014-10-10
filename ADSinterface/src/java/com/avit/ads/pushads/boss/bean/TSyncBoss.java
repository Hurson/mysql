package com.avit.ads.pushads.boss.bean;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TSyncBoss entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_sync_boss", catalog = "ads")
public class TSyncBoss implements java.io.Serializable {

	// Fields

	private Integer id;
	private Date dataTime;
	private Date createDate;
	private String filepath;
	private String filename;
	private Integer flag;

	// Constructors

	/** default constructor */
	public TSyncBoss() {
	}

	/** minimal constructor */
	public TSyncBoss(Integer flag) {
		this.flag = flag;
	}

	/** full constructor */
	public TSyncBoss(Date dataTime, Date createDate, String filepath,
			String filename, Integer flag) {
		this.dataTime = dataTime;
		this.createDate = createDate;
		this.filepath = filepath;
		this.filename = filename;
		this.flag = flag;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "data_time")
	public Date getDataTime() {
		return this.dataTime;
	}

	public void setDataTime(Date dataTime) {
		this.dataTime = dataTime;
	}

	@Column(name = "create_date")
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "filepath", length = 20)
	public String getFilepath() {
		return this.filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	@Column(name = "filename", length = 20)
	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Column(name = "flag", nullable = false)
	public Integer getFlag() {
		return this.flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

}