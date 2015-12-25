package com.avit.ads.dtmb.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.avit.ads.dtmb.dao.DUntDao;
import com.avit.ads.pushads.ocg.bean.UntVersion;
import com.avit.common.page.dao.impl.BaseDaoImpl;

@Repository
public class DUntDaoImpl extends BaseDaoImpl implements DUntDao {

	public int getUntVersion(String areaCode, Integer type) {
		List resultList = this.getHibernateTemplate().find("from DUntVersion uv where uv.areaCode=? and uv.updateType=?", areaCode, type);
		
		if(null != resultList && resultList.size() > 0){
			UntVersion entity = (UntVersion)resultList.get(0);
			return entity.getVersion();
		}else{
			return 0;
		}
	}

	public void updateVersion(String areaCode, Integer type) {
		List resultList = this.getHibernateTemplate().find("from DUntVersion uv where uv.areaCode=? and uv.updateType=?", areaCode, type);
		UntVersion entity = null;
		if(null != resultList && resultList.size() > 0){
			entity = (UntVersion)resultList.get(0);
			entity.setVersion(entity.getVersion() + 1);
		}else{
			entity = new UntVersion();
			entity.setAreaCode(areaCode);
			entity.setUpdateType(type);
			entity.setVersion(1);
		}
		this.save(entity);
	}

}
