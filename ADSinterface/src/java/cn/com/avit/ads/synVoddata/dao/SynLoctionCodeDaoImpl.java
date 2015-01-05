package cn.com.avit.ads.synVoddata.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.avit.ads.synVoddata.bean.LocationCodeBean;

import com.avit.common.page.dao.impl.CommonDaoImpl;
@Repository
public class SynLoctionCodeDaoImpl extends CommonDaoImpl implements SynLocationCodeDao{




	public void execProc() {
		String hql = "Call synLocationCode()";
	   this.excuteBySql(hql, null);
		
	}

	public void deleteLocationCodeList(String netWorkID) {
		String hql = "delete LocationCodeBean where areacode = "+netWorkID;
		this.delete(hql);
		
	}

	public void insertLocationCodes(List<LocationCodeBean> locationCodeBeans) {
		this.saveAll(locationCodeBeans);
		
	}

	public void insertLocationCode(LocationCodeBean locationCodeBean) {
		this.save(locationCodeBean);
		
	}
	
	


}
