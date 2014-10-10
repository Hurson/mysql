package com.avit.common.quartz;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class MyJobListener implements JobListener {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	public String getName() {
		return "MyJobListener";
	}

	public void jobExecutionVetoed(JobExecutionContext context) {
	}

	public void jobToBeExecuted(JobExecutionContext context) {
		logger.info("定时任务【" + context.getJobDetail().getName() + "】开始执行   " 
				+ "\n\t 1、JVM最大可用内存（maxMemory）：" +  formatMemorySize(Runtime.getRuntime().maxMemory())
				+ "\n\t 2、JVM已获取内存（totalMemory）：" +  formatMemorySize(Runtime.getRuntime().totalMemory())
				+ "\n\t 3、JVM闲置内存（freeMemory）：" +  formatMemorySize(Runtime.getRuntime().freeMemory())
				+ "\n\t 4、JVM尚可分配内存（maxMemory-totalMemory）：" +  formatMemorySize(Runtime.getRuntime().maxMemory()-Runtime.getRuntime().totalMemory())
				); 
	}

	public void jobWasExecuted(JobExecutionContext context,JobExecutionException exception) {
		logger.info("定时任务【" + context.getJobDetail().getName() + "】执行完毕" );
	}
	
	public static void showJVMMemoryDetail(String title){
		System.out.println(title 
				+ "\n\t 1、JVM最大可用内存（maxMemory）：" +  formatMemorySize(Runtime.getRuntime().maxMemory())
				+ "\n\t 2、JVM已获取内存（totalMemory）：" +  formatMemorySize(Runtime.getRuntime().totalMemory())
				+ "\n\t 3、JVM闲置内存（freeMemory）：" +  formatMemorySize(Runtime.getRuntime().freeMemory())
				+ "\n\t 4、JVM尚可分配内存（maxMemory-totalMemory）：" +  formatMemorySize(Runtime.getRuntime().maxMemory()-Runtime.getRuntime().totalMemory())
				); 
	}
	
	private static String formatMemorySize(long size){
		return ((int)(size /(1024*1024.0)*1000))/1000.0 + "M"; //保留三位小数
	}

}
