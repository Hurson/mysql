package com.avit.ads.pushads.ui.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.avit.ads.pushads.ui.bean.ui.UiNitAd;
import com.avit.ads.pushads.ui.bean.ui.UiOc;
import com.avit.ads.pushads.ui.bean.ui.UiVersion;
import com.avit.ads.pushads.ui.dao.UiDao;
import com.avit.common.page.dao.impl.CommonDaoImpl;
@Repository
public class UiDaoImpl extends CommonDaoImpl implements UiDao {
	@Resource(name = "sessionFactoryUi")//可替换为Unt的数据库
    public void setSuperSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

	public UiVersion getUiVersionByType(String type) {
		// TODO Auto-generated method stub
		String sql ;
		sql = "from UiVersion t where 1=1";
		sql=sql+" and t.type ="+type;
		List param=null;
		List temp=this.getListForAll(sql,param);
		if (temp!=null && temp.size()>0)
		{
			return (UiVersion)temp.get(0);
		}
		return null;
	}

	public void updateVersion(UiVersion uiVersion) {
		// TODO Auto-generated method stub
		if(uiVersion != null){
			this.save(uiVersion);
		}
	}
	public List<UiOc> queryUiOcList()
	{
		try
		{
		String sql ;
		sql = "from UiOc t where 1=1";
		List param=null;
		List temp=this.getListForAll(sql,param);
		return temp;
		}
		catch(Exception e)
		{
			return null;
		}
	}
	public List<UiNitAd> queryUiNitAdList()
	{
		try
		{
		String sql ;
		sql = "from UiNitAd t where 1=1";
		List param=null;
		List temp=this.getListForAll(sql,param);
		return temp;
		}
		catch(Exception e)
		{
			return null;
		}
	}
	public List<UiVersion> queryUiVersionAdList()
	{
		try
		{
		String sql ;
		sql = "from UiVersion t where 1=1";
		List param=null;
		List temp=this.getListForAll(sql,param);
		return temp;
		}
		catch(Exception e)
		{
			return null;
		}
	}
}
