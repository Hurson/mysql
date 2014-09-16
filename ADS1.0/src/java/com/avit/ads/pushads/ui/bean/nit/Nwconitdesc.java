package com.avit.ads.pushads.ui.bean.nit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Nwconitdesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "NWCONITDESC", schema = "dbo", catalog = "DTVS")
public class Nwconitdesc implements java.io.Serializable {

	// Fields

	private Integer id;
	//private Nwconitconf nwconitconf;
	private Integer nwconitconfid;
	
	private Integer tsid;
	private Integer tag;
	private Integer length;
	private String content;
	private Integer enable;

	// Constructors

	/** default constructor */
	public Nwconitdesc() {
	}

	/** full constructor */
	public Nwconitdesc(Integer id, Integer nwconitconfid, Integer tsid,
			Integer tag, Integer length, String content, Integer enable) {
		this.id = id;
		this.nwconitconfid = nwconitconfid;
		this.tsid = tsid;
		this.tag = tag;
		this.length = length;
		this.content = content;
		this.enable = enable;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	@Column(name = "CONFID", nullable = false)
	public Integer getNwconitconfid() {
		return nwconitconfid;
	}

	public void setNwconitconfid(Integer nwconitconfid) {
		this.nwconitconfid = nwconitconfid;
	}

	@Column(name = "TSID", nullable = false)
	public Integer getTsid() {
		return this.tsid;
	}

	public void setTsid(Integer tsid) {
		this.tsid = tsid;
	}

	@Column(name = "TAG", nullable = false)
	public Integer getTag() {
		return this.tag;
	}

	public void setTag(Integer tag) {
		this.tag = tag;
	}

	@Column(name = "LENGTH", nullable = false)
	public Integer getLength() {
		return this.length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	@Column(name = "CONTENT", nullable = false, length = 510)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "ENABLE", nullable = false)
	public Integer getEnable() {
		return this.enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

}