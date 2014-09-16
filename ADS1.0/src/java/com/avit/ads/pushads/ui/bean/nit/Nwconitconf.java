package com.avit.ads.pushads.ui.bean.nit;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Nwconitconf entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "NWCONITCONF", schema = "dbo", catalog = "DTVS")
public class Nwconitconf implements java.io.Serializable {

	// Fields

	private Integer id;
	private String displayname;
	private Integer networkid;
	private String networkname;
	private Integer pid;
	private Integer cycle;
	private Integer version;
	private Integer tsattach;
	private Integer insertlinkage;
	private Integer insertname;
	private Integer insertmultiname;
	private Integer enable;
	

	// Constructors

	/** default constructor */
	public Nwconitconf() {
	}

	/** minimal constructor */
	public Nwconitconf(Integer id, String displayname, Integer networkid,
			String networkname, Integer pid, Integer cycle, Integer version,
			Integer tsattach, Integer insertlinkage, Integer insertname,
			Integer insertmultiname, Integer enable) {
		this.id = id;
		this.displayname = displayname;
		this.networkid = networkid;
		this.networkname = networkname;
		this.pid = pid;
		this.cycle = cycle;
		this.version = version;
		this.tsattach = tsattach;
		this.insertlinkage = insertlinkage;
		this.insertname = insertname;
		this.insertmultiname = insertmultiname;
		this.enable = enable;
	}
	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "DISPLAYNAME", nullable = false, length = 50)
	public String getDisplayname() {
		return this.displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	@Column(name = "NETWORKID", nullable = false)
	public Integer getNetworkid() {
		return this.networkid;
	}

	public void setNetworkid(Integer networkid) {
		this.networkid = networkid;
	}

	@Column(name = "NETWORKNAME", nullable = false)
	public String getNetworkname() {
		return this.networkname;
	}

	public void setNetworkname(String networkname) {
		this.networkname = networkname;
	}

	@Column(name = "PID", nullable = false)
	public Integer getPid() {
		return this.pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	@Column(name = "CYCLE", nullable = false)
	public Integer getCycle() {
		return this.cycle;
	}

	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}

	@Column(name = "VERSION", nullable = false)
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name = "TSATTACH", nullable = false)
	public Integer getTsattach() {
		return this.tsattach;
	}

	public void setTsattach(Integer tsattach) {
		this.tsattach = tsattach;
	}

	@Column(name = "INSERTLINKAGE", nullable = false)
	public Integer getInsertlinkage() {
		return this.insertlinkage;
	}

	public void setInsertlinkage(Integer insertlinkage) {
		this.insertlinkage = insertlinkage;
	}

	@Column(name = "INSERTNAME", nullable = false)
	public Integer getInsertname() {
		return this.insertname;
	}

	public void setInsertname(Integer insertname) {
		this.insertname = insertname;
	}

	@Column(name = "INSERTMULTINAME", nullable = false)
	public Integer getInsertmultiname() {
		return this.insertmultiname;
	}

	public void setInsertmultiname(Integer insertmultiname) {
		this.insertmultiname = insertmultiname;
	}

	@Column(name = "ENABLE", nullable = false)
	public Integer getEnable() {
		return this.enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	
}