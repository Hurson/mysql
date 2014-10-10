package com.dvnchina.advertDelivery.sysconfig.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
import com.dvnchina.advertDelivery.model.Column;
import com.dvnchina.advertDelivery.order.bean.playlist.MaterialBean;
import com.dvnchina.advertDelivery.sysconfig.bean.Role;
import com.dvnchina.advertDelivery.sysconfig.dao.RoleDao;

public class RoleDaoImpl extends BaseDaoImpl implements RoleDao{
	
	/**
	 * 分页查询角色信息
	 * @param role
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryRoleList(Role role,int pageNo, int pageSize){
		StringBuffer hql = new StringBuffer("from Role where 1=1 and roleId<>1 ");
		if(role != null){
			if(StringUtils.isNotBlank(role.getName())){
				hql.append(" and name like '%").append(role.getName().trim()).append("%' ");
			}
			if(role.getType() != null){
				hql.append(" and type = ").append(role.getType());
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
		hql.append("order by modifyTime desc");
		List<Role> list = (List<Role>)this.getListForPage(hql.toString(), null, pageNo, pageSize);
		page.setDataList(list);
		return page;
	}
	
	/**
	 * 根据输入的角色名称验证用户是否存在
	 * @param role
	 * @return boolean
	 */
	public boolean checkRoleName(Role role){
		StringBuffer sb = new StringBuffer("from Role where 1=1 ");
		if(role != null){
			if(StringUtils.isNotBlank(role.getName())){
				sb.append(" and name= '"+role.getName().trim()+"'");
			}
			if(role.getRoleId() != null && role.getRoleId() != 0){
				sb.append(" and roleId != "+role.getRoleId());
			}
		}
		
		List<Role> roleList = this.list(sb.toString());
		
		if(roleList!=null &&roleList.size()>0){
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 获取所有的角色
	 * 
	 * @return
	 */
	public List<Role> getAllRoleList(){
		String hql = "from Role";
		List<Role> roleList = this.list(hql, null);
		return roleList;
	}
	
	/**
	 * 获取单个对象
	 * @param roleId
	 * @return
	 */
	public Role getRoleById(Integer roleId){
		return getHibernateTemplate().get(Role.class, roleId);
	}
	
	/**
	 * 根本角色ID获取栏目列表
	 * @param roleId
	 * @return
	 */
	public List<Column> getColumnListByRoleId(Integer roleId){
		String sql = "select c.column_id,c.name from t_columns c ,t_columns_role cr where c.column_id = cr.column_id and cr.role_id = ?";
		List<Column> columnList = getColumnList(this.getDataBySql(sql, new Object[]{roleId}));
		return columnList;
	}
	
	private List<Column> getColumnList(List<?> resultList) {
		List<Column> list = new ArrayList<Column>();
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			Column column = new Column();
			column.setId(toInteger(obj[0]));
			column.setName((String)obj[1]);
			list.add(column);
		}
		return list;
	}
	
	/**
	 * 检查角色是否绑定有用户记录
	 * @param ids
	 * @return
	 */
	public boolean checkRoleUserBinging(String ids){
		StringBuffer sql =  new StringBuffer();
		sql.append(" select u.* from t_user u, t_user_role ur, t_role r ");
		sql.append(" where u.user_id = ur.user_id and ur.role_id=r.role_id and r.role_id in (").append(ids).append(")");
		List list = this.getDataBySql(sql.toString(), null);
		if(list != null && list.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 根据用户ID获取角色类型
	 * @param userId
	 * @return
	 */
	public Integer getRoleTypeByUser(Integer userId){
		String sql = "select r.type from t_user_role rel, t_role r  ";
		sql += "where rel.role_id = r.role_id and rel.user_id=?";
		List list = this.getDataBySql(sql,new Object[]{userId});
		if(list != null && list.size()>0){
			Object obj = list.get(0);
			return toInteger(obj);
		}
		return null;
	}

}
