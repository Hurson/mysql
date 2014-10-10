package com.avit.ads.requestads.cache.bean;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Transient;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TOrder entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_order")
public class TOrder implements java.io.Serializable {

	// Fields

	private Long id;
	private Date startTime;
	private Date endTime;
	private Integer playNumber;
	private Integer playedNumber;
    
	// Constructors

	/** default constructor */
	public TOrder() {
	}

	/** full constructor */
	

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public TOrder(Long id, Date startTime, Timestamp Date,
			Integer playNumber, Integer playedNumber) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.playNumber = playNumber;
		this.playedNumber = 0;
		
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	@Column(name = "START_TIME", length = 19)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "END_TIME", length = 19)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@Column(name = "play_number")
	public Integer getPlayNumber() {
		return this.playNumber;
	}

	public void setPlayNumber(Integer playNumber) {
		this.playNumber = playNumber;
	}

	@Column(name = "played_number")
	public Integer getPlayedNumber() {
		return this.playedNumber;
	}

	public void setPlayedNumber(Integer playedNumber) {
		this.playedNumber = playedNumber;
	}

}