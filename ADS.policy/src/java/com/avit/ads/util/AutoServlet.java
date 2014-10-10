package com.avit.ads.util;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;

import javax.servlet.http.HttpServlet;

import com.avit.ads.requestads.service.InitTimerTask;
import com.avit.ads.requestads.tast.PlaylistCacheScheduled;



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
	//AutoAllThread  allThread ;
	//AutoMessageLinkThread messgeLinkThread;
	//AutoStartStbThread    startStbThread;
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
//				
//				PlaylistCacheScheduled scheduled = new PlaylistCacheScheduled();
//				scheduled.timerScheduled();
				InitTimerTask initTimerTask= new InitTimerTask();
				Timer timer = new Timer();
				timer.schedule(initTimerTask, new Date());
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
