package cn.com.avit.ads.synVoddata.service;

import java.util.List;

import cn.com.avit.ads.synVoddata.bean.CategoryInfo;

public interface CategoryService {
	public void deleteCategoryList(String netWorkID);
	public void insertCategoryInfos(List<CategoryInfo> categoryInfos);
	public void insertCategoryInfo(CategoryInfo categoryInfo);
	public void execCate();
}
