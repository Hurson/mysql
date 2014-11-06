package com.avit.ads.pushads.uix.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.avit.ads.pushads.task.bean.TReleaseArea;
import com.avit.ads.pushads.uix.dao.AreaDao;
import com.avit.common.page.dao.impl.BaseDaoImpl;

@Repository
public class AreaDaoImpl extends BaseDaoImpl implements AreaDao {

	public TReleaseArea getAreaByAreaCode(String areaCode) {
		
		List resultList = this.find("from TReleaseArea ra where ra.areaCode=?", areaCode);

		if(null != resultList && resultList.size() > 0){
			TReleaseArea entity = (TReleaseArea)resultList.get(0);
			return entity;
		}
		return null; //预定onid和areaCode相同
	}

	public List<TReleaseArea> getAllArea() {
		return this.find("from TReleaseArea ra where ra.areaCode <> '152000000000'");
	}
	
	
	
}
