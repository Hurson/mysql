package com.dvnchina.advertDelivery.dao;

import java.util.List;
import com.dvnchina.advertDelivery.ploy.bean.LocationCode;


public interface CacheDao {
    
    public List<LocationCode> queryLocationCode();
    
}

