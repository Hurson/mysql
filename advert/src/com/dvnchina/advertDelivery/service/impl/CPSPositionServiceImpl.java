package com.dvnchina.advertDelivery.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.beans.BeanUtils;

import com.dvnchina.advertDelivery.bean.cpsPosition.CategoryBean;
import com.dvnchina.advertDelivery.bean.cpsPosition.CategoryEncapsulationBean;
import com.dvnchina.advertDelivery.bean.cpsPosition.PositionEncapsulationBean;
import com.dvnchina.advertDelivery.bean.cpsPosition.TemplateEncapsulationBean;
import com.dvnchina.advertDelivery.constant.CPSConstant;
import com.dvnchina.advertDelivery.dao.AdvertPositionDao;
import com.dvnchina.advertDelivery.dao.AdvertPositionTypeDao;
import com.dvnchina.advertDelivery.dao.CPSPositionDao;
import com.dvnchina.advertDelivery.dao.PlayCategoryDao;
import com.dvnchina.advertDelivery.model.AdvertPosition;
import com.dvnchina.advertDelivery.model.AdvertPositionType;
import com.dvnchina.advertDelivery.model.PlayCategory;
import com.dvnchina.advertDelivery.model.PortalPlayCategory;
import com.dvnchina.advertDelivery.service.AdvertPositionService;
import com.dvnchina.advertDelivery.service.CPSPositionService;

public class CPSPositionServiceImpl implements CPSPositionService {

	private static final Logger logger = Logger.getLogger(CPSPositionServiceImpl.class);

	private CPSPositionDao cpsPositionDao;

	private List<CategoryBean> categoryBeanList;

	private AdvertPositionDao advertPositionDao;
	
	private PlayCategoryDao playCategoryDao;
	
	private AdvertPositionTypeDao advertPositionTypeDao;
	
	private List<AdvertPosition> synchronous_before_advertPosition = new ArrayList<AdvertPosition>();
	
	private Map<Integer,AdvertPosition> synchronous_before_advertPositionMap;
	
	private Map<Integer,PlayCategory> synchronous_before_playCategoryMap;
	
	@Override
	public Document getDocumentFromCps(String remoteInterfaceUrl) {

		byte[] res = null;
		Document document = null;
		URLConnection connection = null;
		InputStream intpuStream = null;
		boolean flag = true;
		try {
			SAXReader saxReader = new SAXReader();
			connection = new URL(remoteInterfaceUrl).openConnection();
			intpuStream = connection.getInputStream();
			document = saxReader.read(intpuStream);
		} catch (Exception e) {

			logger.error("关闭流时出现异常", e);
		} finally {
			try {
				if (intpuStream != null) {
					intpuStream.close();
				}
			} catch (IOException e) {
				logger.error("关闭流时出现异常", e);
			}

		}
		return document;
	}

	@Override
	public List<CategoryBean> queryWaitBeDeleteCategory() {
		return null;
	}

	@Override
	public Map updateCategoryAndTemplateInfoStage(Document document) {

		Map map = null;
		if (document != null) {
			Element rootElement = document.getRootElement();
			try {
				map = process2Section(rootElement);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	/**
	 * 
	 * 
	 * @param rootElement
	 * 					org.dom4j.Element  
	 * @return map  存放结果集对象的容器
	 *  				  
	 * @throws Exception
	 */
	private Map process2Section(Element rootElement) throws Exception {

		// 得到 xml 中 category 的所有节点的数目
		Double categorySize = this.getTotalCategorySizeFromCps(rootElement);

		// 计算出每次的执行区间 计算的结果：1=1;2, 2=3;4 
		Map<Integer, String> executeRangeMap = this.calculateEveryTimeExecuteRange(categorySize);
		
		Set executeRangeSet = null;

		TemplateEncapsulationBean templateEncapsulationBean = null;
		
		/**
		 * 同步广告位时，存放有效模板
		 */
		List<CategoryBean> effectiveCategory_List = new ArrayList<CategoryBean>();
		
		/**
		 * 同步广告位时，存放有效模板
		 */
		List<CategoryBean> effectivePlayCategory_List = new ArrayList<CategoryBean>();
		
		/**
		 * 保存返回的结果集
		 */
		Map<String, Map<String, CategoryBean>> resultMap = new HashMap<String, Map<String, CategoryBean>>();
		Map<String, CategoryBean> effectMap = new HashMap<String, CategoryBean>();
		Map<String, CategoryBean> invalidateMap_no_template = new HashMap<String, CategoryBean>();
		Map<String, CategoryBean> invalidateMap_no_position = new HashMap<String, CategoryBean>();
		
		/**
		 * 修改广告位信息时，存放有效结点的Map
		 */
		Map<Integer,CategoryBean> queryAdvertPositionMapFromXMLToHashCode = new HashMap<Integer,CategoryBean>();
		
		/**
		 * 修改节点信息时，存放有效节电的Map
		 */
		Map<Integer,CategoryBean> queryPlayCategoryMapFromXMLToHashCode = new HashMap<Integer,CategoryBean>();
		
		Map<String, PlayCategory> stage2queryCategoryMapFromDB = new HashMap<String, PlayCategory>();
		
		Map<String, AdvertPosition> stage2queryAdvertPositionMapFromDB = new HashMap<String, AdvertPosition>();
		
		Map<String, AdvertPositionType> stage2queryAdvertPositionTypeMapFromDB = new HashMap<String, AdvertPositionType>();
		
		AdvertPosition advertPosition = new AdvertPosition();
		
		PlayCategory playCategory  = new PlayCategory();
		
		PortalPlayCategory portalPlayCategory  = new PortalPlayCategory();
		
		// 广告位类型 模型层
		AdvertPositionType advertPositionType = new AdvertPositionType();
		
		if (executeRangeMap != null && executeRangeMap.size() > 0) {
			executeRangeSet = executeRangeMap.entrySet();
			for (Iterator iterator = executeRangeSet.iterator(); iterator.hasNext();) {

				Map.Entry<Integer, String> rangeMap = (Map.Entry<Integer, String>) iterator.next();
				// 拿到 Map 中的key 的值，因为，经过计算的结果为：1=1;2, 2=3;4，在这里拿到等号前面的值
				Integer key = rangeMap.getKey();
				String position = rangeMap.getValue();
				String[] positionArray = null;
				if (StringUtils.isNotBlank(position)) {
					positionArray = position.split(";");
					if (positionArray != null && positionArray.length >= 2) {
						// 分段处理数据
						// 将 在position范围中，根绝循环原则，每2个节点Category的信息存在放一个List中

						List<CategoryEncapsulationBean> categoryEncapsulationBeanList = parseCategory4Section(rootElement, positionArray[0], positionArray[1]);

						if (categoryEncapsulationBeanList != null&& categoryEncapsulationBeanList.size() > 0) {
							for (CategoryEncapsulationBean categoryEncapsulationBean : categoryEncapsulationBeanList) {
								
								// 根据从category节点上获取的templateId定位到对应节点上
								templateEncapsulationBean = parseTemplate4Section(rootElement, categoryEncapsulationBean.getTemplateId()+ "");

								CategoryBean categoryBean = new CategoryBean();
								categoryBean.setCcId(categoryEncapsulationBean.getId());
								categoryBean.setCcategoryId(categoryEncapsulationBean.getCategoryId());
								categoryBean.setCcategoryName(categoryEncapsulationBean.getCategoryName());
								categoryBean.setCtemplateId(categoryEncapsulationBean.getTemplateId());
								categoryBean.setCnetworkId(categoryEncapsulationBean.getNetworkId());
								categoryBean.setCuserLevel(categoryEncapsulationBean.getUserLevel());
								categoryBean.setCindustryType(categoryEncapsulationBean.getIndustryType());
								categoryBean.setCtype(categoryEncapsulationBean.getType());
								categoryBean.setCTreeId(categoryEncapsulationBean.getTreeId());
								categoryBean.setCTreeName(categoryEncapsulationBean.getTreeName());
								categoryBean.setCmodifyTime(categoryEncapsulationBean.getModifyTime());
								
								if (templateEncapsulationBean == null) {
									if (invalidateMap_no_template.get(categoryBean.getCcategoryId()) == null) {
										invalidateMap_no_template.put(categoryBean.getCcategoryId(),categoryBean);
										resultMap.put(CPSConstant.INVALIDS_CATEGORY_TEMPLATE_NO_TEMPLATE,invalidateMap_no_template);
									}
								}
								// 无效节点，没找到和模板对应广告位
								else if (templateEncapsulationBean.getPositionList() == null|| templateEncapsulationBean.getPositionList().size() <= 0) {
									if (invalidateMap_no_position.get(categoryBean.getCcategoryId()) == null) {
										invalidateMap_no_position.put(categoryBean.getCcategoryId(),categoryBean);
										resultMap.put(CPSConstant.INVALIDS_CATEGORY_TEMPLATE_NO_POSITION,invalidateMap_no_position);
									}
								}
								// 有效节点
								else {
									// 这里根据最后节点Position 而确定最后应该有几条数据
									List<CategoryBean> generateCategoryBeanList = this.generateCategoryBean(categoryEncapsulationBean,templateEncapsulationBean);

									if (generateCategoryBeanList != null&& generateCategoryBeanList.size() > 0) {
										for (CategoryBean generateCategoryBean : generateCategoryBeanList) {
											if (effectMap.get(generateCategoryBean.getCcategoryId()+ "#"+ generateCategoryBean.getCtemplateId()+ "#"+ generateCategoryBean.getPId()) == null) {
												// 将这个对应的List放入到对应的存放在有效的Map中
												
											//	queryAdvertPositionMapFromDBToHashCode.put((penginValueAndModifyTime).hashCode(), generateCategoryBean);
												
												queryAdvertPositionMapFromXMLToHashCode.put(generateCategoryBean.hashCode(), generateCategoryBean);
												
												queryPlayCategoryMapFromXMLToHashCode.put(generateCategoryBean.compareValue(),generateCategoryBean);
												
												effectMap.put(generateCategoryBean.getCcId()+ "#"+ generateCategoryBean.getCtemplateId()+ "#"+ generateCategoryBean.getPId(),generateCategoryBean);
											}
										}
									}
									resultMap.put(CPSConstant.EFFECTIVE_CATEGORY,effectMap);
								}
							}
						}
					}
				}
				
				//同步操作前，拿到广告位表中，所有投放方式为"PORTAL"类型的记录  AdvertPositionAction
				synchronous_before_advertPosition = this.getSynchronousBeforeAdvertPositionListFromDB();
				
				synchronous_before_advertPositionMap = this.getSynchronousBeforeAdvertPositionMapFromDB();
				
				synchronous_before_playCategoryMap = this.getSynchronousBeforePlayCategoryMapFromDB();
				
				/**
				 * 当投放信息修改时，同步节点信息数据时，通过Key 中的 HashCode 先比较XML 中 与数据库中的字段
				 * 
				 */
				
				if(queryPlayCategoryMapFromXMLToHashCode != null && queryPlayCategoryMapFromXMLToHashCode.size()>0){
					Set  idModifyBeanSet =  queryPlayCategoryMapFromXMLToHashCode.entrySet();
					for(Iterator i = idModifyBeanSet.iterator();i.hasNext();){
						Map.Entry<Integer, CategoryBean> idModifyMap  = (Entry<Integer, CategoryBean>) i.next();
						Integer adCategoryKeyFromXML = idModifyMap.getKey();
						
						if(synchronous_before_playCategoryMap != null && synchronous_before_playCategoryMap.size()>0){
							Set beforeCategoryMapSet = synchronous_before_playCategoryMap.entrySet();
							for(Iterator j = beforeCategoryMapSet.iterator();j.hasNext();){
								Map.Entry<Integer,PlayCategory> adPlayCategory = (Entry<Integer, PlayCategory>) j.next();
								Integer adCategoryKeyFromDB = adPlayCategory.getKey();
								if( adCategoryKeyFromXML == adCategoryKeyFromDB ){
									queryPlayCategoryMapFromXMLToHashCode.remove(adCategoryKeyFromXML);
									synchronous_before_playCategoryMap.remove(adCategoryKeyFromDB);
								}
							}
						}
					}
				}
				
				/**
				 * 当广告位信息修改时，同步广告位节点，往广告位表中插入数据时，通过Key 中的 HashCode 先比较XML 中 与数据库中的字段
				 * 
				 */
				if(queryAdvertPositionMapFromXMLToHashCode != null && queryAdvertPositionMapFromXMLToHashCode.size()>0){
					
					Set peigenModifyBeanSet = queryAdvertPositionMapFromXMLToHashCode.entrySet();
					
					for(Iterator i = peigenModifyBeanSet.iterator();i.hasNext();){
						
						Map.Entry<Integer, CategoryBean> peigenModifyMap  = (Entry<Integer, CategoryBean>) i.next();
						
						Integer adKeyFromXML = peigenModifyMap.getKey();
						
						if(synchronous_before_advertPositionMap != null && synchronous_before_advertPositionMap.size()>0){
							Set beforeMap = synchronous_before_advertPositionMap.entrySet();
							for(Iterator i1 = beforeMap.iterator();i1.hasNext();){
								Map.Entry<Integer,AdvertPosition> adPosition = (Entry<Integer, AdvertPosition>) i1.next();
								Integer adKeyFromDB = adPosition.getKey();
								
								if(adKeyFromXML == adKeyFromDB){
									queryAdvertPositionMapFromXMLToHashCode.remove(adKeyFromXML);
									synchronous_before_advertPositionMap.remove(adKeyFromDB);
								}
							}
						}
					}
				}
				
				/**
				 * 分别判断从存放数据库Map集合 中剩下的属性 和 存放XML 中剩下的属性，在分别进行对应的操作
				 */
				if(queryPlayCategoryMapFromXMLToHashCode != null && queryPlayCategoryMapFromXMLToHashCode.size()>0){
					Set playCategoryFromXMLSet = queryPlayCategoryMapFromXMLToHashCode.entrySet();
					for(Iterator i = playCategoryFromXMLSet.iterator();i.hasNext();){
						Map.Entry<Integer,CategoryBean> adPlayCategoryFromXMLSet = (Entry<Integer, CategoryBean>) i.next();
						effectivePlayCategory_List.add(adPlayCategoryFromXMLSet.getValue());
					}
				}
				
				if(effectivePlayCategory_List != null && effectivePlayCategory_List.size()>0){
					
					for(int i=0;i<effectivePlayCategory_List.size();i++){
					//生成查询投放节点信息表的SQL语句
					String queryPlayCategorySql = getQueryCategorySqlFromCurrentList(effectivePlayCategory_List);
					// 根据上面生成的关于投放节点表单的查询sql，对数据库中投放节点表进行查询
					List<PlayCategory> stage2QueryPlayCategoryListFromDB = this.queryCategoryBeanBySql(queryPlayCategorySql);	
				
					if (stage2QueryPlayCategoryListFromDB != null&& stage2QueryPlayCategoryListFromDB.size() > 0) {
						 for (PlayCategory stage2QueryCategoryBeanFromDB : stage2QueryPlayCategoryListFromDB) {
							stage2queryCategoryMapFromDB.put(String.valueOf(stage2QueryCategoryBeanFromDB.getId()),stage2QueryCategoryBeanFromDB);
						 }
					  }
					
					Map<Integer, CategoryBean> stage3QueryPlayCategoryMapFromCache = queryPlayCategoryMapFromXMLToHashCode;
					if (stage3QueryPlayCategoryMapFromCache != null&& stage3QueryPlayCategoryMapFromCache.size() > 0) {
						Set stage3QueryPlayCategorySet = stage3QueryPlayCategoryMapFromCache.entrySet();
						for (Iterator iterator2 = stage3QueryPlayCategorySet.iterator(); iterator2.hasNext();) {
							Map.Entry<Integer, CategoryBean> stage3QueryPlayCategoryMap = (Map.Entry<Integer, CategoryBean>) iterator2.next();
							Integer XMLKey = stage3QueryPlayCategoryMap.getKey();
							//拿到 XML 中节点<Category> 的id的值
							Integer playCategoryId = stage3QueryPlayCategoryMap.getValue().getCcId();
							PlayCategory playCategoryBeanFromDB = stage2queryCategoryMapFromDB.get(String.valueOf(playCategoryId));
							if (playCategoryBeanFromDB != null) {
								DateFormat  dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								String modifyTimeFromXML = stage3QueryPlayCategoryMap.getValue().getModifyTime();
						//		Timestamp modifyTimeFromDB = playCategoryBeanFromDB.getModifyTime();
				//从数据库中拿到的值为Date 类型，原来为TimeStamp类型，这里为了最小该代码，从库中取出值后，就转换了一下。
								Date modifyTimeFromDB1 = playCategoryBeanFromDB.getModifyTime();
								
								Timestamp modifyTimeFromDB = new Timestamp(modifyTimeFromDB1.getTime());
								
								Date modifyTimeDate = dateFormat.parse(modifyTimeFromXML);
								Timestamp modifyTime = new Timestamp(modifyTimeDate.getTime());
								//XML 中的修改时间 和数据库中的修改时间不相等 则更新
								if(modifyTime.getTime()>modifyTimeFromDB.getTime() || modifyTime.getTime() < modifyTimeFromDB.getTime() ){
					//			if(modifyTimeDate.getTime()>modifyTimeFromDB.getTime() || modifyTimeDate.getTime() < modifyTimeFromDB.getTime() ){
									CategoryBean categoryBean = new CategoryBean();
									categoryBean = stage3QueryPlayCategoryMap.getValue();
									//投放节点信息表单的主键不是自增，而是手动赋值的。
									playCategory.setId(categoryBean.getCcId());
									playCategory.setCategoryId(categoryBean.getCcategoryId());
									playCategory.setCategoryName(categoryBean.getCcategoryName());
									playCategory.setCategoryType(String.valueOf(categoryBean.getCtype()));
									playCategory.setTemplateId(String.valueOf(categoryBean.getCtemplateId()));
									playCategory.setTemplateName(categoryBean.getTtemplateName());
									//创建时间维持不变
									playCategory.setCreateTime(playCategoryBeanFromDB.getCreateTime());
								//	playCategory.setModifyTime(new Timestamp(System.currentTimeMillis()));
									
									playCategory.setModifyTime(new Date());
									
									List<Integer> delPlayCategoryDBList = new ArrayList<Integer>();
									if(synchronous_before_playCategoryMap != null && synchronous_before_playCategoryMap.size()>0){
										Set DBPlayCategoryMapSet = synchronous_before_playCategoryMap.entrySet();
										for(Iterator i2 = DBPlayCategoryMapSet.iterator();i2.hasNext();){
											Map.Entry<Integer,PlayCategory> stage3QueryPlayCategoryMapSetMap = (Map.Entry<Integer, PlayCategory>) i2.next();
											Integer keyXML = stage3QueryPlayCategoryMapSetMap.getKey();
											if(stage3QueryPlayCategoryMapSetMap.getValue().getId() == stage3QueryPlayCategoryMap.getValue().getCcId()){
												delPlayCategoryDBList.add(keyXML);
											}
										}
									}
									
									if(delPlayCategoryDBList != null && delPlayCategoryDBList.size()>0){
										for(int i0=0;i0<delPlayCategoryDBList.size();i0++){
											synchronous_before_playCategoryMap.remove(delPlayCategoryDBList.get(i0));
										}
									}
									playCategoryDao.updatePlayCategory(playCategory);
									
								}
								/*else{
									logger.debug("-------XML 中的修改时间 和数据库中的修改时间相等------");
									//当id和修改时间都相同的时候
									//投放节点信息表单数据不变。为了关联portal投放节点信息表
									//拿到此条广告记录的id值
									CategoryBean categoryBeanFromCache = stage3QueryCategoryMap.getValue();
									categoryBeanFromCache.setId(categoryBeanFromDB.getId());
								}*/
							} else {//新建
								
								if(portalPlayCategory == null){
									portalPlayCategory = new PortalPlayCategory();
								}
								
								CategoryBean categoryBean = new CategoryBean();
								categoryBean = stage3QueryPlayCategoryMap.getValue();
								
								//投放节点信息表单的主键不是自增，而是手动赋值的。
								playCategory.setId(categoryBean.getCcId());
								playCategory.setCategoryId(categoryBean.getCcategoryId());
								playCategory.setCategoryName(categoryBean.getCcategoryName());
								playCategory.setCategoryType(String.valueOf(categoryBean.getCtype()));
								playCategory.setTemplateId(String.valueOf(categoryBean.getCtemplateId()));
								playCategory.setTemplateName(categoryBean.getTtemplateName());
								playCategory.setCreateTime(new Date());
								playCategory.setModifyTime(new Date());
						//		playCategory.setCreateTime(new Timestamp(System.currentTimeMillis()));
						//		playCategory.setModifyTime(new Timestamp(System.currentTimeMillis()));
								// 保存广告位 
								playCategoryDao.savePlayCategory(playCategory);
								
							}
						}
					}
				}
				
				/**
				 * 分别判断从存放数据库Map集合 中剩下的属性 和 存放XML 中剩下的属性，在分别进行对应的操作
				 */
				if(queryAdvertPositionMapFromXMLToHashCode != null && queryAdvertPositionMapFromXMLToHashCode.size()>0){
					
					Set AdvertPositionMapFromXMLSet = queryAdvertPositionMapFromXMLToHashCode.entrySet();
					
					for(Iterator i = AdvertPositionMapFromXMLSet.iterator();i.hasNext();){
						
						Map.Entry<Integer, CategoryBean> AdvertPositionMapFromXMLBean  = (Entry<Integer, CategoryBean>) i.next();
						//在XML中，拿到比较 hashCode 不相同的 那些广告位的实体,放入 List 中
						effectiveCategory_List.add(AdvertPositionMapFromXMLBean.getValue());
					}	
					
				}
				
				if (effectiveCategory_List != null&& effectiveCategory_List.size() > 0) {
					
					for (int i = 0; i < effectiveCategory_List.size(); i++) {

						//生成查询投放节点信息表的SQL语句
				//		String queryCategorySql = getQueryCategorySqlFromCurrentList(effectiveCategory_List);
						
						//生成查询广告位类型表单的SQL语句
						String queryAdvertPositonTypeSql = getAdvertPositionTypeSqlFromCurrentList(effectiveCategory_List); 
						
						//生成查询广告位表的SQL语句
						String queryAdvertPositionSql = getQueryAdvertPostionSqlFromCurrentList(effectiveCategory_List);
						
						// 根据上面生成的关于投放节点表单的查询sql，对数据库中投放节点表进行查询
				//		List<PlayCategory> stage2QueryCategoryListFromDB = this.queryCategoryBeanBySql(queryCategorySql);

						// 根据上面生成的广告位查询sql，对数据库中广告位表单进行查询
						List<AdvertPosition> stage2QueryAdvertPositionListFromDB = this.queryAdvertPositionBeanBySql(queryAdvertPositionSql);
 
						// 根据上面生成的广告位查询sql，对数据库中广告位类型表单进行查询
						List<AdvertPositionType> stage2QueryAdvertPositionTypeListFromDB = this.queryAdvertPositionTypeBeanBySql(queryAdvertPositonTypeSql);
						
                        //在投放节点表中，如果能从数据库查到数据，将这些记录以主键为key 值，将对应的实体类存到Map中
						/*if (stage2QueryCategoryListFromDB != null&& stage2QueryCategoryListFromDB.size() > 0) {
							for (PlayCategory stage2QueryCategoryBeanFromDB : stage2QueryCategoryListFromDB) {
								stage2queryCategoryMapFromDB.put(String.valueOf(stage2QueryCategoryBeanFromDB.getId()),stage2QueryCategoryBeanFromDB);
							}
						}*/
						
                        //在广告位表单中，如果能从数据库查到数据，将这些记录以主键为key值，将对应的实体类存到Map中
						if(stage2QueryAdvertPositionListFromDB != null && stage2QueryAdvertPositionListFromDB.size()>0){
							for(AdvertPosition stage2QueryAdvertPositionBeanFromDB : stage2QueryAdvertPositionListFromDB ){
								stage2queryAdvertPositionMapFromDB.put(stage2QueryAdvertPositionBeanFromDB.getCharacteristicIdentification(),stage2QueryAdvertPositionBeanFromDB);
							}
						}
						
						//在广告位类型表单中，如果能从数据库查到数据，将这些记录分别存进对应的Map中
						if(stage2QueryAdvertPositionTypeListFromDB != null && stage2QueryAdvertPositionTypeListFromDB.size()>0){
							for(AdvertPositionType stage2QueryAdvertPositionTypeFromDB : stage2QueryAdvertPositionTypeListFromDB){
								stage2queryAdvertPositionTypeMapFromDB.put(stage2QueryAdvertPositionTypeFromDB.getPositionTypeCode(), stage2QueryAdvertPositionTypeFromDB);
							}
						}

		//---------------------------------------				
			//			Map<String, CategoryBean> stage3QueryCategoryMapFromCache = resultMap.get(CPSConstant.EFFECTIVE_CATEGORY);
		//---------------------------------------				
			//         再次拿到经过hashCode 等值判断后的XML 的 集合Map 属性,之后判断是更新还是添加
						
						Map<Integer, CategoryBean> stage3QueryCategoryMapFromCache =  queryAdvertPositionMapFromXMLToHashCode;
					
						if (stage3QueryCategoryMapFromCache != null&& stage3QueryCategoryMapFromCache.size() > 0) {
							Set stage3QueryCategorySet = stage3QueryCategoryMapFromCache.entrySet();
							for (Iterator iterator2 = stage3QueryCategorySet.iterator(); iterator2.hasNext();) {
								Map.Entry<Integer, CategoryBean> stage3QueryCategoryMap = (Map.Entry<Integer, CategoryBean>) iterator2.next();
								Integer XMLKey = stage3QueryCategoryMap.getKey();
								//拿到广告位特征值
								String peigenValue = stage3QueryCategoryMap.getValue().getPeigenValue();
								
								AdvertPosition advertPositionBeanFromDB = stage2queryAdvertPositionMapFromDB.get(peigenValue);
								//判断广告位
							//	if(advertPositionBeanFromDB != null && categoryBeanFromDB != null){
								if(advertPositionBeanFromDB != null){
									
									DateFormat  dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									String modifyTimeFromXML = stage3QueryCategoryMap.getValue().getPmodifyTime();
									Timestamp modifyTimeFromDB = (Timestamp) advertPositionBeanFromDB.getModifyTime();
									Date modifyTimeDate = dateFormat.parse(modifyTimeFromXML);
									Timestamp modifyTime = new Timestamp(modifyTimeDate.getTime());
									
									//XML 中的修改时间 和数据库中的修改时间不相等，则更新
									if(modifyTime.getTime() > modifyTimeFromDB.getTime() || modifyTime.getTime() < modifyTimeFromDB.getTime() ){
										this.createAdvertPositionObjectBeforeUpdate(advertPositionBeanFromDB,stage3QueryCategoryMap,advertPosition);
			//----------------	
								//		this.createPlayCategoryObjectBeforeUpdate(categoryBeanFromDB,stage3QueryCategoryMap,playCategory);
			//----------------	
										List<Integer> delDBList = new ArrayList<Integer>();
										if(synchronous_before_advertPositionMap != null && synchronous_before_advertPositionMap.size()>0){
											Set DBAdvertPositionMapSet = synchronous_before_advertPositionMap.entrySet();
											for(Iterator i2 = DBAdvertPositionMapSet.iterator();i2.hasNext();){
												Map.Entry<Integer, AdvertPosition> stage3QueryAdvertPositionMap = (Map.Entry<Integer, AdvertPosition>) i2.next();
												Integer keyXML = stage3QueryAdvertPositionMap.getKey();
												if(stage3QueryAdvertPositionMap.getValue().getCharacteristicIdentification().equals(stage3QueryCategoryMap.getValue().getPeigenValue())){
													delDBList.add(keyXML);
												}
											}
										}
										
										if(delDBList != null && delDBList.size()>0){
											for(int i0=0;i0<delDBList.size();i0++){
												synchronous_before_advertPositionMap.remove(delDBList.get(i0));
											}
										}
										advertPositionDao.updateAdvertPosition(advertPosition);
									}
								//添加
								}else{
									Integer advertPositionTypeId = null;
									if(portalPlayCategory == null){
										portalPlayCategory = new PortalPlayCategory();
									}
									if(advertPositionType == null){
										advertPositionType = new AdvertPositionType();
									}
									
									this.createAdvertPositionObjectBeforeSave(stage3QueryCategoryMap,advertPosition);
									
									advertPositionType = stage2queryAdvertPositionTypeMapFromDB.get(stage3QueryCategoryMap.getValue().getPpositionTypeCode());
									
									if(advertPositionType != null){
										advertPositionTypeId = advertPositionType.getId();
										//设置广告位表单中的广告位类型id字段
										advertPosition.setPositionTypeId(advertPositionTypeId);
									}else{
										advertPosition.setPositionTypeId(111111111);
									}
							    	Integer categerId = stage3QueryCategoryMap.getValue().getCcId();
									//保存并广告位信息并返回主键
				                    Integer adpositionId =  advertPositionDao.saveAdvertPositionReturnPrimaryKey(advertPosition);
									portalPlayCategory.setCategoryId(categerId);
									portalPlayCategory.setPositionId(adpositionId);
									//将信息保存到关系表中
									cpsPositionDao.savePortalPlayCategory(portalPlayCategory);
								 }
							   }
							}
						}
					}
				}
			}
		}
		
		
		/**
		 * 把数据库中，对没有更新，添加，不变操作的投放节点表单和PORTAL表单中的冗余记录，进行删除操作。
		 */
		if(synchronous_before_playCategoryMap != null && synchronous_before_playCategoryMap.size()>0){
			Set s = synchronous_before_playCategoryMap.entrySet();
			for(Iterator i3 = s.iterator();i3.hasNext();){
				Map.Entry<Integer, PlayCategory> surplusPlayCategoryMapFromDB = (Map.Entry<Integer, PlayCategory>) i3.next();
				cpsPositionDao.removePlayCategory(surplusPlayCategoryMapFromDB.getValue().getId());
				cpsPositionDao.removePORTALByPlayCategoryId(surplusPlayCategoryMapFromDB.getValue().getId());
			}
		}
		
		/**
		 * 把数据库中，对没有更新，添加，不变操作的广告位和PORTAL节点表中的冗余记录，进行删除操作。
		 */
		if(synchronous_before_advertPositionMap != null && synchronous_before_advertPositionMap.size()>0){
			Set s = synchronous_before_advertPositionMap.entrySet();
			for(Iterator i3 = s.iterator();i3.hasNext();){
				Map.Entry<Integer, AdvertPosition> surplusAdvertPositionMapFromDB = (Map.Entry<Integer, AdvertPosition>) i3.next();
				cpsPositionDao.removePORTALByAdvertPositionId(surplusAdvertPositionMapFromDB.getValue().getId());
				advertPositionDao.removeAdvertPosition(surplusAdvertPositionMapFromDB.getValue().getId());
				advertPositionTypeDao.remove(surplusAdvertPositionMapFromDB.getValue().getPositionTypeId());
			}
		}
		
		/*if(synchronous_before_advertPosition != null && synchronous_before_advertPosition.size()>0){
			for(int j=0;j<synchronous_before_advertPosition.size();j++){
				Integer adId = synchronous_before_advertPosition.get(j).getId();
				advertPositionDao.removeAdvertPosition(adId);
			}
		}*/
		return resultMap;
	}
	
	/**
	 * 根据同步过来的广告位的类型编码查询相关数据
	 * @param stage3QueryCategoryMap
	 * @return
	 */
	private String addAdvertPositionTypeSql(Entry<Integer, CategoryBean> stage3QueryCategoryMap) {
		StringBuffer querySql = new StringBuffer("");
		
		querySql.append("SELECT * FROM T_POSITION_TYPE");
		
		String typeCode = stage3QueryCategoryMap.getValue().getPpositionTypeCode();
		
		if(StringUtils.isNotBlank(typeCode)){
			querySql.append(" WHERE POSITION_TYPE_CODE=");
			querySql.append("'");
			querySql.append(typeCode);
			querySql.append("'");
		}
		
		return querySql.toString();
	}

	private void createPlayCategoryObjectBeforeUpdate(PlayCategory categoryBeanFromDB,Entry<Integer, CategoryBean> stage3QueryCategoryMap,PlayCategory playCategory) {

		if(playCategory == null){
			playCategory = new PlayCategory();
		}
		
		CategoryBean categoryBean = new CategoryBean();
		categoryBean = stage3QueryCategoryMap.getValue();
		//投放节点信息表单的主键不是自增，而是手动赋值的。
		playCategory.setId(categoryBean.getCcId());
	//	playCategory.setId(categoryBeanFromDB.getId());
		playCategory.setCategoryId(categoryBean.getCcategoryId());
		playCategory.setCategoryName(categoryBean.getCcategoryName());
		playCategory.setCategoryType(String.valueOf(categoryBean.getCtype()));
		playCategory.setTemplateId(String.valueOf(categoryBean.getCtemplateId()));
		playCategory.setTemplateName(categoryBean.getTtemplateName());
		playCategory.setCreateTime(new Timestamp(System.currentTimeMillis()));
		//更新修改时间
		playCategory.setModifyTime(new Timestamp(System.currentTimeMillis()));
		
	}

	/**
	 * 同步广告位的添加操作前 实例化实体
	 * @param stage3QueryCategoryMap 遍历存放有效节点的Map 时，的实例化对象
	 * @param advertPosition 广告位实体对象
	 */          
	private void createAdvertPositionObjectBeforeSave(Entry<Integer, CategoryBean> stage3QueryCategoryMap,AdvertPosition advertPosition) {

		CategoryBean categoryBean = new CategoryBean();
		categoryBean = stage3QueryCategoryMap.getValue();
		
		//特征值
		advertPosition.setCharacteristicIdentification(categoryBean.getPeigenValue());
		advertPosition.setPositionName(categoryBean.getPcname());
		advertPosition.setDescription(categoryBean.getPdescribe());
		advertPosition.setImageRuleId(categoryBean.getPpictureMaterialSpeciId());
		advertPosition.setVideoRuleId(categoryBean.getPvideoMaterialSpeciId());
		advertPosition.setTextRuleId(categoryBean.getPcontentMaterialSpeciId());
		advertPosition.setQuestionRuleId(categoryBean.getPquestionMaterialSpeciId());
		advertPosition.setIsHd(0);
		advertPosition.setIsAdd(categoryBean.getPisOverlying());
		advertPosition.setIsLoop(0);
		advertPosition.setMaterialNumber(0);
		advertPosition.setDeliveryMode(0);
		advertPosition.setPrice("0");
		advertPosition.setDiscount("0");
		advertPosition.setOperationId("0");
		advertPosition.setState("0");
		advertPosition.setCreateTime(new Timestamp(System.currentTimeMillis()));
		//此广告位类型id 属性，在完成对广告位类型表单存储后，在对其赋值
	//	advertPosition.setPositionTypeId(0);
		advertPosition.setBackgroundPath(categoryBean.getTeffectPictureUrl());
		advertPosition.setCoordinate(categoryBean.getPtop()+"*"+categoryBean.getPleft());
		advertPosition.setWidthHeight(categoryBean.getPwidth()+"*"+categoryBean.getPheight());
		advertPosition.setDeliveryPlatform(CPSConstant.ADVERT_POSITION_PLATFORM);
		advertPosition.setModifyTime(new Timestamp(System.currentTimeMillis()));
		
		advertPosition.setStartTime("0");
		advertPosition.setEndTime("0");
		
	}

	/**
	 * 同步广告位的更新操作前 实例化实体 
	 * @param advertPositionBeanFromDB 查询数据库操作时得到实体
	 * @param stage3QueryCategoryMap 有效节电遍历时的实体
	 * @param advertPosition 广告位实体
	 */
	private void createAdvertPositionObjectBeforeUpdate(AdvertPosition advertPositionBeanFromDB,Entry<Integer, CategoryBean> stage3QueryCategoryMap,AdvertPosition advertPosition) {
		
		if(advertPosition == null){
			advertPosition= new AdvertPosition();
		}
		
		CategoryBean categoryBean = new CategoryBean();
		categoryBean = stage3QueryCategoryMap.getValue();

	    //广告位表单	
		advertPosition.setId(advertPositionBeanFromDB.getId());
		advertPosition.setCharacteristicIdentification(categoryBean.getPeigenValue());
		advertPosition.setPositionName(categoryBean.getPcname());
		advertPosition.setDescription(categoryBean.getPdescribe());
		advertPosition.setImageRuleId(categoryBean.getPpictureMaterialSpeciId());
		advertPosition.setVideoRuleId(categoryBean.getPvideoMaterialSpeciId());
		advertPosition.setTextRuleId(categoryBean.getPcontentMaterialSpeciId());
		advertPosition.setQuestionRuleId(categoryBean.getPquestionMaterialSpeciId());
		advertPosition.setIsHd(0);
		advertPosition.setIsAdd(categoryBean.getPisOverlying());
		advertPosition.setIsLoop(0);
		advertPosition.setMaterialNumber(0);
		advertPosition.setDeliveryMode(0);
		advertPosition.setPrice("0");
		advertPosition.setDiscount("0");
		advertPosition.setOperationId("0");
		advertPosition.setState("0");
		advertPosition.setCreateTime(advertPositionBeanFromDB.getCreateTime());
		advertPosition.setPositionTypeId(advertPositionBeanFromDB.getPositionTypeId());
		advertPosition.setBackgroundPath(categoryBean.getTeffectPictureUrl());
		advertPosition.setCoordinate(categoryBean.getPtop()+"*"+categoryBean.getPleft());
		advertPosition.setWidthHeight(categoryBean.getPwidth()+"*"+categoryBean.getPheight());
		advertPosition.setDeliveryPlatform(CPSConstant.ADVERT_POSITION_PLATFORM);
		advertPosition.setModifyTime(new Timestamp(System.currentTimeMillis()));
	}

	/**
	 * 获取分类节点大小count(/adverts/categories/category)
	 * 
	 * @param rootElement
	 * @return
	 */
	private Double getTotalCategorySizeFromCps(Element rootElement) {
		System.out.println("#"+ rootElement.selectObject("count(/adverts/categories/category)"));
		Double categoriesNodeSize = (Double) rootElement.selectObject("count(/adverts/categories/category)");
		return categoriesNodeSize;
	}

	/**
	 * 根据querySql查询投放节点信息表
	 */
	private List<PlayCategory> queryCategoryBeanBySql(String queryCategorySql) {
		return cpsPositionDao.queryCategoryBeanBySql(queryCategorySql);
	}
	
	/**
	 * 根据querySql查询广告位信息
	 */
	private List<AdvertPosition> queryAdvertPositionBeanBySql(String queryAdvertPositionSql) {
		return cpsPositionDao.queryAdvertPositionBeanBySql(queryAdvertPositionSql);
	}
	
	/**
	 * 按条件查询广告位类型表单
	 */
	private List<AdvertPositionType> queryAdvertPositionTypeBeanBySql(String queryAdvertPositonTypeSql) {
		return advertPositionTypeDao.query(queryAdvertPositonTypeSql);
	}
	
	

	/**
	 * 根据当前从cps获取到的categoryBean中的节点<category>节点属性的值Id，判断能否从数据库中查询出对应记录，
	 * 此根据就是判断记录是该更新还是添加操作。
	 * 
	 * @return
	 */
	private String getQueryCategorySqlFromCurrentList(List<CategoryBean> cpsDataCategoryBeanList) {

		StringBuffer stringBuffer = new StringBuffer();
		//查询投放节点信息表单记录
		stringBuffer.append("SELECT * FROM T_PLAT_CATEGORY t");
		Integer totalCount = 0;
		Integer current = 0;
		if (cpsDataCategoryBeanList != null&& cpsDataCategoryBeanList.size() > 0) {
			totalCount = cpsDataCategoryBeanList.size();
			stringBuffer.append(" WHERE t.ID IN(");
			for (Iterator iterator = cpsDataCategoryBeanList.iterator(); iterator.hasNext();) {
				CategoryBean categoryBean = (CategoryBean) iterator.next();
				current++;
				if (current == totalCount) {
					stringBuffer.append(categoryBean.getCcId()).append(")");
				} else {
					stringBuffer.append(categoryBean.getCcId()).append(",");
				}
			}
		}
		return stringBuffer.toString();
	}
	
	/**
	 * 根据当前从cps获取到的categoryBean中的节点<position>节点属性的值Id，判断从数据库中以投放方式为"PORTAL"类型的记录能够查询出对应结果，此根据就是判断记录是该更新还是添加操作。
	 * 
	 * @return
	 */
	private String getQueryAdvertPostionSqlFromCurrentList(List<CategoryBean> cpsDataCategoryBeanList) {

		StringBuffer stringBuffer = new StringBuffer();
		//查询投放节点信息表单记
		// t.*, t.POSITION_NAME as POSITION_TYPE_NAME
		stringBuffer.append("SELECT * FROM (select * from T_ADVERTPOSITION t where t.delivery_platform='2')");
	//	stringBuffer.append("SELECT  t.*, t.POSITION_NAME as POSITION_TYPE_NAME FROM (select  t.*, t.POSITION_NAME as POSITION_TYPE_NAME from T_ADVERTPOSITION t where t.delivery_platform='PORTAL')");
		Integer totalCount = 0;
		Integer current = 0;
		if (cpsDataCategoryBeanList != null&& cpsDataCategoryBeanList.size() > 0) {
			totalCount = cpsDataCategoryBeanList.size();
			stringBuffer.append(" WHERE");
			for (Iterator iterator = cpsDataCategoryBeanList.iterator(); iterator.hasNext();) {
				CategoryBean categoryBean = (CategoryBean) iterator.next();
				current++;
				if(current == totalCount){
					stringBuffer.append(" CHARACTERISTIC_IDENTIFICATION ='").append(categoryBean.getPeigenValue());
					stringBuffer.append("'");
				}else {
					stringBuffer.append(" CHARACTERISTIC_IDENTIFICATION ='").append(categoryBean.getPeigenValue()).append("' or");
				}
			}
		}
		return stringBuffer.toString();
	}
	

	/**
	 * 
	 * 根据当前从cps获取到的categoryBean中的节点<position>节点属性的值advertPositionTypeCode，判断从数据库的记录能够查询出对应结果。
	 * @return
	 */
	private String getAdvertPositionTypeSqlFromCurrentList(List<CategoryBean> cpsDataCategoryBeanList) {
		
		StringBuffer stringBuffer =new StringBuffer();
		stringBuffer.append("SELECT * FROM T_POSITION_TYPE");
		
		Integer totalCount = 0;
		Integer current = 0;
		
		if (cpsDataCategoryBeanList != null&& cpsDataCategoryBeanList.size() > 0) {
			totalCount = cpsDataCategoryBeanList.size();
			stringBuffer.append(" WHERE");
			for (Iterator iterator = cpsDataCategoryBeanList.iterator(); iterator.hasNext();) {
				CategoryBean categoryBean = (CategoryBean) iterator.next();
				current++;
				if(current == totalCount){
					stringBuffer.append(" POSITION_TYPE_CODE ='").append(categoryBean.getPpositionTypeCode());
					stringBuffer.append("'");
				}else {
					stringBuffer.append(" POSITION_TYPE_CODE ='").append(categoryBean.getPpositionTypeCode()).append("' or");
				}
			}
		}
		return stringBuffer.toString();
	}

	/**
	 * @param categoryEncapsulationBean
	 * @param templateEncapsulationBean
	 * @return
	 */
	private List<CategoryBean> generateCategoryBean(CategoryEncapsulationBean categoryEncapsulationBean,TemplateEncapsulationBean templateEncapsulationBean) {
		List<CategoryBean> categoryBeanList = new ArrayList<CategoryBean>();

		CategoryBean categoryBean = new CategoryBean();
		// 将节点<Category>的所有相关属性都赋给CategoryBean
		// 刚才实体类中得到的有关节点Category 的属性值再付给新的CategoryBean
		categoryBean.setCcId(categoryEncapsulationBean.getId());
		categoryBean.setCcategoryId(categoryEncapsulationBean.getCategoryId());
		categoryBean.setCcategoryName(categoryEncapsulationBean.getCategoryName());
		categoryBean.setCtemplateId(categoryEncapsulationBean.getTemplateId());
		categoryBean.setCnetworkId(categoryEncapsulationBean.getNetworkId());
		categoryBean.setCuserLevel(categoryEncapsulationBean.getUserLevel());
		categoryBean.setCindustryType(categoryEncapsulationBean.getIndustryType());
		categoryBean.setCtype(categoryEncapsulationBean.getType());
		categoryBean.setCTreeId(categoryEncapsulationBean.getTreeId());
		categoryBean.setCTreeName(categoryEncapsulationBean.getTreeName());
		categoryBean.setModifyTime(categoryEncapsulationBean.getModifyTime());

		List<PositionEncapsulationBean> positionList = null;
		if (templateEncapsulationBean != null) {
			// 将节点<Template>的所有相关属性，都赋给 CategoryBean
			categoryBean.setTtemplateName(templateEncapsulationBean.getTemplateName());
			categoryBean.setTtemplateType(templateEncapsulationBean.getType());
			categoryBean.setTeffectPictureUrl(templateEncapsulationBean.getEffectPicture());
			
			positionList = templateEncapsulationBean.getPositionList();
			
		}

		// 如果在对应的节点Template下，存在节点Position，那么拿到其中的值
		if (positionList != null && positionList.size() > 0) {
			for (PositionEncapsulationBean positionEncapsulationBean : templateEncapsulationBean.getPositionList()) {
				CategoryBean categoryBean1 = new CategoryBean();
				BeanUtils.copyProperties(categoryBean, categoryBean1);
				categoryBean1.setPcname(positionEncapsulationBean.getCname());
				categoryBean1.setPwidth(positionEncapsulationBean.getWidth());
				categoryBean1.setPheight(positionEncapsulationBean.getHeight());
				categoryBean1.setPtop(positionEncapsulationBean.getTop());
				categoryBean1.setPleft(positionEncapsulationBean.getLeft());
				categoryBean1.setPId(positionEncapsulationBean.getId());
				
				categoryBean1.setPdescribe(positionEncapsulationBean.getDescribe());
				categoryBean1.setPpictureMaterialSpeciId(positionEncapsulationBean.getPictureMaterialSpeciId());
				categoryBean1.setPvideoMaterialSpeciId(positionEncapsulationBean.getVideoMaterialSpeciId());
				categoryBean1.setPcontentMaterialSpeciId(positionEncapsulationBean.getContentMaterialSpeciId());
				categoryBean1.setPquestionMaterialSpeciId(positionEncapsulationBean.getQuestionMaterialSpeciId());
				categoryBean1.setPisOverlying(positionEncapsulationBean.getIsOverlying());
				categoryBean1.setPcCode(positionEncapsulationBean.getCCode());
				categoryBean1.setPeigenValue(positionEncapsulationBean.getEigenValue());
				categoryBean1.setPmodifyTime(positionEncapsulationBean.getModifyTime());
				categoryBean1.setPpositionTypeCode(positionEncapsulationBean.getPositionTypeCode());
				
				categoryBeanList.add(categoryBean1);
				
			}
		}
		return categoryBeanList;
	}

	/**
	 * 根据从category节点上获取的templateId定位到对应节点上 /adverts/templates/template[@id=1]
	 * @param rootElement
	 * @return
	 */
	private TemplateEncapsulationBean parseTemplate4Section(Element rootElement, String templateId) {
		TemplateEncapsulationBean templateEncapsulationBean = null;
		StringBuffer xpathCondition = new StringBuffer();
		xpathCondition.append("/adverts/templates/template");
		xpathCondition.append("[@id=");
		xpathCondition.append(templateId);
		xpathCondition.append("]");

		// 根据节点Category 得到了 TemplateId 的值
		List<PositionEncapsulationBean> positionEncapsulationBeanList = null;
		
		List<Node> templateNodeList = rootElement.selectNodes(xpathCondition.toString());

		Integer currentTemplateId = null;
		if (templateNodeList != null && templateNodeList.size() > 0) {
			for (Node node : templateNodeList) {
				Element templateElement = (Element) node;
				// 拿到对应节点中的属性
				List templateAttributeList = templateElement.attributes();
				
				templateEncapsulationBean = new TemplateEncapsulationBean();
				if (templateAttributeList != null&& templateAttributeList.size() > 0) {
					for (Iterator iterator = templateAttributeList.iterator(); iterator.hasNext();) {
						Attribute templateAttribute = (Attribute) iterator.next();
						if (checkColumn(templateAttribute.getText())&& ("id".equals(templateAttribute.getName()))) {
							currentTemplateId = Integer.parseInt(templateAttribute.getText());
							templateEncapsulationBean.setId(currentTemplateId);
						} else if ("templateName".equals(templateAttribute.getName())) {
							templateEncapsulationBean.setTemplateName(templateAttribute.getText());
						} else if ("effectPicture".equals(templateAttribute.getName())) {
							String effectPicture = templateAttribute.getText();
							if (StringUtils.isNotBlank(effectPicture)) {
								if (effectPicture.startsWith("/")) {
									// 加载同步过来的图片地址前
									// twowayadvert.sync.category.resoucePath=http://192.168.12.93
									// 根据地址加载图片
									effectPicture = CPSConstant.RESOURCE_SERVER_PATH+ effectPicture;
								} else {
									effectPicture = CPSConstant.RESOURCE_SERVER_PATH+ "/" + effectPicture;
								}
							}
							templateEncapsulationBean.setEffectPicture(effectPicture);
						} else if (checkColumn(templateAttribute.getText())&& ("type".equals(templateAttribute.getName()))) {
							templateEncapsulationBean.setType(Integer.parseInt(templateAttribute.getText()));
						}
					}
				}

				if (positionEncapsulationBeanList == null) {
					positionEncapsulationBeanList = new ArrayList<PositionEncapsulationBean>();
				}
				// 根据Id 确定了节点<Template>后，得到其下面的节点<position>的所有记录
				List<Node> positionNodeList = templateElement.selectNodes("position");
				// 根据 节点 position 遍历出的相关字段，加给 这个Bean中，然后，这个Bean在加到
				// TemplateEncapsulationBean中，然后，返回TemplateEncapsulationBean。
				PositionEncapsulationBean positionEncapsulationBean = null;
				if (positionNodeList != null && positionNodeList.size() > 0) {
					for (Node positionNode : positionNodeList) {
						Element positionElement = (Element) positionNode;
						List positionAttributeList = positionElement.attributes();
						positionEncapsulationBean = new PositionEncapsulationBean();
						if (positionAttributeList != null&& positionAttributeList.size() > 0) {
							for (Iterator iterator = positionAttributeList.iterator(); iterator.hasNext();) {
								Attribute positionAttribute = (Attribute) iterator.next();
								if (checkColumn(positionAttribute.getText())&& ("id".equals(positionAttribute.getName()))) {
									positionEncapsulationBean.setId(Integer.parseInt(positionAttribute.getText()));
								} else if ("cname".equals(positionAttribute.getName())) {
									positionEncapsulationBean.setCname(positionAttribute.getText());
								} else if (checkColumn(positionAttribute.getText())&& "width".equals(positionAttribute.getName())) {
									positionEncapsulationBean.setWidth(Integer.parseInt(positionAttribute.getText()));
								} else if (checkColumn(positionAttribute.getText())&& "height".equals(positionAttribute.getName())) {
									positionEncapsulationBean.setHeight(Integer.parseInt(positionAttribute.getText()));
								} else if (checkColumn(positionAttribute.getText())&& "top".equals(positionAttribute.getName())) {
									positionEncapsulationBean.setTop(Integer.parseInt(positionAttribute.getText()));
								} else if (checkColumn(positionAttribute.getText())&& "left".equals(positionAttribute.getName())) {
									positionEncapsulationBean.setLeft(Integer.parseInt(positionAttribute.getText()));
								}
							// ----------------------------------------------------------------------------------------------
								else if ("desc".equals(positionAttribute.getName())) {
									positionEncapsulationBean.setDescribe(positionAttribute.getText());
								} else if (checkColumn(positionAttribute.getText())&& "pictureMaterialSpeciId".equals(positionAttribute.getName())) {
									positionEncapsulationBean.setPictureMaterialSpeciId(Integer.parseInt(positionAttribute.getText()));
								} else if (checkColumn(positionAttribute.getText())&& "videoMaterialSpeciId".equals(positionAttribute.getName())) {
									positionEncapsulationBean.setVideoMaterialSpeciId(Integer.parseInt(positionAttribute.getText()));
								} else if (checkColumn(positionAttribute.getText())&& "contentMaterialSpeciId".equals(positionAttribute.getName())) {
									positionEncapsulationBean.setContentMaterialSpeciId(Integer.parseInt(positionAttribute.getText()));
								} else if (checkColumn(positionAttribute.getText())&& "questionMaterialSpeciId".equals(positionAttribute.getName())) {
									positionEncapsulationBean.setQuestionMaterialSpeciId(Integer.parseInt(positionAttribute.getText()));
								} else if (checkColumn(positionAttribute.getText())&& "isOverlying".equals(positionAttribute.getName())) {
									positionEncapsulationBean.setIsOverlying(Integer.parseInt(positionAttribute.getText()));
								} else if ("ccode".equals(positionAttribute.getName())) {
									positionEncapsulationBean.setCCode(positionAttribute.getText());
								}else if("eigenValue".equals(positionAttribute.getName())){
									positionEncapsulationBean.setEigenValue(positionAttribute.getText());
								}else if("modifyTime".equals(positionAttribute.getName())){
									positionEncapsulationBean.setModifyTime(positionAttribute.getText());
								}else if("positionTypeCode".equals(positionAttribute.getName())){
									positionEncapsulationBean.setPositionTypeCode(positionAttribute.getText());
									
								}
							}
						}
						// 把所有关于Position 的记录方法List 中
						positionEncapsulationBeanList.add(positionEncapsulationBean);
					}
				}
				// 再把存放特定id下的Position 的集合，在放到Template的List 中。
				templateEncapsulationBean.setPositionList(positionEncapsulationBeanList);
				// 这里为什么要置空?
				positionEncapsulationBeanList = null;
			}
		}
		return templateEncapsulationBean;
	}

	/**
	 * 解析XPATH=/adverts/categories/category[position()>=1 and position()<=2]路径上的内容
	 * 
	 * @param rootElement
	 * @return
	 */
	private List<CategoryEncapsulationBean> parseCategory4Section(Element rootElement, String startPosition, String endPosition) {
		// 将解析中读取的节点，根据相应的属性，赋值到相应的Bean的属性里面，然后，把这个Bean类添加到此容器中.
		List<CategoryEncapsulationBean> categoryEncapsulationBeanList = null;
		StringBuffer queryCondition = new StringBuffer();
		queryCondition.append("/adverts/categories/category[position()>=");
		queryCondition.append(startPosition);
		queryCondition.append(" and position()<=");
		queryCondition.append(endPosition);
		queryCondition.append("]");

		List<Node> categoriesNodeList = rootElement.selectNodes(queryCondition.toString());
		// Category 与模板对象的实体类
		CategoryEncapsulationBean categoryEncapsulationBean = null;
		// 对应返回的结果集
		if (categoriesNodeList != null && categoriesNodeList.size() > 0) {
			if (categoryEncapsulationBeanList == null) {
				categoryEncapsulationBeanList = new ArrayList<CategoryEncapsulationBean>();
			}
			for (Node node2 : categoriesNodeList) {
				Element categoryElement = (Element) node2;
				List categoryAttributeList = categoryElement.attributes();
				categoryEncapsulationBean = new CategoryEncapsulationBean();
				if (categoryAttributeList != null&& categoryAttributeList.size() > 0) {
					for (Iterator iterator = categoryAttributeList.iterator(); iterator.hasNext();) {
						Attribute categoryAttribute = (Attribute) iterator.next();
						if ("id".equals(categoryAttribute.getName())) {
							categoryEncapsulationBean.setId(Integer.parseInt(categoryAttribute.getText()));
						} else if ("categoryId".equals(categoryAttribute.getName())) {
							categoryEncapsulationBean.setCategoryId(categoryAttribute.getText());
						} else if ("categoryName".equals(categoryAttribute.getName())) {
							categoryEncapsulationBean.setCategoryName(categoryAttribute.getText());
						} else if (checkColumn(categoryAttribute.getText())&& "networkId".equals(categoryAttribute.getName())) {
							categoryEncapsulationBean.setNetworkId(Integer.parseInt(categoryAttribute.getText()));
						} else if (checkColumn(categoryAttribute.getText())&& "userLevel".equals(categoryAttribute.getName())) {
							categoryEncapsulationBean.setUserLevel(Integer.parseInt(categoryAttribute.getText()));
						} else if (checkColumn(categoryAttribute.getText())&& "industryType".equals(categoryAttribute.getName())) {
							categoryEncapsulationBean.setIndustryType(Integer.parseInt(categoryAttribute.getText()));
						} else if (checkColumn(categoryAttribute.getText())&& "type".equals(categoryAttribute.getName())) {
							categoryEncapsulationBean.setType(Integer.parseInt(categoryAttribute.getText()));
						} else if (checkColumn(categoryAttribute.getText())&& "templateId".equals(categoryAttribute.getName())) {
							Integer templateId = Integer.parseInt(categoryAttribute.getText());
							categoryEncapsulationBean.setTemplateId(templateId);
						} else if (checkColumn(categoryAttribute.getText())&& "treeId".equals(categoryAttribute.getName())) {
							Integer treeId = Integer.parseInt(categoryAttribute.getText());
							categoryEncapsulationBean.setTreeId(treeId);
						} else if ("treeName".equals(categoryAttribute.getName())) {
							categoryEncapsulationBean.setTreeName(categoryAttribute.getText());
						} else if("modifyTime".equals(categoryAttribute.getName())){
							categoryEncapsulationBean.setModifyTime(categoryAttribute.getText());
						}
					}
				}
				categoryEncapsulationBeanList.add(categoryEncapsulationBean);
			}
		}
		return categoryEncapsulationBeanList;
	}
	
	/**
	 * 
	 * 同步操作前，得到广告位中，类型为 portal 的所有记录
	 * @return
	 */
	public List<AdvertPosition> getSynchronousBeforeAdvertPositionListFromDB(){
		
		List<AdvertPosition> listAdvertPosition = new ArrayList<AdvertPosition>();
		
		List<AdvertPosition> listBeforeAdvertPosition = new ArrayList<AdvertPosition>();
		
		
	/*	Map conditionMap = new HashMap();
		String str = "PORTAL";
		conditionMap.put("PORTAL",str);
		
		advertPositionService.find(conditionMap);*/
		
		//在同步操作前，得到广告位表单中的投放方式为"PORTAL"类型的记录
		listAdvertPosition = cpsPositionDao.getAdvertPositionByAdvertiseWay();
		
		if(listAdvertPosition != null && listAdvertPosition.size()>0){
			for(AdvertPosition advertPosition : listAdvertPosition){
				listBeforeAdvertPosition.add(advertPosition);
			}
		}
		return listBeforeAdvertPosition;
	}
	
	
	/**
	 * 同步操作前 得到投放节点的所有记录，把这些都放在Map中
	 * @return
	 */
	
	public Map<Integer,PlayCategory>  getSynchronousBeforePlayCategoryMapFromDB(){
		Map<Integer,PlayCategory> advertPositionMapFromDB = new HashMap<Integer, PlayCategory>();
		List<PlayCategory> listPlayCategory = new ArrayList<PlayCategory>();
		listPlayCategory = playCategoryDao.getAllPlayCategory();
		if(listPlayCategory != null && listPlayCategory.size()>0){
			for(PlayCategory playCategory : listPlayCategory){
				advertPositionMapFromDB.put(playCategory.compareValue(), playCategory);
			}
		}
		return advertPositionMapFromDB;
	}
	
	
	/*
	 * 同步操作前 得到广告位中，类型为 portal 的所有记录，把这些都放在Map中
	 * @return
	 */
	public Map<Integer,AdvertPosition> getSynchronousBeforeAdvertPositionMapFromDB(){
		
		Map<Integer,AdvertPosition> advertPositionMapFromDB = new HashMap<Integer, AdvertPosition>();
		
		List<AdvertPosition> listAdvertPosition = new ArrayList<AdvertPosition>();
		
		//在同步操作前，得到广告位表单中的投放方式为"PORTAL"类型的记录
		listAdvertPosition = cpsPositionDao.getAdvertPositionByAdvertiseWay();
		
		if(listAdvertPosition != null && listAdvertPosition.size() > 0){
			for(AdvertPosition advertPosition:listAdvertPosition){
		    //将value 存为 实体
				advertPositionMapFromDB.put(advertPosition.compareHashCode(), advertPosition);
//另一种方法，将value 存为 实体的id
			}
		}
		return advertPositionMapFromDB;
	}
	
	/**
	 * 计算出分几次执行, 得出结果如下： {1=1;2, 2=3;4, 3=5;6, 4=7;8, 5=9;10, 6=11;12, 7=13;14,
	 * 8=15;16, 9=17;18, 10=19;20, 11=21;22, 12=23;24} key 第几次流程 value 1 开始
	 * value 2 结束
	 */
	private Map<Integer, String> calculateEveryTimeExecuteRange(
			Double categorySize) {

		Map<Integer, String> map = new HashMap<Integer, String>();
		Integer page = null;
		String categorySizeBefore = String.valueOf(categorySize);
		String categorySizeAfter = categorySizeBefore.substring(0,categorySizeBefore.indexOf("."));
		Integer categoriesNodeSizeInt = Integer.parseInt(categorySizeAfter);

		if (categoriesNodeSizeInt % CPSConstant.PARSE_SIZE == 0) {
			// TwowayConstant.PARSE_SIZE 为 2，即 每次解析50条数据
			// 计算 一共需要多少
			page = categoriesNodeSizeInt / CPSConstant.PARSE_SIZE;
		} else {
			//
			page = (categoriesNodeSizeInt / CPSConstant.PARSE_SIZE) + 1;
		}

		for (int i = 1; i <= page; i++) {
			// 得出结果如下
			// {1=1;2, 2=3;4}
			map.put(i, ((i - 1) * CPSConstant.PARSE_SIZE + 1) + ";"
					+ (i * CPSConstant.PARSE_SIZE));
		}
		return map;

	}

	public AdvertPositionDao getAdvertPositionDao() {
		return advertPositionDao;
	}

	public void setAdvertPositionDao(AdvertPositionDao advertPositionDao) {
		this.advertPositionDao = advertPositionDao;
	}

	/**
	 * 格式检查，判断是否为数字
	 * 
	 * @param text
	 * @return
	 */
	private static boolean checkColumn(String text) {
		boolean flag = false;

		if (StringUtils.isNotBlank(text) && text.matches("^[0-9]\\d*$")) {
			flag = true;
		}
		return flag;
	}

	@Override
	public int[] saveAdvertPositionList(List<AdvertPosition> advertPosition) {
		return cpsPositionDao.saveAdvertPositionTemplateBatch(advertPosition);
	}
	@Override
	public int[] updateAdvertPositionList(List<AdvertPosition> advertPosition) {
		return cpsPositionDao.updateAdvertPositionTemplateBatch(advertPosition,CPSConstant.CATEGORY_TEMPLATE_STATE_MODIFY);
	}

	@Override
	public int[] savePlayCategoryList(List<PlayCategory> playCategory) {
		return cpsPositionDao.savePlayCategoryTemplateBatch(playCategory);
	}

	@Override
	public int[] updatePlayCategoryList(List<PlayCategory> playCategory) {
		return cpsPositionDao.updatePlayCategoryTemplateBatch(playCategory);
	}

	@Override
	public List<AdvertPosition> getAdvertPositionIdByEigenValue(String eigenValue) {
		return cpsPositionDao.getAdvertPositionIdByEigenValue(eigenValue);
	}

	@Override
	public String savePortalPlayCategory(PortalPlayCategory portalPlayCategory) {
		return null;
	}

	public List<AdvertPosition> getSynchronous_before_advertPosition() {
		return synchronous_before_advertPosition;
	}

	public void setSynchronous_before_advertPosition(
			List<AdvertPosition> synchronous_before_advertPosition) {
		this.synchronous_before_advertPosition = synchronous_before_advertPosition;
	}

	public Map<Integer, AdvertPosition> getSynchronous_before_advertPositionMap() {
		return synchronous_before_advertPositionMap;
	}

	public void setSynchronous_before_advertPositionMap(
			Map<Integer, AdvertPosition> synchronous_before_advertPositionMap) {
		this.synchronous_before_advertPositionMap = synchronous_before_advertPositionMap;
	}

	public PlayCategoryDao getPlayCategoryDao() {
		return playCategoryDao;
	}

	public void setPlayCategoryDao(PlayCategoryDao playCategoryDao) {
		this.playCategoryDao = playCategoryDao;
	}

	public Map<Integer, PlayCategory> getSynchronous_before_playCategoryMap() {
		return synchronous_before_playCategoryMap;
	}

	public void setSynchronous_before_playCategoryMap(Map<Integer, PlayCategory> synchronous_before_playCategoryMap) {
		this.synchronous_before_playCategoryMap = synchronous_before_playCategoryMap;
	}
	
	public List<CategoryBean> getCategoryBeanList() {
		return categoryBeanList; 
	}

	public void setCategoryBeanList(List<CategoryBean> categoryBeanList) {
		this.categoryBeanList = categoryBeanList;
	}

	public CPSPositionDao getCpsPositionDao() {
		return cpsPositionDao;
	}

	public void setCpsPositionDao(CPSPositionDao cpsPositionDao) {
		this.cpsPositionDao = cpsPositionDao;
	}

	public AdvertPositionTypeDao getAdvertPositionTypeDao() {
		return advertPositionTypeDao;
	}

	public void setAdvertPositionTypeDao(AdvertPositionTypeDao advertPositionTypeDao) {
		this.advertPositionTypeDao = advertPositionTypeDao;
	}
	
}



