package com.avit.dtmb.channelGroup.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.avit.dtmb.channelGroup.bean.DChannelGroup;
import com.avit.dtmb.channelGroup.bean.DChannelGroupRef;
import com.avit.dtmb.channelGroup.bean.DChannelInfoSync;
import com.avit.dtmb.channelGroup.service.ChannelGroupService;
import com.dvnchina.advertDelivery.action.BaseActionSupport;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.channelGroup.action.ChannelGroupManagerAction;
import com.dvnchina.advertDelivery.channelGroup.bean.ChannelGroupRef;
import com.dvnchina.advertDelivery.channelGroup.bean.TChannelGroup;
import com.dvnchina.advertDelivery.common.BaseAction;
import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.log.bean.OperateLog;
import com.dvnchina.advertDelivery.log.service.OperateLogService;
import com.dvnchina.advertDelivery.model.UserLogin;
import com.dvnchina.advertDelivery.sysconfig.bean.ChannelInfo;

@ParentPackage("default")
@Namespace("/dchannelGroup")
@Scope("prototype")
@Controller
public class ChannelGroupAction extends BaseActionSupport<Object> implements ServletRequestAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2134222271562713929L;
	private Logger logger = Logger.getLogger(ChannelGroupManagerAction.class);
	
	@Resource
	private ChannelGroupService channelGroupService;
	
	
	private PageBeanDB channelGroupPage =  new PageBeanDB();
	private PageBeanDB channelListPage =  new PageBeanDB();
	private PageBeanDB selectchannelPage =  new PageBeanDB();//频道选择列表
	private DChannelInfoSync selectChannelQuery;//频道选择列表页面查询条件
	private DChannelGroup channelGroupQuery;//频道组列表页面查询条件
	private DChannelGroup channelGroup;
	private DChannelInfoSync channelQuery;//频道列表页面查询条件
	private Long channelGroupId;
	private String channelGroupType;
	public OperateLog operLog;
	private OperateLogService operateLogService;
	private String dataIds;
	private String selChannelIds;
	private List selChannelIdList;
	private String channelGroupIdTemp;
	private String serviceIds;
	private String selServiceIds;
	
	@Action(value = "queryChanelGroupList",results={@Result(name="success",location="/page/channelGroup/dtmbChannelGroup/channelGroupList.jsp")})
	public String queryChanelGroupList(){
		if (channelGroupPage==null)
		{
			channelGroupPage  =  new PageBeanDB();
		}
		if (channelGroupQuery==null)
        {
			channelGroupQuery  =  new DChannelGroup();
        }
		try {	        
		    
			channelGroupPage = channelGroupService.queryChanelGroupList(channelGroupQuery, channelGroupPage.getPageSize(), channelGroupPage.getPageNo());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取频道组列表时出现异常",e);
		}
		
		return SUCCESS;
		
		
				
	}
	@Action(value = "intoAddChannelGroup",results={@Result(name = "success",location = "/page/channelGroup/dtmbChannelGroup/addChannelGroup.jsp")})
	public String intoAddChannelGroup(){
		return SUCCESS;
	}
	@Action(value = "saveChannelGroup",results={@Result(name="success",location="/page/channelGroup/dtmbChannelGroup/channelGroupList.jsp")})
	public String saveChannelGroup() {
	    
	       try{
	    	   String channelGroupDesc = channelGroup.getChannelDesc().trim();
	   	    channelGroup.setChannelDesc(channelGroupDesc);
	   	    HttpSession session = ServletActionContext.getRequest().getSession();
	   	    UserLogin user = (UserLogin)session.getAttribute("USER_LOGIN_INFO");
	   	    //modified by liuwenping 如果编码已存在，不能保存（更新）
	   	    String code = channelGroup.getCode().trim();
	   	    int count = channelGroupService.getEntityCountByCode(code);
	   	    if(count > 0 && channelGroup.getId()==null){ //存在该编码
	   	    	String url = "queryChannelGroupList.do"; 
	   	    	this.renderHtml(generateMessage(url,"该编码已存在，不能重复编码"));
	   	    	return null;
	   	    }
	   	  
	   	    if(channelGroup.getId()!=null){
	   	        //修改流程
	   	    	channelGroupService.updateChannelGroup(channelGroup);
	   	        //操作日志
	   	        operInfo = channelGroup.toString();
	   			operType = "operate.update";
	   			operLog = this.setOperationLog(Constant.OPERATE_MODULE_CHANNEL_GROUP);
	   			operateLogService.saveOperateLog(operLog);
	   	    }else{
	   	    	channelGroup.setOperatorId(user.getUserId());
	   	        //新增流程
	   	    	channelGroupService.saveChannelGroup(channelGroup);
	   	        
	   	        //操作日志
	   	        operInfo = channelGroup.toString();
	   			operType = "operate.add";
	   			operLog = this.setOperationLog(Constant.OPERATE_MODULE_CHANNEL_GROUP);
	   			operateLogService.saveOperateLog(operLog);
	   	    }
	   	   channelGroupQuery = null; 
	   	   queryChanelGroupList();
	   	    
	       }catch(Exception e){
	    	   e.printStackTrace();
	    	   logger.error("保存频道组异常", e);
	       }
	        return SUCCESS;
	    }

	//删除频道组
	@Action(value = "deleteChannelGroup")
	public String deleteChannelGroup(){
	       if(StringUtils.isNotBlank(dataIds)){
	           try {
	               
	               boolean flag = channelGroupService.deleteChannelGroup(dataIds);
	               if(flag){
	               	//记录操作日志
	               	/*StringBuffer delInfo = new StringBuffer();
	           		delInfo.append("删除频道组：");
	                delInfo.append("共").append(dataIds.split(",").length).append("条记录(ids:"+dataIds+")");
	       			operType = "operate.delete";
	       			operInfo = delInfo.toString();
	       			operLog = this.setOperationLog(Constant.OPERATE_MODULE_CHANNEL_GROUP);
	       			operateLogService.saveOperateLog(operLog);*/
	                
	       			channelGroupQuery = null; 
	       	        queryChanelGroupList();
	       			return SUCCESS;
	               }
	           } catch (RuntimeException e) {
	               e.printStackTrace();
	               logger.error("删除频道组异常", e);
	           }
	       }
	       return NONE;
	   }
	
	@Action(value = "queryChannelGroupRefList",results={@Result(name = "success",location = "/page/channelGroup/dtmbChannelGroup/channelGroupRefList.jsp")})
	public String queryChannelGroupRefList(){
		
		try{
			if (channelQuery==null)
			{
				channelQuery = new DChannelInfoSync();
				if(channelGroupId!=null){
					channelQuery.setChannelGroupId(Long.valueOf(channelGroupId));
				}
				
			}
			else
			{
				channelQuery.setChannelGroupId(Long.valueOf(channelGroupId));
			}
			channelListPage = channelGroupService.queryChannelGroupRefList(channelQuery, channelListPage.getPageSize(), channelListPage.getPageNo());
			channelListPage.setChannelGroupType(channelGroupType);	
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询频道组所属频道列表异常", e);
		}
		return SUCCESS;
	}
	
	 @Action(value = "selectChannel",results={@Result(name = "success",location = "/page/channelGroup/dtmbChannelGroup/selectChannel.jsp")})
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
	               selectChannelQuery = new DChannelInfoSync();
	           }
	           
	           selectChannelQuery.setChannelGroupId(channelGroupId);
	        	   
	    	   selectchannelPage = channelGroupService.selectChannelList(selectChannelQuery, selectchannelPage.getPageSize(), selectchannelPage.getPageNo(),channelGroupType);
	       } catch (Exception e) {
	    	   e.printStackTrace();
	           logger.error("获取频道列表时出现异常",e);
	       }
	       
	       return SUCCESS;
	   }
	 @Action(value = "saveChannelGroupRef",results={@Result(name = "success",location = "/page/channelGroup/dtmbChannelGroup/channelGroupRefList.jsp")})
	 public String saveChannelGroupRef() {	   
		    try{
		    	List<DChannelGroupRef> channelGroupRefList = new ArrayList<DChannelGroupRef>();
			    String[] ids = selServiceIds.split(",");
			    for(int i=0;i<ids.length;i++){
			    	DChannelGroupRef temp =new DChannelGroupRef();
			    	if(channelGroupIdTemp!=null&&!channelGroupIdTemp.equals("")){
			    		temp.setGroupId(Long.valueOf(channelGroupIdTemp));
			    	}
			    	
			    	temp.setServiceId(ids[i].trim());
			    	
			    	channelGroupRefList.add(temp);
			    }
			    
			    if(channelGroupRefList!=null&&channelGroupRefList.size()>0){
			    	channelGroupService.saveChannelGroupRefList(channelGroupRefList);

		        }
		    }catch(Exception e){
		    	e.printStackTrace();
		    	logger.error("保存频道组频道关联关系异常", e);
		    }
		    return SUCCESS;
	   }
	 /**
	  * 删除关联
	  * @return
	  */
	 @Action(value = "deleteChannelGroupRef",results={@Result(name = "success",location = "/page/channelGroup/dtmbChannelGroup/channelGroupRefList.jsp")})
	 public String deleteChannelGroupRef(){
	       if(StringUtils.isNotBlank(dataIds)){
	           try {
	               
	               boolean flag = channelGroupService.deleteChannelGroupRef(dataIds);
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
	 
	 /*public String selectChannelIds(){
		   
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

			}*/
	@Action(value="initChannelGroup",results={@Result(name = "success",location = "/page/channelGroup/dtmbChannelGroup/addChannelGroup.jsp")})	   
	 public String initChannelGroup(){
		  
	        try{
	            channelGroup = channelGroupService.getChannelGroupByID(channelGroupId);
	            channelGroup.setType(channelGroupType);
	        }catch(Exception e){
	        	e.printStackTrace();
	        	logger.error("初始化频道组信息异常", e);
	        }


	        return SUCCESS;
	    }
	
	
	public PageBeanDB getChannelGroupPage() {
		return channelGroupPage;
	}



	public void setChannelGroupPage(PageBeanDB channelGroupPage) {
		this.channelGroupPage = channelGroupPage;
	}



	public PageBeanDB getChannelListPage() {
		return channelListPage;
	}



	public void setChannelListPage(PageBeanDB channelListPage) {
		this.channelListPage = channelListPage;
	}



	public DChannelGroup getChannelGroupQuery() {
		return channelGroupQuery;
	}



	public void setChannelGroupQuery(DChannelGroup channelGroupQuery) {
		this.channelGroupQuery = channelGroupQuery;
	}
	
	
	
	public DChannelGroup getChannelGroup() {
		return channelGroup;
	}
	
	
	
	public void setChannelGroup(DChannelGroup channelGroup) {
		this.channelGroup = channelGroup;
	}
	
	
	public Logger getLogger() {
		return logger;
	}
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	public OperateLog getOperLog() {
		return operLog;
	}
	public void setOperLog(OperateLog operLog) {
		this.operLog = operLog;
	}
	
	
	public String getDataIds() {
		return dataIds;
	}
	public void setDataIds(String dataIds) {
		this.dataIds = dataIds;
	}
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		
	}
	public DChannelInfoSync getChannelQuery() {
		return channelQuery;
	}
	public void setChannelQuery(DChannelInfoSync channelQuery) {
		this.channelQuery = channelQuery;
	}
	public Long getChannelGroupId() {
		return channelGroupId;
	}
	public void setChannelGroupId(Long channelGroupId) {
		this.channelGroupId = channelGroupId;
	}
	public String getChannelGroupType() {
		return channelGroupType;
	}
	public void setChannelGroupType(String channelGroupType) {
		this.channelGroupType = channelGroupType;
	}
	public PageBeanDB getSelectchannelPage() {
		return selectchannelPage;
	}
	public void setSelectchannelPage(PageBeanDB selectchannelPage) {
		this.selectchannelPage = selectchannelPage;
	}
	public DChannelInfoSync getSelectChannelQuery() {
		return selectChannelQuery;
	}
	public void setSelectChannelQuery(DChannelInfoSync selectChannelQuery) {
		this.selectChannelQuery = selectChannelQuery;
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
	public String getChannelGroupIdTemp() {
		return channelGroupIdTemp;
	}
	public void setChannelGroupIdTemp(String channelGroupIdTemp) {
		this.channelGroupIdTemp = channelGroupIdTemp;
	}
	public ChannelGroupService getChannelGroupService() {
		return channelGroupService;
	}
	public void setChannelGroupService(ChannelGroupService channelGroupService) {
		this.channelGroupService = channelGroupService;
	}
	public OperateLogService getOperateLogService() {
		return operateLogService;
	}
	public void setOperateLogService(OperateLogService operateLogService) {
		this.operateLogService = operateLogService;
	}
	public String getServiceIds() {
		return serviceIds;
	}
	public void setServiceId(String serviceIds) {
		this.serviceIds = serviceIds;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getSelServiceIds() {
		return selServiceIds;
	}
	public void setSelServiceIds(String selServiceIds) {
		this.selServiceIds = selServiceIds;
	}
	public void setServiceIds(String serviceIds) {
		this.serviceIds = serviceIds;
	}
	
	
	
}
