package cn.com.avit.ads.synVoddata.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.avit.ads.synVoddata.bean.Productinfo;

import com.avit.common.page.dao.impl.CommonDaoImpl;
@Repository
public class ProductinfoDaoImpl extends CommonDaoImpl implements ProductinfoDao{

	public void deleteProductList(String netWorkID) {
		String hql = "delete Productinfo where netWorkID = "+netWorkID;
		this.delete(hql);
		
	}

	public void insertProductInfos(List<Productinfo> productInfos) {
		this.saveAll(productInfos);
		
	}

	public void insertProductInfo(Productinfo productInfo) {
		this.save(productInfo);
		
	}

	public void execProc() {
		String hql = "Call synProduct()";
	   this.excuteBySql(hql, null);
		
	}
	
	


}
