package com.dvnchina.advertDelivery.dao;

import java.util.List;
import java.util.Map;

import com.dvnchina.advertDelivery.model.AssetInfo;

/**
 * 资源包实体表数据库操作
 * */
public interface AssetPackageDao {

	/**
	 * 从数据库实体表中查询资源包及资源包与资源关系信息,保存在Map中返回
	 * 
	 * @return 
	 *         封装的Map对象，key=>Asset对象hashcode，value=>Map(key=>[ASSETPACKAGE|ASSETMAP]
	 *         ,value=[Asset对象|Map(key=>PackageAsset对象的hashcode
	 *         ,value=>PackageAsset对象]>
	 */
	public Map<Integer, Map<String, Object>> getAssetPackages(); 
	
	
	public Map<Integer, Map<String, Object>> getAssetPackagesByPId(
			List<String> packageIds);

	/**
	 * 批量插入资源包记录到实体表
	 * 
	 * @param assets
	 *            待插入资源包的集合
	 * */
	public void insertAssetPackage(List<AssetInfo> assetPackagetList);

	/**
	 * 批量更新实体表资源包记录
	 * 
	 * @param assets
	 *            待更新资源包的集合
	 * */
	public void updateAssetPackage(List<AssetInfo> assetPackagetList); 

	/**
	 * 批量删除实体表资源包记录
	 * 
	 * @param assets
	 *            待删除资源包id的集合
	 * */
	public void deleteAssetPackage(List<Integer> assetPackagets);

	/**
	 * 批量插入资源包与资源关系记录到实体表
	 * 
	 * @param assets
	 *            待插入资源包与资源关系的集合
	 * */
	public void insertPackageAsset(final Object[] packageAssets); 

	/**
	 * 批量删除实体表资源包与资源关系记录
	 * 
	 * @param assets
	 *            待删除资源包与资源关系id的集合
	 * */
	public void deletePackageAsset(final List<Integer> ids);
}
