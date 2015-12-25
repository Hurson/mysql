package com.avit.ads.util;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServlet;

import com.avit.ads.dtmb.thread.DtmbThread;
import com.avit.ads.pushads.ocg.service.OcgService;
import com.avit.ads.pushads.ocg.service.impl.OcgServiceImpl;
import com.avit.ads.pushads.task.AutoAllThread;
import com.avit.ads.pushads.task.AutoMessageLinkThread;
import com.avit.ads.pushads.task.AutoStartStbThread;
import com.avit.ads.pushads.task.cache.SendAdsElementMap;



// TODO: Auto-generated Javadoc
/**
 * 启动推送式广告发送线程.
 * 启动字幕，推荐链接广告发送线程.
 * 启动开机广告发送线程.
 */
public class AutoServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The thread. */
	AutoAllThread  allThread ;
	AutoMessageLinkThread messgeLinkThread;
	AutoStartStbThread    startStbThread;
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
				SendAdsElementMap.initSendAdsElementMap();
				
				allThread = new AutoAllThread();
				allThread.start();				
				Thread.sleep(500);	
				
				/**
				 * 无线投放任务
				 */
				DtmbThread dtmbThread = new DtmbThread();
				//dtmbThread.start();
				//Thread.sleep(500);
				ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
				service.scheduleWithFixedDelay(dtmbThread, 1, 30, TimeUnit.SECONDS);
//				startStbThread =new AutoStartStbThread();
//				startStbThread.start();				
//				Thread.sleep(500);	

				
				//testUnt();  //测试UNT
				
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
	}
	
	private void testUnt(){
		OcgService ocgService= (OcgService)ContextHolder.getApplicationContext().getBean("OcgService");
		for(int i = 1; i <= 7; i++){
			Object untMsgObj = OcgServiceImpl.initUNTMsg(i);
			ocgService.sendUNTMessageUpdateByIp("192.168.2.221", i, untMsgObj, "20517");
		}
	}
	

}
