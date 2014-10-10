package com.dvnchina.advertDelivery.action;

import com.dvnchina.advertDelivery.model.PlayCategory;
import com.dvnchina.advertDelivery.service.PlayCategoryService;

public class PlayCategoryAction extends BaseActionSupport<Object>{
	
	private static final long serialVersionUID = 1374275951522753720L;
	private PlayCategoryService playCategoryService;
	private PlayCategory playCategory;

	public PlayCategoryService getPlayCategoryService() {
		return playCategoryService;
	}

	public void setPlayCategoryService(PlayCategoryService playCategoryService) {
		this.playCategoryService = playCategoryService;
	}

	public PlayCategory getPlayCategory() {
		return playCategory;
	}

	public void setPlayCategory(PlayCategory playCategory) {
		this.playCategory = playCategory;
	}

	public String save(){
		playCategoryService.savePlayCategory(playCategory);
		return SUCCESS;
	}
	
	public String addPage(){
		return SUCCESS;
	}
}
