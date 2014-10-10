package com.dvnchina.advertDelivery.service.impl;

import java.util.List;

import com.dvnchina.advertDelivery.dao.AssetDao;
import com.dvnchina.advertDelivery.dao.ProductDao;
import com.dvnchina.advertDelivery.model.AssetInfo;
import com.dvnchina.advertDelivery.model.ProductInfo;
import com.dvnchina.advertDelivery.service.VodInfoService;

public class VodInfoServiceImpl implements VodInfoService {

	private ProductDao productDao;
	private AssetDao assetDao;

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	public void setAssetDao(AssetDao assetDao) {
		this.assetDao = assetDao;
	}

	@Override
	public int getAssetCount(String id, String name) {
		return assetDao.getAssetCount(id, name);
	}

	@Override
	public List<AssetInfo> getAssets(int begin, int end, String id, String name) {
		return assetDao.getAssets(begin, end, id, name);
	}

	@Override
	public int getProductCount(String id, String name) {
		return productDao.getProductCount(id, name);
	}

	@Override
	public List<ProductInfo> getProducts(int begin, int end, String id,
			String name) {
		return productDao.getProducts(begin, end, id, name);
	}

	
}
