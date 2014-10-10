package com.dvnchina.advertDelivery.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.dvnchina.advertDelivery.dao.AdAssetsDao;
import com.dvnchina.advertDelivery.model.AdvertPositionType;
import com.dvnchina.advertDelivery.model.ImageMeta;
import com.dvnchina.advertDelivery.model.ImageReal;
import com.dvnchina.advertDelivery.model.MessageMeta;
import com.dvnchina.advertDelivery.model.MessageReal;
import com.dvnchina.advertDelivery.model.Resource;
import com.dvnchina.advertDelivery.model.ResourceReal;
import com.dvnchina.advertDelivery.model.UserCustomer;
import com.dvnchina.advertDelivery.model.VideoMeta;
import com.dvnchina.advertDelivery.model.VideoReal;
import com.dvnchina.advertDelivery.utils.HibernateSQLTemplete;

public class AdAssetsDaoImpl extends HibernateSQLTemplete implements AdAssetsDao,ServletRequestAware {
	
	private Logger logger = Logger.getLogger(AdAssetsDaoImpl.class);
	private HttpServletRequest request;
	
	/**
	 * 运营商查询结果集
	 */
	public List listAdAssestsMgrReal(ResourceReal resourceReal,int x,int y ,String state){
		logger.debug("-------listAdAssestsMgr  start------------");
		
		Map<String,Object> map = new HashMap<String, Object>();
		List<ResourceReal>  listResourceReal = null;
		String hql = "from ResourceReal where 1=1";
		hql += fillCondReal(map, resourceReal, state);
		listResourceReal = this.findByHQL(hql, map, x,y);
		
		return listResourceReal;
	}
	
	/**
	 * 查询结果集
	 */
	public List listAdAssestsMgr(Resource resource,int x,int y ,String state,List<Integer> cIds){
		logger.debug("-------listAdAssestsMgr  start------------");
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		List<Resource>  listResource = null;
		
		String hql = "from Resource where 1=1";
		
		hql += fillCond(map, resource, state,cIds);
		
		listResource = this.findByHQL(hql, map, x,y);
		return listResource;
	}
	
	/**
	 * 添加文字消息素材
	 * 
	 */
	public void insertMessageMetaInfo(MessageMeta messageMeta){
		logger.debug("------insertMessageMetaInfo()-----start----");
		if(messageMeta != null){
			this.getHibernateTemplate().saveOrUpdate(messageMeta);
		}
		logger.debug("------insertMessageMetaInfo()-----end----");
	}
	
	/**
	 * 添加视频素材
	 * 
	 */
	public void insertVideoMetaInfo(VideoMeta videoMeta) {
		logger.debug("------insertVideoMetaInfo()-----start----");
		if(videoMeta != null){
			this.getHibernateTemplate().saveOrUpdate(videoMeta);
		}
		
		logger.debug("------insertVideoMetaInfo()-----end----");
	}
	
	/**
	 * 添加图片素材
	 */
	public void insertImagMetaInfo(ImageMeta imageMeta) {
		logger.debug("------insertImagetaInfo()-----start----");
		if(imageMeta != null){
			this.getHibernateTemplate().saveOrUpdate(imageMeta);
		}
		logger.debug("------insertImagetaInfo()-----end----");
	}
	
	/**
	 * 删除资源对象
	 * 
	 */
	public void deleteResource(Resource resource){
		logger.debug("------deleteResource()-----start----");
		if(resource != null){
			this.getHibernateTemplate().delete(resource);
		}
		logger.debug("------deleteImageMeta()-----end----");
	}
	
	/**
	 *删除图片素材
	 */
	public void deleteImageMeta(ImageMeta imageMeta) {
		logger.debug("------deleteImageMeta()-----start----");
		if(imageMeta != null){
			this.getHibernateTemplate().delete(imageMeta);
		}
		logger.debug("------deleteImageMeta()-----end----");
	}
	/**
	 *删除视频素材 
	 */
	public void deleteVideoMeta(VideoMeta videoMeta) {
		logger.debug("------deleteVideoMeta()-----start----");
		if(videoMeta != null){
			this.getHibernateTemplate().delete(videoMeta);
		}
		logger.debug("------deleteVideoMeta()-----end----");
	}
	/**
	 *删除文字素材 
	 */
	public void deleteMessageMeta(MessageMeta messageMeta) {
		logger.debug("------deleteMessageMeta()-----start----");
		if(messageMeta !=null){
			this.getHibernateTemplate().delete(messageMeta);
		}
		logger.debug("------deleteMessageMeta()-----end----");
	}
	/**
	 * 获得资源
	 * 
	 */
	public Resource getResourceById(Integer id){
		System.out.println("----id----"+id);
		return this.getHibernateTemplate().get(Resource.class, id);
		
	}
	
	/**
	 * 获得图片素材
	 */
    public ImageMeta getImageMetaById(Integer id) {
		
    	logger.debug("-----getImageMetaById start------");
    	
    	return this.getHibernateTemplate().get(ImageMeta.class, id);
    	
	}
    
	/**
	 * 获得视频素材
	 */
	public VideoMeta getVideoMetaById(Integer id) {
		
		logger.debug("----getVideoMetaById start-----");
		
		return this.getHibernateTemplate().get(VideoMeta.class, id);
		
	}
	
	/**
	 * 获得文字素材
	 */
	public MessageMeta getMessageMetaById(Integer id) {
		
		logger.debug("getMessageMetaById start");
		return this.getHibernateTemplate().get(MessageMeta.class, id);
		
	}
	
	/**
	 * 删除运行期资源对象(Real 表)
	 * 
	 */
	public void deleteResourceReal(ResourceReal resourceReal){
		logger.debug("------deleteResourceReal()-----start----");
		if(resourceReal != null){
			this.getHibernateTemplate().delete(resourceReal);
		}
		logger.debug("------deleteResourceReal()-----end----");
	}
	
	/**
	 *删除运行期图片素材(Real 表)
	 */
	public void deleteImageMetaReal(ImageReal imageReal) {
		logger.debug("------deleteImageMetaReal()-----start----");
		if(imageReal != null){
			this.getHibernateTemplate().delete(imageReal);
		}
		logger.debug("------deleteImageMetaReal()-----end----");
	}
	
	/**
	 *删除运行期文字素材(Real 表) 
	 */
	public void deleteMessageMetaReal(MessageReal messageReal) {
		logger.debug("------deleteMessageRealMeta()-----start----");
		if(messageReal !=null){
			this.getHibernateTemplate().delete(messageReal);
		}
		logger.debug("------deleteMessageRealMeta()-----end----");
	}
	
	/**
	 *删除运行期视频素材(Real 表)
	 */
	public void deleteVideoMetaReal(VideoReal videoReal) {
		logger.debug("------deleteVideoMeta()-----start----");
		if(videoReal != null){
			this.getHibernateTemplate().delete(videoReal);
		}
		logger.debug("------deleteVideoMeta()-----end----");
	}
	
	/**
	 * 获得运行期资源(Real 表)
	 * 
	 */
	public ResourceReal getResourceRealById(Integer id){
		logger.debug("----id----"+id);
		return this.getHibernateTemplate().get(ResourceReal.class, id);
		
	}
	
	/**
	 * 获得运行期图片素材(Real 表)
	 */
    public ImageReal getImageMetaRealById(Integer id) {
    	logger.debug("-----getImageMetaRealById start------");
    	return this.getHibernateTemplate().get(ImageReal.class, id);
	}
    
	/**
	 * 获得运行期视频素材(Real 表)
	 */
	public VideoReal getVideoMetaRealById(Integer id) {
		logger.debug("----getVideoMetaRealById start-----");
		return this.getHibernateTemplate().get(VideoReal.class, id);
	}
	
	/**
	 * 获得运行期文字素材(Real 表)
	 */
	public MessageReal getMessageMetaRealById(Integer id) {
		logger.debug("----getMessageMetaRealById start----");
		return this.getHibernateTemplate().get(MessageReal.class, id);
	}
	
	/**
	 * 提交修改的资源信息
	 * 
	 */
	public void updateResource(Resource resource){
		logger.debug("-----updateImageMeta() start-----");
		if(resource != null){
			this.getHibernateTemplate().update(resource);
		}else{
			logger.info("---Resource 为空----");
			
		}
		logger.debug("----updateResource() end-----");
	}
	
	public void updateResourceReal(ResourceReal resourceReal){
		
		logger.debug("-----updateResourceReal() start-----");
		if(resourceReal != null){
			this.getHibernateTemplate().update(resourceReal);
		}else{
			logger.info("---resourceReal 为空----");
			
		}
		logger.debug("----resourceReal() end-----");
		
	}
	
	
	
	/**
	 * 提交修改图片信息
	 */
	public void updateImageMeta(ImageMeta imageMeta) {
		logger.debug("----updateImageMeta() start------");
		if(imageMeta != null){
			this.getHibernateTemplate().update(imageMeta);
		}else{
			logger.info("imageMeta 为空");
		}
		
		logger.debug("----updateImageMeta() end-----");
	}
	
	/**
	 * 提交修改视频信息
	 */
	public void updateVideoMeta(VideoMeta videoMeta) {
		logger.debug("updateVideoMeta() start");
		
		if(videoMeta != null){
			this.getHibernateTemplate().update(videoMeta);
		}else{
			logger.info("updateVideoMeta 为空");
		}
		logger.debug("updateVideoMeta() end");
		
	}

	/**
	 * 提交修改文字信息
	 */
	public void updateMessageMeta(MessageMeta messageMeta) {
		logger.debug("updateMessageMeta() start");
		
		if(messageMeta != null){
			this.getHibernateTemplate().update(messageMeta);
		}else{
			logger.info("messageMeta 为空");
		}
		
		logger.debug("updateMessageMeta() end");
		
	}
	
	/**
	 * 添加通过审核通过的资产素材，往T_RESOURCE_REAL 表中添加
	 * 
	 */
	public void insertAuditResourceReal(ResourceReal resourceReal){
		logger.debug("------insertAuditResourceReal()-----start----");
		Serializable id = null;
		if(resourceReal !=null && !"".equals(resourceReal)){
			this.getHibernateTemplate().save(resourceReal);
		}else{
			logger.debug("----resourceReal 为空-----");
		}
		logger.debug("------insertAuditResourceReal()-----end----");
	}
	
	

	/**
	 *添加通过审核的图片素材 往T_IMAGE_META_REAL表中添加
	 */
	public void insertAuditImageReal(ImageReal imageReal) {
		logger.debug("------insertAuditImageMeta()-----start----");
		if(imageReal !=null && !"".equals(imageReal)){
			//this.getHibernateTemplate().saveOrUpdate(imageReal);
			this.getHibernateTemplate().save(imageReal);
			
		}else{
			logger.debug("imageReal 为空");
		}
		logger.debug("------insertAuditImageMeta()-----end----");
	}
	
	/**
	 *添加通过审核的视频素材 往T_VIDEO_META_REAL表中添加
	 */
	public void insertAuditVideoReal(VideoReal videoReal) {
		logger.debug("------insertAuditVideoReal()-----start----");
		if(videoReal !=null && !"".equals(videoReal)){
			this.getHibernateTemplate().save(videoReal);
		}else{
			logger.debug("imageReal 为空");
		}
		logger.debug("------insertAuditVideoReal()-----end----");
	}

	/**
	 *添加通过审核的文字素材 往T_WRITING_MESSAGE表中添加
	 */
	public void insertAuditMessageReal(MessageReal messageReal) {
		logger.debug("------insertAuditMessageMeta()-----start----");
		if(messageReal !=null && !"".equals(messageReal)){
			//this.getHibernateTemplate().saveOrUpdate(messageReal);
			this.getHibernateTemplate().save(messageReal);
		}else{
			logger.debug("imageReal 为空");
		}
		logger.debug("------insertAuditMessageMeta()-----end----");
	}
	
	
	/**
	 * 广告商查询结果集条件
	 */
	private String fillCondReal(Map<String, Object> map,ResourceReal resourceReal,String state) {
		
		logger.debug("------fillCond()-----start----");
		StringBuffer sb = new StringBuffer("");
		
		//广告位id
		if(resourceReal.getAdvertPositionId() != null){
			map.put("advertPositionId",resourceReal.getAdvertPositionId());
			sb.append(" AND advertPositionId =:advertPositionId");
		}
		
		//审核状态
		/*if(resourceReal.getState() != null && !"".equals(String.valueOf(resourceReal.getState())) && !"10".charAt(0).equals(resourceReal.getState()){
			map.put("state",resourceReal.getState());
			sb.append(" AND state =:state");
			
			
			String str = String.valueOf(resourceReal.getState());
			
			stateStr
		}*/
		
		
		if(resourceReal.getStateStr() != null && !"".equals(resourceReal.getStateStr()) && !"10".equals(resourceReal.getStateStr())){
			map.put("state",resourceReal.getStateStr().charAt(0));
			sb.append(" AND state =:state");
		}
		
		//资产名称
		if(resourceReal.getResourceName() != null && !"".equals(resourceReal.getResourceName()) ){
			map.put("resourceName","%" + resourceReal.getResourceName()+ "%");
			sb.append(" AND resourceName like:resourceName");
		}
		//资产类型
		if(resourceReal.getResourceType() != null && !"".equals(String.valueOf(resourceReal.getResourceType())) && !"10".equals(String.valueOf(resourceReal.getResourceType()))){
			map.put("resourceType",resourceReal.getResourceType());
			sb.append(" AND resourceType =:resourceType");
		}
		//所属内容分类
		if(resourceReal.getCategoryId() != null && !"".equals(String.valueOf(resourceReal.getCategoryId())) && !"10".equals(String.valueOf(resourceReal.getCategoryId()))){
			map.put("category",resourceReal.getCategoryId());
			sb.append(" AND category =:category");
		}
		//资产描述
		if(resourceReal.getResourceDesc() != null && !"".equals(resourceReal.getResourceDesc()) ){
			map.put("resourceDesc","%" + resourceReal.getResourceDesc()+ "%");
			sb.append(" AND resourceDesc like:resourceDesc");
		}
		//所属合同号
		if(resourceReal.getContractNumberStr() != null && !"".equals(resourceReal.getContractNumberStr()) ){
			map.put("contractNumber", Integer.parseInt(resourceReal.getContractNumberStr()));
			sb.append(" AND contractNumber =:contractNumber");
		}
		
		if(resourceReal.getMetaState() != null && !"".equals(resourceReal.getMetaState()) && !"10".equals(resourceReal.getMetaState())){
			map.put("state",resourceReal.getMetaState().charAt(0));
			sb.append(" AND state=:state");
		}
		
		//创建时间 前
		if(resourceReal.getCreateTimeA() != null && !"".equals(resourceReal.getCreateTimeA()) ){
			map.put("createTimeA",resourceReal.getCreateTimeA());
			sb.append(" AND createTime >=:createTimeA");
		}
		//创建时间 后
		if(resourceReal.getCreateTimeB() != null && !"".equals(resourceReal.getCreateTimeB()) ){
			map.put("createTimeB",resourceReal.getCreateTimeB());
			sb.append(" AND createTime <=:createTimeB");
		}
		
		// 过滤 提交审核 信息
		if(state != null && !"".equals(state)){
			sb.append(" AND state='0'");
		}
		//特别注意，这里需要在order 前加一个 空格，否则就连在了一起，会报错。
		sb.append(" order by createTime desc ");
		
		return sb.toString();
	}
	
	
	/**
	 * 获得查询结果集条件
	 */
	private String fillCond(Map<String, Object> map,Resource resource,String state,List<Integer> cIds) {
	    logger.debug("------fillCond()-----start----");
		StringBuffer sb = new StringBuffer("");
		
//		String stateStr = this.request.getParameter("state"); 
		
		Integer userId = resource.getUserId();
		
		//广告位id
		if(resource.getAdvertPositionId() != null){
			map.put("advertPositionId",resource.getAdvertPositionId());
			sb.append(" AND advertPositionId =:advertPositionId");
		}
		
		//资产名称
		if(resource.getResourceName() != null && !"".equals(resource.getResourceName()) ){
			map.put("resourceName","%" + resource.getResourceName()+ "%");
			sb.append(" AND resourceName like:resourceName");
		}
		//资产类型
		if(resource.getResourceType() != null && !"".equals(String.valueOf(resource.getResourceType())) && !"10".equals(String.valueOf(resource.getResourceType()))){
			map.put("resourceType",resource.getResourceType());
			sb.append(" AND resourceType =:resourceType");
		}
		//所属内容分类
		if(resource.getCategoryId() != null && !"".equals(String.valueOf(resource.getCategoryId())) && !"10".equals(String.valueOf(resource.getCategoryId()))){
			map.put("category",resource.getCategoryId());
			sb.append(" AND category =:category");
		}
		//资产描述
		if(resource.getResourceDesc() != null && !"".equals(resource.getResourceDesc()) ){
			map.put("resourceDesc","%" + resource.getResourceDesc()+ "%");
			sb.append(" AND resourceDesc like:resourceDesc");
		}
		//所属合同号
		if(resource.getContractId() != null ){
			map.put("contractNumber", resource.getContractId());
			sb.append(" AND contractNumber =:contractNumber");
		}
		
		//素材状态类型
		/*if(resource.getState()!= null && !"".equals(String.valueOf(resource.getState())) && !"10".equals(String.valueOf(resource.getState()))){
			map.put("state",resource.getState());
			sb.append(" AND state=:state");
 		}*/
		
		if(resource.getMetaState() != null && !"".equals(resource.getMetaState()) && !"10".equals(resource.getMetaState())){
			map.put("state",resource.getMetaState().charAt(0));
			sb.append(" AND state=:state");
		}
		
		//创建时间 前
		if(resource.getCreateTimeA() != null && !"".equals(resource.getCreateTimeA()) ){
			map.put("createTimeA",resource.getCreateTimeA());
			sb.append(" AND createTime >=:createTimeA");
		}
		//创建时间 后
		if(resource.getCreateTimeB() != null && !"".equals(resource.getCreateTimeB()) ){
			map.put("createTimeB",resource.getCreateTimeB());
			sb.append(" AND createTime <=:createTimeB");
		}
		
		System.out.println("state="+state);
		
		if(state != null && !"".equals(state) && state.equals("0")){
			sb.append(" AND state='0'");
		}
		
		/*if(StringUtils.isNotBlank(stateStr)){
			sb.append(" AND state='0'");
		}*/
		
		// 过滤 提交审核 和 删除待审核状态的记录
		//审核状态
		if(state != null && !"".equals(state) && state.equals("1")){
			//上线
			if("2".equals(resource.getStateStr()) && resource.getStateStr() != null && !"".equals(resource.getStateStr()) && !"10".equals(resource.getStateStr()) ){
				sb.append(" AND state='2'");
			}else if("3".equals(resource.getStateStr()) && resource.getStateStr() != null && !"".equals(resource.getStateStr()) && !"10".equals(resource.getStateStr()) ){
				sb.append(" AND state='3'");
			}else{
				sb.append(" AND state='2'");
				sb.append(" OR state='3'");
			}
		}
		
		if (userId == 0) {
			System.out.println("##########超级管理员#####");
		}else{
			//过滤属于绑定了特定素材的广告商
			if(cIds != null && cIds.size()>0){
				sb.append(" and customerId in(");
				for (int i = 0; i < cIds.size(); i++) {
					if (i > 0) {
						sb.append(",");
					}
					sb.append(cIds.get(i));
				}
				sb.append(")");
			}
		}
		
		//特别注意，这里需要在order 前加一个 空格，否则就连在了一起，会报错。
		sb.append(" order by createTime desc ");
		return sb.toString();
	}
	
	/**
	 * 运营商查询总的记录数
	 */
	
	public int getAdContentCountReal(ResourceReal resourceReal,String state){
		logger.debug("-----getAdContentCountReal  start------");
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		int count = 0;
		
		String hql = "select count(*) from ResourceReal where 1=1";
		hql += fillCondReal(map,resourceReal,state);
		count = this.getTotalByHQL(hql, map);
		
		if(count != 0){
			System.out.println(count);
		}
		return count;
	}
	
	/**
	 * 查询总的记录数
	 */
	public int getAdContentCount(Resource resource,String state,List<Integer> cIds){
		logger.debug("-----getAdContentCount  start------");
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		int count = 0;
		
		String hql = "select count(*) from Resource where 1=1";
		hql += fillCond(map,resource,state,cIds);
		count = this.getTotalByHQL(hql, map);
		
		if(count != 0){
			System.out.println(count);
		}
		return count;
	}

	@Override
	public AdvertPositionType getAdvertPositionTypeById(Integer id) {
		
		List<AdvertPositionType> list = null;
		AdvertPositionType advertPositionType = null;
		
		list = (List<AdvertPositionType>) this.getHibernateTemplate().get(AdvertPositionType.class,id);
		
		if(list != null && list.size()>0){
			for(int i=0;i<list.size();i++){
				advertPositionType = list.get(0);
			}
		}else{
			advertPositionType = null;
		}
	    return advertPositionType;
	}

	@Override
	public Resource getResourceByAdvertPositionId(Integer advertPositionId) {
		
		Resource resource=null;
		
		String sql = "from Resource where advertPositionId = " + advertPositionId;
		
		List<Resource>  listResource = new ArrayList<Resource>();
		
		listResource = this.getHibernateTemplate().find(sql);
		
		if(listResource.size() == 0){
			return null;
		}else{
			for(int i=0;i<listResource.size();i++){
				resource = listResource.get(0);
			}
		}
		return resource;
	}

	@Override
	public ResourceReal getResourceRealByAdvertPositionId(Integer advertPositionId) {
		
		ResourceReal resourceReal=null;
		
		String sql = "from ResourceReal where advertPositionId = " + advertPositionId;
		
		List<ResourceReal>  listResourceReal = new ArrayList<ResourceReal>();
		
		listResourceReal = this.getHibernateTemplate().find(sql);
		
		if(listResourceReal.size() == 0){
			return null;
		}else{
			for(int i=0;i<listResourceReal.size();i++){
				resourceReal = listResourceReal.get(0);
			}
		}
		return resourceReal;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public int deleteResourceAbatch(Integer id, Integer resourceType) {
		return 0;
	}

}


