package com.avit.ads.pushads.task.dao;

import java.util.List;

import com.avit.ads.pushads.task.bean.AdDefault;
import com.avit.ads.pushads.task.bean.AdvertPosition;
import com.avit.ads.pushads.task.bean.TChannelinfo;
import com.avit.ads.pushads.task.bean.TChannelinfoNpvr;
import com.avit.ads.pushads.task.bean.TLoopbackCategory;

public interface DatainitDao {
	public List<TChannelinfo> queryChannel();
	public List<TChannelinfoNpvr> queryChannelNpvr();
	public List<TLoopbackCategory> queryLookbackCategory();
	public List<AdDefault> queryAdDefalult();
	public List<AdvertPosition> queryAdvertPosition();
}
