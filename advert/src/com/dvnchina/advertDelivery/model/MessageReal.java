package com.dvnchina.advertDelivery.model;

import java.sql.Blob;

public class MessageReal implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 内容
	 */
//	private String content;
//	private Blob content;
	private byte[] content;
	
	/**
	 * URL
	 */
	private String URL;
	
	private String action;
    private Integer durationTime;
    private Integer fontSize;
    private String fontColor;
    private String bkgColor;
    private Integer rollSpeed;
    private String positionVertexCoordinates;
    private String positionWidthHeight;
	
	public MessageReal(){}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String url) {
		URL = url;
	}

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(Integer durationTime) {
        this.durationTime = durationTime;
    }

    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public String getBkgColor() {
        return bkgColor;
    }

    public void setBkgColor(String bkgColor) {
        this.bkgColor = bkgColor;
    }

    public Integer getRollSpeed() {
        return rollSpeed;
    }

    public void setRollSpeed(Integer rollSpeed) {
        this.rollSpeed = rollSpeed;
    }

    public String getPositionVertexCoordinates() {
        return positionVertexCoordinates;
    }

    public void setPositionVertexCoordinates(String positionVertexCoordinates) {
        this.positionVertexCoordinates = positionVertexCoordinates;
    }

    public String getPositionWidthHeight() {
        return positionWidthHeight;
    }

    public void setPositionWidthHeight(String positionWidthHeight) {
        this.positionWidthHeight = positionWidthHeight;
    }
	
	

}







