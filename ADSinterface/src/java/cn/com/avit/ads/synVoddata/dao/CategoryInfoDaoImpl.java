package cn.com.avit.ads.synVoddata.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.avit.ads.synVoddata.bean.CategoryInfo;

import com.avit.common.page.dao.impl.CommonDaoImpl;
@Repository
public class CategoryInfoDaoImpl extends CommonDaoImpl implements CategoryInfoDao{

	public void deleteCategoryList(String netWorkID) {
		String hql = "delete CategoryInfo where areaCode = '"+netWorkID + "'";
		this.delete(hql);
		
	}

	public void insertCategoryInfos(List<CategoryInfo> CategoryInfos) {
		this.saveAll(CategoryInfos);
		
	}

	public void insertCategoryInfo(CategoryInfo CategoryInfo) {
		this.save(CategoryInfo);
		
	}

	public void execCate() {
		String hql = "Call synCategory()";
	   this.excuteBySql(hql, null);
		
	}
	
	


}
