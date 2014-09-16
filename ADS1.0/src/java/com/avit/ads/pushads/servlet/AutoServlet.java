package com.avit.ads.pushads.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.avit.ads.pushads.dtv.service.impl.DtvServiceImpl;
import com.avit.ads.pushads.task.AutoAllThread;
import com.avit.ads.pushads.task.AutoMessageLinkThread;
import com.avit.ads.pushads.task.AutoStartStbThread;
import com.avit.ads.pushads.task.cache.SendAdsElementMap;
import com.avit.ads.pushads.task.cache.SendFileMap;
import com.avit.ads.requestads.service.PlaylistCache;
import com.avit.ads.requestads.tast.PlaylistCacheScheduled;
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
	private Logger logger = Logger.getLogger(DtvServiceImpl.class);
	
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
				//初始化广告位默认素材缓存 addefaultMap
				
				//初始化所有广告位文件发送缓存 adSendFileMap   用于管理临时文件夹文件  只负责单向投放内容 ,CPS,NFPS均不处理
				
				//初始化广告位-文件列表
				
				//配置文件计划均用XML格式定义
				//读取配置文件   广告位--投放路径--文件名
				//字幕，推荐链接广告位属性  资源投放路径，UNT接口参数
				getConfig();
				SendFileMap.initSendFileMap();
				SendAdsElementMap.initSendAdsElementMap();
				allThread = new AutoAllThread();
				allThread.start();				
				Thread.sleep(500);	
				
//				messgeLinkThread =new AutoMessageLinkThread() ;
//				messgeLinkThread.start();				
//				Thread.sleep(500);	
			/**/	
				startStbThread =new AutoStartStbThread();
				startStbThread.start();				
				Thread.sleep(500);	
				
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

}
