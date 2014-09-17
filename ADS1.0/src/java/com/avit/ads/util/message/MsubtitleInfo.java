package com.avit.ads.util.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="msubtitleInfo")
public class MsubtitleInfo {

	@XmlAttribute(name="ServiceID", required = true)
	private String serviceID;
	@XmlAttribute(name="ActionType", required = true)
	private String actionType;
	@XmlAttribute(name="Timeout", required = true)
	private String timeout;
	@XmlAttribute(name="FontSize", required = true)
	private String fontSize;
	@XmlAttribute(name="FontColor", required = true)
	private String fontColor;
	@XmlAttribute(name="BackgroundX", required = true)
	private String backgroundX;
	@XmlAttribute(name="BackgroundY", required = true)
	private String backgroundY;
	@XmlAttribute(name="BackgroundWidth", required = true)
	private String backgroundWidth;
	@XmlAttribute(name="BackgroundHeight", required = true)
	private String backgroundHeight;
	@XmlAttribute(name="BackgroundColor", required = true)
	private String backgroundColor;
	@XmlAttribute(name="ShowFrequency", required = true)
	private String showFrequency;
	@XmlAttribute(name="Word", required = true)
	private String word;
	
	@XmlAttribute(name="uiId", required = true)
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
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	
	
	
}
