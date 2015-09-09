package com.dvnchina.advertDelivery.bean;

import java.util.List;

import com.dvnchina.advertDelivery.constant.PageConstant;

/**
 * 用于数据库分页的封装类
 * */
public class PageBeanDB {

	/**查询记录的开始位置*/
	private int begin = 0;
	
	private int end = 0;
	
	/**总记录数*/
	private int count;
	
	/**当前页码*/
	private int pageNo;
	
	/**每页显示记录数*/
	private int pageSize = PageConstant.PAGE_SIZE;
	
	/**记录总页数*/
	private int totalPage;
    
	private List dataList;
	/**传递频道组类型**/
	private String channelGroupType;
	
	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public PageBeanDB() 
	{
		
	}
	public PageBeanDB(int count, int pageNo) {
		this.count = count;
		totalPage = (this.count - 1) / pageSize + 1;
		if (count == 0) {
			totalPage = 0;
		}
		this.pageNo = pageNo;
		if (pageNo <= 0) {
			this.pageNo = 1;
		} else if (pageNo > totalPage) {
			this.pageNo = totalPage;
		}
		begin = (this.pageNo - 1) * pageSize+1;
		end = begin+pageSize-1;
	}
	
	public PageBeanDB(int count, int pageNo,int pageSize) {
		this.count = count;
		if (pageSize <= 0) {
			pageSize = 20;
		}
		this.pageSize = pageSize;
		totalPage = (this.count - 1) / pageSize + 1;
		if (count == 0) {
			totalPage = 0;
		}
		this.pageNo = pageNo;
		if (pageNo <= 0) {
			this.pageNo = 1;
		} else if (pageNo > totalPage) {
			this.pageNo = totalPage;
		}
		begin = (this.pageNo - 1) * pageSize+1;
		end = begin+pageSize-1;
	}

	public int getBegin() {
		return begin;
	}

	public int getEnd(){
		return end;
	}
	public int getCount() {
		return count;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public int getPageNo() {
		return pageNo;
	}

	public String getChannelGroupType() {
		return channelGroupType;
	}

	public void setChannelGroupType(String channelGroupType) {
		this.channelGroupType = channelGroupType;
	}
	
}