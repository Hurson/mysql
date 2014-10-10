package com.dvnchina.advertDelivery.sysconfig.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.common.BaseAction;
import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.constant.PurviewConstant;
import com.dvnchina.advertDelivery.log.bean.OperateLog;
import com.dvnchina.advertDelivery.log.service.OperateLogService;
import com.dvnchina.advertDelivery.model.Column;
import com.dvnchina.advertDelivery.service.ColumnService;
import com.dvnchina.advertDelivery.service.PurviewService;
import com.dvnchina.advertDelivery.sysconfig.bean.Role;
import com.dvnchina.advertDelivery.sysconfig.service.RoleService;

public class RoleAction extends BaseAction{
	
	private static final long serialVersionUID = 1L;
	private RoleService roleService;
	private ColumnService columnService;
	private PurviewService purviewService;
	private OperateLogService operateLogService = null;
	private PageBeanDB page = null;
	private Role role = null;
	private OperateLog operLog = null;
	
	/**
	 * 分页查询角色信息
	 */
	public String queryRoleList(){
		try{
			if(page == null){
				page = new PageBeanDB();
			}
			page = roleService.queryRoleList(role,page.getPageNo(), page.getPageSize());
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 进入新增角色页面
	 * @return
	 */
	public String initAdd(){
		return SUCCESS;
	}
	
	/**
	 * 获取树状栏目
	 * @return
	 */
	public void getTreeColumn(){
		String jsonTree = columnService.getTreeColumnList(getColumnIDList());
		renderJson(jsonTree);
	}
	
	//检查角色名称是否存在
	public void checkRoleName(){
		String roleId = getRequest().getParameter("roleId");
		String name = getRequest().getParameter("name");
		Role role = new Role();
		role.setName(name);
		if(StringUtils.isNotBlank(roleId)){
			role.setRoleId(Integer.valueOf(roleId));
		}
		Boolean existsRole = roleService.checkRoleName(role);
		this.renderText(existsRole.toString());
	}
	
	/**
	 * 添加角色
	 * @return
	 */
	public String addRole(){
		try {
			//1、插入角色表的记录
			roleService.insertRole(role);
			//2、插入绑定关系
			purviewService.addRoleColumnBinding(role.getRoleId(), getColumnIDList());
			message = "common.add.success";//保存成功
			
		} catch (Exception e) {
			message = "common.add.fail";//保存失败
			operResult = Constant.OPERATE_FAIL;
			log.error("***** RoleAction addRole occur a exception: "+e);
		}finally{
			operType = "operate.add";
			operInfo = role.toString();
			operLog = this.setOperationLog(Constant.OPERATE_MODULE_ROLE);
			operateLogService.saveOperateLog(operLog);
		}
		role = null;
		return queryRoleList();
	}
	
	/**
	 * 获取栏目的Id的集合
	 * 
	 * @return
	 */
	private List<Integer> getColumnIDList(){
		
		Map<String,String> map = new HashMap<String,String>();
		List<Integer> columnIdL = new ArrayList<Integer>();
		String columnIds = getRequest().getParameter("sel_columns");//多个栏目的Id以“,”分割的
		if(StringUtils.isNotBlank(columnIds)){
			String[] columnIDS = columnIds.split(PurviewConstant.SIGN_COMMA);
			for(String id : columnIDS ){
				if(!map.containsKey(id)){
					map.put(id, "");
					columnIdL.add(Integer.valueOf(id));
				}
			}
		}
		
		String p_column_ids = getRequest().getParameter("sel_p_columns");//多个栏目的Id以“,”分割的
		if(StringUtils.isNotBlank(p_column_ids)){
			String[] columnPids = p_column_ids.split(PurviewConstant.SIGN_COMMA);
			for(String id : columnPids ){
				if(!map.containsKey(id)){
					map.put(id, "");
					columnIdL.add(Integer.valueOf(id));
				}
			}
		}
		
		return columnIdL;
	}
	
	/**
	 * 进入角色修改页面
	 * @return
	 */
	public String getRoleForUpdate(){
		 role = roleService.getRoleById(role.getRoleId());
		 List<Column> columnList = roleService.getColumnListByRoleId(role.getRoleId());
		 if(columnList != null && columnList.size()>0){
			 String columnIds = "";
			 String columnNames = "";
			 for(Column c : columnList){
				 columnIds += c.getId()+",";
				 columnNames += c.getName()+",";
			 }
			 role.setColumnIds(columnIds.substring(0,columnIds.length()-1));
			 role.setColumnNames(columnNames.substring(0,columnNames.length()-1));
		 }
		return SUCCESS;
	}
	
	/**
	 * 修改角色
	 * @return
	 */ 
	public String updateRole(){
		try {
			roleService.updateRole(role);
			purviewService.deleteRoleColumnAllBinding(role.getRoleId());
			purviewService.addRoleColumnBinding(role.getRoleId(), getColumnIDList());
			message = "common.update.success";//修改成功
		} catch (Exception e) {
			message = "common.update.failed";//保存失败
			operResult = Constant.OPERATE_FAIL;
			log.error("***** RoleAction updateRole occur a exception: "+e);
		}finally{
			operType = "operate.update";
			operInfo = role.toString();
			operLog = this.setOperationLog(Constant.OPERATE_MODULE_ROLE);
			operateLogService.saveOperateLog(operLog);
		}
		role = null;
		return queryRoleList();
	}
	
	/**
	 * 检查角色是否绑定用户
	 */
	public void checkRoleUserBinging(){
		String[] idsStr = ids.split(",");
		ids = "";
		for (String idStr : idsStr) {
			ids += StringUtils.trim(idStr.split("_")[0])+",";
		}
		Boolean binging = roleService.checkRoleUserBinging(ids.substring(0,ids.length()-1));
		this.renderText(binging.toString());
	}
	
	/**
	 * 删除角色
	 * @return
	 */
	public String delRole(){
		StringBuffer delInfo = new StringBuffer();
		delInfo.append("删除角色：");
		List<Role> list = new ArrayList<Role>();
		try{
			
			String[] idsStr = ids.split(",");
			for (String idStr : idsStr) {
				Role r = new Role();
				r.setRoleId(Integer.valueOf(StringUtils.trim(idStr.split("_")[0])));
				delInfo.append(Integer.valueOf(StringUtils.trim(idStr.split("_")[0]))).append(",");
				delInfo.append(idStr.split("_")[1]).append(Constant.OPERATE_SEPARATE);
				list.add(r);
			}
			
			roleService.delRole(list);
			message = "common.delete.success";//删除成功
		}catch(Exception e){
			message = "common.delete.fail";//删除失败
			operResult = Constant.OPERATE_FAIL;
			log.error("***** RoleAction delUserRank occur a exception: "+e);
		}finally{
			delInfo.append("共").append(ids.split(",").length).append("条记录");
			operType = "operate.delete";
			operInfo = delInfo.toString();
			operLog = this.setOperationLog(Constant.OPERATE_MODULE_ROLE);
			operateLogService.saveOperateLog(operLog);
		}
		ids = null;
		return queryRoleList();
	}

	public PageBeanDB getPage() {
		return page;
	}

	public void setPage(PageBeanDB page) {
		this.page = page;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public void setPurviewService(PurviewService purviewService) {
		this.purviewService = purviewService;
	}

	public void setOperateLogService(OperateLogService operateLogService) {
		this.operateLogService = operateLogService;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Role getRole() {
		return role;
	}

	public void setColumnService(ColumnService columnService) {
		this.columnService = columnService;
	}

}
