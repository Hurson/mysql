package com.dvnchina.advertDelivery.model;


public class MessageMeta implements java.io.Serializable{
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
	private byte[] content;
	private String contentMsg;
	
	
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
	
	
	public MessageMeta(){
	}
	
	public MessageMeta(Integer id){
		this.id = id;
	}

	public MessageMeta(Integer id, String name, byte[] content, String url) {
		this.id = id;
		this.name = name;
		this.content = content;
		URL = url;
	}

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

	public String getURL() {
		return URL;
	}

	public void setURL(String url) {
		URL = url;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
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

    public String getContentMsg() {
        return contentMsg;
    }

    public void setContentMsg(String contentMsg) {
        this.contentMsg = contentMsg;
    }
	
	

}


