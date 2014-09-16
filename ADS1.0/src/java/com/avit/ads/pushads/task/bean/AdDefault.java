package com.avit.ads.pushads.task.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 直播频道 entity. @author MyEclipse Persistence Tools
 */

public class AdDefault implements java.io.Serializable {

	// Fields

	private String POSITIONCODE;
	private String DEFAULTFILE;
	private String fileType;
	

	// Constructors

	/** default constructor */
	public AdDefault() {
	}


	

	public String getPOSITIONCODE() {
		return POSITIONCODE;
	}




	public void setPOSITIONCODE(String pOSITIONCODE) {
		POSITIONCODE = pOSITIONCODE;
	}




	public String getDEFAULTFILE() {
		return DEFAULTFILE;
	}




	public void setDEFAULTFILE(String dEFAULTFILE) {
		DEFAULTFILE = dEFAULTFILE;
	}




	public String getFileType() {
		return fileType;
	}


	public void setFileType(String fileType) {
		this.fileType = fileType;
	}


	
	
}