package com.avit.ads.util.type;

public enum UIUpdate {
	PIC(1, "initPic-c.iframe"),
	ADV(3, "advResource-c.dat"),
	VIDEO(5, "initVideo-c.ts");
	
	private int type;
	private String fileName;
	
	private UIUpdate(int type, String fileName){
		this.type = type;
		this.fileName = fileName;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	
}
