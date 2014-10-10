package com.dvnchina.advertDelivery.action;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import com.dvnchina.advertDelivery.model.Column;
import com.dvnchina.advertDelivery.service.ColumnService;
import com.dvnchina.advertDelivery.service.PurviewService;
import com.dvnchina.advertDelivery.sysconfig.bean.Role;

public class PurviewAction extends BaseActionSupport<Object>{

	private static final long serialVersionUID = 1L;
	
	private List<Column> columnList;
	private PurviewService purviewService;
	private ColumnService columnService;

	
	/**
	 * 授权
	 */
	public String  getUserAuthor(){
		
		
		return "toAuthor";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 用户绑定角色的集合
	 * @return
	 */
	public String getRoleListByUserId(){
		String userId = getRequest().getParameter("id");
		
		List<Role> roleL=purviewService.getUserOwnRoleList(Integer.valueOf(userId));
		String json  = JSONArray.fromObject(roleL).toString()+"";
		renderJson(json);
		return NONE;
	}
	
	/**
	 * 角色绑定栏目的集合
	 * @return
	 */
	public String getColumnListByRoleId(){
		String roleId = getRequest().getParameter("id");
		
		List<Column> columnL=purviewService.getRoleOwnColumnList(Integer.valueOf(roleId));
		String json  = JSONArray.fromObject(columnL).toString()+"";
		renderJson(json);
		return NONE;
	}
	
	/**
	 * 保存绑定
	 * @return
	 */
	public String saveRoleColumnBanding(){
		try {
			String roleId = getRequest().getParameter("id");
			String columnIds = getRequest().getParameter("columnIds");
			String[] columnId = columnIds.split(";");
			List<Integer> columnIdList = new ArrayList<Integer>();
			for(int i = 0 ; i < columnId.length;i++){
				columnIdList.add(Integer.valueOf(columnId[i]));
			}
			//1、删除之前的绑定
			purviewService.deleteRoleColumnAllBinding(Integer.valueOf(roleId));
			//2、更新绑定
			purviewService.addRoleColumnBinding(Integer.valueOf(roleId), columnIdList);
			
	
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		return NONE;
	}
	/**
	 * 保存用户和栏目的绑定
	 * @return
	 */
	public String saveUserRoleBanding(){
		try {
			String userId = getRequest().getParameter("id");
			String roleIds = getRequest().getParameter("roleIds");
			String[] roleId = roleIds.split(";");
			List<Integer> roleIdList = new ArrayList<Integer>();
			for(int i = 0 ; i < roleId.length;i++){
				roleIdList.add(Integer.valueOf(roleId[i]));
			}
			//1、删除之前的绑定
			purviewService.deleteUserRoleAllBinding(Integer.valueOf(userId));
			//2、更新绑定
			purviewService.addUserRoleBatchBinding(Integer.valueOf(userId), roleIdList);
			
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		return NONE;
	}
	
	public PurviewService getPurviewService() {
		return purviewService;
	}

	public void setPurviewService(PurviewService purviewService) {
		this.purviewService = purviewService;
	}

	public List<Column> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<Column> columnList) {
		this.columnList = columnList;
	}

	public ColumnService getColumnService() {
		return columnService;
	}

	public void setColumnService(ColumnService columnService) {
		this.columnService = columnService;
	}
	
}
