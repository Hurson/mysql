package com.dvnchina.advertDelivery.sysconfig.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
import com.dvnchina.advertDelivery.sysconfig.bean.UserRank;
import com.dvnchina.advertDelivery.sysconfig.dao.UserRankDao;

public class UserRankDaoImpl extends BaseDaoImpl implements UserRankDao {

	/**
	 * 分页查询用户级别信息
	 * @param userRank
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryUserRankList (UserRank userRank,int pageNo, int pageSize){
		StringBuffer hql = new StringBuffer("from UserRank where 1=1");
		if(userRank != null){
			if(!StringUtils.isEmpty(userRank.getUserRankName())){
				hql.append(" and userRankName like '%").append(userRank.getUserRankName().trim()).append("%' ");
			}
			if(!StringUtils.isEmpty(userRank.getUserRankCode())){
				hql.append(" and userRankCode like '%").append(userRank.getUserRankCode().trim()).append("%' ");
			}
		}
		
		int rowcount = this.getTotalCountHQL(hql.toString(), null);
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
		hql.append("order by id");
		List<UserRank> list = (List<UserRank>)this.getListForPage(hql.toString(), null, pageNo, pageSize);
		page.setDataList(list);
		return page;
	}
	
	/**
	 * 根据ID获取用户级别信息
	 * @param id
	 * @return
	 */
	public UserRank getUserRankById(Integer id){
		return this.getHibernateTemplate().get(UserRank.class,id);
	}
	
	/**
	 * 判断是否已经存在用户级别
	 * @param userRank
	 * @return
	 */
	public boolean existsUserRank(UserRank userRank){
		StringBuffer hql = new StringBuffer("from UserRank where 1=1");
		if(userRank != null){
			if(!StringUtils.isEmpty(userRank.getUserRankName()) && !StringUtils.isEmpty(userRank.getUserRankCode())){
				hql.append(" and ( userRankName = '").append(userRank.getUserRankName().trim()).append("' ");
				hql.append(" or userRankCode = '").append(userRank.getUserRankCode().trim()).append("' ) ");
			}else if(!StringUtils.isEmpty(userRank.getUserRankName())){
				hql.append(" and userRankName = '").append(userRank.getUserRankName().trim()).append("' ");
			}else if(!StringUtils.isEmpty(userRank.getUserRankCode())){
				hql.append(" and userRankCode = '").append(userRank.getUserRankCode().trim()).append("' ");
			}
			
			if(userRank.getId() != null && userRank.getId().intValue() != 0){
				hql.append(" and id != ").append(userRank.getId().intValue());
			}
		}else{
			return false;
		}
		
		List<UserRank> list = (List<UserRank>)this.getListForPage(hql.toString(), null, -1, -1);
		if(list != null && list.size()>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 保存用户级别信息（新增或修改）
	 * @param userRank
	 */
	public void saveUserRank(UserRank userRank){
		this.getHibernateTemplate().saveOrUpdate(userRank);
	}
	
//	/**
//	 * 根据ids删除用户级别
//	 * @param ids
//	 */
//	public void delUserRankByIds(String ids){
//		String delSql = "delete from t_user_rank where id in ("+ids+")";
//		this.executeBySQL(delSql, null);
//	}
}
