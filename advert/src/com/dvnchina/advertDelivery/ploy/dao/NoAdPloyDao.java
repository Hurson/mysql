package com.dvnchina.advertDelivery.ploy.dao;

import java.util.List;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.model.ChannelInfo;
import com.dvnchina.advertDelivery.model.Contract;
import com.dvnchina.advertDelivery.model.ContractAD;
import com.dvnchina.advertDelivery.model.MarketingRule;
import com.dvnchina.advertDelivery.model.Ploy;
import com.dvnchina.advertDelivery.model.ReleaseArea;
import com.dvnchina.advertDelivery.ploy.bean.PloyAreaChannel;
import com.dvnchina.advertDelivery.ploy.bean.PloyBackup;
import com.dvnchina.advertDelivery.ploy.bean.TNoAdPloy;
import com.dvnchina.advertDelivery.position.bean.AdvertPosition;
import com.dvnchina.advertDelivery.utils.page.PageBean;

// TODO: Auto-generated Javadoc
/**
 * The Interface 禁播Dao.
 */
public interface NoAdPloyDao {
	
    /**
     * 查询禁播列表.
     *
     * @param ploy the ploy
     * @param pageSize the page size
     * @param pageNumber the page number
     * @return the page bean db
     */
    PageBeanDB queryNoAdPloyList(TNoAdPloy ploy,Integer pageSize, Integer pageNumber);

    /**
     * 获取禁播详情.
     *
     * @param ployId the ploy id
     * @return the no ad ploy by id
     */
    TNoAdPloy getNoAdPloyByID(Long ployId);
    
    /**
     * 查询双向实时请求广告位列表.
     *
     * @param adPosition the ad position
     * @param pageSize the page size
     * @param pageNumber the page number
     * @return the page bean db
     */
    PageBeanDB queryAdPosition(AdvertPosition adPosition,Integer pageSize, Integer pageNumber);
	
	/**
	 * 校验名称是否重复.
	 *
	 * @param ploy the ploy
	 * @return the string
	 */
	String checkNoAdPloy(TNoAdPloy ploy);	
	
	/**
	 * 保存.
	 *
	 * @param ploy the ploy
	 * @return true, if successful
	 */
	boolean saveOrUpdate(TNoAdPloy ploy);	
	boolean deleteNoAdPloy(String dataIds);
}
