package com.dvnchina.advertDelivery.sysconfig.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
import com.dvnchina.advertDelivery.sysconfig.bean.UserIndustryCategory;
import com.dvnchina.advertDelivery.sysconfig.bean.UserRank;
import com.dvnchina.advertDelivery.sysconfig.dao.UserTradeDao;

public class UserTradeDaoImpl extends BaseDaoImpl implements UserTradeDao{

	/**
	 * 分页查信息
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryUserTradeList(UserIndustryCategory insdustry, int pageNo, int pageSize ){
		String hql = "from UserIndustryCategory b where 1=1 ";
		if(insdustry != null ){
			if(StringUtils.isNotBlank(insdustry.getUserIndustryCategoryCode())){
				hql += " and b.userIndustryCategoryCode like '%"+insdustry.getUserIndustryCategoryCode()+"%'";
			}
			if(StringUtils.isNotBlank(insdustry.getUserIndustryCategoryValue())){
				hql += " and b.userIndustryCategoryValue like '%"+insdustry.getUserIndustryCategoryValue()+"%'";
			}
		}
		
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
		hql += " order by b.userIndustryCategoryCode ";
		List<UserIndustryCategory> list = (List<UserIndustryCategory>)this.getListForPage(hql, null, pageNo, pageSize);
		page.setDataList(list);
		return page;
	}
	
	/**
	 * 根据ID获取配置项信息
	 * @param id
	 * @return BaseConfig
	 */
	public UserIndustryCategory getUserTradeById(int id){
		return (UserIndustryCategory) this.get(UserIndustryCategory.class, id);
	}
	
	/**
	 * 保存
	 * @param BaseConfig
	 * @return int
	 */
	public void saveUserTrade(UserIndustryCategory obj){
		this.save(obj);
	}
	
	/**
	 * 修改
	 * @param BaseConfig
	 * 
	 */
	public void updateUserTrade(UserIndustryCategory obj){
		this.update(obj);
	}
	
	/**
	 * 删除用户行业级别
	 * @param ids
	 * 
	 */
	public void deleteUserTrade(String ids){
		Session session = this.getSessionFactory().openSession();
		Transaction ts = session.beginTransaction();
		String hqlUpdate = "DELETE FROM T_USER_INDUSTRY_CATEGORY WHERE ID IN" +ids;
		Query query = session.createSQLQuery(hqlUpdate);
		query.executeUpdate();
		ts.commit();
		session.close();
	}
	
	/**
	 * 判断是否存在用户行业
	 * @param industry
	 * @return  true 存在  false 不存在
	 */
	public boolean existsIndustry(UserIndustryCategory industry){
		StringBuffer hql = new StringBuffer("from UserIndustryCategory where 1=1");
		if(industry != null){
			if(!StringUtils.isEmpty(industry.getUserIndustryCategoryCode()) && !StringUtils.isEmpty(industry.getUserIndustryCategoryValue())){
				hql.append(" and ( userIndustryCategoryCode = '").append(industry.getUserIndustryCategoryCode().trim()).append("' ");
				hql.append(" or userIndustryCategoryValue = '").append(industry.getUserIndustryCategoryValue().trim()).append("' ) ");
			}else if(!StringUtils.isEmpty(industry.getUserIndustryCategoryCode())){
				hql.append(" and userIndustryCategoryCode = '").append(industry.getUserIndustryCategoryCode().trim()).append("' ");
			}else if(!StringUtils.isEmpty(industry.getUserIndustryCategoryValue())){
				hql.append(" and userIndustryCategoryValue = '").append(industry.getUserIndustryCategoryValue().trim()).append("' ");
			}
			
			if(industry.getId() != null && industry.getId().intValue() != 0){
				hql.append(" and id != ").append(industry.getId().intValue());
			}
		}else{
			return false;
		}
		
		List<UserIndustryCategory> list = (List<UserIndustryCategory>)this.getListForPage(hql.toString(), null, -1, -1);
		if(list != null && list.size()>0){
			return true;
		}
		return false;
	}

}
