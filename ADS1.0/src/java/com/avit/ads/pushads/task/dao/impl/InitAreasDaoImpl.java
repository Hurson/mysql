package com.avit.ads.pushads.task.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.avit.ads.pushads.task.bean.TChannelinfo;
import com.avit.ads.pushads.task.bean.TReleaseArea;
import com.avit.ads.pushads.task.dao.InitAreasDao;
import com.avit.common.page.dao.impl.CommonDaoImpl;
@Repository
public class InitAreasDaoImpl extends CommonDaoImpl implements InitAreasDao {
	 public List<String> getAreas(){
		 StringBuffer sb = new StringBuffer();
		 sb.append("select distinct t.areaCode from TReleaseArea t  where 1=1");
	      
	      List params = new ArrayList();
	     // sb.append(" order by ads.id desc "); //按id升序排序
	    
	      List<String>  areaList =this.getListForAll(sb.toString(), params);
	      List<String>  tempList=new ArrayList<String>();
	      tempList=areaList;
	      for(int i=0;i<tempList.size();i++){
	    	  if(tempList.get(i).equals("152000000000")){
	    		  areaList.remove(i);
	    	  }
	    	  
	      }
	      
	      return areaList;
	      
		 
	 }
}
