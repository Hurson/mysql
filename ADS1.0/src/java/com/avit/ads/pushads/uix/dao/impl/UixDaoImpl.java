package com.avit.ads.pushads.uix.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.avit.ads.pushads.uix.dao.UixDao;
import com.avit.ads.pushads.uix.domain.UixVersion;
import com.avit.common.page.dao.impl.BaseDaoImpl;

@Repository
public class UixDaoImpl extends BaseDaoImpl implements UixDao {

	public int getUiVersion(String areaCode, Integer type) {
		
		List resultList = this.getHibernateTemplate().find("from UixVersion uv where uv.areaCode=? and uv.updateType=?", areaCode, type);
		
		if(null != resultList && resultList.size() > 0){
			UixVersion entity = (UixVersion)resultList.get(0);
			return entity.getVersion();
		}else{
			return 0;
		}
	}
	

	public void updateVersion(String areaCode, Integer type) {
		List resultList = this.getHibernateTemplate().find("from UixVersion uv where uv.areaCode=? and uv.updateType=?", areaCode, type);
		UixVersion entity = null;
		if(null != resultList && resultList.size() > 0){
			entity = (UixVersion)resultList.get(0);
			entity.setVersion(entity.getVersion() + 1);
		}else{
			entity = new UixVersion();
			entity.setAreaCode(areaCode);
			entity.setUpdateType(type);
			entity.setVersion(1);
		}
		entity.setAvailable(1);
		this.save(entity);
	}


	public int getAvailableVersion(String areaCode, Integer type) {
		List resultList = this.getHibernateTemplate().find("from UixVersion uv where uv.areaCode=? and uv.updateType=? and uv.available=1", areaCode, type);
		
		if(null != resultList && resultList.size() > 0){
			UixVersion entity = (UixVersion)resultList.get(0);
			return entity.getVersion();
		}else{
			return 0;
		}
	}




	public void abolishVersion(String areaCode, Integer type) {
		String updateSql ="update UixVersion uv set uv.available=0 where uv.areaCode=" + areaCode + " and uv.updateType=" + type;
		executeByHQL(updateSql, null);
	}

}
