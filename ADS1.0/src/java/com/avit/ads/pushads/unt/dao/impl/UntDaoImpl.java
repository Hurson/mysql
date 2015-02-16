package com.avit.ads.pushads.unt.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;



import com.avit.ads.pushads.unt.bean.AdsLink;
import com.avit.ads.pushads.unt.bean.AdsPicture;
import com.avit.ads.pushads.unt.bean.AdsSubtitle;
import com.avit.ads.pushads.unt.bean.ResourceUrl;
import com.avit.ads.pushads.unt.bean.UntConfigFile;
import com.avit.ads.pushads.unt.dao.UntDao;

import com.avit.common.page.dao.impl.CommonDaoImpl;
//@Repository
public class UntDaoImpl extends CommonDaoImpl implements UntDao{
	@Resource(name = "sessionFactory")//可替换为Unt的数据库
    public void setSuperSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

	private Log log = LogFactory.getLog(this.getClass());
	
	public void addLink(List<AdsLink> linkList) {
		if(linkList != null && linkList.size()>0){
			this.saveAll(linkList);
		}
	}

	public void addPicture(List<AdsPicture> pictureList) {
		if(pictureList != null && pictureList.size()>0){
			this.saveAll(pictureList);
		}
	}

	public void addSubtitle(List<AdsSubtitle> subtitleList) {
		if(subtitleList != null && subtitleList.size()>0){
			this.saveAll(subtitleList);
		}
	}
	
	public void setConfigFile(UntConfigFile configFile){
		if(configFile != null){
			this.save(configFile);
		}
	}
	public void addResourceUrl(ResourceUrl resourceUrl)
	{
		if(resourceUrl != null){
			this.save(resourceUrl);
		}
	}
}
