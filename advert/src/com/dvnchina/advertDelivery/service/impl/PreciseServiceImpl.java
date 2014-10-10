package com.dvnchina.advertDelivery.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.dvnchina.advertDelivery.bean.precise.PreciseBean;
import com.dvnchina.advertDelivery.dao.PreciseDao;
import com.dvnchina.advertDelivery.model.PreciseMatch;
import com.dvnchina.advertDelivery.service.PreciseService;

public class PreciseServiceImpl implements PreciseService{

	private PreciseDao preciseDao;
	
	@Override
	public Map<String, String> insertPrecise(PreciseMatch precise) {
		Map<String,String> resultMap = new HashMap<String,String>();
		String handleFlag = "";
		try {
			preciseDao.insertPrecise(precise);
			handleFlag = "true";
		} catch (RuntimeException e) {
			handleFlag = "false";
			e.printStackTrace();
		}
		resultMap.put("flag", handleFlag);
		return resultMap;
	}
	
	@Override
	public PreciseBean getPreciseById(Integer preciseId) {
		StringBuffer query = new StringBuffer();
		query.append("select preciseP.*,plat.category_name categoryName from(select t.id,t.match_name matchName,t.type,t.product_code productCode,");
		query.append("t.product_name productName,t.btv_channel_id btvChannelId,t.btv_channel_name btvChannelName,t.asset_sort_id assetSortId,");
		query.append("t.user_area userArea,t.user_area_name userAreaName,t.userindustrys userIndustrys,t.userindustrys_name userIndustrysName,");
		query.append("t.key,t.userlevels userLevels,t.userlevels_name userLevelsName,t.tvn_number tvnNumber,t.priority,t.ploy_id ployId,");
		query.append("t.category_id categoryId,ploy.ploy_name ployName from t_precise_match t left join t_ploy ploy on t.ploy_id=ploy.id) ");
		query.append("preciseP left join t_plat_category plat on preciseP.categoryId=plat.category_id where preciseP.id="+preciseId);
		return preciseDao.getPreciseById(query.toString());
	}

	@Override
	public Map<String, String> updatePrecise(PreciseMatch precise) {
		Map<String,String> resultMap = new HashMap<String,String>();
		String handleFlag = "";
		try {
			preciseDao.updatePrecise(precise);
			handleFlag = "true";
		} catch (RuntimeException e) {
			handleFlag = "false";
			e.printStackTrace();
		}
		resultMap.put("flag", handleFlag);
		return resultMap;
	}

	@Override
	public void deletePreciseById(String preciseId) {
		preciseDao.deletePreciseById(preciseId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getPreciseCount(Map condition,String ployId) {
		
		StringBuffer query = new StringBuffer();
		query.append("select count(*) from t_precise_match ");
		if(condition!=null&&condition.size()>0){
			Set conditionSet = condition.entrySet();
			for (Iterator iterator = conditionSet.iterator(); iterator
					.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String columnName = (String)entry.getKey();
				Object object = entry.getValue();
				query.append("where ").append(columnName).append(" like '%").append(object).append("%'");
				if (StringUtils.isNotBlank(ployId)) {
					query.append(" and ploy_id=").append(ployId);
				}
			}
		}else{
			if (StringUtils.isNotBlank(ployId)) {
				query.append("where ploy_id=").append(ployId);
			}
		}
		return preciseDao.getPreciseCount(query.toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PreciseBean> page(String ployId,Map condition, int start, int end) {
		StringBuffer query = new StringBuffer();
		/*query.append("select precisePCCP.*,plat.category_name categoryName from(select precisePCC.*,ploy.ploy_name ployName ");
		query.append("from(select precisePC.*,category.category_name assetSortName from(select preciseP.*,channel.channel_name ");
		query.append("btvChannelName from(select precise.id id,precise.match_name matchName,precise.type, precise.product_code productCode,");
		query.append("product.product_name productName,precise.btv_channel_id btvChannelId,precise.asset_sort_id assetSortId,");
		query.append("precise.key,precise.user_area userArea,precise.userindustrys userIndustrys,precise.userlevels userLevels,");
		query.append("precise.tvn_number tvnNumber,precise.priority,precise.ploy_id ployId,precise.category_id categoryId ");
		query.append("from t_precise_match precise left join t_productinfo product on precise.product_code=product.product_id) ");
		query.append("preciseP left join t_channelinfo channel on preciseP.btvChannelId=channel.channel_id) precisePC left join ");
		query.append("t_categoryinfo category on precisePC.assetSortId=category.category_id) precisePCC left join t_ploy ploy on ");
		query.append("precisePCC.ployId=ploy.ploy_id) precisePCCP left join t_plat_category plat on precisePCCP.categoryId=plat.category_id ");*/
		query.append("select t.id,t.match_name matchName,t.type,t.product_code productCode,t.product_name productName,t.key,");
		query.append("t.btv_channel_id btvChannelId,t.btv_channel_name btvChannelName,t.user_area userArea,t.user_area_name userAreaName,");
		query.append("t.userindustrys userIndustrys,t.userindustrys_name userIndustrysName,t.userlevels userLevels,t.userlevels_name userLevelsName,");
		query.append("t.tvn_number tvnNumber,t.priority,t.ploy_id ployId,t.category_id categoryId,ploy.ploy_name ployName ");
		query.append("from t_precise_match t left join t_ploy ploy on t.ploy_id=ploy.ploy_id ");
		if(condition!=null&&condition.size()>0){
			Set conditionSet = condition.entrySet();
			for (Iterator iterator = conditionSet.iterator(); iterator
					.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String columnName = (String)entry.getKey();
				Object object = entry.getValue();
				query.append("where ").append(columnName).append(" like '%").append(object).append("%'");
				if (StringUtils.isNotBlank(ployId)) {
					query.append(" and t.ploy_id=").append(ployId);
				}
			}
		}else{
			if (StringUtils.isNotBlank(ployId)) {
				query.append("where t.ploy_id=").append(ployId);
			}
		}
		return preciseDao.page(query.toString(), start, end);
	}
	
	@Override
	public int getProductCount(String productName) {
		StringBuffer query = new StringBuffer();
		query.append("select count(*) from t_productinfo ");
		if(productName!=null&&"".equals(productName)){
			query.append("where product_name like '%").append(productName).append("%'");
		}
		return preciseDao.getProductCount(query.toString());
	}

	@Override
	public List<PreciseBean> getProductList(String productName, int start, int end) {
		StringBuffer query = new StringBuffer();
		query.append("select t.product_id productCode,t.product_name productName from t_productinfo t ");
		if(productName!=null&&"".equals(productName)){
			query.append("where product_name like '%").append(productName).append("%'");
		}
		return preciseDao.getProductList(query.toString(), start, end);
	}
	
	@Override
	public int getChannelCount(String channelName) {
		StringBuffer query = new StringBuffer();
		query.append("select count(*) from t_channelinfo ");
		if(channelName!=null&&"".equals(channelName)){
			query.append("where channel_name like '%").append(channelName).append("%'");
		}
		return preciseDao.getChannelCount(query.toString());
	}

	@Override
	public List<PreciseBean> getChannelList(String channelName, int start, int end) {
		StringBuffer query = new StringBuffer();
		query.append("select t.channel_id btvChannelId,t.channel_name btvChannelName from t_channelinfo t ");
		if(channelName!=null&&"".equals(channelName)){
			query.append("where channel_name like '%").append(channelName).append("%'");
		}
		return preciseDao.getChannelList(query.toString(), start, end);
	}
	
	@Override
	public int getAssetSortCount(String assetSortName) {
		StringBuffer query = new StringBuffer();
		query.append("select count(*) from t_categoryinfo ");
		if(assetSortName!=null&&"".equals(assetSortName)){
			query.append("where category_name like '%").append(assetSortName).append("%'");
		}
		return preciseDao.getAssetSortCount(query.toString());
	}

	@Override
	public List<PreciseBean> getAssetSortList(String assetSortName, int start, int end) {
		StringBuffer query = new StringBuffer();
		query.append("select t.category_id assetSortId,t.category_name assetSortName from t_categoryinfo t ");
		if(assetSortName!=null&&"".equals(assetSortName)){
			query.append("where category_name like '%").append(assetSortName).append("%'");
		}
		return preciseDao.getAssetSortList(query.toString(), start, end);
	}
	
	@Override
	public int getKeywordCount(String keyword) {
		StringBuffer query = new StringBuffer();
		query.append("select count(*) from t_assetinfo where keyword is not null ");
		if(keyword!=null&&"".equals(keyword)){
			query.append("and keyword like '%").append(keyword).append("%'");
		}
		return preciseDao.getKeywordCount(query.toString());
	}

	@Override
	public List<PreciseBean> getKeywordList(String keyword, int start, int end) {
		StringBuffer query = new StringBuffer();
		query.append("select keyword key,id from t_assetinfo where keyword is not null ");
		if(keyword!=null&&"".equals(keyword)){
			query.append("and keyword like '%").append(keyword).append("%'");
		}
		return preciseDao.getKeywordList(query.toString(), start, end);
	}
	
	@Override
	public int getUserAreaCount(String locationName) {
		StringBuffer query = new StringBuffer();
		query.append("select count(*) from t_location_code ");
		if(locationName!=null&&"".equals(locationName)){
			query.append("where locationname like '%").append(locationName).append("%'");
		}
		return preciseDao.getUserAreaCount(query.toString());
	}

	@Override
	public List<PreciseBean> getUserAreaList(String locationName, int start, int end) {
		StringBuffer query = new StringBuffer();
		query.append("select t.locationname userAreaName,t.locationcode userArea from t_location_code t ");
		if(locationName!=null&&"".equals(locationName)){
			query.append("where locationname like '%").append(locationName).append("%'");
		}
		return preciseDao.getUserAreaList(query.toString(), start, end);
	}
	
	@Override
	public int getUserIndustrysCount(String userIndustrysName) {
		StringBuffer query = new StringBuffer();
		query.append("select count(*) from t_user_industry_category ");
		if(userIndustrysName!=null&&"".equals(userIndustrysName)){
			query.append("where user_industry_category_value like '%").append(userIndustrysName).append("%'");
		}
		return preciseDao.getUserIndustrysCount(query.toString());
	}

	@Override
	public List<PreciseBean> getUserIndustrysList(String userIndustrysName, int start, int end) {
		StringBuffer query = new StringBuffer();
		query.append("select t.user_industry_category_value userIndustrysName,t.user_industry_category_code userIndustrys from t_user_industry_category t ");
		if(userIndustrysName!=null&&"".equals(userIndustrysName)){
			query.append("where user_industry_category_value like '%").append(userIndustrysName).append("%'");
		}
		return preciseDao.getUserIndustrysList(query.toString(), start, end);
	}
	
	@Override
	public int getUserRankCount(String userRankName) {
		StringBuffer query = new StringBuffer();
		query.append("select count(*) from t_user_rank ");
		if(userRankName!=null&&"".equals(userRankName)){
			query.append("where user_rank_name like '%").append(userRankName).append("%'");
		}
		return preciseDao.getUserRankCount(query.toString());
	}

	@Override
	public List<PreciseBean> getUserRankList(String userRankName, int start, int end) {
		StringBuffer query = new StringBuffer();
		query.append("select t.user_rank_name userLevelsName,t.user_rank_code userLevels from t_user_rank t ");
		if(userRankName!=null&&"".equals(userRankName)){
			query.append("where user_rank_name like '%").append(userRankName).append("%'");
		}
		return preciseDao.getUserRankList(query.toString(), start, end);
	}
	
	@Override
	public int getPlatCategoryCount(String categoryName) {
		StringBuffer query = new StringBuffer();
		query.append("select count(*) from t_plat_category ");
		if(categoryName!=null&&"".equals(categoryName)){
			query.append("where category_name like '%").append(categoryName).append("%'");
		}
		return preciseDao.getPlatCategoryCount(query.toString());
	}

	@Override
	public List<PreciseBean> getPlatCategoryList(String categoryName, int start, int end) {
		StringBuffer query = new StringBuffer();
		query.append("select t.category_name categoryName,t.category_id productCode from t_plat_category t ");
		if(categoryName!=null&&"".equals(categoryName)){
			query.append("where category_name like '%").append(categoryName).append("%'");
		}
		return preciseDao.getPlatCategoryList(query.toString(), start, end);
	}
	
	@Override
	public int getPloyCount(String ployName) {
		StringBuffer query = new StringBuffer();
		query.append("select count(*) from t_ploy ");
		if(ployName!=null&&"".equals(ployName)){
			query.append("where ploy_name like '%").append(ployName).append("%'");
		}
		return preciseDao.getPloyCount(query.toString());
	}

	@Override
	public List<PreciseBean> getPloyList(String ployName, int start, int end) {
		StringBuffer query = new StringBuffer();
		query.append("select t.ploy_name ployName,t.id ployId from t_ploy t ");
		if(ployName!=null&&"".equals(ployName)){
			query.append("where ploy_name like '%").append(ployName).append("%'");
		}
		return preciseDao.getPloyList(query.toString(), start, end);
	}
	

	public PreciseDao getPreciseDao() {
		return preciseDao;
	}

	public void setPreciseDao(PreciseDao preciseDao) {
		this.preciseDao = preciseDao;
	}

}