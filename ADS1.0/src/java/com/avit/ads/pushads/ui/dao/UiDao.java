package com.avit.ads.pushads.ui.dao;

import java.util.List;

import com.avit.ads.pushads.ui.bean.ui.UiNitAd;
import com.avit.ads.pushads.ui.bean.ui.UiOc;
import com.avit.ads.pushads.ui.bean.ui.UiVersion;

public interface UiDao {

	public List<UiOc> queryUiOcList();
	public List<UiNitAd> queryUiNitAdList();
	public List<UiVersion> queryUiVersionAdList();
	public UiVersion getUiVersionByType(String type);
	public void updateVersion(UiVersion uiVersion);
}
