package com.dvnchina.advertDelivery.service;

import java.util.List;

import com.dvnchina.advertDelivery.model.AssetInfo;
import com.dvnchina.advertDelivery.model.ProductInfo;

public interface VodInfoService {
	/**
	 * 根据资源编号，资源名称分页查询资源信息
	 * */
	public List<AssetInfo> getAssets(int begin,int end,String id,String name);
	
	/**
	 * 根据资源编号，资源名称查询资源数量
	 * */
	public int getAssetCount(String id,String name);
	
	/**
	 * 根据产品编号，产品名称分页查询产品信息
	 * */
	public List<ProductInfo> getProducts(int begin,int end,String id,String name);
	
	/**
	 * 根据产品编号，产品名称查询产品数量
	 * */
	public int getProductCount(String id,String name);
	
}
