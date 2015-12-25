package com.avit.ads.dtmb.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.avit.ads.dtmb.bean.DOcgInfo;
import com.avit.ads.dtmb.dao.DOcgInfoDao;
import com.avit.ads.pushads.task.bean.OcgInfo;
import com.avit.common.page.dao.impl.CommonDaoImpl;


@Repository("DOcgInfoDao")
public class DOcgInfoDaoImpl extends CommonDaoImpl implements DOcgInfoDao{
	
	public List<DOcgInfo> getOcgInfoList(){
		String sql = "from DOcgInfo";
		List param = null;
		return this.getListForAll(sql, param);
	}
	public List<DOcgInfo> getOcgMulticastInfoList(String areaCode, String tsId){
		
		String sql = "select new DOcgInfo(ocg.id, ocg.ip, ocg.port, ocg.user, ocg.pwd, ocg.areaCode, cast.tsId, cast.multicastIp, cast.multicastPort, cast.multicastBitrate, cast.streamId)" +
				" from DOcgInfo ocg,DMulticastInfo cast where ocg.areaCode = cast.areaCode and ocg.flag = cast.flag and ocg.areaCode = ?";
		List<String> param = new ArrayList<String>();
		param.add(areaCode);
		if(StringUtils.isNotBlank(tsId)){
			sql += " and cast.tsId = ?";
			param.add(tsId);
		}
		
		List<DOcgInfo> rs = this.getListForAll(sql,param);
		
		return rs;
	}
	
	
}
