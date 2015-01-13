package cn.com.avit.ads.synVoddata.service;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.com.avit.ads.synVoddata.bean.CategoryInfo;
import cn.com.avit.ads.synVoddata.dao.CategoryInfoDao;
@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Inject
	CategoryInfoDao categoryInfoDao;
	
	private Logger logger = Logger.getLogger(CategoryServiceImpl.class);
	public void deleteCategoryList(String netWorkID) {
		
		categoryInfoDao.deleteCategoryList(netWorkID);
	}

	public void insertCategoryInfos(List<CategoryInfo> categoryInfos) {
		categoryInfoDao.insertCategoryInfos(categoryInfos);
		
	}

	public void insertCategoryInfo(CategoryInfo categoryInfo) {
		categoryInfoDao.insertCategoryInfo(categoryInfo);
		
	}
	public void execCate(){
		categoryInfoDao.execCate();
	}


}
