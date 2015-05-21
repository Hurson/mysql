package com.dvnchina.advertDelivery.order.bean.playlist;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.dvnchina.advertDelivery.meterial.bean.PriorityWordBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TextMate implements Serializable{

	private static final long serialVersionUID = 1L;
	/** 主键 */
	private Integer id;
	/** 内容标题 */
	private String name;
	/** 字幕内容 */
	private String content;
	/** 链接 */
	private String URL;
	/** 文本显示动作 */
	private String action;
	/** 文本显示持续时间 */
    private Integer durationTime;
    /** 文本显示字体大小 */
    private Integer fontSize;
    /** 文本显示字体颜色 */
    private String fontColor;
    /** 文本显示背景色 */
    private String bkgColor;
    /** 文本显示滚动速度 */
    private Integer rollSpeed;
    /** 文本显示显示坐标 x,y */
    private String positionVertexCoordinates;
    /** 文本显示显示区域宽高  w,h */
    private String positionWidthHeight;
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
	public String getContent() {
		return content;
	}
	public void setContent(Blob content) {
		InputStream in;
		StringBuffer buf = new StringBuffer();
		try {
			in = content.getBinaryStream();
			byte[] buff = new byte[(int) content.length()];
			while (in.read(buff) > 0) {
				buf.append(new String(buff, "gbk"));
			}
			String jsonContent = buf.toString();
			Gson gson = new Gson();
			List<PriorityWordBean> list = gson.fromJson(jsonContent, new TypeToken<List<PriorityWordBean>>(){ }.getType());
			Collections.sort(list, new Comparator<PriorityWordBean>(){
	            public int compare(PriorityWordBean arg0, PriorityWordBean arg1) {
	                return arg0.getPriority().compareTo(arg1.getPriority());
	            }
	        });
			String words = "";
			for(PriorityWordBean entity : list){
				words += entity.getWord() + "  @_@   ";
			}
			this.content = words;
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
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
