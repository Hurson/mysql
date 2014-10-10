package com.avit.ads.webservice;
import javax.activation.DataHandler;

public class ResourceFile {
	private String fileName;
	private String adsTypeCode;
	private DataHandler file;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	public String getAdsTypeCode() {
		return adsTypeCode;
	}
	public void setAdsTypeCode(String adsTypeCode) {
		this.adsTypeCode = adsTypeCode;
	}
	public DataHandler getFile() {
		return file;
	}
	public void setFile(DataHandler file) {
		this.file = file;
	}

}
