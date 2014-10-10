package com.dvnchina.advertDelivery.model;

public class VideoMeta extends CommonObject {

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
	 * 临时库存储路径
	 */
	private String temporaryFilePath;
	
	private String fileSize;
	

	public VideoMeta(){}

	public VideoMeta(String name, String description, String runTime,
			String temporaryFilePath) {
		super();
		this.name = name;
		this.description = description;
		this.runTime = runTime;
		this.temporaryFilePath = temporaryFilePath;
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

	public String getTemporaryFilePath() {
		return temporaryFilePath;
	}

	public void setTemporaryFilePath(String temporaryFilePath) {
		this.temporaryFilePath = temporaryFilePath;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
	
	
}









