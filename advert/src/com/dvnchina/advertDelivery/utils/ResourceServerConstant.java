package com.dvnchina.advertDelivery.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class ResourceServerConstant {
	String cfgFile = DirectoryUtils.getSerlvetContextPath()+"/WEB-INF/classes/resourceServer.xml";
	Document document = null;
	
	public ResourceServerConstant(){
		SAXReader reader = new SAXReader();   
		try {
			File file=new File(cfgFile);
			document = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}   
	}
	
	public static ResourceServerConstant getInstance(){
		return new ResourceServerConstant();
	}
	
	@SuppressWarnings("unchecked")
	public List<Serverinfo> loadServerinfoS(){
		List<Node> list = document.selectNodes("//server");
		List<Serverinfo> servers = new ArrayList<Serverinfo> ();
		for(Node node:list){
			Element element = (Element)node;
			String ip = element.element("ip").getText();
			String port = element.element("port").getText();
			String username = element.element("username").getText();
			String password = element.element("password").getText();
			String path = element.element("path").getText();
			Serverinfo serverInfo = new Serverinfo();
			serverInfo.setIp(ip);
			serverInfo.setPort(port);
			serverInfo.setUsername(username);
			serverInfo.setPassword(password);
			serverInfo.setPath(path);
			servers.add(serverInfo);
		}
		return servers;
	}

	public class Serverinfo{
		private String ip;
		private int port;
		private String username;
		private String password;
		private String path;
		
		public String getIp() {
			return ip;
		}
		public void setIp(String ip) {
			this.ip = ip;
		}
		public int getPort() {
			return port;
		}
		public void setPort(int port) {
			this.port = port;
		}
		public void setPort(String port) {
			this.port = Integer.parseInt(port);
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
	}
}
