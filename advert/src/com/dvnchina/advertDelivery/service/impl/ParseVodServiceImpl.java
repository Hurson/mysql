package com.dvnchina.advertDelivery.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dvnchina.advertDelivery.constant.VodConstant;
import com.dvnchina.advertDelivery.model.AssetInfo;
import com.dvnchina.advertDelivery.model.AssetProduct;
import com.dvnchina.advertDelivery.model.PackageAsset;
import com.dvnchina.advertDelivery.model.ProductInfo;
import com.dvnchina.advertDelivery.service.ParseVodService;
import com.dvnchina.advertDelivery.utils.Transform;

/**
 * 用于解析从BSMP获取到的数据信息
 * 
 * @author chenjing
 * @version 1.0
 * @since 2008-6-2
 */

public class ParseVodServiceImpl implements ParseVodService{
	
	private static final Log LOG = LogFactory.getLog(ParseVodServiceImpl.class);

	/** 系统当前时间 */
	private Date currentTime = new Date();
	
	/**
	 * 解析读取的document对象,封装成不同的对象并保存在Map对象中返回
	 * @param document 读取的document对象
	 * @return 解析后封装的Map对象
	 */
	@SuppressWarnings("unchecked")
	private HashMap<String, Object> getDocument(Document document)
	{
		//存放返回的封装对象
		HashMap<String, Object> infos = new HashMap<String, Object>();
		//存放解析的所有asset对象,key=>asset hashcode,value=>asset对象
		HashMap<Integer, AssetInfo> assetMap = new HashMap<Integer, AssetInfo>();
		//判断读取的对象是否为空,如果不为空怎进行解析操作,否则不做任何处理
		if (document != null)
		{
			// 取得根结点
			Element element = document.getRootElement();
			LOG.debug("性能优化记录：开始遍历BSMP数据===>"+Transform.CalendartoString(Calendar.getInstance()));
			//迭代根节点下的所有子节点内容
			for (Iterator childrenElements = element.elementIterator(); childrenElements
					.hasNext();)
			{
				Element rootChildren = (Element) childrenElements.next();
				// 解析影片信息,并将影片信息放入assets对象
				if (rootChildren.getName().endsWith("assets"))
				{
					// 解析影片信息
					try
					{
						assetMap = parseAssets(rootChildren);
						infos.put(VodConstant.ASSET, assetMap);
					} catch (Exception e)
					{
						e.printStackTrace();
						LOG.error("解析的XML格式错误,解析asset信息出错!请检查asset结点格式",e);
						return null;
					}
				}
				// 解析资源包信息(map中的key为:资源包的信息,key为assetList:资源包下的资源信息列表)
				Map<Integer, Map<String, Object>> assetPackageMap = new HashMap<Integer, Map<String, Object>>();

				if (rootChildren.getName().endsWith("assetpackage"))
				{
					// 解析资源包信息,并存放在结果集中
					try
					{
						assetPackageMap = parseAssetPackage(rootChildren);
						infos.put(VodConstant.ASSETPACKAGE, assetPackageMap);
					} catch (Exception e)
					{
						e.printStackTrace();
						LOG.error("解析的XML格式错误,解析assetpackage信息出错!请检查assetpackage结点格式",e);
						return null;
					}
				}
				assetPackageMap = null;

				// 判断是否存在产品结点,如果存在解析产品信息
				if (rootChildren.getName().endsWith("products"))
				{
					// key:product hashcode,value:map<key:type[PRODUCT|ASSETLIST],value:object>
					Map<Integer, Map<String,Object>> productMaps = new HashMap<Integer,Map<String,Object>>();
					
				//	Map<Integer, BusinessInfo> businessMaps = new HashMap<Integer,BusinessInfo>();

					try
					{
						productMaps = parseProduct(rootChildren);
						infos.put(VodConstant.PRODUCT, productMaps);
						
				//		businessMaps = parseBusiness(rootChildren);		
				//		infos.put(VodConstant.BUSINESS, businessMaps);
					} catch (Exception e)
					{
						e.printStackTrace();
						LOG.error("解析的XML格式错误,解析products信息出错!请检查products结点格式",e);
						return null;
					}
					productMaps = null;
				//	businessMaps = null;
				}
				
			}

		}
		document=null;
		assetMap = null;
		LOG.debug("性能优化记录：遍历BSMP数据结束===>"+Transform.CalendartoString(Calendar.getInstance()));
		return infos;
	}



	/**
	 * 解析影片对象结点内容
	 * @param e 影片结点对象
	 * @return 解析后的对象,key=>asset hashcode,value=>Asset对象
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private HashMap<Integer, AssetInfo> parseAssets(Element e) throws Exception
	{
		HashMap<Integer, AssetInfo> assets = new HashMap<Integer, AssetInfo>();
		for (Iterator iterator = e.elementIterator(); iterator.hasNext();)
		{
			Element assetElement = (Element) iterator.next();
			// 解析影片信息
			if (assetElement.getName().endsWith("asset"))
			{
				AssetInfo asset = new AssetInfo();
				asset.setCreateTime(currentTime);
				// asset.setModifytime(currentTime);
				asset.setState(VodConstant.STATE);
				asset.setIsPackage(VodConstant.IS_NOT_PACKAGE);
				// 解析某个具体影片内的信息
				for (Iterator assetIterator = assetElement.elementIterator(); assetIterator
						.hasNext();)
				{
					Element element = (Element) assetIterator.next();
					// 解析Metadata信息
					if (element.getName().endsWith("metadatas"))
					{
						parseMetadata(element, asset);
					}// 解析预览片信息
					else if (element.getName().endsWith("preview"))
					{
						parsePreview(element, asset);
					}// 解析海报信息
					else if (element.getName().endsWith("poster"))
					{
						asset.setPosterUrl(element.attributeValue("url"));
					}
				}
				// 设置影片的ID信息
				asset.setAssetId(assetElement.attributeValue("assetId"));
				Date updateTime = DateUtils.parseDate(assetElement
						.attributeValue("updateTime"),
						new String[] { "yyyy-MM-dd HH:mm:ss" });
				if (updateTime != null)
					asset.setModifyTime(updateTime);
				
				//类型
				asset.setCategory(assetElement.attributeValue("category"));
				
				//得分
				if (StringUtils.isNotBlank(assetElement.attributeValue("score"))){
					asset.setScore(Double.valueOf(assetElement.attributeValue("score")));
				}
				// 将影片对象添加到Map中
				assets.put(asset.hashCode(), asset);
			}

		}
		e=null;
		return assets;

	}

	/**
	 * 解析所有资源包对象
	 * @param e 资源包结点对象
	 * @return 解析后的对象,key=>asset hashcode,value=>Map(key=>type[ASSETPACKAGE|ASSETLIST],value=>object)
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private Map<Integer, Map<String, Object>> parseAssetPackage(Element e)
			throws Exception
	{
		Map<Integer, Map<String, Object>> assetPackages = new HashMap<Integer, Map<String, Object>>();

		for (Iterator iterator = e.elementIterator(); iterator.hasNext();)
		{
			Element assetElement = (Element) iterator.next();
			Map<String, Object> assetPackageInfos = new HashMap<String, Object>();
			// 解析影片信息
			if (assetElement.getName().endsWith("asset"))
			{

				AssetInfo asset = new AssetInfo();
				asset.setCreateTime(currentTime);
				// asset.setModifytime(currentTime);
				asset.setState(VodConstant.STATE);
				asset.setIsPackage(VodConstant.IS_PACKAGE);
				// 设置影片的ID信息
				asset.setAssetId(assetElement.attributeValue("assetId"));

				// 解析某个具体影片内的信息
				for (Iterator assetIterator = assetElement.elementIterator(); assetIterator.hasNext();)
				{
					Element element = (Element) assetIterator.next();
					// 解析Metadata信息
					if (element.getName().endsWith("metadatas"))
					{
						parseMetadata(element, asset);
					}// 解析预览片信息
					else if (element.getName().endsWith("preview"))
					{
						parsePreview(element, asset);
					}// 解析海报信息
					else if (element.getName().endsWith("poster"))
					{
						asset.setPosterUrl(element.attributeValue("url"));
					}
					// 解析资源包下的资源id
					else if (element.getName().endsWith("assetIds"))
					{
						Map<Integer,PackageAsset> assetMap = new HashMap<Integer,PackageAsset>();
						
						for (Iterator assetIdsElements = element
								.elementIterator(); assetIdsElements.hasNext();)
						{
							PackageAsset packageAsset = new PackageAsset();
							packageAsset.setPackageId(asset.getAssetId());
							Element assetIds = (Element) assetIdsElements.next();
							if (assetIds.getName().endsWith("assetId"))
							{
								packageAsset.setAssetId(assetIds.getText());								
							}
							if(packageAsset.getAssetId()!=null){
								assetMap.put(packageAsset.hashCode(),packageAsset);
							}
						}
						assetPackageInfos.put(VodConstant.ASSETMAP, assetMap);
						assetMap = null;
					}
				}

				
				Date updateTime = DateUtils.parseDate(assetElement
						.attributeValue("updateTime"),
						new String[] { "yyyy-MM-dd HH:mm:ss" });
				if (updateTime != null)
					asset.setModifyTime(updateTime);
				
				
				//类型
				asset.setCategory(assetElement.attributeValue("category"));
				
				//得分
				if (StringUtils.isNotBlank(assetElement.attributeValue("score"))){
					asset.setScore(Double.valueOf(assetElement.attributeValue("score")));
				}
				assetPackageInfos.put(VodConstant.ASSETPACKAGE, asset);
				// 将影片对象添加到Map中
				
				assetPackages.put(asset.hashCode(), assetPackageInfos);
			}
			assetPackageInfos = null;
		}
		e=null;
		return assetPackages;

	}

	/**
	 * 解析影片的Metadata信息(影片的导演\演员信息当有多个时以“,”连接成一个字符串进行存储)
	 * @param metadata
	 * @param asset
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void parseMetadata(Element e, AssetInfo asset) throws Exception
	{
		// 存放影片的演员信息
		StringBuffer actor = new StringBuffer();
		// 存放影片的导演信息
		StringBuffer director = new StringBuffer();

		// 遍历所有的metadata信息进行解析
		for (Iterator metadataIterator = e.elementIterator(); metadataIterator
				.hasNext();)
		{
			Element metadataElement = (Element) metadataIterator.next();
			if (metadataElement.getName().endsWith("metadata"))
			{
				if (metadataElement.attributeValue("name").equalsIgnoreCase(
						"assetName"))
				{
					asset.setAssetName(metadataElement.attributeValue("value"));
				}
				if (metadataElement.attributeValue("name").equalsIgnoreCase(
						"shortName"))
				{
					asset.setShortName(metadataElement.attributeValue("value"));
				}
				if (metadataElement.attributeValue("name").equalsIgnoreCase(
						"title"))
				{
					asset.setTitle(metadataElement.attributeValue("value"));					
				}
				if (metadataElement.attributeValue("name").equalsIgnoreCase(
						"director"))
				{
					if(director.length()>0){
						director.append(",");
					}
					director.append(metadataElement.attributeValue("value"));
				}
				if (metadataElement.attributeValue("name").equalsIgnoreCase(
						"actor"))
				{
					if(actor.length()>0){
						actor.append(",");
					}
					actor.append(metadataElement.attributeValue("value"));
				}
				if (metadataElement.attributeValue("name").equalsIgnoreCase(
						"year"))
				{
					asset.setYear(metadataElement.attributeValue("value"));
				}
				if (metadataElement.attributeValue("name").equalsIgnoreCase(
						"createTime"))
				{
					asset.setAssetCreatetime(DateUtils.parseDate(
							metadataElement.attributeValue("value"),
							new String[] { "yyyy-MM-dd HH:mm:ss" }));
				}
				if (metadataElement.attributeValue("name").equalsIgnoreCase(
						"runTime"))
				{
					asset.setRuntime(metadataElement.attributeValue("value"));
				}
				if (metadataElement.attributeValue("name").equalsIgnoreCase(
						"displayRunTime"))
				{
					asset.setDisplayRuntime(metadataElement
							.attributeValue("value"));
				}
				if (metadataElement.attributeValue("name").equalsIgnoreCase(
						"rating"))
				{
					asset.setRating(metadataElement.attributeValue("value"));
				}
				if (metadataElement.attributeValue("name").equalsIgnoreCase(
						"description"))
				{
					asset.setAssetDesc(metadataElement.attributeValue("value"));
				}
				if (metadataElement.attributeValue("name").equalsIgnoreCase(
						"keywords"))
				{
					asset.setKeyword(metadataElement.attributeValue("value"));
				}
				
				//解析视频编码格式
				if (metadataElement.attributeValue("name").equalsIgnoreCase("Video_Code")) {
					asset.setVideoCode(metadataElement.attributeValue("value"));
				}

				//解析视频分辨率
				if (metadataElement.attributeValue("name").equalsIgnoreCase("Video_Resolution")) {
					asset.setVideoResolution(metadataElement.attributeValue("value"));
				}
				
				//解析score
				if (metadataElement.attributeValue("name").equalsIgnoreCase("score")) {
					String score = metadataElement.attributeValue("value");
					if (StringUtils.isNotBlank(score)) {
						asset.setScore(Double.valueOf(score));
					}
				}
			}

		}
		asset.setActor(actor.toString());
		asset.setDirector(director.toString());
		actor=null;
		director=null;
		e = null;
	}

	/**
	 * 解析预览片信息
	 * @param preview 预览片结点
	 * @param asset 影片结点
	 */
	
	private void parsePreview(Element e, AssetInfo asset)
	{
		asset.setPreviewAssetId(e.attributeValue("assetId"));
		asset.setPreviewAssetName(e.attributeValue("assetName"));
		asset.setPreviewRuntime(e.attributeValue("runTime"));
		e=null;
	}

	/**
	 * 解析product
	 * @return
	 * @throws Exception
	 */
	
	@SuppressWarnings("unchecked")
	private Map<Integer, Map<String,Object>> parseProduct(Element e) throws Exception
	{
		Map<Integer, Map<String,Object>> products = new HashMap<Integer, Map<String,Object>>();

		// 遍历产品节点
		for (Iterator productIterator = e.elementIterator(); productIterator
				.hasNext();)
		{
			Map<String, Object> productInfos = new HashMap<String, Object>();

			Element productElement = (Element) productIterator.next();
			// 遍历每个产品节点的属性,当type的值为vod时为产品信息，剩下的为业务信息
			ProductInfo product = new ProductInfo();
			String id = "";
			String name = "";
			String description = "";
			String type = "";
			String bizId = "";
			String billingModelName = "";
			String billingModelId = "";
			String billingModelType = "";
			String spId = "";
			String posterUrl = "";
		//	String url = "";
		//	String demoUrl = "";
		//	String priceDesc = "";
			String price="";
			Date updateTime = null;
		//	Date startTime = null;
		//	Date endTime = null;

			// 遍历每个产品节点的属性
			for (Iterator productAttributeIterator = productElement
					.attributeIterator(); productAttributeIterator.hasNext();)
			{
				Attribute productAttribute = (Attribute) productAttributeIterator
						.next();
				if (productAttribute.getName().equalsIgnoreCase("id"))
					id = productAttribute.getValue();
				if (productAttribute.getName().equalsIgnoreCase("name"))
					name = productAttribute.getValue();
				if (productAttribute.getName().equalsIgnoreCase("description"))
					description = productAttribute.getValue();
				if (productAttribute.getName().equalsIgnoreCase("price"))
					price = productAttribute.getValue();
				if (productAttribute.getName().equalsIgnoreCase("type"))
					type = productAttribute.getValue();
				if (productAttribute.getName().equalsIgnoreCase("bizId"))
					bizId = productAttribute.getValue();
				if (productAttribute.getName().equalsIgnoreCase(
						"billingModelName"))
				{
					billingModelName = productAttribute.getValue();
			//		priceDesc = productAttribute.getValue();
				}
				if (productAttribute.getName().equalsIgnoreCase(
						"billingModelId"))
					billingModelId = productAttribute.getValue();
				if (productAttribute.getName().equalsIgnoreCase(
						"billingModelType"))
					billingModelType = productAttribute.getValue();
				if (productAttribute.getName().equalsIgnoreCase("spId"))
					spId = productAttribute.getValue();
				if (productAttribute.getName().equalsIgnoreCase("posterUrl"))
					posterUrl = productAttribute.getValue();
			//	if (productAttribute.getName().equalsIgnoreCase("url"))
			//		url = productAttribute.getValue();
			//	if (productAttribute.getName().equalsIgnoreCase("demoUrl"))
			//		demoUrl = productAttribute.getValue();
				if (productAttribute.getName().equalsIgnoreCase("updateTime"))
				{
					updateTime = DateUtils
							.parseDate(productAttribute.getValue(),
									new String[] { "yyyy-MM-dd HH:mm:ss" });
				}
			//	if (productAttribute.getName().equalsIgnoreCase("startTime"))
			//		startTime = DateUtils.parseDate(
			//				productAttribute.getValue(),
			//				new String[] { "yyyy-MM-dd HH:mm:ss" });
			//	if (productAttribute.getName().equalsIgnoreCase("endTime"))
			//		endTime = DateUtils.parseDate(productAttribute.getValue(),
			//				new String[] { "yyyy-MM-dd HH:mm:ss" });
			}
			if (type.equalsIgnoreCase("vod"))
			{
				product.setBillingModelId(billingModelId);
				product.setBillingModelName(billingModelName);
				product.setBillingModelType(billingModelType);
				product.setBizId(bizId);
				product.setPrice(price);
				product.setCreateTime(currentTime);
				product.setModifyTime(updateTime);
				product.setState(VodConstant.STATE);
				product.setIsPackage(VodConstant.IS_NOT_PACKAGE);
				product.setPosterUrl(posterUrl);
				product.setProductDesc(description);
				product.setProductId(id);
				product.setProductName(name);
				product.setType("vod");
				product.setSpId(spId);
				
				productInfos.put(VodConstant.PRODUCT, product);	

				Map<Integer,AssetProduct> assetMap = new HashMap<Integer,AssetProduct>();
				
				// 解析product中的assetIds子节点
				for (Iterator productElements = productElement
						.elementIterator(); productElements.hasNext();)
				{
					
					// product节点
					Element element = (Element) productElements.next();
					if (element.getName().endsWith("assetIds"))
					{
						for (Iterator assetIdsElements = element.elementIterator(); assetIdsElements.hasNext();)
						{
							AssetProduct assetProduct = new AssetProduct();
							assetProduct.setProductId(product.getProductId());
							Element assetIds = (Element) assetIdsElements.next();
							if (assetIds.getName().endsWith("assetId"))
							{
								assetProduct.setAssetId(assetIds.getText());								
							}
							if(assetProduct.getAssetId()!=null){
								assetMap.put(assetProduct.hashCode(),assetProduct);
							}
						}
						productInfos.put(VodConstant.ASSETMAP, assetMap);
					}
				}	
				products.put(product.hashCode(), productInfos);
				assetMap = null;
			}
			productInfos = null;
			
		}
		e=null;
		return products;
	}
	
	/**
	 * 解析product
	 * @param product 产品结点
	 * @return
	 * @throws Exception
	
	
	@SuppressWarnings("unchecked")
	private Map<Integer, BusinessInfo> parseBusiness(Element e) throws Exception
	{
		Map<Integer, BusinessInfo> businesses = new HashMap<Integer, BusinessInfo>();

		// 遍历产品节点
		for (Iterator productIterator = e.elementIterator(); productIterator
				.hasNext();)
		{
			Element productElement = (Element) productIterator.next();
			BusinessInfo business = new BusinessInfo();
			ProductInfo product = new ProductInfo();
			String id = "";
			String name = "";
			String description = "";
			String type = "";
			String bizId = "";
			String billingModelName = "";
			String billingModelId = "";
			String billingModelType = "";
			String spId = "";
			String posterUrl = "";
			String url = "";
			String demoUrl = "";
			String priceDesc = "";
			String price="";
			Date updateTime = null;
			Date startTime = null;
			Date endTime = null;

			// 遍历每个产品节点的属性
			for (Iterator productAttributeIterator = productElement
					.attributeIterator(); productAttributeIterator.hasNext();)
			{
				Attribute productAttribute = (Attribute) productAttributeIterator
						.next();
				if (productAttribute.getName().equalsIgnoreCase("id"))
					id = productAttribute.getValue();
				if (productAttribute.getName().equalsIgnoreCase("name"))
					name = productAttribute.getValue();
				if (productAttribute.getName().equalsIgnoreCase("description"))
					description = productAttribute.getValue();
				if (productAttribute.getName().equalsIgnoreCase("price"))
					price = productAttribute.getValue();
				if (productAttribute.getName().equalsIgnoreCase("type"))
					type = productAttribute.getValue();
				if (productAttribute.getName().equalsIgnoreCase("bizId"))
					bizId = productAttribute.getValue();
				if (productAttribute.getName().equalsIgnoreCase(
						"billingModelName"))
				{
					billingModelName = productAttribute.getValue();
					priceDesc = productAttribute.getValue();
				}
				if (productAttribute.getName().equalsIgnoreCase(
						"billingModelId"))
					billingModelId = productAttribute.getValue();
				if (productAttribute.getName().equalsIgnoreCase(
						"billingModelType"))
					billingModelType = productAttribute.getValue();
				if (productAttribute.getName().equalsIgnoreCase("spId"))
					spId = productAttribute.getValue();
				if (productAttribute.getName().equalsIgnoreCase("posterUrl"))
					posterUrl = productAttribute.getValue();
				if (productAttribute.getName().equalsIgnoreCase("url"))
					url = productAttribute.getValue();
				if (productAttribute.getName().equalsIgnoreCase("demoUrl"))
					demoUrl = productAttribute.getValue();
				if (productAttribute.getName().equalsIgnoreCase("updateTime"))
				{
					updateTime = DateUtils
							.parseDate(productAttribute.getValue(),
									new String[] { "yyyy-MM-dd HH:mm:ss" });
				}
				if (productAttribute.getName().equalsIgnoreCase("startTime"))
					startTime = DateUtils.parseDate(
							productAttribute.getValue(),
							new String[] { "yyyy-MM-dd HH:mm:ss" });
				if (productAttribute.getName().equalsIgnoreCase("endTime"))
					endTime = DateUtils.parseDate(productAttribute.getValue(),
							new String[] { "yyyy-MM-dd HH:mm:ss" });
			}
		
			if (type.equalsIgnoreCase("vod"))
			{
				product.setBillingModelId(billingModelId);
				product.setBillingModelName(billingModelName);
				product.setBillingModelType(billingModelType);
				product.setBizId(bizId);
				product.setPrice(price);
				product.setCreateTime(currentTime);
				product.setModifyTime(updateTime);
				product.setState(VodConstant.STATE);
				product.setIsPackage(VodConstant.NO);
				product.setPosterUrl(posterUrl);
				product.setProductDesc(description);
				product.setProductId(id);
				product.setProductName(name);
				product.setType("vod");
				product.setSpId(spId);
				
				product.setStartTime(startTime);
				product.setEndTime(endTime);
				business = this.productToBusiness(product);
			}else{	
				business.setBusinessId(bizId);	
				business.setBillingModelId(billingModelId);
				business.setBillingModelName(billingModelName);
				business.setBillingModelType(billingModelType);
				business.setCreateTime(currentTime);
				business.setModifyTime(updateTime);
				business.setState(VodConstant.STATE);
				business.setViewURL(posterUrl);
				business.setDescription(description);
				business.setName(name);
				business.setType(type);
				business.setSpId(spId);
				business.setThumbnailURL(demoUrl);
				business.setPriceDesc(priceDesc);
				business.setStartTime(startTime);
				business.setEndTime(endTime);
				business.setBusinessURL(url);
			}
			businesses.put(business.hashCode(), business);
		}
		e=null;
		return businesses;
	}
	
	public BusinessInfo productToBusiness(ProductInfo product){
		BusinessInfo businessInfo = new BusinessInfo();
		
		businessInfo.setBillingModelId(product.getBillingModelId());
		businessInfo.setBillingModelName(product.getBillingModelName());
		businessInfo.setBillingModelType(product.getBillingModelType());
		businessInfo.setBusinessId(product.getBizId());
		businessInfo.setBusinessURL("");
		businessInfo.setCreateTime(product.getCreateTime());
		businessInfo.setDescription(product.getProductDesc());
		businessInfo.setEndTime(product.getEndTime());
		businessInfo.setModifyTime(product.getModifyTime());
		businessInfo.setName(product.getProductName());
		businessInfo.setPriceDesc(product.getPrice());
		businessInfo.setSpId(product.getSpId());
		businessInfo.setStartTime(product.getStartTime());
		businessInfo.setState(product.getState());
		businessInfo.setThumbnailURL("");
		businessInfo.setType(product.getType());
		businessInfo.setViewURL(product.getPosterUrl());
		return businessInfo;
	} */
	
	/**
	 * 获取远程文件,并调用解析方法
	 * 
	 * @param url  服务提供的地址
	 * return 
	 */
	public HashMap<String, Object> parseInfoByHttp(String url)
	{
		Document document = null;
		URLConnection connection;
		InputStream intpuStream = null;
		try
		{
			SAXReader saxReader = new SAXReader();
			connection = new URL(url).openConnection();
			intpuStream = connection.getInputStream();
			document = saxReader.read(intpuStream);

		} catch (Exception e)
		{
			document = null;
			if (LOG.isErrorEnabled())
				LOG.error("VOD业务 解析document出错", e);
		} finally
		{
			try
			{
				if (intpuStream != null)
					intpuStream.close();
			} catch (IOException e)
			{
				e.printStackTrace();
				if (LOG.isErrorEnabled())
					LOG.error("VOD业务 关闭流文件失败", e);
			}
		}
		if (document != null){
			HashMap<String,Object> infos = getDocument(document);
			document = null;
			return infos;
		}else{
			return null;
		}
	}

	/**
	 * 以读取本地路径的方式解析VOD数据
	 * @param url 文件存放位置
	 * @return 解析后的对象
	 */
	public HashMap<String, Object> parseInfoByLocal(String url) {

		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			document = saxReader.read(url);
		} catch (DocumentException e) {
			document = null;
			LOG.info("解析出错：url="+url);
		//	throw new TurboException(e, "解析document出错");
		}
		HashMap<String,Object> infos = getDocument(document);
		document = null;
		return infos;
	}
	
	public HashMap<String, Object> convertStreamToObject(InputStream intpuStream)
	{
		Document document = null;
		SAXReader saxReader = new SAXReader();
		try
		{
			LOG.debug("性能优化记录：读取BSMP数据开始于===>"+Transform.CalendartoString(Calendar.getInstance()));
			document = saxReader.read(intpuStream);
			//System.out.println(document.asXML());
			LOG.debug("性能优化记录：读取BSMP数据完成于===>"+Transform.CalendartoString(Calendar.getInstance()));
		} catch (DocumentException e)
		{
			e.printStackTrace();
			document = null;
		//	throw new TurboException(e, "解析document出错");
		}
		HashMap<String,Object> infos = getDocument(document);
		document = null;
		return infos;
	}
}
