package com.dvnchina.advertDelivery.action;

import java.util.List;

import com.dvnchina.advertDelivery.model.Column;
import com.dvnchina.advertDelivery.service.ColumnService;

public class ColumnAction  extends BaseActionSupport<Object>{

	private static final long serialVersionUID = 1L;
	
	private ColumnService columnService;
	private List<Column> columnList;
	private Column column;
	
	/**
	 * 父级栏目集合
	 */
	private List<Column> firstColumnList; 

	/**
	 * 栏目列表页
	 * @return
	 */
	public String getAllColumnList(){
		try {
			columnList = columnService.getAllColumnList();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	
	/**
	 * 获取父级栏目
	 * @return
	 */
	public String getParentColumn(){
		firstColumnList=columnService.getFirstColumnList();
		return "add";
	}
	
	/**
	 * 添加栏目
	 * @return
	 */
	public String addColumn(){
		boolean flag= columnService.insertColumn(column);
		System.out.println("  ===="+flag);
		return "list";
	}
	/**
	 * 删除栏目
	 * @return
	 */
	public String deleteColumn(){
		String columnId = getRequest().getParameter("id");
		boolean flag= columnService.deleteColumn(columnId);
		System.out.println("  ===="+flag);
		return NONE;
	}
	
	/**
	 * 修改栏目
	 * @return
	 */
	public String updateColumn(){
		String flag = getRequest().getParameter("flag");
		if("update".equals(flag)){
			columnService.updateColumn(column);
			return NONE;
		}else{
			String columnId = getRequest().getParameter("id");
			column = columnService.getSingleColumn(columnId);
			firstColumnList=columnService.getFirstColumnList();
			return "update";
		}
	}
	
	public ColumnService getColumnService() {
		return columnService;
	}

	public void setColumnService(ColumnService columnService) {
		this.columnService = columnService;
	}
	
	public List<Column> getFirstColumnList() {
		return firstColumnList;
	}

	public void setFirstColumnList(List<Column> firstColumnList) {
		this.firstColumnList = firstColumnList;
	}

	public Column getColumn() {
		return column;
	}

	public void setColumn(Column column) {
		this.column = column;
	}

	public List<Column> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<Column> columnList) {
		this.columnList = columnList;
	}

	
}
