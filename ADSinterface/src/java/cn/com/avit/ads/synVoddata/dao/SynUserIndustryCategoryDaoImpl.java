package cn.com.avit.ads.synVoddata.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.avit.ads.synVoddata.bean.UserIndustryCategoryBean;

import com.avit.common.page.dao.impl.CommonDaoImpl;
@Repository
public class SynUserIndustryCategoryDaoImpl extends CommonDaoImpl implements SynUserIndustryCategoryDao{




	public void execProc() {
		String hql = "Call synUserIndustryCategory()";
	   this.excuteBySql(hql, null);
		
	}

	public void deleteUserIndustryCategoryList(String netWorkID) {
		String hql = "delete UserIndustryCategoryBean where areacode = "+netWorkID;
		this.delete(hql);
		
	}

	public void insertUserIndustryCategorys(List<UserIndustryCategoryBean> userIndustryCategoryBeans) {
		this.saveAll(userIndustryCategoryBeans);
		
	}

	public void insertUserIndustryCategory(UserIndustryCategoryBean userIndustryCategoryBean) {
		this.save(userIndustryCategoryBean);
		
	}

}
	
	



