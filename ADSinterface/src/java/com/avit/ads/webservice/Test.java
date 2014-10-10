package com.avit.ads.webservice;

import java.io.File;

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
		
		
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();

		factory.getInInterceptors().add(new LoggingInInterceptor());

		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		factory.setServiceClass(UploadService.class);

		//factory.setAddress("http://192.168.102.104:8080/CXFwebservice/ws/uploadService");
		factory.setAddress("http://10.63.62.118:8080/ads/ws/uploadService");

		UploadService client = (UploadService) factory.create();

		client.sendFileVideoPump("//root/advertres/temp/wf/initVideo-c_1372731172306.ts", "/video/");
		
		ResourceFile res=new ResourceFile();
		
		res.setFileName("servlet-apia");

		//res.("1");

		DataSource source = new FileDataSource(new File("d:\\servlet-api.jar"));

		res.setFile(new DataHandler(source));

		String ret="";// = client.SendFile(res);
		System.out.println(ret);

	//	UploadClient upClient = new UploadClient();
	//	upClient.sendFile("d:\\ads\\adsConfigFile.js", "1");
		System.exit(0);


	}
	

}
