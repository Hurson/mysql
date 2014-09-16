package com.avit.ads.pushads.task.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.avit.ads.pushads.task.dao.InitAreasDao;
import com.avit.ads.pushads.task.service.InitAreasService;
@Service("InitAreasService")
public class InitAreasServiceImpl implements InitAreasService {
   @Inject
   public InitAreasDao initAreasDao;
   public List<String> getAreas(){
	  return initAreasDao.getAreas();
   }
}
