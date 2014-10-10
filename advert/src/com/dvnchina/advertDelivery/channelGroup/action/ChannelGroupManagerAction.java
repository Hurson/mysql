package com.dvnchina.advertDelivery.channelGroup.action;

import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import com.alibaba.fastjson.JSON;
import com.dvnchina.advertDelivery.action.BaseActionSupport;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.bean.contract.ContractContractADRelation;
import com.dvnchina.advertDelivery.bean.contract.ContractQueryBean;
import com.dvnchina.advertDelivery.channelGroup.bean.ChannelGroupRef;
import com.dvnchina.advertDelivery.channelGroup.bean.TChannelGroup;
import com.dvnchina.advertDelivery.channelGroup.service.ChannelGroupManagerService;
import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.constant.LoginConstant;
import com.dvnchina.advertDelivery.constant.PageConstant;
import com.dvnchina.advertDelivery.contract.bean.AdvertPositionPackage;
import com.dvnchina.advertDelivery.contract.bean.PositionAD;
import com.dvnchina.advertDelivery.contract.service.ContractManagerService;
import com.dvnchina.advertDelivery.log.bean.OperateLog;
import com.dvnchina.advertDelivery.log.service.OperateLogService;
import com.dvnchina.advertDelivery.model.Contract;
import com.dvnchina.advertDelivery.model.ContractAD;
import com.dvnchina.advertDelivery.model.ContractADBackup;
import com.dvnchina.advertDelivery.model.ContractBackup;
import com.dvnchina.advertDelivery.model.Customer;
import com.dvnchina.advertDelivery.model.CustomerBackUp;
import com.dvnchina.advertDelivery.model.MarketingRule;
import com.dvnchina.advertDelivery.model.Resource;
import com.dvnchina.advertDelivery.ploy.bean.PloyAreaChannel;
import com.dvnchina.advertDelivery.ploy.bean.PloyChannel;
import com.dvnchina.advertDelivery.ploy.bean.TPreciseMatchBk;
import com.dvnchina.advertDelivery.ploy.service.PloyService;
import com.dvnchina.advertDelivery.service.ContractBackupService;
import com.dvnchina.advertDelivery.service.ContractRunService;
import com.dvnchina.advertDelivery.sysconfig.bean.ChannelInfo;
import com.dvnchina.advertDelivery.utils.CookieUtils;
import com.dvnchina.advertDelivery.utils.json.Obj2JsonUtil;
import com.dvnchina.advertDelivery.utils.page.PageBean;
import com.dvnchina.advertDelivery.utils.page.PageUtils;

public class ChannelGroupManagerAction extends BaseActionSupport<Object> implements ServletRequestAware{
	
	private static final long serialVersionUID = -3666982468062423696L;
	
	private Logger logger = Logger.getLogger(ChannelGroupManagerAction.class);
	
	private PageBeanDB channelGroupPage =  new PageBeanDB();//频道组列表
	
	private PageBeanDB channelListPage =  new PageBeanDB();//频道列表
	
	private PageBeanDB selectchannelPage =  new PageBeanDB();//频道选择列表
	
	private TChannelGroup channelGroupQuery;//频道组列表页面查询条件
	
	private ChannelInfo channelQuery;//频道列表页面查询条件
	
	private ChannelInfo selectChannelQuery;//频道选择列表页面查询条件
	
	private String dataIds;
	
	private String channelIds;
	
	private Integer channelGroupId;
	private String channelGroupIdTemp;
	
	private TChannelGroup channelGroup;
	
	private HttpServletRequest request;
	
	
	private ChannelGroupManagerService channelGroupManagerService;
	
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
			channelGroupQuery  =  new TChannelGroup();
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
   	    	String url = "queryChannelGroupList.do"; 
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
				channelQuery = new ChannelInfo();
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
				channelQuery = new ChannelInfo();
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
               selectChannelQuery = new ChannelInfo();
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
	    	List<ChannelGroupRef> channelGroupRefList = new ArrayList<ChannelGroupRef>();
		    String[] ids = channelIds.split(",");
		    for(int i=0;i<ids.length;i++){
		    	ChannelGroupRef temp =new ChannelGroupRef();
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

	

	public TChannelGroup getChannelGroupQuery() {
		return channelGroupQuery;
	}

	public void setChannelGroupQuery(TChannelGroup channelGroupQuery) {
		this.channelGroupQuery = channelGroupQuery;
	}

	public ChannelGroupManagerService getChannelGroupManagerService() {
		return channelGroupManagerService;
	}

	public void setChannelGroupManagerService(
			ChannelGroupManagerService channelGroupManagerService) {
		this.channelGroupManagerService = channelGroupManagerService;
	}

	public OperateLogService getOperateLogService() {
		return operateLogService;
	}

	public void setOperateLogService(OperateLogService operateLogService) {
		this.operateLogService = operateLogService;
	}


	public TChannelGroup getChannelGroup() {
		return channelGroup;
	}


	public void setChannelGroup(TChannelGroup channelGroup) {
		this.channelGroup = channelGroup;
	}


	public PageBeanDB getChannelListPage() {
		return channelListPage;
	}


	public void setChannelListPage(PageBeanDB channelListPage) {
		this.channelListPage = channelListPage;
	}


	public ChannelInfo getChannelQuery() {
		return channelQuery;
	}


	public void setChannelQuery(ChannelInfo channelQuery) {
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


	public ChannelInfo getSelectChannelQuery() {
		return selectChannelQuery;
	}


	public void setSelectChannelQuery(ChannelInfo selectChannelQuery) {
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
