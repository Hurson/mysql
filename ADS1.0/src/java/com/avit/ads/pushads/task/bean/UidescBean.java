package com.avit.ads.pushads.task.bean;

import java.util.ArrayList;
import java.util.List;

public class UidescBean {
	public UidescBean()
	{
		
	}
	
	public UidescBean(String uitype, String uifilename, String uipositioncode,
			String areaCode) {
		super();
		this.uitype = uitype;
		this.uifilename = uifilename;
		this.uipositioncode = uipositioncode;
		this.areaCode = areaCode;
	}

	private String uitype;
	private String uifilename;
	private String uipositioncode;
	private String areaCode;
	public String getUitype() {
		return uitype;
	}
	public void setUitype(String uitype) {
		this.uitype = uitype;
	}
	public String getUifilename() {
		return uifilename;
	}
	public void setUifilename(String uifilename) {
		this.uifilename = uifilename;
	}
	public String getUipositioncode() {
		return uipositioncode;
	}
	public void setUipositioncode(String uipositioncode) {
		this.uipositioncode = uipositioncode;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
}
