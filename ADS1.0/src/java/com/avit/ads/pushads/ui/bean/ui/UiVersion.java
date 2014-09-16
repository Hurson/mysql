package com.avit.ads.pushads.ui.bean.ui;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ui_typeversion", catalog = "ui")
//@SequenceGenerator(name = "resource_seq", sequenceName= "resource_url_SEQ", allocationSize = 25)
public class UiVersion implements Serializable{

	//@Id
	//@Column(name = "ID", unique = true, nullable = false, precision = 38, scale = 0)
	//private Long id;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "TYPE")
	private Long type;//1/2/3/4/5，分别指：开机画面、配置文件、广告资源、HTML文件、开机视频五种资源文件；
	@Column(name = "version")
	private Short version;

	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public Short getVersion() {
		return version;
	}
	public void setVersion(Short version) {
		this.version = version;
	}
	
	
}
