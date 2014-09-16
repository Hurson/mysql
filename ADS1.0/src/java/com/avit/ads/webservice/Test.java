package com.avit.ads.webservice;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class Test {

	/**
	 * @param args
	 */
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Date loopDate = new Date();
		System.out.println("loopDate=="+loopDate);
		try {
			System.out.println( new String("%E7%A9%BA%E4%B8%AD%E7%9B%91%E7%8B%B1".getBytes("ISO-8859-1"), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//
//		factory.setServiceClass(UploadService.class);
//
//		//factory.setAddress("http://192.168.102.104:8080/CXFwebservice/ws/uploadService");
//		factory.setAddress("http://192.168.102.104:8080/ads/ws/uploadService");
//
//		UploadService client = (UploadService) factory.create();
//
//		//client.sendFileVideoPump("uploadFiles/contractScanFile/", "/video/");
//		
//		ResourceFile res=new ResourceFile();
//		
//		res.setFileName("servlet-apia");
//
//		//res.("1");
//
//		DataSource source = new FileDataSource(new File("d:\\servlet-api.jar"));
//
//		res.setFile(new DataHandler(source));
//
//		String ret="";// = client.SendFile(res);
//		System.out.println(ret);
//
//	//	UploadClient upClient = new UploadClient();
//	//	upClient.sendFile("d:\\ads\\adsConfigFile.js", "1");
//		System.exit(0);


	}
	

}
