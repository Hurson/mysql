package com.dvnchina.advertDelivery.utils.page;

import java.util.List;

/**
 *	数据假分页的工具类,
 */
public class PageUtils {
	
	
	/**
	 * 数据分页
	 * 
	 * @param pageNo		当前第几页
	 * @param pageSize		每页多少条
	 * @param totalSize		命中的总记录数
	 * @param listAll	           数据列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static PageBean getPageBean(int pageNo,int pageSize,int totalSize,List listAll){
		
		PageBean pb = new PageBean();  	
		
        pb.setCurrentPage(pageNo);  				
        pb.setPageSize(pageSize);   				
        pb.setTotal(totalSize);         			 
        pb.setLength(pageSize);    					
        pb.setFirst((pageNo-1)*pageSize);			
        
        long totalPage = 0;				
    	if(totalSize ==0)
    		totalPage = 1;
		if(totalSize%pageSize==0){
			totalPage = totalSize/pageSize;
		}
		else{
			totalPage = totalSize/pageSize+1;
		}
        pb.setTotalPage(totalPage);					 
    
        if (totalSize < pageSize ){
        	listAll = listAll.subList(0, totalSize);
        }
        if(totalSize > pageNo*pageSize ){
        	listAll = listAll.subList((pageNo-1)*pageSize, pageNo*pageSize);
        }
        if(totalSize <= pageNo*pageSize ){
        	listAll = listAll.subList((pageNo-1)*pageSize, totalSize);
        }
        pb.setList(listAll);						 
        
        return pb;
	}
	
	/**
	 * 数据分页
	 * 
	 * @param pageNo		当前第几页
	 * @param pageSize		每页多少条
	 * @param totalSize		命中的总记录数
	 * @param listAll	           数据列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static PageBean getPageBean2(int pageNo,int pageSize,int totalSize,List listAll){
		
		PageBean pb = new PageBean();  	
		
        pb.setCurrentPage(pageNo);  				
        pb.setPageSize(pageSize);   				
        pb.setTotal(totalSize);         			 
        pb.setLength(pageSize);    					
        pb.setFirst((pageNo-1)*pageSize);
        
        long totalPage = (totalSize + pageSize - 1) / pageSize;
        pb.setTotalPage(totalPage);					 
        pb.setList(listAll);						 
        return pb;
	}
	
//	public static void main(String[] args) {
//		List<Long> list = new ArrayList<Long>();
//		
//		list.add(1L);
//		list.add(2L);
//		list.add(3L);
//		
//		List list2 = list.subList(0, 1);
//		System.out.println(list2.size());
//	}
}
