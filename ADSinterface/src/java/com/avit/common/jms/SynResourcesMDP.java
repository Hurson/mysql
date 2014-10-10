package com.avit.common.jms;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * jms通知同步资源文件
 */
public class SynResourcesMDP implements MessageListener{

	private static Log log = LogFactory.getLog( SynResourcesMDP.class );
	
	//class文件路径
	private static String CLASS_PATH = null;
	
	//资源文件配置名称
	private static String SYN_RESOURCES_URLS = "syn_resources_urls";
	
	/**
	 * 读取数据超时时间
	 */
	private int readTimeout;
	
	/**
	 * 连接超时时间
	 */
	private int connectTimeout;
	
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	/**
	 * jms消息处理
	 */
	public void onMessage(Message msg) {
		
		String[] urlArrs = getSynResourcesUrls();
		String message = null;
		for (int i = 0; i < urlArrs.length; i++) {
			log.info("开始调用["+urlArrs[i]+"]同步资源");
			try {
				message = callSyn(urlArrs[i]);
				log.info("调用["+urlArrs[i]+"],返回结果 "+message);
			} catch (Exception e) {
				log.error("调用["+urlArrs[i]+"]同步资源错误", e);
			}
		}
	}
	
	/**
	 * 读取配置文件，取得同步连接
	 * @return 连接地址数组
	 */
	public String[] getSynResourcesUrls() {
		if (CLASS_PATH == null) {
			CLASS_PATH = SynResourcesMDP.class.getClassLoader()
							.getResource("").getPath()+"system.properties";
		}
		InputStream in = null;
		String[] urlArrs = null;
		try {
			in = new FileInputStream( new File(CLASS_PATH) );
			Properties p = new Properties();
			p.load(in);
			log.info("同步资源连接="+p.getProperty(SYN_RESOURCES_URLS));
			String urls = p.getProperty(SYN_RESOURCES_URLS);
			urlArrs = urls.split(",");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return urlArrs;
	}

	/**
	 * 调用远程servlet,触发同步程序(Rsync)
	 * @param synUrl  url连接
	 */
	public String callSyn(String synUrl) {
		InputStream in = null;
		String msg = null;
		URL url = null;
		URLConnection con = null;
		try {
			url = new URL(synUrl);
			con = url.openConnection();
			log.info("callSyn readTimeout="+readTimeout + "  connectTimeout="+connectTimeout);
			con.setConnectTimeout(connectTimeout);//连接超时时间
			con.setReadTimeout(readTimeout);//读取数据超时时间
			in = con.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in));
			String line = rd.readLine();
			msg = line;
			log.info("jms调用远程["+synUrl+"]成功,返回信息["+msg+"]");
		} catch (Exception e) {
			log.error("jms调用远程["+synUrl+"]地址出错... ");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			con = null;
			url = null;
		}
		return msg;
	}

}
