package cn.com.avit.ads.synVoddata.service;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.com.avit.ads.synVoddata.bean.LocationCodeBean;
import cn.com.avit.ads.synVoddata.dao.SynLocationCodeDao;
@Service("SynLocationCodeService")
public class SynLocationCodeServiceImpl implements SynLocationCodeService{
	
	@Inject
	SynLocationCodeDao synLocationCodeDao;
	private Logger logger = Logger.getLogger(SynLocationCodeServiceImpl.class);
	public void deleteLocationCodeList(String netWorkID) {
		synLocationCodeDao.deleteLocationCodeList(netWorkID);
		
	}
	public void insertLocationCodes(List<LocationCodeBean> locationCodeBeans) {
		synLocationCodeDao.insertLocationCodes(locationCodeBeans);
		
	}
	public void insertLocationCode(LocationCodeBean locationCodeBean) {
		synLocationCodeDao.insertLocationCode(locationCodeBean);
		
	}
	public void execProc() {
		synLocationCodeDao.execProc();
		
	}


	

}
