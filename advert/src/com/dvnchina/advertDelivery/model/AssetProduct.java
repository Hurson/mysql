package com.dvnchina.advertDelivery.model;

/**
 *封装产品和资源关系的相关信息
 */
public class AssetProduct {

	private Integer id;
	
	/**
	 * 资源记录的主键id
	 * */
	private Integer asset_id;
	
	/**
	 * 资源id
	 * */
	private String assetId;
	
	/**
	 * 产品记录的主键id
	 * */
	private Integer product_id;
	
	/**
	 * 产品id
	 * */
	private String productId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Integer productId) {
		product_id = productId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	@Override
	public int hashCode() {
		 String code = this.productId + this.assetId;
		 return code.hashCode();
	}

	/**
	 * 复写equals方法
	 * 
	 * @param assetProduct
	 *            比较的对象
	 * @return 
	 *         比较的对象是AssetProduct的实例，并且属性id,asset_id,product_id,assetOrder都与当前对象相等时
	 *         ，返回true，否则返回false
	 * */
	@Override
	public boolean equals(Object assetProduct) {
		if (assetProduct instanceof AssetProduct) {
			AssetProduct ap = (AssetProduct) assetProduct;
			if (this.id != null && this.id.equals(ap.getId())
					&& this.asset_id != null
					&& this.asset_id.equals(ap.getAsset_id())
					&& this.product_id != null
					&& this.product_id.equals(ap.getProduct_id())) {
				return true;
			}
		}
		return false;
	}
}
