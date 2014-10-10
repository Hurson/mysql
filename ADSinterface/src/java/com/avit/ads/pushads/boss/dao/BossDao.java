/*
 * 
 */
package com.avit.ads.pushads.boss.dao;

import java.util.Date;
import java.util.List;

import com.avit.ads.pushads.boss.bean.TSyncBoss;
import com.avit.ads.pushads.boss.bean.TvnScore;

public interface BossDao {
	public List<TvnScore> queryTvnScoreList(Date startDate,Date endDate);
	public TSyncBoss querySyncBossListbyTime(Date dataTime);
	public List<TSyncBoss> querySyncBossListBeforeTime(Date dataTime);
	public void saveSyncBoss(TSyncBoss syncBoss);
}
