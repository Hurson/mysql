package com.dvnchina.advertDelivery.sysconfig.action;

import com.dvnchina.advertDelivery.action.BaseActionSupport;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.sysconfig.bean.ChannelInfo;
import com.dvnchina.advertDelivery.sysconfig.service.ChannelService;

public class ChannelAction extends BaseActionSupport<Object>{
	
	private static final long serialVersionUID = -4163955288129670463L;
	private ChannelService channelService = null;
	private PageBeanDB page = null;
	private ChannelInfo channel = null;
	
	/**
	 * 分页查询频道信息
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public String queryChannelList(){
		try{
			if(page == null){
				page = new PageBeanDB();
			}
			page = channelService.queryChannelList(channel,page.getPageNo(), page.getPageSize());
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 根据频道ID获取频道信息
	 * @param channelId
	 * @return
	 */
	public String getChannelById(){
		try{
			channel = channelService.getChannelById(channel.getChannelId());
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 修改频道信息
	 * @param channel
	 */
	public String updateChannel(){
		try{
			channelService.updateChannel(channel);
			message = "common.update.success";//修改成功
		}catch(Exception e){
			message = "common.update.failed";//修改失败
			e.printStackTrace();
		}
		channel = null;
		return queryChannelList();
	}
	
	
	public PageBeanDB getPage() {
		return page;
	}
	public void setPage(PageBeanDB page) {
		this.page = page;
	}
	public ChannelInfo getChannel() {
		return channel;
	}

	public void setChannel(ChannelInfo channel) {
		this.channel = channel;
	}

	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}

}
