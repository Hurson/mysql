package com.dvnchina.advertDelivery.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dvnchina.advertDelivery.constant.VodConstant;
import com.dvnchina.advertDelivery.dao.AssetDao;
import com.dvnchina.advertDelivery.dao.AssetPackageDao;
import com.dvnchina.advertDelivery.dao.ProductDao;
import com.dvnchina.advertDelivery.model.AssetInfo;
import com.dvnchina.advertDelivery.model.AssetProduct;
import com.dvnchina.advertDelivery.model.PackageAsset;
import com.dvnchina.advertDelivery.model.ProductInfo;
import com.dvnchina.advertDelivery.service.VodIncrSyncService;

public class VodIncrSyncServiceImpl implements VodIncrSyncService  {

	private final Log logger = LogFactory.getLog(VodIncrSyncServiceImpl.class);
	private AssetDao assetDao;
	private AssetPackageDao assetPackageDao;
	private ProductDao productDao;

	public void setAssetDao(AssetDao assetDao) {
		this.assetDao = assetDao;
	}

	public void setAssetPackageDao(
			AssetPackageDao assetPackageDao) {
		this.assetPackageDao = assetPackageDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized boolean updateDataIncrement(HashMap<String, Object> infos) {
		boolean flag = true;
		logger.info("###增量同步VOD数据开始");
		Map<Integer, AssetInfo> assetFromXmlMap = (Map<Integer, AssetInfo>) infos
				.get(VodConstant.ASSET);
		Map<Integer, Map<String, Object>> aPackageFromXmlMap = (Map<Integer, Map<String, Object>>) infos
				.get(VodConstant.ASSETPACKAGE);
		Map<Integer, Map<String, Object>> productFromXmlMap = (Map<Integer, Map<String, Object>>) infos
				.get(VodConstant.PRODUCT);
		if(assetFromXmlMap!=null&&assetFromXmlMap.size()>0){
			boolean ca = this.compareAssets(assetFromXmlMap);
			if(ca==false){
				flag=false;
			}
		}
		if(aPackageFromXmlMap!=null&&aPackageFromXmlMap.size()>0){
			boolean cap = this.compareAssetPackages(aPackageFromXmlMap);
			if(cap==false){
				flag = false;
			}
		}
		if(productFromXmlMap!=null&&productFromXmlMap.size()>0){
			boolean cp = this.compareProducts(productFromXmlMap);
			if(cp==false){
				flag = false;
			}
		}	
		logger.info("###增量同步VOD数据结束");
		assetFromXmlMap = null;
		aPackageFromXmlMap = null;
		productFromXmlMap = null;
		infos = null;
		return flag;
	}
	
	/**
	 * 从数据库获取资源信息
	 * */
	private Map<Integer, AssetInfo> getAssetFromDB(Map<Integer, AssetInfo> assetFromXmlMap){
		List<String> assetIds = new ArrayList<String>();
		Object[] assets = assetFromXmlMap.values().toArray();
		for(int i = 0;i<assets.length;i++){
			AssetInfo asset = (AssetInfo)assets[i];
			assetIds.add(asset.getAssetId());
		}
		Map<Integer, AssetInfo> assetFromDBMap = assetDao.getAssetsByAId(assetIds);
		assetFromXmlMap = null;
		assetIds = null;
		assets = null;
		return assetFromDBMap;
		
	}
	private boolean compareAssets(Map<Integer, AssetInfo> assetFromXmlMap) {
		try {
			Date d0 = new Date();
			/** 资源实体表数据 */
			Map<Integer, AssetInfo> assetFromDBMap = this.getAssetFromDB(assetFromXmlMap);

			Date d1 = new Date();
			logger.info("查询资源实体表，耗时：" + (d1.getTime() - d0.getTime()) + "毫秒");
			logger.info("同步资源实体表开始，实体表记录数：" + assetFromDBMap.size()
					+ ";XML文件记录数：" + assetFromXmlMap.size() + "。");

			/**
			 * 将实体表资源数据映射的键赋值给keySet
			 * */
			Set<Integer> assetFromDBKeys = assetFromDBMap.keySet();
			Set<Integer> keySet = new HashSet<Integer>();
			Iterator<Integer> iterator = assetFromDBKeys.iterator();
			while (iterator.hasNext()) {
				keySet.add(iterator.next());
			}

			/** 在实体表和XML数据Map中排除键值相同的记录 */
			Iterator<Integer> iter = keySet.iterator();
			while (iter.hasNext()) {
				Integer key = iter.next();
				if (assetFromXmlMap.containsKey(key)) {
					assetFromXmlMap.remove(key);
					assetFromDBMap.remove(key);
				}
			}
			assetUpdate(assetFromDBMap, assetFromXmlMap);
			
			assetFromXmlMap = null;
			assetFromDBMap = null;
			assetFromDBKeys = null;
			keySet = null;
			Date d2 = new Date();
			logger.info("同步资源实体表完成,耗时：" + (d2.getTime() - d1.getTime()) + "毫秒");
			return true;
		} catch (Exception e) {
			logger.info("资源实体表与XML数据比较更新实体表数据时出错！", e);
			return false;
		}
	}
	
	private void assetUpdate(Map<Integer, AssetInfo> fromDBMap,
			Map<Integer, AssetInfo> fromXmlMap) {

		List<AssetInfo> assetList = new ArrayList<AssetInfo>();

		/** 获得实体表资源数据Map的值的数组 */
		Object[] assetsFromDB = fromDBMap.values().toArray();

		/** 获得XML资源数据Map的值的数组 */
		Object[] assetsFromXml = fromXmlMap.values().toArray();

		/** 获得实体表需要更新的记录添加到集合中 */
		for (int i = 0; i < assetsFromDB.length; i++) {
			AssetInfo assetFromDB = (AssetInfo) assetsFromDB[i];
			if (assetFromDB != null) {
				for (int j = 0; j < assetsFromXml.length; j++) {
					AssetInfo assetFromXml = (AssetInfo) assetsFromXml[j];
					if (assetFromXml != null
							&& assetFromDB.getAssetId().equals(
									assetFromXml.getAssetId())) {
						assetFromXml.setId(assetFromDB.getId());
						assetList.add(assetFromXml);
						assetsFromXml[j] = null;
					}
				}
			}
		}

		if (assetList.size() > 0) {
			/** 更新资源实体表记录 */
			assetDao.updateAsset(assetList);
			logger.info("更新" + assetList.size() + "条资源实体表记录");

		}

		assetInsert(assetsFromXml);
		
		fromDBMap = null;
		fromXmlMap = null;
		assetList = null;
		assetsFromDB = null;
		assetsFromXml = null;
	}

	/**
	 * 插入资源
	 * 
	 * @param assets
	 *            资源数组
	 * */
	private void assetInsert(Object[] assets) {
		List<AssetInfo> assetList = new ArrayList<AssetInfo>();
		for (int i = 0; i < assets.length; i++) {
			if (assets[i] != null) {
				assetList.add((AssetInfo) assets[i]);
			}
		}

		if (assetList.size() > 0) {
			assetDao.insertAsset(assetList);
			logger.info("插入" + assetList.size() + "条资源实体表记录");
			
		}
		assets = null;
		assetList = null;
	}
	@SuppressWarnings("unchecked")
	private Map<Integer, Map<String, Object>> getAssetPackageFromDB(Map<Integer, Map<String, Object>> aPackageFromXmlMap){
		List<String> packageIds = new ArrayList<String>();
		Object[] packages = aPackageFromXmlMap.values().toArray();
		for(int i = 0;i<packages.length;i++){
			Map<String, Object> pack = (Map<String, Object>)packages[i];
			Object[] packs = pack.values().toArray();
			for(Object p : packs){
				if(p instanceof AssetInfo){
					packageIds.add(((AssetInfo)p).getAssetId());
				}
			}
			
		}
		Map<Integer, Map<String, Object>> aPackageFromDBMap = assetPackageDao.getAssetPackagesByPId(packageIds);
		aPackageFromXmlMap = null;
		packages = null;
		packageIds = null;
		return aPackageFromDBMap;
		
	}
	/**
	 * 比较资源包XML文件与实体表，同步数据
	 * 
	 * @param assetFromXmlMap
	 *            XML文件资源数据映射
	 * */
	private boolean compareAssetPackages(
			Map<Integer, Map<String, Object>> aPackageFromXmlMap) {
		Map<Integer, Map<String, Object>> aPackageFromDBMap;
		try {
			Date d0 = new Date();
			/** 资源包实体表数据 */
			aPackageFromDBMap = this.getAssetPackageFromDB(aPackageFromXmlMap);
			Date d1 = new Date();
			logger.info("查询资源包及关系实体表，耗时：" + (d1.getTime() - d0.getTime())
					+ "毫秒");

			logger.info("同步资源包实体表开始，实体表记录数：" + aPackageFromDBMap.size()
					+ ";XML文件记录数：" + aPackageFromXmlMap.size() + "。");

			/**
			 * 将实体表资源数据Map的键赋值给key
			 * */
			Set<Integer> aPackageFromDBKeys = aPackageFromDBMap.keySet();
			Set<Integer> keySet = new HashSet<Integer>();
			Iterator<Integer> iterator = aPackageFromDBKeys.iterator();
			while (iterator.hasNext()) {
				keySet.add(iterator.next());
			}

			/** 在XML和实体表数据Map中排除键值相同的记录 */
			Iterator<Integer> iter = keySet.iterator();
			while (iter.hasNext()) {
				Integer key = iter.next();
				if (aPackageFromXmlMap.containsKey(key)) {
					aPackageFromDBMap.remove(key);
					aPackageFromXmlMap.remove(key);
				}
			}
			assetPackageUpdate(aPackageFromDBMap, aPackageFromXmlMap);
			Date d2 = new Date();
			logger.info("同步资源包实体表完成,耗时：" + (d2.getTime() - d1.getTime())
							+ "毫秒");
			aPackageFromXmlMap = null;
			aPackageFromDBMap = null;
			aPackageFromDBKeys = null;
			keySet = null;
			return true;
		} catch (Exception e) {
			aPackageFromXmlMap = null;
			aPackageFromDBMap = null;
			logger.info("资源包实体表与XML数据比较更新实体表数据时出错！", e);
			return false;
		}
	}

	/**
	 * 更新资源包
	 * 
	 * @param fromDBMap
	 *            资源包实体表数据映射
	 * @param fromXmlMap
	 *            资源包XML数据映射
	 * */
	@SuppressWarnings("unchecked")
	private void assetPackageUpdate(Map<Integer, Map<String, Object>> fromDBMap,
			Map<Integer, Map<String, Object>> fromXmlMap) {

		List<AssetInfo> assetPackageList = new ArrayList<AssetInfo>();

		/** 获得实体表资源包数据Map的值的数组 */
		Object[] aPackagesFromDB = fromDBMap.values().toArray();

		/** 获得XML资源包数据Map的值的数组 */
		Object[] aPackagesFromXml = fromXmlMap.values().toArray();

		/** 获得实体表需要更新的记录添加到集合中 */
		for (int i = 0; i < aPackagesFromDB.length; i++) {
			if (aPackagesFromDB[i] != null) {
				AssetInfo aPackageFromDB = (AssetInfo) ((Map<Integer, Object>) aPackagesFromDB[i])
						.get(VodConstant.ASSETPACKAGE);
				Map<Integer, PackageAsset> paFromDB = (Map<Integer, PackageAsset>) ((Map<Integer, Object>) aPackagesFromDB[i])
						.get(VodConstant.ASSETMAP);
				aPackageFromDB.setAssetMap(paFromDB);
				for (int j = 0; j < aPackagesFromXml.length; j++) {
					if (aPackagesFromXml[j] != null) {
						Map<String, Object> aPackageFromXml = (Map<String, Object>) aPackagesFromXml[j];
						AssetInfo aPackage = (AssetInfo) aPackageFromXml
								.get(VodConstant.ASSETPACKAGE);
						if (aPackageFromDB.getAssetId().equals(
								aPackage.getAssetId())) {
							aPackage.setId(aPackageFromDB.getId());
							assetPackageList.add(aPackage);

							/** 比较实体表和XML的资源包与资源关系 */
							comparePackageAsset(
									aPackageFromDB.getAssetMap(),
									(Map<Integer, PackageAsset>) aPackageFromXml
											.get(VodConstant.ASSETMAP));
							aPackagesFromXml[j] = null;
						}
						aPackageFromXml = null;
					}

				}
				aPackageFromDB = null;
				paFromDB = null;
			}
		}

		if (assetPackageList.size() > 0) {
			/** 更新资源包实体表记录 */
			assetPackageDao.updateAssetPackage(assetPackageList);
			logger.info("更新" + assetPackageList.size() + "条资源包实体表记录。");
		}

		assetPackageInsert(aPackagesFromXml);
		fromDBMap = null;
		fromXmlMap = null;
		assetPackageList = null;
		aPackagesFromXml = null;
		aPackagesFromDB = null;
	}

	/**
	 * 插入资源包
	 * 
	 * @param assetPackages
	 *            资源包数组
	 * */
	@SuppressWarnings("unchecked")
	private void assetPackageInsert(Object[] assetPackages) {
		List<AssetInfo> assetPackageList = new ArrayList<AssetInfo>();
		Map<Integer, PackageAsset> packageAssetMap = new HashMap<Integer, PackageAsset>();
		for (int i = 0; i < assetPackages.length; i++) {
			if (assetPackages[i] != null) {
				AssetInfo assetPackage = (AssetInfo) ((Map<Integer, Object>) assetPackages[i])
						.get(VodConstant.ASSETPACKAGE);
				Map<Integer, PackageAsset> paMap = (Map<Integer, PackageAsset>) ((Map<Integer, Object>) assetPackages[i])
						.get(VodConstant.ASSETMAP);
				assetPackageList.add(assetPackage);
				packageAssetMap.putAll(paMap);
			}
		}

		if (assetPackageList.size() > 0) {
			assetPackageDao.insertAssetPackage(assetPackageList);
			logger.info("插入" + assetPackageList.size() + "条资源包实体表记录");
		}
		this.packageAssetInsert(packageAssetMap);
		assetPackages = null;
		packageAssetMap = null;
		assetPackageList = null;
	}

	
	/**
	 * 比较资源包和资源关系的实体表和XML数据，同步备份表数据
	 * 
	 * @param packageAssetFromDB
	 *            资源包和资源关系的实体表数据映射
	 * @param packageAssetFromXml
	 *            资源包和资源关系的XML数据映射
	 * */
	private void comparePackageAsset(
			Map<Integer, PackageAsset> packageAssetFromDB,
			Map<Integer, PackageAsset> packageAssetFromXml) {

		/** 将实体表数据Map键值赋值给keys */
		Set<Integer> packageAssetFromDBKeys = packageAssetFromDB.keySet();
		Set<Integer> keys = new HashSet<Integer>();
		Iterator<Integer> apKeysIter = packageAssetFromDBKeys.iterator();
		while (apKeysIter.hasNext()) {
			keys.add(apKeysIter.next());
		}
		Iterator<Integer> keysIter = keys.iterator();

		while (keysIter.hasNext()) {
			Integer key = keysIter.next();
			if (packageAssetFromXml.containsKey(key)) {
				packageAssetFromXml.remove(key);
				packageAssetFromDB.remove(key);
			}
		}
		packageAssetInsert(packageAssetFromXml);
		packageAssetDelete(packageAssetFromDB);
		
		packageAssetFromXml = null;
		packageAssetFromDB = null;
		packageAssetFromDBKeys = null;
		keys = null;
	}

	/**
	 * 插入资源和资源包关系
	 * 
	 * @param packageAssetMap
	 *            资源包和资源关系的数据映射
	 * */
	private void packageAssetInsert(Map<Integer, PackageAsset> packageAssetMap) {
		Object[] packageAssets = packageAssetMap.values().toArray();

		if (packageAssets.length > 0) {
			assetPackageDao.insertPackageAsset(packageAssets);
			logger.info("插入" + packageAssets.length + "条资源包与资源关系实体表记录");

		}
		packageAssetMap = null;
		packageAssets = null;
	}

	/**
	 * 删除资源和资源包关系
	 * 
	 * @param packageAssetMap
	 *            资源包和资源关系的数据映射
	 * */
	private void packageAssetDelete(Map<Integer, PackageAsset> packageAssetMap) {
		Object[] packageAssets = packageAssetMap.values().toArray();
		List<Integer> ids = new ArrayList<Integer>();
		for (int i = 0; i < packageAssets.length; i++) {
			ids.add(((PackageAsset) packageAssets[i]).getId());
		}

		if (ids.size() > 0) {
			assetPackageDao.deletePackageAsset(ids);
			logger.info("删除" + ids.size() + "条资源包与资源关系实体表记录");
		}
		packageAssetMap = null;
		packageAssets = null;
		ids = null;
	}

	@SuppressWarnings("unchecked")
	private Map<Integer, Map<String, Object>> getProductFromDB(Map<Integer, Map<String, Object>> productFromXmlMap){
		List<String> productIds = new ArrayList<String>();
		Object[] products = productFromXmlMap.values().toArray();
		for(int i = 0;i<products.length;i++){
			Map<String, Object> product = (Map<String, Object>)products[i];
			Object[] ps = product.values().toArray();
			for(Object p : ps){
				if(p instanceof ProductInfo){
					productIds.add(((ProductInfo)p).getProductId());
				}
			}
			
		}
		Map<Integer, Map<String, Object>> productFromDBMap = productDao.getProductsByPId(productIds);
		productFromXmlMap = null;
		productIds = null;
		products = null;
		return productFromDBMap;
		
	}
	
	/**
	 * 比较产品XML与实体表，同步数据
	 * 
	 * @param productFromXmlMap
	 *            XML文件产品数据映射
	 * */
	private boolean compareProducts(
			Map<Integer, Map<String, Object>> productFromXmlMap) {
		Map<Integer, Map<String, Object>> productFromDBMap;
		try {
			Date d0 = new Date();
			/** 产品实体表数据 */
			productFromDBMap = this.getProductFromDB(productFromXmlMap);
			Date d1 = new Date();
			logger
					.info("查询产品及关系实体表，耗时：" + (d1.getTime() - d0.getTime())
							+ "毫秒");
			logger.info("同步产品实体表开始，实体表记录数：" + productFromDBMap.size()
					+ ";XML文件记录数：" + productFromXmlMap.size() + "。");

			/**
			 * 将实体表资源数据Map的键赋值给key
			 * */
			Set<Integer> productFromDBKeys = productFromDBMap.keySet();
			Set<Integer> keySet = new HashSet<Integer>();
			Iterator<Integer> iterator = productFromDBKeys.iterator();
			while (iterator.hasNext()) {
				keySet.add(iterator.next());
			}

			/** 在XML和实体表数据Map中排除键值相同的记录 */
			Iterator<Integer> iter = keySet.iterator();
			while (iter.hasNext()) {
				Integer key = iter.next();
				if (productFromXmlMap.containsKey(key)) {
					productFromDBMap.remove(key);
					productFromXmlMap.remove(key);
				}
			}

			productUpdate(productFromDBMap, productFromXmlMap);
			Date d2 = new Date();
			logger.info("同步产品实体表完成,耗时：" + (d2.getTime() - d1.getTime()) + "毫秒");
			
			productFromXmlMap = null;
			productFromDBMap = null;
			productFromDBKeys = null;
			keySet = null;
			return true;
		} catch (Exception e) {
			logger.info("产品实体表与XML数据比较更新实体表数据时出错！", e);
			productFromXmlMap = null;
			productFromDBMap = null;
			return false;
		}
	}

	/**
	 * 更新产品
	 * 
	 * @param fromDBMap
	 *            产品实体表数据映射
	 * @param fromXmlMap
	 *            产品XML数据映射
	 * */
	@SuppressWarnings("unchecked")
	private void productUpdate(Map<Integer, Map<String, Object>> fromDBMap,
			Map<Integer, Map<String, Object>> fromXmlMap) {

		List<ProductInfo> productList = new ArrayList<ProductInfo>();

		/** 获得实体表产品数据Map的值的数组 */
		Object[] productsFromDB = fromDBMap.values().toArray();

		/** 获得XML产品数据Map的值的数组 */
		Object[] productsFromXml = fromXmlMap.values().toArray();

		/** 获得实体表需要更新的记录添加到集合中 */
		for (int i = 0; i < productsFromDB.length; i++) {
			if (productsFromDB[i] != null) {
				ProductInfo productFromDB = (ProductInfo) ((Map<String, Object>) productsFromDB[i])
						.get(VodConstant.PRODUCT);
				Map<Integer, AssetProduct> paFromDB = (Map<Integer, AssetProduct>) ((Map<String, Object>) productsFromDB[i])
						.get(VodConstant.ASSETMAP);
				productFromDB.setAssetMap(paFromDB);
				for (int j = 0; j < productsFromXml.length; j++) {
					if (productsFromXml[j] != null) {
						Map<String, Object> productFromXml = (Map<String, Object>) productsFromXml[j];
						ProductInfo product = (ProductInfo) productFromXml
								.get(VodConstant.PRODUCT);
						if (productFromDB.getProductId().equals(
								product.getProductId())) {
							product.setId(productFromDB.getId());
							productList.add(product);
							/** 比较产品和资源关系记录，同步实体表数据 */
							compareAssetProduct(productFromDB.getAssetMap(),
									(Map<Integer, AssetProduct>) productFromXml
											.get(VodConstant.ASSETMAP));
							productsFromXml[j] = null;
						}
						productFromXml = null;
					}
					
				}
				productFromDB = null;
				paFromDB = null;
			}
		}

		if (productList.size() > 0) {
			/** 更新产品实体表记录 */
			productDao.updateProduct(productList);
			logger.info("更新" + productList.size() + "条产品实体表记录");
		}
		productInsert(productsFromXml);
		fromDBMap = null;
		fromXmlMap = null;
		productsFromXml = null;
		productsFromDB = null;
		productList = null;
	}

	/**
	 * 插入产品
	 * 
	 * @param products
	 *            产品数组
	 * */
	@SuppressWarnings("unchecked")
	private void productInsert(Object[] products) {
		List<ProductInfo> productList = new ArrayList<ProductInfo>();
		Map<Integer, AssetProduct> assetProductMap = new HashMap<Integer, AssetProduct>();
		for (int i = 0; i < products.length; i++) {
			if (products[i] != null) {
				ProductInfo product = (ProductInfo) ((Map<String, Object>) products[i])
						.get(VodConstant.PRODUCT);
				Map<Integer, AssetProduct> paMap = (Map<Integer, AssetProduct>) ((Map<String, Object>) products[i])
						.get(VodConstant.ASSETMAP);
				productList.add(product);
				assetProductMap.putAll(paMap);
			}
		}

		if (productList.size() > 0) {
			productDao.insertProduct(productList);
			logger.info("插入" + productList.size() + "条产品实体表记录");
		}
		this.assetProductInsert(assetProductMap);
		products = null;
		productList = null;
		assetProductMap = null;
	}

	

	/**
	 * 比较产品和资源关系的实体表和XML数据，同步备份表数据
	 * 
	 * @param aProductFromDB
	 *            产品和资源关系的实体表数据映射
	 * @param aProductFromXml
	 *            产品和资源关系的XML数据映射
	 * */
	private void compareAssetProduct(Map<Integer, AssetProduct> aProductFromDB,
			Map<Integer, AssetProduct> aProductFromXml) {
		Set<Integer> aProductFromDBKeys = aProductFromDB.keySet();
		Set<Integer> keys = new HashSet<Integer>();
		Iterator<Integer> apKeysIter = aProductFromDBKeys.iterator();
		while (apKeysIter.hasNext()) {
			keys.add(apKeysIter.next());
		}

		Iterator<Integer> keysIter = keys.iterator();
		while (keysIter.hasNext()) {
			Integer key = keysIter.next();
			if (aProductFromXml.containsKey(key)) {
				aProductFromXml.remove(key);
				aProductFromDB.remove(key);
			}
		}
		assetProductInsert(aProductFromXml);
		assetProductDelete(aProductFromDB);
		
		aProductFromDB = null;
		aProductFromXml = null;
		aProductFromDBKeys = null;
		keys = null;
	}

	/**
	 * 插入产品和资源关系
	 * 
	 * @param assetProductMap
	 *            产品和资源包关系映射
	 * */
	private void assetProductInsert(Map<Integer, AssetProduct> assetProductMap) {
		Object[] assetProducts = assetProductMap.values().toArray();

		if (assetProducts.length > 0) {
			productDao.insertAssetProduct(assetProducts);
			logger.info("插入" + assetProducts.length + "条产品与资源关系实体表记录");

		}
		assetProductMap = null;
		assetProducts = null;
	}

	/**
	 * 删除产品和资源关系
	 * 
	 * @param assetProductMap
	 *            产品和资源包关系映射
	 * */
	private void assetProductDelete(Map<Integer, AssetProduct> assetProductMap) {
		Object[] assetProducts = assetProductMap.values().toArray();
		List<Integer> ids = new ArrayList<Integer>();
		for (int i = 0; i < assetProducts.length; i++) {
			ids.add(((AssetProduct) assetProducts[i]).getId());
		}

		if (ids.size() > 0) {
			productDao.deleteAssetProduct(ids);
			logger.info("删除" + ids.size() + "条产品与资源关系实体表记录");
		}
		assetProductMap = null;
		assetProducts = null;
		ids = null;
	}
}
