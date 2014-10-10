package com.avit.common.warn;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_warn_info")
public class WarnInfo {
	/** 主键 */
	private Integer id;
	/** 告警时间 */
	private Date time;
	/** 告警内容 */
	private String content;
	/** 是否已经处理 （1: 已处理， 0： 未处理）*/
	private Integer isProcessed;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "time")
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	@Column(name = "content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(name = "is_processed")
	public Integer getIsProcessed() {
		return isProcessed;
	}
	public void setIsProcessed(Integer isProcessed) {
		this.isProcessed = isProcessed;
	}
}
