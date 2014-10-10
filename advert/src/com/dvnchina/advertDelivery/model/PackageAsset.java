package com.dvnchina.advertDelivery.model;

/**
 * 封装资源包与资源关系的相关信息
 * */
public class PackageAsset {
	
	private Integer id;
	
	/**
	 * 资源包记录的主键id
	 * */
	private Integer package_id;
	
	/**
	 * 资源包id
	 * */
	private String packageId;
	
	/**
	 * 资源记录的主键id
	 * */
	private Integer asset_id;
	
	/**
	 * 资源id
	 * */
	private String assetId;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPackage_id() {
		return package_id;
	}

	public void setPackage_id(Integer packageId) {
		package_id = packageId;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public Integer getAsset_id() {
		return asset_id;
	}

	public void setAsset_id(Integer assetId) {
		asset_id = assetId;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	@Override
	public int hashCode() {
		String code = this.packageId + this.assetId;
		return code.hashCode();
	}

	/**
	 * 复写equals方法
	 * 
	 * @param packageAsset
	 *            比较的对象
	 * @return 
	 *         比较的对象是PackageAsset的实例，并且属性id,asset_id,package_id,assetOrder都与当前对象相等时
	 *         ，返回true，否则返回false
	 * */
	@Override
	public boolean equals(Object packageAsset) {
		if (packageAsset instanceof PackageAsset) {
			PackageAsset pa = (PackageAsset) packageAsset;
			if (this.id != null && this.id.equals(pa.getId())
					&& this.asset_id != null
					&& this.asset_id.equals(pa.getAsset_id())
					&& this.package_id != null
					&& this.package_id.equals(pa.getPackage_id())) {
				return true;
			}
		}
		return false;
	}
}
