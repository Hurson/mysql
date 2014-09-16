package com.avit.ads.pushads.task.service;

import java.util.List;

import com.avit.ads.pushads.task.bean.AdDefault;
import com.avit.ads.pushads.task.bean.AdvertPosition;

public interface DataInitService {

	public void initChannel();
	public void initChannelNpvr();
	public void initLookbackCategory();
	public void initAdDefalult();
	public void initAdvertPosition();
}
