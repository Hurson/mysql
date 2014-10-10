package com.dvnchina.advertDelivery.utils.page;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具类
 * 
 */
public class PageUtil {
	/**
	 * 每页显示记录数常量
	 * */
	public static final int NUMBER_PER_PAGE = 10;
	
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
	private int currentPage; 
	/**
	 * 结果集存放集合
	 */
	private List resultList;

	/**
	 * 构造方法，通过结果集，当前页，每页显示记录数初始化成员变量
	 * @param list 记录集合
	 * @param currentPage 当前页
	 * @param numPerPage 每页显示记录数
	 * */
	public PageUtil(List list, int currentPage, int numPerPage) {
		/**
		 * 当记录为空时，设置总页数为0，当前页为1，结果集为空集合
		 * */
		if (list.size() == 0) {
			setTotalPage(0);
			setCurrentPage(1);
			setResultList(new ArrayList());
			return;
		}
		/**
		 * 设置总页数
		 * */
		setTotalPages(numPerPage, list.size());
		/**
		 * 设置总记录数
		 * */
		setTotalRow(list.size());
		/**
		 * 设置当前页
		 * */
		if(currentPage<=totalPage&&currentPage>=1){
		
			setCurrentPage(currentPage);
		}else if(currentPage>totalPage){
			setCurrentPage(totalPage);
		}else if(currentPage<1){
			setCurrentPage(1);
		}

		/**
		 * 计算开始记录位置
		 * */
		int startIndex = (getCurrentPage() - 1) * numPerPage;
		List objects = new ArrayList();
		for (int i = 0; i < numPerPage && (startIndex + i) < list.size(); i++) {
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
	public PageUtil(){};
	
/*	
	*//**
	 * 构造oracle分页sql语句
	 * @param sql 查询sql语句
	 * @param begin 开始记录行数
	 * @param end 结尾记录行数
	 * @return 分页sql语句
	 *//*
	public String getPageSql(String sql,int begin,int end){
		
		  SELECT * FROM
		  (
			SELECT A.*, ROWNUM RN
				FROM (SELECT * FROM t_user) A 
				WHERE ROWNUM <= 15
			)
			WHERE RN >= 11
		  
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT * FROM(SELECT select_table.*, ROWNUM RN FROM (");
		buf.append(sql);
		buf.append(") select_table WHERE ROWNUM <=");
		buf.append(begin);
		buf.append(")WHERE RN >=");
		buf.append(end);
		return buf.toString();
		
	}
*/
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

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public List getResultList() {
		return resultList;
	}

	public void setResultList(List resultList) {
		this.resultList = resultList;
	}

	
	/**
	 * 计算总页数
	 * @param numPerPage 每页显示记录数
	 * @param totalRows 总记录数
	 */
	public void setTotalPages(int numPerPage, int totalRows) {
		if (totalRows % numPerPage == 0) {
			this.totalPage = totalRows / numPerPage;
		} else {
			this.totalPage = (totalRows / numPerPage) + 1;
		}
	}

}
