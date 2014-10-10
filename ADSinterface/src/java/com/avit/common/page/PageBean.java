package com.avit.common.page;

import java.util.List;

public class PageBean<T> {

	private int pageno = 1; // 当前页码
	private int pagesize = 10; // 每页行数
	private int rowcount; // 总行数
	private int pagecount; // 总页数
	private boolean usepage = true;// 是否分页
	private List<T> listpage;// 分页得到的数记录

	public PageBean()	{

	}

	public PageBean(boolean usepage)	{
		this.usepage = usepage;
	}

	public PageBean(int pagesize){
		this.pagesize = pagesize;
	}

	public PageBean(int pageno, int pagesize){
		this.pageno = pageno;
		this.pagesize = pagesize;
	}

	public PageBean(int pageno, int pagesize, boolean usepage){
		this.pageno = pageno;
		this.pagesize = pagesize;
		this.usepage = usepage;
	}

	public List<T> getListpage(){
		return listpage;
	}

	public void setListpage(List<T> listpage){
		this.listpage = listpage;
	}

	public int getPageno(){
		return pageno;
	}

	public void setPageno(int pageno){
		this.pageno = pageno;
	}

	public int getPagesize(){
		return pagesize;
	}

	public void setPagesize(int pagesize){
		if (pagesize == 0){
			usepage = false;
			pagecount = 1;
			pageno = 1;
		}
		this.pagesize = pagesize;
	}

	public int getRowcount(){
		return rowcount;
	}

	public void setRowcount(int rowcount){
		this.rowcount = rowcount;
		if (usepage)	{
			pagecount = (rowcount % pagesize == 0) ? (rowcount / pagesize) : (rowcount / pagesize + 1);
			if (pageno > pagecount)	{
				pageno = pagecount;
			}
			
			if(pageno == 0){
				pageno = 1;
			}
		}
	}

	public int getPagecount(){
		return pagecount;
	}

	public void setPagecount(int pagecount){
		this.pagecount = pagecount;
	}

	public boolean isUsepage(){
		return usepage;
	}

	public void setUsepage(boolean usepage){
		this.usepage = usepage;
	}
}
