package com.dvnchina.advertDelivery.dao;

import java.util.List;
import java.util.Map;

import com.dvnchina.advertDelivery.model.AssetInfo;

/**
 * 资源实体表数据库操作
 * */
public interface AssetDao{


	/**
	 * 从数据库实体表中查询资源信息,保存在Map中返回
	 * 
	 * @return 
	 * 			封装的Map对象，key=>Asset对象的hashcode，value=>Asset对象
	 */
	public Map<Integer, AssetInfo> getAssets();

	/**
	 * 从数据库实体表中查询资源信息,保存在Map中返回
	 * 
	 * @return 
	 * 			封装的Map对象，key=>Asset对象的hashcode，value=>Asset对象
	 */
	public Map<Integer, AssetInfo> getAssetsByAId(List<String> assetIds);
	
	/**
	 * 批量插入资源记录到实体表
	 * 
	 * @param assets
	 *            	待插入资源的集合
	 * */
	public void insertAsset(final List<AssetInfo> assets); 

	/**
	 * 批量删除实体表资源记录
	 * 
	 * @param assets
	 *            	待删除资源id的集合
	 * */
	public void deleteAsset(final List<Integer> ids);

	/**
	 * 批量更新实体表资源记录
	 * 
	 * @param assets
	 *           	 待更新资源的集合
	 * */
	public void updateAsset(final List<AssetInfo> assets);
	
	/**
	 * 根据资源编号，资源名称分页查询资源信息
	 * */
	public List<AssetInfo> getAssets(int begin,int end,String id,String name);
	
	/**
	 * 根据资源编号，资源名称查询资源数量
	 * */
	public int getAssetCount(String id,String name);
}
