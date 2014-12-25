package com.avit.ads.pushads.ocg.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.avit.ads.pushads.ocg.dao.OcgInfoDao;
import com.avit.ads.pushads.task.bean.OcgInfo;
import com.avit.common.page.dao.impl.CommonDaoImpl;


@Repository("OcgInfoDao")
public class OcgInfoDaoImpl extends CommonDaoImpl implements OcgInfoDao{
	
	public List<OcgInfo> getOcgInfoList(){
		
		String sql = "from OcgInfo";
		
		List param=null;
		List<OcgInfo> rs = this.getListForAll(sql,param);
		
		return rs;
	}
	
	
}
