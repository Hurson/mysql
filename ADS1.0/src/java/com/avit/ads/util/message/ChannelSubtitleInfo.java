package com.avit.ads.util.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="subtitleInfo")
public class ChannelSubtitleInfo {

	@XmlAttribute(name="serviceID")
	private String serviceID;
	@XmlAttribute(name="actionType", required = true)
	private String actionType;
	@XmlAttribute(name="timeout", required = true)
	private String timeout;
	@XmlAttribute(name="fontSize", required = true)
	private String fontSize;
	@XmlAttribute(name="fontColor", required = true)
	private String fontColor;
	@XmlAttribute(name="backgroundX", required = true)
	private String backgroundX;
	@XmlAttribute(name="backgroundY", required = true)
	private String backgroundY;
	@XmlAttribute(name="backgroundWidth", required = true)
	private String backgroundWidth;
	@XmlAttribute(name="backgroundHeight", required = true)
	private String backgroundHeight;
	@XmlAttribute(name="backgroundColor", required = true)
	private String backgroundColor;
	@XmlAttribute(name="showFrequency", required = true)
	private String showFrequency;
		
	@XmlAttribute(name="uiID")
	private String uiId;
	
	

	
	public String getServiceID() {
		return serviceID;
	}
	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getUiId() {
		return uiId;
	}
	public void setUiId(String uiId) {
		this.uiId = uiId;
	}
	
	public String getTimeout() {
		return timeout;
	}
	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}
	public String getFontSize() {
		return fontSize;
	}
	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}
	public String getFontColor() {
		return fontColor;
	}
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}
	public String getBackgroundX() {
		return backgroundX;
	}
	public void setBackgroundX(String backgroundX) {
		this.backgroundX = backgroundX;
	}
	public String getBackgroundY() {
		return backgroundY;
	}
	public void setBackgroundY(String backgroundY) {
		this.backgroundY = backgroundY;
	}
	public String getBackgroundWidth() {
		return backgroundWidth;
	}
	public void setBackgroundWidth(String backgroundWidth) {
		this.backgroundWidth = backgroundWidth;
	}
	public String getBackgroundHeight() {
		return backgroundHeight;
	}
	public void setBackgroundHeight(String backgroundHeight) {
		this.backgroundHeight = backgroundHeight;
	}
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public String getShowFrequency() {
		return showFrequency;
	}
	public void setShowFrequency(String showFrequency) {
		this.showFrequency = showFrequency;
	}	
	
}
