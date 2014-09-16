package com.avit.ads.pushads.ui.dao;

import com.avit.ads.pushads.ui.bean.nit.Nwconitconf;
import com.avit.ads.pushads.ui.bean.nit.Nwconitdesc;

public interface NitDao {
	public  Nwconitconf getNwconitconfByNid(String networkid);
	
	public void addNitDesc(Nwconitdesc desc);
}
