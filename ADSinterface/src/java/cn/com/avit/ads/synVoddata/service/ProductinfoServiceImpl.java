package cn.com.avit.ads.synVoddata.service;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.com.avit.ads.synVoddata.bean.Productinfo;
import cn.com.avit.ads.synVoddata.dao.ProductinfoDao;
@Service("ProductinfoService")
public class ProductinfoServiceImpl implements ProductinfoService{
	
	@Inject
	ProductinfoDao productinfoDao;
	private Logger logger = Logger.getLogger(ProductinfoServiceImpl.class);
	public void deleteProductList(String netWorkID) {
		
		productinfoDao.deleteProductList(netWorkID);
	}

	public void insertProductInfos(List<Productinfo> productInfos) {
		productinfoDao.insertProductInfos(productInfos);
		
	}

	public void insertProductInfo(Productinfo productInfo) {
		productinfoDao.insertProductInfo(productInfo);
		
	}
	public void execProc(){
		productinfoDao.execProc();
	}

}
