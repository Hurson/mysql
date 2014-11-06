package com.avit.ads.pushads.ui.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.avit.ads.pushads.ui.bean.nit.Nwconitconf;
import com.avit.ads.pushads.ui.bean.nit.Nwconitdesc;
import com.avit.ads.pushads.ui.bean.ui.UiVersion;
import com.avit.ads.pushads.ui.dao.NitDao;
import com.avit.common.page.dao.impl.CommonDaoImpl;

public class NitDaoImpl  extends CommonDaoImpl implements NitDao {
	@Resource(name = "sessionFactoryNit")//
    public void setSuperSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
	public void addNitDesc(Nwconitdesc desc) {
		// TODO Auto-generated method stub
		if(desc != null){
			this.save(desc);
		}
	}
	public  Nwconitconf getNwconitconfByNid(String networkid)
	{
		String sql ;
		sql = "from Nwconitconf t where 1=1";
		sql=sql+" and t.networkid ="+networkid;
		List param=null;
		List temp=this.getListForAll(sql,param);
		if (temp!=null && temp.size()>0)
		{
			return (Nwconitconf)temp.get(0);
		}
		return null;
	}
	
}
