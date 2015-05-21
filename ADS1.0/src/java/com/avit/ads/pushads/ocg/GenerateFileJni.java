package com.avit.ads.pushads.ocg;


public class GenerateFileJni {

	private static GenerateFileJni instance;
	static{
		System.loadLibrary("CreateUNT");
	}
	public native boolean geneTSFile(byte[] xmlData, int tableVersion, int descVersion, String destPath, String logPath);
	
	public static GenerateFileJni getInstance(){
		if(instance == null){
			instance = new GenerateFileJni();
		}
		return instance;
	}

}
