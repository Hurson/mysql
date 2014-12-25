package com.dvnchina.advertDelivery.subtitle.action;

import java.util.Date;
import java.util.List;

import com.avit.ads.webservice.AdsClient;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.common.BaseAction;
import com.dvnchina.advertDelivery.subtitle.bean.SubtitleBean;
import com.dvnchina.advertDelivery.subtitle.service.SubtitleService;

public class SubtitleAction extends BaseAction {
	
	private static final long serialVersionUID = 370038398441176820L;
	
	private SubtitleService subtitleService;
	
	private PageBeanDB page;
	
	private SubtitleBean subtitle;
	
	public String findSubtitleList(){
		if(null == page){
			page = new PageBeanDB();
		}
		page = subtitleService.findSubtitleList(subtitle, page.getPageNo(), page.getPageSize());
		return SUCCESS;
	}
	
	public String delSubtitle(){
		subtitleService.delSubtitles(ids);
		return findSubtitleList();
	}
	
	public String addSubtitle(){
		return SUCCESS;
	}
	
	public String saveSubtitle(){
		subtitle.setState(1);
		subtitle.setCreateTime(new Date());
		subtitleService.saveSubtitle(subtitle);
		subtitle = null;
		return findSubtitleList();
	}
	
	public String saveAndPushSubtitle(){
		subtitle.setState(1);
		subtitle.setCreateTime(new Date());
		subtitleService.saveSubtitle(subtitle);
		
		AdsClient adsClient = new AdsClient();
		if(adsClient.pushSubtitle(subtitle)){
			subtitle.setPushTime(new Date());
			subtitle.setState(2);
			subtitleService.saveSubtitle(subtitle);
		}else{
			message = "common.push.failed";
		}
		
		subtitle = null;
		return findSubtitleList();
	}
	
	public String getSubtitleDetail(){
		subtitle = subtitleService.getSubtitleById(subtitle.getId());
		return SUCCESS;
	}
	
	public List getAreaList(){
		return subtitleService.getAreaList();
	}
	
	
	
	public PageBeanDB getPage() {
		return page;
	}
	public void setPage(PageBeanDB page) {
		this.page = page;
	}
	public SubtitleService getSubtitleService() {
		return subtitleService;
	}
	public void setSubtitleService(SubtitleService subtitleService) {
		this.subtitleService = subtitleService;
	}
	public SubtitleBean getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(SubtitleBean subtitle) {
		this.subtitle = subtitle;
	}

	
	
	
}
