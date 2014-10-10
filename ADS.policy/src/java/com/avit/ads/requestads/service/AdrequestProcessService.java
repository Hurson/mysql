package com.avit.ads.requestads.service;



public interface AdrequestProcessService {
	/**
	 * 根据gateway传输过来的XML请求中的参数，过滤出会使用到得播出单，然后将播出单打包并返回.
	 *
	 * @param xml 封装请求参数的XML
	 * @return string 封装请求到得播出单的LIST的XML
	 */
	public String GenerateADPlayList(String xml);
	
	/**
	 * 接收gateway发出广告投放情况，并处理
	 * 
	 * @param String gateway发出广告投放情况的XML
	 * @return 处理成功
	 */
	public boolean StartReportRequest(String xml);
	
	
}
