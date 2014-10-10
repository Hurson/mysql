package com.dvnchina.advertDelivery.dao.impl;


import java.util.List;
import com.dvnchina.advertDelivery.dao.CacheDao;
import com.dvnchina.advertDelivery.ploy.bean.LocationCode;

public class CacheDaoImpl  extends BaseDaoImpl implements CacheDao {
    
    public List<LocationCode> queryLocationCode()
    {
        String sql = "from LocationCode where locationtype>1 order by locationtype,parentlocation,locationcode";
        List<LocationCode> temp = this.getListForAll(sql,( Object[] )null);
        return temp;
    }
}


