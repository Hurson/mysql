package com.dvnchina.advertDelivery.dao.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

import com.dvnchina.advertDelivery.bean.precise.PreciseBean;
import com.dvnchina.advertDelivery.dao.PreciseDao;
import com.dvnchina.advertDelivery.model.PreciseMatch;

/**
 * 精准数据操作类
 * @author Administrator
 *
 */
public class PreciseDaoImpl extends BaseDaoImpl implements PreciseDao{

	@Override
	public void insertPrecise(PreciseMatch precise) {
		getSession().save(precise);
	}

	@Override
	public void updatePrecise(PreciseMatch precise) {
		getSession().update(precise);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PreciseBean getPreciseById(String sql) {
		List<PreciseBean> preciseList = getSession().createSQLQuery(sql)
		  .addScalar("id",Hibernate.INTEGER)
		  .addScalar("matchName",Hibernate.STRING)
		  .addScalar("type",Hibernate.INTEGER)
		  .addScalar("productCode",Hibernate.STRING)
		  .addScalar("productName",Hibernate.STRING)
		  .addScalar("btvChannelId",Hibernate.STRING)
		  .addScalar("btvChannelName",Hibernate.STRING)
		  .addScalar("assetSortId",Hibernate.STRING)
		  .addScalar("key",Hibernate.STRING)
		  .addScalar("userArea",Hibernate.STRING)
		  .addScalar("userAreaName",Hibernate.STRING)
		  .addScalar("userIndustrys",Hibernate.STRING)
		  .addScalar("userIndustrysName",Hibernate.STRING)
		  .addScalar("userLevels",Hibernate.STRING)
		  .addScalar("userLevelsName",Hibernate.STRING)
		  .addScalar("tvnNumber",Hibernate.STRING)
		  .addScalar("priority",Hibernate.INTEGER)
		  .addScalar("ployId",Hibernate.INTEGER)
		  .addScalar("ployName",Hibernate.STRING)
		  .addScalar("categoryId",Hibernate.STRING)
		  .addScalar("categoryName",Hibernate.STRING)
		  .setResultTransformer(Transformers.aliasToBean(PreciseBean.class)).list();
		return preciseList.get(0);
	}

	@Override
	public void deletePreciseById(String preciseId) {
		String hql = "delete from PreciseMatch where id=?";
		getSession().createQuery(hql).setInteger(0, Integer.valueOf(preciseId))
				.executeUpdate();
	}
	
	@Override
	public int getPreciseCount(String sql) {
		String count = this.getSession().createSQLQuery(sql).uniqueResult().toString();
		return new Integer(count);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PreciseBean> page(String sql, int start, int end) {
		String pageSql = "select * from (select row_.*, rownum rownum_ from ("
			+ sql + ") row_ where rownum <= "+end+") where rownum_ >="+start;
		List<PreciseBean> preciseList = getSession().createSQLQuery(pageSql)
		  .addScalar("id",Hibernate.INTEGER)
		  .addScalar("matchName",Hibernate.STRING)
		  .addScalar("type",Hibernate.INTEGER)
		  .addScalar("productCode",Hibernate.STRING)
		  .addScalar("productName",Hibernate.STRING)
		  .addScalar("btvChannelId",Hibernate.STRING)
		  .addScalar("btvChannelName",Hibernate.STRING)
//		  .addScalar("assetSortId",Hibernate.STRING)
		  .addScalar("key",Hibernate.STRING)
		  .addScalar("userArea",Hibernate.STRING)
		  .addScalar("userAreaName",Hibernate.STRING)
		  .addScalar("userIndustrys",Hibernate.STRING)
		  .addScalar("userIndustrysName",Hibernate.STRING)
		  .addScalar("userLevels",Hibernate.STRING)
		  .addScalar("userLevelsName",Hibernate.STRING)
		  .addScalar("tvnNumber",Hibernate.STRING)
		  .addScalar("priority",Hibernate.INTEGER)
		  .addScalar("ployId",Hibernate.INTEGER)
		  .addScalar("ployName",Hibernate.STRING)
//		  .addScalar("categoryId",Hibernate.STRING)
//		  .addScalar("categoryName",Hibernate.STRING)
		  .setResultTransformer(Transformers.aliasToBean(PreciseBean.class)).list();
		return preciseList;
	}

	@Override
	public int getProductCount(String sql) {
		String count = this.getSession().createSQLQuery(sql).uniqueResult().toString();
		return new Integer(count);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PreciseBean> getProductList(String sql, int start, int end) {
		String pageSql = "select * from (select row_.*, rownum rownum_ from ("
			+ sql + ") row_ where rownum <= "+end+") where rownum_ >="+start;
		List<PreciseBean> productList = getSession().createSQLQuery(pageSql)
		  .addScalar("productCode",Hibernate.STRING)
		  .addScalar("productName",Hibernate.STRING)
		  .setResultTransformer(Transformers.aliasToBean(PreciseBean.class)).list();
		return productList;
	}
	
	@Override
	public int getChannelCount(String sql) {
		String count = this.getSession().createSQLQuery(sql).uniqueResult().toString();
		return new Integer(count);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PreciseBean> getChannelList(String sql, int start, int end) {
		String pageSql = "select * from (select row_.*, rownum rownum_ from ("
			+ sql + ") row_ where rownum <= "+end+") where rownum_ >="+start;
		List<PreciseBean> channelList = getSession().createSQLQuery(pageSql)
		  .addScalar("btvChannelId",Hibernate.STRING)
		  .addScalar("btvChannelName",Hibernate.STRING)
		  .setResultTransformer(Transformers.aliasToBean(PreciseBean.class)).list();
		return channelList;
	}
	
	@Override
	public int getAssetSortCount(String sql) {
		String count = this.getSession().createSQLQuery(sql).uniqueResult().toString();
		return new Integer(count);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PreciseBean> getAssetSortList(String sql, int start, int end) {
		String pageSql = "select * from (select row_.*, rownum rownum_ from ("
			+ sql + ") row_ where rownum <= "+end+") where rownum_ >="+start;
		List<PreciseBean> assetSortList = getSession().createSQLQuery(pageSql)
		  .addScalar("assetSortId",Hibernate.STRING)
		  .addScalar("assetSortName",Hibernate.STRING)
		  .setResultTransformer(Transformers.aliasToBean(PreciseBean.class)).list();
		return assetSortList;
	}
	
	@Override
	public int getKeywordCount(String sql) {
		String count = this.getSession().createSQLQuery(sql).uniqueResult().toString();
		return new Integer(count);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PreciseBean> getKeywordList(String sql, int start, int end) {
		String pageSql = "select * from (select row_.*, rownum rownum_ from ("
			+ sql + ") row_ where rownum <= "+end+") where rownum_ >="+start;
		List<PreciseBean> keywordList = getSession().createSQLQuery(pageSql)
			.addScalar("id",Hibernate.INTEGER)
			.addScalar("key",Hibernate.STRING)
			.setResultTransformer(Transformers.aliasToBean(PreciseBean.class)).list();
		return keywordList;
	}
	
	@Override
	public int getUserAreaCount(String sql) {
		String count = this.getSession().createSQLQuery(sql).uniqueResult().toString();
		return new Integer(count);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PreciseBean> getUserAreaList(String sql, int start, int end) {
		String pageSql = "select * from (select row_.*, rownum rownum_ from ("
			+ sql + ") row_ where rownum <= "+end+") where rownum_ >="+start;
		List<PreciseBean> userAreaList = getSession().createSQLQuery(pageSql)
		  .addScalar("userAreaName",Hibernate.STRING)
		  .addScalar("userArea",Hibernate.STRING)
		  .setResultTransformer(Transformers.aliasToBean(PreciseBean.class)).list();
		return userAreaList;
	}
	
	@Override
	public int getUserIndustrysCount(String sql) {
		String count = this.getSession().createSQLQuery(sql).uniqueResult().toString();
		return new Integer(count);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PreciseBean> getUserIndustrysList(String sql, int start, int end) {
		String pageSql = "select * from (select row_.*, rownum rownum_ from ("
			+ sql + ") row_ where rownum <= "+end+") where rownum_ >="+start;
		List<PreciseBean> userIndustrysList = getSession().createSQLQuery(pageSql)
		  .addScalar("userIndustrys",Hibernate.STRING)
		  .addScalar("userIndustrysName",Hibernate.STRING)
		  .setResultTransformer(Transformers.aliasToBean(PreciseBean.class)).list();
		return userIndustrysList;
	}
	
	@Override
	public int getUserRankCount(String sql) {
		String count = this.getSession().createSQLQuery(sql).uniqueResult().toString();
		return new Integer(count);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PreciseBean> getUserRankList(String sql, int start, int end) {
		String pageSql = "select * from (select row_.*, rownum rownum_ from ("
			+ sql + ") row_ where rownum <= "+end+") where rownum_ >="+start;
		List<PreciseBean> userRankList = getSession().createSQLQuery(pageSql)
		  .addScalar("userLevels",Hibernate.STRING)
		  .addScalar("userLevelsName",Hibernate.STRING)
		  .setResultTransformer(Transformers.aliasToBean(PreciseBean.class)).list();
		return userRankList;
	}
	
	@Override
	public int getPlatCategoryCount(String sql) {
		String count = this.getSession().createSQLQuery(sql).uniqueResult().toString();
		return new Integer(count);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PreciseBean> getPlatCategoryList(String sql, int start, int end) {
		String pageSql = "select * from (select row_.*, rownum rownum_ from ("
			+ sql + ") row_ where rownum <= "+end+") where rownum_ >="+start;
		List<PreciseBean> userRankList = getSession().createSQLQuery(pageSql)
		  .addScalar("productCode",Hibernate.STRING)
		  .addScalar("categoryName",Hibernate.STRING)
		  .setResultTransformer(Transformers.aliasToBean(PreciseBean.class)).list();
		return userRankList;
	}

	@Override
	public int getPloyCount(String sql) {
		String count = this.getSession().createSQLQuery(sql).uniqueResult().toString();
		return new Integer(count);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PreciseBean> getPloyList(String sql, int start, int end) {
		String pageSql = "select * from (select row_.*, rownum rownum_ from ("
			+ sql + ") row_ where rownum <= "+end+") where rownum_ >="+start;
		List<PreciseBean> ployList = getSession().createSQLQuery(pageSql)
		  .addScalar("ployId",Hibernate.INTEGER)
		  .addScalar("ployName",Hibernate.STRING)
		  .setResultTransformer(Transformers.aliasToBean(PreciseBean.class)).list();
		return ployList;
	}

}