package com.dvnchina.advertDelivery.position.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
import com.dvnchina.advertDelivery.model.ResourceReal;
import com.dvnchina.advertDelivery.position.bean.AdvertPosition;
import com.dvnchina.advertDelivery.position.bean.DefaulResourceAD;
import com.dvnchina.advertDelivery.position.bean.PositionPackage;
import com.dvnchina.advertDelivery.position.dao.DefaulResourceDao;

public class DefaulResourceDaoImpl extends BaseDaoImpl implements DefaulResourceDao{
	
	/**
	 * 分页查询默认素材信息
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageBeanDB queryResourceList(int pageNo, int pageSize){
		String hql = "from PositionPackage order by id";
		
		String resourceSql  = "select r.resource_id,r.resource_name,r.resource_type,a.position_name " +
				"from t_resource r,t_resource_ad ra, t_advertposition a " +
				"where r.id = ra.resource_id and ra.ad_id = a.id and ra.position_package_id = a.position_package_id " +
				"and a.position_package_id = ? order by a.id";
		String videoSql = "select formal_file_path from t_video_meta where id = ?";
		String imageSql = "select formal_file_path from t_image_meta where id = ?";
		String textSql = "select formal_file_path from t_video_meta where id = ?";
		String questionnaireSql = "select formal_file_path from t_video_meta where id = ?";
		
		int rowcount = this.getTotalCountHQL(hql, null);
		PageBeanDB page = new PageBeanDB();
		if (pageNo==0){
			pageNo =1;
		}		
		page.setPageSize(pageSize);
		page.setCount(rowcount);
		int totalPage = (rowcount - 1) / pageSize + 1;
		
		
		if (rowcount == 0) {
			pageNo = 1;
			totalPage = 0;
		}
		if (pageNo <= 0) {
			pageNo = 1;
		} else if (pageNo > totalPage) {
			pageNo = totalPage;
		}

		page.setTotalPage(totalPage);
		page.setPageNo(pageNo);
		List<PositionPackage> list = (List<PositionPackage>)this.getListForPage(hql, null, pageNo, pageSize);
		List<DefaulResourceAD> resourceADList = new ArrayList<DefaulResourceAD>();
		for(PositionPackage pp : list){
			DefaulResourceAD resourceAD = new DefaulResourceAD();
			resourceAD.setPositionPackageId(pp.getId());
			resourceAD.setPositionPackageName(pp.getPositionPackageName());
			resourceAD.setPositionPackageType(pp.getPositionPackageType());
			String resourceName = "";
			List<Object[]> resourceList = this.getDataBySql(resourceSql, new Object[]{pp.getId()});
			if(resourceList != null && resourceList.size()>0){
				for (Object[] objects : resourceList) {
//					String resourcePath = (String)objects[0];
//					resourcePath = resourcePath.substring(resourcePath.lastIndexOf(File.separator)+1,resourcePath.length());
					resourceName = resourceName+(String)objects[1]+"("+(String)objects[3]+"),";
	            }
			}
			if(resourceName.length()>0){
				resourceName = resourceName.substring(0,resourceName.length()-1);
			}
			resourceAD.setResourceName(resourceName);
			resourceADList.add(resourceAD);
		}
		page.setDataList(resourceADList);
		return page;
	}
	
	/**
	 * 分页查询默认素材信息
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryDefResourceList(int pageNo, int pageSize){
		String hql = "from ResourceReal where isDefault = 1 order by id";
		
		
		int rowcount = this.getTotalCountHQL(hql, null);
		PageBeanDB page = new PageBeanDB();
		if (pageNo==0){
			pageNo =1;
		}		
		page.setPageSize(pageSize);
		page.setCount(rowcount);
		int totalPage = (rowcount - 1) / pageSize + 1;
		
		
		if (rowcount == 0) {
			pageNo = 1;
			totalPage = 0;
		}
		if (pageNo <= 0) {
			pageNo = 1;
		} else if (pageNo > totalPage) {
			pageNo = totalPage;
		}

		page.setTotalPage(totalPage);
		page.setPageNo(pageNo);
		List<ResourceReal> list = (List<ResourceReal>)this.getListForPage(hql, null, pageNo, pageSize);
		page.setDataList(list);
		return page;
	}
	
	/**
	 * 根据子广告位ID查询默认素材
	 * @param adId
	 */
	public List<ResourceReal> queryDefResourceListByAdId(Integer adId){
		String hql = "from ResourceReal where isDefault = 1 and advertPositionId = ? order by id";
		
		return this.list(hql, new Object[]{adId});
	}
	
	/**
	 * 根据素材ID获取素材信息
	 * @param ids
	 */
	public List<ResourceReal> findResourceListByIds(String ids){
		String hql = "from ResourceReal where id in ("+ids+")";
		
		return this.list(hql, null);
	}
	
	/**
	 * 根据子广告位ID查询已经设置默认素材
	 * @param adId
	 */
	public List<DefaulResourceAD> queryDefResourceADListByAdId(Integer adId){
		String hql = "from DefaulResourceAD where adId = ? ";
		
		return this.list(hql, new Object[]{adId});
	}
	
	/**
	 * 根据广告位包ID查询子广告位列表
	 * @param positionPackageId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AdvertPosition> queryADList(Integer positionPackageId){
		String hql = "from AdvertPosition where positionPackageId = ? order by id";
		return this.list(hql, new Object[]{positionPackageId});
	}
	
	/**
	 * 根据子广告位ID获取默认素材
	 * @param adId
	 * @return
	 */
	public List<ResourceReal> getDefResourceByAdId(Integer adId){
		String sql = "select r.id,r.resource_name from t_resource_ad ra,t_resource r where ra.resource_id = r.id and ra.ad_id = ?";
		List<ResourceReal> resourceList = getDefResourceList(this.getDataBySql(sql, new Object[]{adId}));
		return resourceList;
	}
	
	private List<ResourceReal> getDefResourceList(List<?> resultList) {
		List<ResourceReal> list = new ArrayList<ResourceReal>();
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			ResourceReal tmp = new ResourceReal();
			tmp.setId(toInteger(obj[0]));
			tmp.setResourceName((String)obj[1]);
			list.add(tmp);
		}
		return list;
	}
	
	/**
	 * 根据子广告位ID删除默认素材
	 * @param adId
	 */
	public void delResourceADByAdId(Integer adId){
		String hql = "delete DefaulResourceAD where adId = ?";
		this.executeByHQL(hql,new Object[]{adId});
	}
	
	/**
	 * 保存子广告位的默认素材
	 * @param raList
	 */
	public void saveDefResource(List<DefaulResourceAD> raList){
		this.saveAll(raList);
	}
	

}
