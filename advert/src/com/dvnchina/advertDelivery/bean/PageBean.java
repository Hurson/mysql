package com.dvnchina.advertDelivery.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于内存分页的封装类
 * 
 */
public class PageBean {
	/**
	 * 每页显示记录数常量
	 * */
	public static final int pageSize = 10;
	
	/**
	 * 总页数
	 */
	private int totalPage;
	
	/**
	 * 总记录数
	 */
	private int totalRow;
	
	/**
	 * 当前页码
	 */
	private int pageNo; 
	/**
	 * 结果集存放集合
	 */
	private List resultList;

	/**
	 * 构造方法，通过结果集，当前页，每页显示记录数初始化成员变量
	 * @param list 记录集合
	 * @param pageNo 当前页
	 * */
	public PageBean(List list, int pageNo) {
		/**
		 * 当记录为空时，设置总页数为0，当前页为1，结果集为空集合
		 * */
		if (list.size() == 0) {
			setTotalPage(0);
			setPageNo(1);
			setResultList(new ArrayList());
			return;
		}
		/**
		 * 设置总页数
		 * */
		setTotalPages(pageSize, list.size());
		/**
		 * 设置总记录数
		 * */
		setTotalRow(list.size());
		/**
		 * 设置当前页
		 * */
		if(pageNo<=totalPage&&pageNo>=1){
		
			setPageNo(pageNo);
		}else if(pageNo>totalPage){
			setPageNo(totalPage);
		}else if(pageNo<1){
			setPageNo(1);
		}

		/**
		 * 计算开始记录位置
		 * */
		int startIndex = (getPageNo() - 1) * pageSize;
		List objects = new ArrayList();
		for (int i = 0; i < pageSize && (startIndex + i) < list.size(); i++) {
			objects.add(list.get(startIndex + i));
		}
		/**
		 * 设置截取后的结果集集合
		 * */
		setResultList(objects);
	}

	/**
	 * 无参构造方法
	 */
	public PageBean(){};
	

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public List getResultList() {
		return resultList;
	}

	public void setResultList(List resultList) {
		this.resultList = resultList;
	}

	
	/**
	 * 计算总页数
	 * @param pageSize 每页显示记录数
	 * @param totalRows 总记录数
	 */
	public void setTotalPages(int pageSize, int totalRows) {
		if (totalRows % pageSize == 0) {
			this.totalPage = totalRows / pageSize;
		} else {
			this.totalPage = (totalRows / pageSize) + 1;
		}
	}
	public int getPageSize(){
		return pageSize;
	}
}
