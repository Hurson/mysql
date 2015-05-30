package com.avit.ads.pushads.ocg.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.avit.ads.pushads.ocg.dao.OcgInfoDao;
import com.avit.ads.pushads.task.bean.OcgInfo;
import com.avit.common.page.dao.impl.CommonDaoImpl;


@Repository("OcgInfoDao")
public class OcgInfoDaoImpl extends CommonDaoImpl implements OcgInfoDao{
	
	public List<OcgInfo> getOcgInfoList(){
		String sql = "from OcgInfo";
		List param = null;
		return this.getListForAll(sql, param);
	}
	public List<OcgInfo> getOcgMulticastInfoList(String areaCode, String tsId){
		
		String sql = "select new com.avit.ads.pushads.task.bean.OcgInfo(ocg.id, ocg.ip, ocg.port, ocg.user, ocg.pwd, ocg.areaCode, cast.tsId, cast.multicastIp, cast.multicastPort, cast.multicastBitrate, cast.streamId)" +
				" from OcgInfo ocg,TMulticastInfo cast where ocg.areaCode = cast.areaCode and ocg.flag = cast.flag and ocg.areaCode = ?";
		List<String> param = new ArrayList<String>();
		param.add(areaCode);
		if(StringUtils.isNotBlank(tsId)){
			sql += " and cast.tsId = ?";
			param.add(tsId);
		}
		
		List<OcgInfo> rs = this.getListForAll(sql,param);
		
		return rs;
	}
	
	
}
