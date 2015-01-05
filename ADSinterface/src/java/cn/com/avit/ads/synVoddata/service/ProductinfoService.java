package cn.com.avit.ads.synVoddata.service;

import java.util.List;

import cn.com.avit.ads.synVoddata.bean.Productinfo;

public interface ProductinfoService {
	public void deleteProductList(String netWorkID);
	public void insertProductInfos(List<Productinfo> productInfos);
	public void insertProductInfo(Productinfo productInfo);
	public void execProc();
}
