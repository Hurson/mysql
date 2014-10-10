package com.dvnchina.advertDelivery.model;

import java.io.Serializable;
/**
 * 视频规格信息
 * @author lester
 *
 */
public class VideoSpecification implements Serializable{

	private static final long serialVersionUID = 7835091084066346682L;
	
	private Integer id;
	/**
	 * 视频描述
	 */
	private String movieDesc;
	/**
	 * 分辨率
	 */
	private String resolution;
	/**
	 * 时长
	 */
	private Integer duration;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 文件大小
	 */
	private String fileSize;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMovieDesc() {
		return movieDesc;
	}

	public void setMovieDesc(String movieDesc) {
		this.movieDesc = movieDesc;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
}
