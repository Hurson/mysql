package com.dvnchina.advertDelivery.dao.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

import com.dvnchina.advertDelivery.bean.marketingRule.AddRuleBean;
import com.dvnchina.advertDelivery.bean.marketingRule.MarketingRuleBean;
import com.dvnchina.advertDelivery.dao.MarketingRuleDao;
import com.dvnchina.advertDelivery.model.MarketingRule;

/**
 * 营销规则数据操作类
 * @author Administrator
 *
 */
public class MarketingRuleDaoImpl extends BaseDaoImpl implements MarketingRuleDao{

	@Override
	public void insertMarketingRule(MarketingRule marketingRule) {
		getSession().save(marketingRule);
	}

	@Override
	public void updateMarketingRule(MarketingRule marketingRule) {
		getSession().update(marketingRule);
	}

	@Override
	public void deleteMarketingRuleById(String marketingRuleId) {
		String hql = "delete from MarketingRule where marketingRuleId=?";
		getSession().createQuery(hql).setInteger(0, Integer.valueOf(marketingRuleId))
				.executeUpdate();
	}
	
	@Override
	public void upLineMarketingRule(String marketingRuleId) {
		String hql = "update MarketingRule set state=1 where marketingRuleId=?";
		getSession().createQuery(hql).setInteger(0, Integer.valueOf(marketingRuleId))
		.executeUpdate();
	}
	
	@Override
	public void downLineMarketingRule(String marketingRuleId) {
		String hql = "update MarketingRule set state=0 where marketingRuleId=?";
		getSession().createQuery(hql).setInteger(0, Integer.valueOf(marketingRuleId))
				.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MarketingRuleBean> getAllMarketingRule(int begin, int end) {
		String sql = "select * from (select row_.*, rownum rownum_ from (select t.id id,t.rule_name ruleName,t.start_time startTime," +
				"t.end_time endTime, ta.position_name positionName,tr.area_name areaName,tc.channel_name channelName,t.create_time " +
				"createTime,t.state from t_marketing_rule t,t_channelinfo tc,t_advertposition ta," +
				"t_release_area tr where t.position_id=ta.id and t.channel_id=tc.channel_id and t.location_id=tr.id) " +
				"row_ where rownum <= " + end + ") where rownum_ >=" + begin;
		List<MarketingRuleBean> ruleList = getSession().createSQLQuery(sql)
							  .addScalar("id",Hibernate.INTEGER)
							  .addScalar("ruleName",Hibernate.STRING)
							  .addScalar("startTime",Hibernate.STRING)
							  .addScalar("endTime",Hibernate.STRING)
							  .addScalar("positionName",Hibernate.STRING)
							  .addScalar("areaName",Hibernate.STRING)
							  .addScalar("channelName",Hibernate.STRING)
							  .addScalar("createTime",Hibernate.DATE)
							  .addScalar("state",Hibernate.INTEGER)
							  .setResultTransformer(Transformers.aliasToBean(MarketingRuleBean.class)).list();
		return ruleList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MarketingRuleBean> getMarketingRuleById(Integer ruleId) {
		String sql = "select marketRuleAdpc.*,tc.channel_name channelName from(select marketRuleAdp.*,ta.area_name areaName " +
				"from(select t.id id,t.rule_id ruleId,t.rule_name ruleName,t.start_time startTime,t.location_id areaId," +
				"t.position_id positionId,t.channel_id channelId,t.end_time endTime,ta.position_name positionName from " +
				"t_marketing_rule t left join t_advertposition ta on t.position_id=ta.id) marketRuleAdp left join " +
				"t_release_area ta on marketRuleAdp.areaId=ta.id) marketRuleAdpc left join t_channelinfo tc on " +
				"marketRuleAdpc.channelId=tc.channel_id where marketRuleAdpc.ruleId="+ruleId;
		List<MarketingRuleBean> marketingRuleList = getSession().createSQLQuery(sql)
		  .addScalar("id",Hibernate.INTEGER)
		  .addScalar("ruleId",Hibernate.STRING)
		  .addScalar("ruleName",Hibernate.STRING)
		  .addScalar("startTime",Hibernate.STRING)
		  .addScalar("endTime",Hibernate.STRING)
		  .addScalar("positionName",Hibernate.STRING)
		  .addScalar("areaName",Hibernate.STRING)
		  .addScalar("channelName",Hibernate.STRING)
		  .addScalar("positionId",Hibernate.STRING)
		  .addScalar("areaId",Hibernate.STRING)
		  .addScalar("channelId",Hibernate.STRING)
		  .setResultTransformer(Transformers.aliasToBean(MarketingRuleBean.class)).list();
		return marketingRuleList;
	}
	
	@Override
	public int getMarketingRuleCount(String sql) {
		String count = this.getSession().createSQLQuery(sql).uniqueResult().toString();
		return new Integer(count);
	}
	
	@Override
	public int getAreaCount() {
		String sql = "select count(*) from t_release_area";
		String count = this.getSession().createSQLQuery(sql).uniqueResult().toString();
		return new Integer(count);
	}
	
	@Override
	public int getAreaCount(String positionId) {
		String sql = "select count(*) from t_release_area ta where not exists (select * from t_marketing_rule t where t.position_id="+positionId+" and t.location_id=ta.id)";
		String count = this.getSession().createSQLQuery(sql).uniqueResult().toString();
		return new Integer(count);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MarketingRuleBean> getAreaList(String positionId, int begin, int end) {
		String sql = "select * from (select row_.*, rownum rownum_ from (select ta.id,ta.area_name as areaName from t_release_area ta " +
				"where not exists (select * from t_marketing_rule t where t.position_id="+positionId+" and t.location_id=ta.id)) " +
				"row_ where rownum <= " + end + ") where rownum_ >=" + begin;
		List<MarketingRuleBean> areaList = getSession().createSQLQuery(sql)
							  .addScalar("id",Hibernate.INTEGER)
							  .addScalar("areaName",Hibernate.STRING)
							  .setResultTransformer(Transformers.aliasToBean(MarketingRuleBean.class)).list();
		return areaList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MarketingRuleBean> getAreaList(int begin, int end) {
		String sql = "select * from (select row_.*, rownum rownum_ from (select ta.id,ta.area_name as areaName from t_release_area ta)" +
				"row_ where rownum <= " + end + ") where rownum_ >=" + begin;
		List<MarketingRuleBean> areaList = getSession().createSQLQuery(sql)
							  .addScalar("id",Hibernate.INTEGER)
							  .addScalar("areaName",Hibernate.STRING)
							  .setResultTransformer(Transformers.aliasToBean(MarketingRuleBean.class)).list();
		return areaList;
	}
	
	@Override
	public int getRuleCount(String positionId) {
		String sql = "select count(*) from t_marketing_rule where position_id="+positionId;
		String count = this.getSession().createSQLQuery(sql).uniqueResult().toString();
		return new Integer(count);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MarketingRuleBean> getRuleList(String positionId, int begin, int end) {
		String sql = "select * from (select row_.*, rownum rownum_ from (select t.id id,t.rule_name ruleName,t.start_time startTime," +
		"t.end_time endTime, ta.position_name positionName,tr.area_name areaName,tc.channel_name channelName,t.create_time " +
		"createTime,t.state from t_marketing_rule t,t_channelinfo tc,t_advertposition ta,t_release_area tr where " +
		"t.position_id=ta.id and t.channel_id=tc.channel_id and t.location_id=tr.id and t.position_id="+positionId+") " +
		"row_ where rownum <= " + end + ") where rownum_ >=" + begin;
		List<MarketingRuleBean> ruleList = getSession().createSQLQuery(sql)
							  .addScalar("id",Hibernate.INTEGER)
							  .addScalar("ruleName",Hibernate.STRING)
							  .addScalar("startTime",Hibernate.STRING)
							  .addScalar("endTime",Hibernate.STRING)
							  .addScalar("positionName",Hibernate.STRING)
							  .addScalar("areaName",Hibernate.STRING)
							  .addScalar("channelName",Hibernate.STRING)
							  .addScalar("createTime",Hibernate.DATE)
							  .addScalar("state",Hibernate.INTEGER)
							  .setResultTransformer(Transformers.aliasToBean(MarketingRuleBean.class)).list();
		return ruleList;
	}
	
	@Override
	public int getChannelCount(String positionId, String areaId) {
		String sql = "select count(*) from t_channelinfo tc where not exists (select * from t_marketing_rule t where t.position_id="+positionId+" and t.location_id="+areaId+" and t.channel_id=tc.channel_id)";
		String count = this.getSession().createSQLQuery(sql).uniqueResult().toString();
		return new Integer(count);
	}

	@Override
	public int getChannelCount(String positionId) {
		String sql = "select count(*) from t_channelinfo tc where not exists (select * from t_marketing_rule t where t.position_id="+positionId+" and t.channel_id=tc.channel_id)";
		String count = this.getSession().createSQLQuery(sql).uniqueResult().toString();
		return new Integer(count);
	}

	@Override
	public int getChannelCount() {
		String sql = "select count(*) from t_channelinfo";
		String count = this.getSession().createSQLQuery(sql).uniqueResult().toString();
		return new Integer(count);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MarketingRuleBean> getChannelList(String positionId,
			String areaId, int begin, int end) {
		String sql = "select * from (select row_.*, rownum rownum_ from (select tc.channel_id as id,tc.channel_name as channelName from t_channelinfo tc " +
		"where not exists (select * from t_marketing_rule t where t.position_id="+positionId+" and t.location_id="+areaId+" and t.channel_id=tc.channel_id)) " +
		"row_ where rownum <= " + end + ") where rownum_ >=" + begin;
		List<MarketingRuleBean> channelList = getSession().createSQLQuery(sql)
					  .addScalar("id",Hibernate.INTEGER)
					  .addScalar("channelName",Hibernate.STRING)
					  .setResultTransformer(Transformers.aliasToBean(MarketingRuleBean.class)).list();
		return channelList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MarketingRuleBean> getChannelList(String positionId, int begin,
			int end) {
		String sql = "select * from (select row_.*, rownum rownum_ from (select tc.channel_id as id,tc.channel_name as channelName from t_channelinfo tc " +
		"where not exists (select * from t_marketing_rule t where t.position_id="+positionId+" and t.channel_id=tc.channel_id)) " +
		"row_ where rownum <= " + end + ") where rownum_ >=" + begin;
		List<MarketingRuleBean> channelList = getSession().createSQLQuery(sql)
					  .addScalar("id",Hibernate.INTEGER)
					  .addScalar("channelName",Hibernate.STRING)
					  .setResultTransformer(Transformers.aliasToBean(MarketingRuleBean.class)).list();
		return channelList;
}

	@SuppressWarnings("unchecked")
	@Override
	public List<MarketingRuleBean> getChannelList(int begin, int end) {
		String sql = "select * from (select row_.*, rownum rownum_ from (select tc.channel_id as id,tc.channel_name as channelName from t_channelinfo tc)" +
		"row_ where rownum <= " + end + ") where rownum_ >=" + begin;
		List<MarketingRuleBean> channelList = getSession().createSQLQuery(sql)
					  .addScalar("id",Hibernate.INTEGER)
					  .addScalar("channelName",Hibernate.STRING)
					  .setResultTransformer(Transformers.aliasToBean(MarketingRuleBean.class)).list();
		return channelList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MarketingRuleBean> page(String sql, int start, int end) {
		String pageSql = "select * from (select row_.*, rownum rownum_ from ("
			+ sql + ") row_ where rownum <= "+end+") where rownum_ >="+start;
		List<MarketingRuleBean> ruleList = getSession().createSQLQuery(pageSql)
		  .addScalar("ruleName",Hibernate.STRING)
		  .addScalar("ruleId",Hibernate.STRING)
		  .addScalar("endTime",Hibernate.STRING)
		  .addScalar("startTime",Hibernate.STRING)
		  .addScalar("createTime",Hibernate.DATE)
		  .addScalar("state",Hibernate.INTEGER)
		  .setResultTransformer(Transformers.aliasToBean(MarketingRuleBean.class)).list();
		return ruleList;
	}

}