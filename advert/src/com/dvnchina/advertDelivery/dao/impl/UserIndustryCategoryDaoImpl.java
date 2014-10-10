package com.dvnchina.advertDelivery.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.dvnchina.advertDelivery.dao.UserIndustryCategoryDao;
import com.dvnchina.advertDelivery.sysconfig.bean.UserIndustryCategory;
import com.dvnchina.advertDelivery.utils.HibernateSQLTemplete;

public class UserIndustryCategoryDaoImpl extends HibernateSQLTemplete implements UserIndustryCategoryDao {
	
	private static Logger logger = Logger.getLogger(UserIndustryCategoryDaoImpl.class);  

	@Override
	public List<UserIndustryCategory> listUserIndustryCategoryMgr(UserIndustryCategory userIndustryCategory, int x, int y,String state) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		List<UserIndustryCategory> listUserIndustryCategory = null;
		String hql = "from UserIndustryCategory where 1=1"; 
		hql += fillCond(map,userIndustryCategory,state);
		listUserIndustryCategory =(List<UserIndustryCategory>)this.findByHQL(hql, map,x,y);
		
		return listUserIndustryCategory;
		
	}

	@Override
	public int getUserIndustryCategoryCount(UserIndustryCategory userIndustryCategory, String state) {
	
		Map<String,Object> map = new HashMap<String,Object>();
		
		int count = 0;
		
		String hql = "select count(*) from UserIndustryCategory where 1=1";
		hql += fillCond(map,userIndustryCategory,state);
		count = this.getTotalByHQL(hql, map);
		
		if(count != 0){
			System.out.println(count);
		}
		return count;
		
	}

	private String fillCond(Map<String, Object> map,UserIndustryCategory userIndustryCategory, String state) {
		
		logger.debug("---fillCond()  start-------");
		
		StringBuffer sb = new StringBuffer("");
		
		if(StringUtils.isNotBlank(userIndustryCategory.getUserIndustryCategoryCode())){
			map.put("userIndustryCategoryCode",userIndustryCategory.getUserIndustryCategoryCode());
			sb.append(" AND userIndustryCategoryCode =:userIndustryCategoryCode");
		}
		
		if(StringUtils.isNotBlank(userIndustryCategory.getUserIndustryCategoryValue())){
			map.put("userIndustryCategoryValue","%"+userIndustryCategory.getUserIndustryCategoryValue()+"%");
			sb.append(" AND userIndustryCategoryValue like:userIndustryCategoryValue");
		}
		
		return sb.toString();
	}
}
