package com.avit.ads.util;

import java.util.List;
import javax.inject.Inject;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.avit.ads.pushads.task.service.InitAreasService;
import com.avit.ads.pushads.task.service.PushAdsService;
import com.avit.common.page.dao.impl.CommonDaoImpl;

public class InitAreas extends CommonDaoImpl {
	@Inject
	public InitAreasService initAreasService;
	private static InitAreas initAreas = new InitAreas();
    public static InitAreas getInstance() {
			return initAreas;
			
	}
    
    public List<String> getAreas(){
    	initAreasService= (InitAreasService)ContextHolder.getApplicationContext().getBean("InitAreasService");
    	//initAreasService  = (InitAreasService) wac.getBean("InitAreasService");
    	return initAreasService.getAreas();
    }
		
}
