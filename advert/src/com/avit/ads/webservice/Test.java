package com.avit.ads.webservice;

public class Test {

	public void test(){
		UploadClient upClient = new UploadClient();
		//upClient.setServerUrl("http://192.168.102.104:8080/ads/");
		
		//upClient.sendFileVideoPump("uploadFiles/contractScanFile/movie05.png", "/video/");
		upClient.sendFileDTV("0", "uploadFiles/contractScanFile/movie05.png", "/dtv/");

	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Test test = new Test();
		test.test();
	}

}
