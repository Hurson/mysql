package com.dvnchina.advertDelivery.npvrChannelGroup.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.dvnchina.advertDelivery.action.BaseActionSupport;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.constant.LoginConstant;
import com.dvnchina.advertDelivery.log.bean.OperateLog;
import com.dvnchina.advertDelivery.log.service.OperateLogService;
import com.dvnchina.advertDelivery.npvrChannelGroup.bean.NpvrChannelGroupRef;
import com.dvnchina.advertDelivery.npvrChannelGroup.bean.NpvrChannelInfo;
import com.dvnchina.advertDelivery.npvrChannelGroup.bean.TNpvrChannelGroup;
import com.dvnchina.advertDelivery.npvrChannelGroup.service.NpvrChannelGroupManagerService;
import com.dvnchina.advertDelivery.sysconfig.bean.ChannelInfo;
import com.dvnchina.advertDelivery.utils.CookieUtils;

public class NpvrChannelGroupManagerAction extends BaseActionSupport<Object> implements ServletRequestAware{
	
	private static final long serialVersionUID = -3666982468062423696L;
	
	private Logger logger = Logger.getLogger(NpvrChannelGroupManagerAction.class);
	
	private PageBeanDB channelGroupPage =  new PageBeanDB();//频道组列表
	
	private PageBeanDB channelListPage =  new PageBeanDB();//频道列表
	
	private PageBeanDB selectchannelPage =  new PageBeanDB();//频道选择列表
	
	private TNpvrChannelGroup channelGroupQuery;//频道组列表页面查询条件
	
	private NpvrChannelInfo channelQuery;//回看频道列表页面查询条件
	
	private NpvrChannelInfo selectChannelQuery;//频道选择列表页面查询条件
	
	private String dataIds;
	
	private String channelIds;
	
	private Integer channelGroupId;
	private String channelGroupIdTemp;
	
	private TNpvrChannelGroup channelGroup;
	
	private HttpServletRequest request;
	
	
	private NpvrChannelGroupManagerService channelGroupManagerService;
	
	private OperateLogService operateLogService;
	
	
	/**操作日志类*/
	public OperateLog operLog;
	private String selChannelIds;
	private List selChannelIdList;
	
	public void setServletRequest(HttpServletRequest request) {
        this.request = request; 
    }
	
	
	/**
	 * 获取用户id
	 * */
	private Integer getOpId() {
		String userId = CookieUtils.getCookieValueByKey(getRequest(),
				LoginConstant.COOKIE_USER_ID);
		return new Integer(userId);
	}

	/**
	 * 获取用户名
	 * */
	private String getUserName() {
		String userName = CookieUtils.getCookieValueByKey(getRequest(),
				LoginConstant.COOKIE_USER_NAME);
		return userName;
	}
	
	
	

	
   
	
	/**
	 * 
	 * @description: 查询频道组列表
	 * @return 
	 * String
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-4-28 上午10:53:22
	 */
	public String queryChannelGroupList(){
		if (channelGroupPage==null)
		{
			channelGroupPage  =  new PageBeanDB();
		}
		if (channelGroupQuery==null)
        {
			channelGroupQuery  =  new TNpvrChannelGroup();
        }
		try {	        
		    
			channelGroupPage = channelGroupManagerService.queryChanelGroupList(channelGroupQuery, channelGroupPage.getPageSize(), channelGroupPage.getPageNo());

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取频道组列表时出现异常",e);
		}
		
		return SUCCESS;
	}
	
	public String intoAddChannelGroup(){
		return SUCCESS;
	}
	
	
	public String saveChannelGroup() {    
       try{
    	    String channelGroupDesc = channelGroup.getChannelDesc().trim();
	   	    channelGroup.setChannelDesc(channelGroupDesc);
	   	    
	   	    //modified by liuwenping 如果编码已存在，不能保存（更新）
	   	    String code = channelGroup.getCode().trim();
	   	    int count = channelGroupManagerService.getEntityCountByCode(code);
	   	    if(count > 0){ //存在该编码
	   	    	String url = "queryChannelGroupListNpvr.do"; 
	   	    	this.renderHtml(generateMessage(url,"该编码已存在，不能重复编码"));
	   	    	return null;
	   	    }
	   	    
	   	    if(channelGroup.getId()!=null){
	   	        //修改流程
	  	    	channelGroupManagerService.updateChannelGroup(channelGroup);
	   	        //操作日志
	   	        operInfo = channelGroup.toString();
	   			operType = "operate.update";
	   			operLog = this.setOperationLog(Constant.OPERATE_MODULE_CHANNEL_GROUP);
	   			operateLogService.saveOperateLog(operLog);
	   	    }else{
	   	        //新增流程
	   	    	channelGroupManagerService.saveChannelGroup(channelGroup);
	   	        
	   	        //操作日志
	   	        operInfo = channelGroup.toString();
	   			operType = "operate.add";
	   			operLog = this.setOperationLog(Constant.OPERATE_MODULE_CHANNEL_GROUP);
	   			operateLogService.saveOperateLog(operLog);
	   	    }
	   	    channelGroupQuery = null; 
	   	    queryChannelGroupList();
       }catch(Exception e){
    	   e.printStackTrace();
    	   logger.error("保存频道组异常", e);
       }
        return SUCCESS;
    }
	
	
   public String initChannelGroup(){
  
        try{
        	int channelGroupId = Integer.valueOf(request.getParameter("channelGroupId"));      
            channelGroup = channelGroupManagerService.getChannelGroupByID(channelGroupId);
        }catch(Exception e){
        	e.printStackTrace();
        	logger.error("初始化频道组信息异常", e);
        }


        return SUCCESS;
    }
	
   
   public String queryChannelGroupRefList(){
				
		try{
			if (channelQuery==null)
			{
				channelQuery = new NpvrChannelInfo();
				if(channelGroupId!=null){
					channelQuery.setChannelGroupId(Integer.valueOf(channelGroupId));
				}
				
			}
			else
			{
				channelQuery.setChannelGroupId(Integer.valueOf(channelGroupId));
			}
		
			
			channelListPage = channelGroupManagerService.queryChannelGroupRefList(channelQuery, channelListPage.getPageSize(), channelListPage.getPageNo());
				
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询频道组所属频道列表异常", e);
		}
		return SUCCESS;
	}
   
   public String showChannelGroupRefList(){
		
		try{
			if (channelQuery==null)
			{
				channelQuery = new NpvrChannelInfo();
				if(channelGroupId!=null){
					channelQuery.setChannelGroupId(Integer.valueOf(channelGroupId));
				}
				
			}
			else
			{
				channelQuery.setChannelGroupId(Integer.valueOf(channelGroupId));
			}
		
			
			channelListPage = channelGroupManagerService.queryChannelGroupRefList(channelQuery, channelListPage.getPageSize(), channelListPage.getPageNo());
				
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询频道组所属频道列表异常", e);
		}
		return SUCCESS;
	}
   public String selectChannel(){
	   
	   if(selChannelIds!=null&&!selChannelIds.equals("")){
			if(selChannelIdList==null){
				selChannelIdList=new ArrayList(){};			
			}
			
			String[] ids=selChannelIds.split(",");
			for (int i=0;i<ids.length;i++) {
				selChannelIdList.add(ids[i]);
			}
			HashSet h = new HashSet(selChannelIdList);
			selChannelIdList.clear();
			selChannelIdList.addAll(h);

		}
	   
	   if(channelGroupIdTemp==null||channelGroupIdTemp.equals("")||channelGroupIdTemp.equals(" ")){
		   channelGroupIdTemp = channelGroupId.toString();
	   }
	   
       if (selectchannelPage==null)
       {
    	   selectchannelPage  =  new PageBeanDB();
       }
       try {
           if(selectChannelQuery==null){
               selectChannelQuery = new NpvrChannelInfo();
           }
           selectChannelQuery.setChannelGroupId(channelGroupId);
    	   selectchannelPage = channelGroupManagerService.selectChannelList(selectChannelQuery, selectchannelPage.getPageSize(), selectchannelPage.getPageNo());

       } catch (Exception e) {
    	   e.printStackTrace();
           logger.error("获取频道列表时出现异常",e);
       }
       
       return SUCCESS;
   }
   
   
   
   public void saveChannelGroupRef() {	   
	    try{
	    	List<NpvrChannelGroupRef> channelGroupRefList = new ArrayList<NpvrChannelGroupRef>();
		    String[] ids = channelIds.split(",");
		    for(int i=0;i<ids.length;i++){
		    	NpvrChannelGroupRef temp =new NpvrChannelGroupRef();
		    	if(channelGroupIdTemp!=null&&!channelGroupIdTemp.equals("")){
		    		temp.setChannelGroupId(Integer.valueOf(channelGroupIdTemp));
		    	}		    	
		    	temp.setChannelId(Integer.valueOf(ids[i].trim()));		    	
		    	channelGroupRefList.add(temp);
		    }		    
		    if(channelGroupRefList!=null&&channelGroupRefList.size()>0){
		    	channelGroupManagerService.saveChannelGroupRefList(channelGroupRefList);
	        }
	    }catch(Exception e){
	    	e.printStackTrace();
	    	logger.error("保存频道组频道关联关系异常", e);
	    }
   }
   
   
   public String deleteChannelGroupRef(){
       if(StringUtils.isNotBlank(dataIds)){
           try {
               
               boolean flag = channelGroupManagerService.deleteChannelGroupRef(dataIds);
               if(flag){
               	//记录操作日志
               	StringBuffer delInfo = new StringBuffer();
           		delInfo.append("删除频道组频道关联关系：");
                delInfo.append("共").append(dataIds.split(",").length).append("条记录(channelIds:"+dataIds+")");
       			operType = "operate.delete";
       			operInfo = delInfo.toString();
       			operLog = this.setOperationLog(Constant.OPERATE_MODULE_CHANNEL_GROUP);
       			operateLogService.saveOperateLog(operLog);
                
       			channelQuery=null;
       			//channelGroupId=
       			queryChannelGroupRefList();
       			return SUCCESS;
               }
           } catch (RuntimeException e) {
               e.printStackTrace();
               logger.error("删除频道组频道关联关系异常", e);
           }
       }
       return NONE;
   }
   
   
   public String deleteChannelGroup(){
       if(StringUtils.isNotBlank(dataIds)){
           try {
               
               boolean flag = channelGroupManagerService.deleteChannelGroup(dataIds);
               if(flag){
               	//记录操作日志
               	StringBuffer delInfo = new StringBuffer();
           		delInfo.append("删除频道组：");
                delInfo.append("共").append(dataIds.split(",").length).append("条记录(ids:"+dataIds+")");
       			operType = "operate.delete";
       			operInfo = delInfo.toString();
       			operLog = this.setOperationLog(Constant.OPERATE_MODULE_CHANNEL_GROUP);
       			operateLogService.saveOperateLog(operLog);
                
       			channelGroupQuery = null; 
       	        queryChannelGroupList();
       			return SUCCESS;
               }
           } catch (RuntimeException e) {
               e.printStackTrace();
               logger.error("删除频道组异常", e);
           }
       }
       return NONE;
   }
   
	

	public PageBeanDB getChannelGroupPage() {
		return channelGroupPage;
	}

	public void setChannelGroupPage(PageBeanDB channelGroupPage) {
		this.channelGroupPage = channelGroupPage;
	}

	

	public TNpvrChannelGroup getChannelGroupQuery() {
		return channelGroupQuery;
	}


	public void setChannelGroupQuery(TNpvrChannelGroup channelGroupQuery) {
		this.channelGroupQuery = channelGroupQuery;
	}


	public OperateLogService getOperateLogService() {
		return operateLogService;
	}

	public void setOperateLogService(OperateLogService operateLogService) {
		this.operateLogService = operateLogService;
	}

	public NpvrChannelGroupManagerService getChannelGroupManagerService() {
		return channelGroupManagerService;
	}

	public void setChannelGroupManagerService(
			NpvrChannelGroupManagerService channelGroupManagerService) {
		this.channelGroupManagerService = channelGroupManagerService;
	}





	public TNpvrChannelGroup getChannelGroup() {
		return channelGroup;
	}


	public void setChannelGroup(TNpvrChannelGroup channelGroup) {
		this.channelGroup = channelGroup;
	}


	public PageBeanDB getChannelListPage() {
		return channelListPage;
	}


	public void setChannelListPage(PageBeanDB channelListPage) {
		this.channelListPage = channelListPage;
	}


	public NpvrChannelInfo getChannelQuery() {
		return channelQuery;
	}


	public void setChannelQuery(NpvrChannelInfo channelQuery) {
		this.channelQuery = channelQuery;
	}


	public Integer getChannelGroupId() {
		return channelGroupId;
	}


	public void setChannelGroupId(Integer channelGroupId) {
		this.channelGroupId = channelGroupId;
	}


	public PageBeanDB getSelectchannelPage() {
		return selectchannelPage;
	}


	public void setSelectchannelPage(PageBeanDB selectchannelPage) {
		this.selectchannelPage = selectchannelPage;
	}




	public NpvrChannelInfo getSelectChannelQuery() {
		return selectChannelQuery;
	}


	public void setSelectChannelQuery(NpvrChannelInfo selectChannelQuery) {
		this.selectChannelQuery = selectChannelQuery;
	}


	public String getChannelIds() {
		return channelIds;
	}


	public void setChannelIds(String channelIds) {
		this.channelIds = channelIds;
	}


	public String getDataIds() {
		return dataIds;
	}


	public void setDataIds(String dataIds) {
		this.dataIds = dataIds;
	}


	public String getChannelGroupIdTemp() {
		return channelGroupIdTemp;
	}


	public void setChannelGroupIdTemp(String channelGroupIdTemp) {
		this.channelGroupIdTemp = channelGroupIdTemp;
	}


	public String getSelChannelIds() {
		return selChannelIds;
	}


	public void setSelChannelIds(String selChannelIds) {
		this.selChannelIds = selChannelIds;
	}


	public List getSelChannelIdList() {
		return selChannelIdList;
	}


	public void setSelChannelIdList(List selChannelIdList) {
		this.selChannelIdList = selChannelIdList;
	}

	
	

	
	
}
