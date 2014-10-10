package com.avit.ads.pushads.boss.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.avit.ads.pushads.boss.bean.TSyncBoss;
import com.avit.ads.pushads.boss.bean.TvnScore;
import com.avit.ads.pushads.boss.dao.BossDao;
import com.avit.ads.util.DateUtil;
import com.avit.common.page.dao.impl.CommonDaoImpl;
import com.avit.common.util.StringUtil;
@Repository
public class BossDaoImpl extends CommonDaoImpl implements BossDao {

	public List<TvnScore> queryTvnScoreList(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		String month=DateUtil.formatDateTime(startDate,"yyyyMM");
		
		String sql="select u.USERSN,COUNT(*)*q.INTEGRAL*SUBSTRING(t.integral_ratio,1,INSTR(t.integral_ratio,':')-1)/SUBSTRING(t.integral_ratio,INSTR(t.integral_ratio,':')+1), "+month+" month"+
					" from t_user_questionnaire u,t_questionnaire_real q,t_order t"+
					" where  t.id=u.order_id and u.QUESTIONNAIRE_id=q.ID and u.create_time >=? and u.create_time <=?"+
					" group by u.USERSN,u.QUESTIONNAIRE_id";
		
		//select t.integral_ratio,SUBSTRING(t.integral_ratio,1,INSTR(t.integral_ratio,":")-1)/SUBSTRING(t.integral_ratio,INSTR(t.integral_ratio,":")+1),INSTR(t.integral_ratio,":") from t_order t 

		Object[] params= new Object[2];
		params[0]= startDate;
		params[1]= endDate;
		List<TvnScore> listScore = new ArrayList<TvnScore>();
		try
		{
			List listtemp=this.getDataBySql(sql, params);
			if (listtemp!=null && listtemp.size()>0)
			{
				for (int i=0;i<listtemp.size();i++)
				{
					TvnScore scoreTemp =  new TvnScore();
					Object [] objs = (Object [])listtemp.get(i);
					scoreTemp.setTvn(StringUtil.toNotNullStr(objs[0]));
					scoreTemp.setScore(StringUtil.toDouble(objs[1]));
					scoreTemp.setMonth(StringUtil.toNotNullStr(objs[2]));
					listScore.add(scoreTemp);
				}
			}
			
		}catch(Exception e)
		{
			
		}
		return listScore;
	}
	public TSyncBoss querySyncBossListbyTime(Date dataTime)
	{
		TSyncBoss temp = null;
		String sql="from TSyncBoss where dataTime= ?";
		Object[] params=new Object[1];
		params[0]=dataTime;
		try
		{
			List list = this.getListForAll(sql, params);
			if (list!=null && list.size()>0)
			{
				temp = (TSyncBoss)list.get(0);
			}
		}
		catch(Exception e)
		{
			
		}
		return temp;
	}
	public List<TSyncBoss> querySyncBossListBeforeTime(Date dataTime)
	{
		String sql="from TSyncBoss where 1=1 and  dataTime<= ?";
		Object[] params=new Object[1];
		params[0]=dataTime;
		try
		{
			List list = this.getListForAll(sql, params);
			if (list!=null && list.size()>0)
			{
				return (List<TSyncBoss>)list;
			}
		}
		catch(Exception e)
		{
			return null;
		}
		return null;
	}
	public void saveSyncBoss(TSyncBoss syncBoss)
	{
		this.save(syncBoss);
	}
}
