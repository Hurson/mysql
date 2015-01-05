package cn.com.avit.ads.synVoddata.service;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.com.avit.ads.synVoddata.bean.UserIndustryCategoryBean;
import cn.com.avit.ads.synVoddata.dao.SynUserIndustryCategoryDao;
@Service("SynUserIndustryCategoryService")
public class SynUserIndustryCategoryServiceImpl implements SynUserIndustryCategoryService{
	
	@Inject
	SynUserIndustryCategoryDao synUserIndustryCategoryDao;
	private Logger logger = Logger.getLogger(SynUserIndustryCategoryServiceImpl.class);
	public void deleteUserIndustryCategoryList(String netWorkID) {
		synUserIndustryCategoryDao.deleteUserIndustryCategoryList(netWorkID);
		
	}
	public void insertUserIndustryCategorys(
			List<UserIndustryCategoryBean> userIndustryCategoryBeans) {
		synUserIndustryCategoryDao.insertUserIndustryCategorys(userIndustryCategoryBeans);
		
	}
	public void insertUserIndustryCategory(
			UserIndustryCategoryBean userIndustryCategoryBean) {
		synUserIndustryCategoryDao.insertUserIndustryCategory(userIndustryCategoryBean);
		
	}
	public void execProc() {
		synUserIndustryCategoryDao.execProc();
		
	}


}
