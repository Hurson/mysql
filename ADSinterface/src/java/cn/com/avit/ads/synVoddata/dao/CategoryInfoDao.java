package cn.com.avit.ads.synVoddata.dao;

import java.util.List;

import cn.com.avit.ads.synVoddata.bean.CategoryInfo;


public interface CategoryInfoDao {
	public void deleteCategoryList(String netWorkID);
	public void insertCategoryInfos(List<CategoryInfo> categoryInfos);
	public void insertCategoryInfo(CategoryInfo categoryInfo);
	public void execCate();
}
