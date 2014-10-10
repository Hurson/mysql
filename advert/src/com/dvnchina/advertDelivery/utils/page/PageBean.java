package com.dvnchina.advertDelivery.utils.page;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;   
	  
/**
 * 分页类
 * 
 * @author Administrator
 *
 */
@SuppressWarnings("unchecked")
public class PageBean {   
  
	
	
		private HashMap jsonMap;
		
	
		public HashMap getJsonMap() {
			return jsonMap;
		}

		public void setJsonMap(HashMap jsonMap) {
			this.jsonMap = jsonMap;
		}

		/**
		 * 每一页数据的 Map
		 */
		private HashMap  map;
		
		/**
		 * 获得每页数据 Map
		 * @return
		 */
		public HashMap getMap() {
			return map;
		}
		
		/**
		 * @param map  每一页数据的 Map
		 */
		public void setMap(HashMap map) {
			this.map = map;
		}

		/**
	  	 *  要返回的某一页的记录列表   
	  	 */
		
		private List list;    
		
	    /**
	     *   总页数   
	     */
	    private long  totalPage;		
	    /**
	     * 当前页  
	     */
	    private int  currentPage;	 
	    /**
	     * 每页记录数   
	     */
	    private int  pageSize; 		
	    /**
	     * 补偿几个记录才能满
	     */
	    private int  offset;   
	  
	    public int getOffset() {   
	        return offset;   
	    }   
	  
	    public void setOffset(int offset) {   
	        this.offset = offset;   
	    }   
	  
	    /**
	     *  是否为第一页   
	     */
	    @SuppressWarnings("unused")
		private boolean isFirstPage;  		
	    /**
	     * 是否为最后一页   
	     */
	    @SuppressWarnings("unused")
		private boolean isLastPage;			
	    /**
	     * 是否有前一页   
	     */
	   
		@SuppressWarnings("unused")
		private boolean hasPreviousPage; 	
	    /**
	     * 是否有下一页   
	     */
	    @SuppressWarnings("unused")
		private boolean hasNextPage; 		
	  
	    /**
	     * 
	     * @return  记录的列表
	     */
	    public List getList() {   
	        return list;   
	    }   
	  
	    /** 每页记录列表
	     * @param list 每页数据列表
	     */
	    public void setList(List list) {   
	        this.list = list;   
	    }   
	  
	  
	    /** 
	     * @param total  总记录数
	     */
	    public void setTotal(int total) {   
	        this.total = total;   
	    }   

	    /**
	     * @param totalPage  总页数
	     */
	    public void setTotalPage(long totalPage) {   
	        this.totalPage = totalPage;   
	    }   
	  
	    /**
	     * @param currentPage 当前页号
	     */
	    public void setCurrentPage(int currentPage) {   
	        this.currentPage = currentPage;   
	    }   
	  
	    /**
	     * @return 每页记录数   
	     */
	    public int getPageSize() {   
	        return pageSize;   
	    }   
	  
	    /**
	     * @param pageSize 每页记录数   
	     */
	    public void setPageSize(int pageSize) {   
	        this.pageSize = pageSize;   
	    }   
	  
	   //=====================================// 
	    /**  
	     * 初始化分页信息  
	     */  
	    public void init() {   
	        this.isFirstPage = isFirstPage();   
	        this.isLastPage = isLastPage();   
	        this.hasPreviousPage = isHasPreviousPage();   
	        this.hasNextPage = isHasNextPage();   
	    }  
	    
	  //=======================================//
	    /**  
	     * 以下判断页的信息,只需getter方法(is方法)即可  
	     *   
	     * @return  
	     */  
	  
	    /**
	     *  如是当前页是第1页
	     * @return
	     */
	    public boolean isFirstPage() {   
	        return currentPage == 1; // 如是当前页是第1页   
	    }   
	  
	    /**
	     * 如果当前页是最后一页   
	     * @return
	     */
	    public boolean isLastPage() {   
	        return currentPage == totalPage;   
	    }   
	  
	    /**
	     * 只要当前页不是第1页   
	     * @return
	     */
	    public boolean isHasPreviousPage() {   
	        return currentPage != 1;   
	    }   
	  
	    /**
	     * 只要当前页不是最后1页   
	     * @return
	     */
	    public boolean isHasNextPage() {   
		  if(totalPage==0){   
			  return false;   
		  }else{   
			  return currentPage != totalPage; // 只要当前页不是最后1页   
		  }   
	     
	 } 
	    /**  
	     * 计算总页数,静态方法,供外部直接通过类名调用  
	     *   
	     * @param pageSize  
	     *            每页记录数  
	     * @param total  
	     *            总记录数  
	     * @return 总页数  
	     */  
	    public static int countTotalPage(final int pageSize, final int total) {   
	        int totalPage = total % pageSize == 0 ? total / pageSize : total   
	                / pageSize + 1;   
	        return totalPage;   
	    }   
	  
	    /**  
	     * 计算当前页开始记录  
	     *   
	     * @param pageSize  
	     *            每页记录数  
	     * @param currentPage  
	     *            当前第几页  
	     * @return 当前页开始记录号  
	     */  
	    public static int countOffset(final int pageSize, final int currentPage) {   
	        final int offset = pageSize * (currentPage - 1);   
	        return offset;   
	    }   
	  
	    /**  
	     * 计算当前页,若为0或者请求的URL中没有"?page=",则用1代替  
	     *   
	     * @param page  
	     *            传入的参数(可能为空,即0,则返回1)  
	     * @return 当前页  
	     */  
	    public static int countCurrentPage(int page) {   
	        final int curPage = (page == 0 ? 1 : page);   
	        return curPage;   
	    }   
	  
	    /**计算下一行开始的位置
	     * 
	     * @param rowStartIdx   当前行开始的位置
	     * @param pageSize      每页数据多少条
	     * @return   
	     */
	    public static int lastSqlIdx(int rowStartIdx, int pageSize) {   
	        return rowStartIdx + pageSize;   
	    }   
//============================================================

		/**
		 * 起始记录号
		 */
		private int first = -1;
		/**
		 * 要显示的数据的数量
		 */
		private int length = -1;
		/**
		 * 总的数据的数量
		 */
		private long total = -1;

		/**
		 * 得到总的数据量
		 * 
		 * @return
		 */
		public long getTotal()
		{
			return total;
		}

		/**
		 * 设置总的数据量
		 * 
		 * @param total
		 */
		public void setTotal(long total)
		{
			this.total = total;
		}

		/**
		 * 得到起始记录号
		 * 
		 * @return
		 */
		public int getFirst()
		{
			return first;
		}

		/**
		 * 设置起始记录号
		 * 
		 * @param first
		 */
		public void setFirst(int first)
		{
			this.first = first;
		}

		/**
		 * 设置要显示的数据的个数
		 * 
		 * @param length
		 */
		public void setLength(int length)
		{
			this.length = length;
		}

		/**
		 * 取得要显示的数据的个数
		 * 
		 * @return
		 */
		public int getLength()
		{
			return length;
		}

		private int pos = 0;

		private long end = -2;

		public void start()
		{
			pos = first < 0 ? 0 : first;
			if (length < 0)
			{
				end = total;
			} else
			{
				end = pos + length;
				if (total > -1 && end > total)
				{
					end = total;
				}
			}
		}

		/**
		 * 判断下一个数据是否存在
		 * 
		 * @return
		 */
		public boolean hasNext()
		{
			return end < 0 || pos < end;
		}

		/**
		 * 当前指针位置下移一位
		 * 
		 * @return
		 */
		public int next()
		{
			return pos++;
		}

		/**
		 * 判断是否有下一页
		 * 
		 * @return
		 */
		public boolean getNextPage()
		{
			return (end != -2 && end < total);
		}

		/**
		 * 判断上一页是否存在
		 * 
		 * @return
		 */
		public boolean getPrePage()
		{
			return first != 0;
		}

		// TODO
		public boolean hasNext(Iterator iter)
		{
			return false;
		}

		public <T extends Object> T next(Iterator<T> iter)
		{
			return null;
		}

		/**
		 * 取得当前页号
		 * 
		 * @return
		 */
		public int getCurrentPage()
		{
//			return first / length + 1;
			return currentPage;
		}

		/**
		 * 得到总页数
		 * 
		 * @return
		 */
		public long getTotalPage()
		{
			return totalPage;
		}


		
		@Override
		public String toString() {
			return "PageBean [map=" + map + "]";
		}
	    //====================================================
	    
//	    @Override
//		public String toString() {
//			return "PageBean [list=" + list + "]";
//		}
		
		
}
