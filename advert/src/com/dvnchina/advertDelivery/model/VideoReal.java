package com.dvnchina.advertDelivery.model;

import java.util.Date;

public class VideoReal extends CommonObject {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 素材名称
	 */
	private String name;
	
	/**
	 * 描述
	 */
	private String description;
	
	/**
	 * 时长
	 */
	private String runTime;
	
	
	/**
	 * 正式库存储路径
	 */
	private String formalFilePath;
	
	/**
	 * 文件上传到videoPump的路径
	 */
	private String videoPumpPath;
	
	private String fileSize;
	
	public VideoReal(){}

	public VideoReal(String name, String description, String runTime,
			String formalFilePath) {
		super();
		this.name = name;
		this.description = description;
		this.runTime = runTime;
		this.formalFilePath = formalFilePath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRunTime() {
		return runTime;
	}

	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}

	public String getFormalFilePath() {
		return formalFilePath;
	}

	public void setFormalFilePath(String formalFilePath) {
		this.formalFilePath = formalFilePath;
	}

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

	public String getVideoPumpPath() {
		return videoPumpPath;
	}

	public void setVideoPumpPath(String videoPumpPath) {
		this.videoPumpPath = videoPumpPath;
	}
	
	
}


