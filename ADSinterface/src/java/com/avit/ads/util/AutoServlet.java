package com.avit.ads.util;

import java.io.IOException;

import javax.servlet.http.HttpServlet;


import com.avit.ads.util.InitConfig;



// TODO: Auto-generated Javadoc
/**
 * 启动推送式广告发送线程.
 * 启动字幕，推荐链接广告发送线程.
 * 启动开机广告发送线程.
 */
public class AutoServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	

	// TODO: Auto-generated Javadoc
	/** 
	 * 配置文件计划均用XML格式定义
	 * 读取配置文件   广告位--投放路径--文件名
	 * 字幕，推荐链接广告位属性  资源投放路径，UNT接口参数
	 * 开机广告  区域配置--区域编码，NIT配置，投放路径，临时路径
	 */
	public void init(){
		//while(true)
		{
			try{
				//配置文件计划均用XML格式定义
				//读取配置文件   广告位--投放路径--文件名
				//字幕，推荐链接广告位属性  资源投放路径，UNT接口参数
				getConfig();
//				UiService uiService;
//				uiService= (UiService)ContextHolder.getApplicationContext().getBean("UiService");
//				List<String> typeList=new ArrayList();
//				List<String> nameList=new ArrayList();
//				typeList.add("1");
//				typeList.add("5");
//				nameList.add("initPic-c.iframe");
//				nameList.add("initVideo-c.ts");
//				uiService.addUiDesc(typeList, nameList);
			
				//SendFileMap.initSendFileMap();
				//SendAdsElementMap.initSendAdsElementMap();
				//allThread = new AutoAllThread();
				//allThread.start();				
				//Thread.sleep(500);	
				

				//startStbThread =new AutoStartStbThread();
				//startStbThread.start();				
				//Thread.sleep(500);	

				
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				
			}
		}

	}	
	
	/**
	 * Gets the config.
	 *
	 * @return the config
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void getConfig() throws IOException{
		InitConfig config = new InitConfig();	
		config.initConfig();
		System.out.println("");
	}

}
