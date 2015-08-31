package com.dvnchina.advertDelivery.ploy.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.dvnchina.advertDelivery.action.CustomerAction;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.bean.marketingRule.MarketingRuleBean;
import com.dvnchina.advertDelivery.common.BaseAction;
import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.constant.CustomerConstant;
import com.dvnchina.advertDelivery.log.bean.OperateLog;
import com.dvnchina.advertDelivery.log.service.OperateLogService;
import com.dvnchina.advertDelivery.model.ChannelInfo;
import com.dvnchina.advertDelivery.model.Contract;
import com.dvnchina.advertDelivery.model.ContractAD;
import com.dvnchina.advertDelivery.model.Customer;
import com.dvnchina.advertDelivery.model.MarketingRule;
import com.dvnchina.advertDelivery.model.Ploy;
import com.dvnchina.advertDelivery.model.ReleaseArea;
import com.dvnchina.advertDelivery.model.UserLogin;
import com.dvnchina.advertDelivery.ploy.bean.PloyAreaChannel;
import com.dvnchina.advertDelivery.ploy.bean.PloyBackup;
import com.dvnchina.advertDelivery.ploy.bean.PloyChannel;
import com.dvnchina.advertDelivery.ploy.bean.PloyTimeCGroup;
import com.dvnchina.advertDelivery.ploy.bean.PreciseUiBean;
import com.dvnchina.advertDelivery.ploy.bean.TPreciseMatch;
import com.dvnchina.advertDelivery.ploy.bean.TPreciseMatchBk;
import com.dvnchina.advertDelivery.ploy.service.PloyService;
import com.dvnchina.advertDelivery.ploy.service.PreciseMatchService;
import com.dvnchina.advertDelivery.position.bean.AdvertPosition;
import com.dvnchina.advertDelivery.utils.StringUtil;
import com.dvnchina.advertDelivery.utils.json.Obj2JsonUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.dvnchina.advertDelivery.order.bean.MenuType;
import com.dvnchina.advertDelivery.model.PreciseMatch;

public class PloyAction extends BaseAction implements ServletRequestAware{
	private HttpServletRequest request;
	private Logger logger = Logger.getLogger(PloyAction.class);
	
	private OperateLogService operateLogService = null;
	
	private PloyService ployService;
	PreciseMatchService preciseservice;
	private PageBeanDB pagePreciseMatch =  new PageBeanDB();
	private PloyBackup ploy =new PloyBackup() ;
	private List<PloyAreaChannel> lstPloyAreaChannel;
	private List<ReleaseArea> lstReleaseArea;
	private List<ChannelInfo> lstChannelInfo;
	private List<MenuType> lstMenuType;
	private Contract contract=new Contract();
	private AdvertPosition adPosition = new AdvertPosition();
	private MarketingRule rule =new MarketingRule();
	private PageBeanDB pagePloy =  new PageBeanDB();
	private PageBeanDB pageContract=  new PageBeanDB() ;
	private PageBeanDB pageCustomer=  new PageBeanDB() ;
	private PageBeanDB pageAdPosition =  new PageBeanDB();
	private PageBeanDB pageAdPositionType=  new PageBeanDB() ;
	private PageBeanDB pageRule=  new PageBeanDB() ;
	
	private PageBeanDB pageArea=  new PageBeanDB() ;
	private PageBeanDB pageChannel=  new PageBeanDB() ;
	private PageBeanDB pageMenutype = new PageBeanDB();
	private String areasJson;
	private int contractId; 
	private int postionId; 
	private int ruleId; 
	private String startTime;
	private String endTime;
	private int areaId;

	private String dataIds;
	private String areaids;
	//用于显示合同，广告位已有规则
	private String ploysJson;
	private String postionJson;
	//策略基本参数 时间段 频道组  优先级 
	//策略时间数组  如页面提交为空，则设置默认0
	private String starttime,endtime;
	//频道组IDS 如页面提交为空，则设置默认0
	private String channelgroup;
	private String bchannelgroup;
	//频道组类别（直播、回看）  add by lwp
	private String channelGroupType; 
	
	private String priority;
	private PloyTimeCGroup ployTimeCGroup;
	private PreciseUiBean preciseUiBean;
	private PreciseUiBean areaChannelUiBean;
	
	private String tvnexport;
	
	private PageBeanDB pageLocation = new PageBeanDB();
	private PageBeanDB pageReleaseLocation = new PageBeanDB();
	
	public PloyBackup getPloy() {
		return ploy;
	}
	public void setPloy(PloyBackup ploy) {
		this.ploy = ploy;
	}
	public void initData()
	{
		//查询操作员可操作广告商ID
		//contract.setId(1);
		//初始化合同列表
		
		pageContract.setPageSize(1000);
		pageContract =  ployService.queryContract(null,null,pageContract.getPageSize(),pageContract.getPageNo());
		
		pageCustomer.setPageSize(1000);
		pageCustomer = ployService.queryCustomer(pageCustomer.getPageSize(), pageCustomer.getPageNo());
		//初始化广告位列表
		pageAdPosition.setPageSize(1000);
		pageAdPosition = ployService.queryAdPosition(null,null, pageAdPosition.getPageSize(),pageAdPosition.getPageNo());
		pageReleaseLocation = ployService.queryAreaList(null, 1, 100);
		
		//初始化规则
		//pageRule.setPageSize(1000);
	    //pageRule = ployService.queryAdPositionRule(contract.getId(), adPosition.getId(), rule,pageRule.getPageSize(),pageRule.getPageNo());
		
	}
	/**
	 * 添加营销规则
	 * @return
	 */
	public String initPloy(){
		int ployId = Integer.valueOf(request.getParameter("ployId"));
		
		// added by liuwenping
		String state = request.getParameter("state");
		
		pageAdPosition.setPageSize(1000);
	
		pageCustomer.setPageSize(100000);
		pageCustomer = ployService.queryCustomer(pageCustomer.getPageSize(), pageCustomer.getPageNo());
		//初始化广告位列表
		pageAdPosition.setPageSize(1000);
		pageAdPosition = ployService.queryAdPosition(null,null, pageAdPosition.getPageSize(),pageAdPosition.getPageNo());
		
		ploy = ployService.getPloyByPloyID(ployId);
		
		// added by liuwenping
		ploy.setState(state);
		ployTimeCGroup = ployService.getPloyTimeCGroupByPloyID(ployId, channelGroupType);
		List releaseAreaList = new ArrayList();
		String [] areas = ployTimeCGroup.getAreas().split(",");
		for (int i=0;i<areas.length;i++)
		{
			if (!"0".equals(areas[i]))  
			{
				releaseAreaList.add(areas[i]);
			}
		}
		areaChannelUiBean =  new PreciseUiBean();
		areaChannelUiBean.setUserAreaList(releaseAreaList);
		preciseUiBean  = ployService.getPreciseUiBeanByPloyID(ployId);
/*		lstMenuType = ployService.getMenuTypeByPloyID(ployId);
*/		
		
		postionJson = Obj2JsonUtil.list2json(pageAdPosition.getDataList());
		pageLocation =preciseservice.queryLocationCodeList(null, 100000, 1);
		
		String areaCodes = getLoginUser().getAreaCodes();
		pageReleaseLocation = ployService.queryAreaListByCodes(areaCodes, 1, 100);

		return SUCCESS;		
		
		
	}
	
	public String getCheckPloyById(){
		int ployId = Integer.valueOf(request.getParameter("ployId"));
		pageAdPosition.setPageSize(1000);
		pageCustomer.setPageSize(1000);
		pageCustomer = ployService.queryCustomer(pageCustomer.getPageSize(), pageCustomer.getPageNo());
		//初始化广告位列表
		pageAdPosition.setPageSize(1000);
		pageAdPosition = ployService.queryAdPosition(null,null, pageAdPosition.getPageSize(),pageAdPosition.getPageNo());
		
		ploy = ployService.getPloyByPloyID(ployId);
		
        ployTimeCGroup = ployService.getPloyTimeCGroupByPloyID(ployId, this.channelGroupType);
           
		//add 增加区域属性
		List releaseAreaList = new ArrayList();
		String [] areas = ployTimeCGroup.getAreas().split(",");
		for (int i=0;i<areas.length;i++)
		{
			if (!"0".equals(areas[i]))      
			{
				releaseAreaList.add(areas[i]);
			}
		}
		areaChannelUiBean =  new PreciseUiBean();
		areaChannelUiBean.setUserAreaList(releaseAreaList);
		//end add 
		
		preciseUiBean  = ployService.getPreciseUiBeanByPloyID(ployId);
		
		postionJson = Obj2JsonUtil.list2json(pageAdPosition.getDataList());
		pageLocation =preciseservice.queryLocationCodeList(null, 100000, 1);
		pageReleaseLocation = ployService.queryAreaList(null, 1, 100);		
		
		//pageContract.setPageSize(1000);
		//pageContract =  ployService.queryContract(contract,customerids,pageContract.getPageSize(),pageContract.getPageNo());
	
		
		/*lstPloyAreaChannel= ployService.getAreaChannelsByPloyID(ployId);
		request.setAttribute("ploy", ploy);
		areasJson = Obj2JsonUtil.list2json(lstPloyAreaChannel);
		ArrayList<test> array = new ArrayList<test>();
		test ss = new test();
		for (int i = 0; i < 3; i++) {
			ss.setArg1(""+i);
			ss.setArg2(""+i);
			ss.setArg3(i);
			ss.setArg4(i);
			array.add(ss);
		}
		request.setAttribute("select", array);
		*/
		return SUCCESS;
	}
	
	public String getContractByAduserId(){
		
		List<Contract> lstContract = ployService.getContractByAdMerchantId(1);
		request.setAttribute("contracts", lstContract);
		return SUCCESS;
	}
	
	/**
	 *  异步方法，根据合同编号获取对应的广告位
	 *  @param contractId 合同编码
	 */
	public String getAdSiteByContract(int contractId){
		List<ContractAD> lstContractAD = ployService.getAdSiteByContract(contractId);
		return SUCCESS;
	}
	/**
	 * 异步方法：根据广告位编码获取营销规则
	 * @param adSiteId 广告位编码
	 */
	public String getMarketRuleByAdSiteId(int contractId, int adSiteId){
		List<ContractAD> lstContractAD = ployService.getMarketRuleByAdSiteId(contractId, adSiteId);
		return SUCCESS;
	}
	
	/**
	 * 异步方法：根据营销规则获取规则的开始时间和结束时间
	 */
	public String getTimeSegmentsByMarketRule(int ruleId){
		String[] array = ployService.getTimeSegmentsByMarketRule(ruleId);
		return SUCCESS;
	}
	/**
	 * 异步方法：根据策略ID获取类型
	 */
	public String getMenuTypeByPloyId(int ployId){
		List<MenuType> lstMenuType = ployService.getMenuTypeByPloyID(ployId);
		return SUCCESS;
	}
	/**
	 * 判断策略是否被占用
	 * @param ployId
	 */
	public boolean judgePloyOccupied(String ployId){
		boolean flag = true;
		return flag;
	}
	
	/**
	 * 获取可以选择的区域。暂时不清楚业务
	 */
	public String getChoiceArea(){
		lstReleaseArea = ployService.getChoiceArea(contractId, postionId, ruleId, startTime, endTime);
		return SUCCESS;
	}
	
	/**
	 * 获取可以选择的频道。
	 */
	public String getChoiceChannels(){
		lstChannelInfo = ployService.getChoiceChannels(contractId, postionId, ruleId, startTime, endTime, areaId);
		return SUCCESS;
	}
	
	/**
	 * 根据区域查询可选择的频道
	 * @return
	 */

	//public String getChannelListByArea(int contractId, int adSiteId, int ruleId, String startTime, String endTime, int areaId){
	public String getChannelListByArea(){

		//pageChannel =ployService.queryChannelList(ploy,pageChannel.getPageSize(),pageChannel.getPageNo());
		return SUCCESS;
	}
	
	public String getAreaChannelsByPloyID(int ployId){
		List<PloyAreaChannel> list = ployService.getAreaChannelsByPloyID(ployId);
		return SUCCESS;
	}
	
	/**
	 * 添加营销规则
	 * @return
	 */
	public String insertPloy(){
		pageAdPosition.setPageSize(1000);
		String positionPackageIds = StringUtil.objListToString(getLoginUser().getPositionIds(), ",", "-1");
		pageAdPosition = ployService.getAdPositionByPackageIds(positionPackageIds, pageAdPosition.getPageSize(),pageAdPosition.getPageNo());
		postionJson = Obj2JsonUtil.list2json(pageAdPosition.getDataList());
		pageLocation =preciseservice.queryLocationCodeList(null, 1000, 1);
		String areaCodes = getLoginUser().getAreaCodes();
		pageReleaseLocation = ployService.queryAreaListByCodes(areaCodes, 1, 100);
		return SUCCESS;
	}
	
	/*
	 	public String insertPloy(){
		pageAdPosition.setPageSize(1000);
		List<Integer> customerList;
		List<Integer> positionPackageList;
		String customerids  = null;
		String postionPackageids  = null;
		UserLogin userLogin  = (UserLogin)ServletActionContext.getRequest().getSession().getAttribute("USER_LOGIN_INFO");
		if (userLogin==null)
		{
			userLogin = new UserLogin();
			userLogin.setRoleType(2);
			List<Integer> tempList =new ArrayList<Integer>();
			for (int i=1;i<40;i++)
			{
				tempList.add(i);
			}
			userLogin.setPositionIds(tempList);
			
		}
		if (userLogin.getRoleType()==1)
		{
			customerids = userLogin.getCustomerId().toString();			
		}
		else
		{
			positionPackageList = userLogin.getPositionIds();
			if (positionPackageList!=null && positionPackageList.size()>0)
			{
				postionPackageids="";
				for (int i=0;i<positionPackageList.size();i++)
				{
					postionPackageids = postionPackageids+positionPackageList.get(i);
					if (i<positionPackageList.size()-1)
					{
						postionPackageids = postionPackageids+",";
					}
				}
			}
		}
		pageAdPosition = ployService.queryAdPosition(customerids,postionPackageids, pageAdPosition.getPageSize(),pageAdPosition.getPageNo());
		postionJson = Obj2JsonUtil.list2json(pageAdPosition.getDataList());
		pageLocation =preciseservice.queryLocationCodeList(null, 1000, 1);
		pageReleaseLocation = ployService.queryAreaList(null, 1, 100);
		
		// modified by liuwenping 去掉河南这个区域
//		List<?> areaList = pageReleaseLocation.getDataList();
//		for(int i = areaList.size()-1; i >= 0; i--){
//			ReleaseArea entity = (ReleaseArea)areaList.get(i);
//			if("152000000000".equals(entity.getAreaCode())){
//				areaList.remove(i);
//				break;
//			}
//		}
		
		return SUCCESS;
	}
	  
	  */
	
	
	/**
	 * @return
	 */ 
	public String updatePloy(){
		
		MarketingRuleBean marketingRule = null;
		
		
		
		request.setAttribute("rule", marketingRule);
		return SUCCESS;
	}
	
	public String getDataIds() {
		return dataIds;
	}

	public void setDataIds(String dataIds) {
		this.dataIds = dataIds;
	}
	public String checkDelPloy()
	{
		String result="0";
		if (dataIds!=null)
		{
			dataIds = dataIds.replace(" ","");	
			String id[] = dataIds.split(",");
			
			if(!"null".equals(dataIds) && !"".equals(dataIds)){
				for(int i=0;i<id.length;i++){
					if(!"".equals(id[i]) && id[i] != null){
						
						int flag = ployService.checkDelPloyBackUpById(Integer.parseInt(id[i]));
						if (flag!=0)
						{
						   result = "1";
						   break;
						}
						else
						{
							result="0";	
						}
					}
				}
			}
		}
		//符合要求的的付给产出状态。
		
		//String json  = "{\"result\":"+result+",\"userId\":"+userId+"}";
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		print(result);
		return NONE;
	}
	
	public String deletePloy()
	{
		try
		{
			dataIds = dataIds.replace(" ","");
			ployService.deletePloyByIds(dataIds);
			message = "common.delete.success";//保存成功
		}catch(Exception e){
			message = "common.delete.failed";//保存失败
			operResult = Constant.OPERATE_FAIL;
			e.printStackTrace();
		}
		finally
		{
			operInfo ="删除策略：IDS="+dataIds;
			operType = "operate.delete";
			OperateLog operLog = this.setOperationLog(Constant.OPERATE_MODULE_PLOY);
			operateLogService.saveOperateLog(operLog);
		}		
		queryPloyList();
		return SUCCESS;
	}
	public String checkPloy()
	{
		//名称重复
		String flag ="";
		flag = ployService.checkPloy(ploy);
		print(flag);
		return NONE;
	}
	public String saveUpdatePloy(){

		//ployService.queryAreaList(null,10,1);
		boolean flag = false;
		if (null==starttime || starttime=="")
		{
			starttime="0";
		}
		if (null==endtime || endtime=="")
		{
			endtime="0";
		}
		if (null==channelgroup || channelgroup=="")
		{
			channelgroup="0";
		}
		
		
		if (null==priority || priority=="")
		{
			priority="1";
		}
		
		try {
			starttime = starttime.replace(" ","");
			endtime = endtime.replace(" ","");
			channelgroup = channelgroup.replace(" ","");
			priority = priority.replace(" ","");
			
			PloyTimeCGroup ployTimeCGroup = new PloyTimeCGroup();
			ployTimeCGroup.setStarttimes(starttime);
			ployTimeCGroup.setEndtimes(endtime);
			
			ployTimeCGroup.setChannelgroups(channelgroup);
			
			//区域
			if (ploy.getPositionId()==13 || ploy.getPositionId()==14   	//广播背景
					|| ploy.getPositionId()==1 || ploy.getPositionId()==2   //开机图片
					|| ploy.getPositionId()==45 || ploy.getPositionId()==46   //开机视频
					|| ploy.getPositionId()==47 || ploy.getPositionId()==48   //直播下排
					|| ploy.getPositionId()==49 || ploy.getPositionId()==50 //滚动字幕
					|| ploy.getPositionId() == 44|| ploy.getPositionId()==51 )   //热点推荐,NVOD主界面广告
			{
				if (areaChannelUiBean!=null && areaChannelUiBean.getUserArea()!=null && !areaChannelUiBean.getUserArea().equals(""))
				{
					ployTimeCGroup.setAreas(areaChannelUiBean.getUserArea().replace(" ",""));
				}
				else
				{
					//ployTimeCGroup.setAreas("152000000000");
					ployTimeCGroup.setAreas(getLoginUser().getAreaCodes());
				}
				if (null==bchannelgroup || bchannelgroup=="")
				{
					bchannelgroup="0";
				}
				bchannelgroup = bchannelgroup.replace(" ","");
				
				if(StringUtils.isNotBlank(this.channelGroupType)&& channelGroupType.substring(0,1).equals("3")){
					ployTimeCGroup.setChannelgroups(bchannelgroup);
				}
				
				if (preciseUiBean!=null)
				{
					preciseUiBean.setUserArea(null);
				}
			}
			ployTimeCGroup.setPriority(priority);
			UserLogin userLogin  = (UserLogin)ServletActionContext.getRequest().getSession().getAttribute("USER_LOGIN_INFO");
			
			if(StringUtils.isNotBlank(this.channelGroupType)){
				int channelGroupType = Integer.parseInt(this.channelGroupType.substring(0, 1));
				ploy.setChannelGroupType(channelGroupType);
			}
						
			if(ploy != null && (ploy.getPloyId() == null)){
				ploy.setOperatorId(this.getUserId().longValue());
				ploy.setOperationId(this.getUserId());
				operType = "operate.add";
				if (userLogin.getRoleType()==1)
				{
					ploy.setCustomerId(userLogin.getCustomerId());
				}
				else
				{
					ploy.setCustomerId(0);
				}				
				flag = ployService.savePloy(ployTimeCGroup, preciseUiBean, ploy);
			} else if(ploy != null && ploy.getPloyId() > 0) {
				operType = "operate.update";
				flag = ployService.updatePloy(ployTimeCGroup, preciseUiBean,ploy);
				ploy.setOperatorId(this.getUserId().longValue()); // 修改只在日志管理记录就行了
				ploy.setOperationId(this.getUserId());
			} else {
				return ERROR;
			}
			message = "common.save.success";//保存成功
		}catch(Exception e){
			message = "common.save.failed";//保存失败
			operResult = Constant.OPERATE_FAIL;
			e.printStackTrace();
		}
		finally
		{
			operInfo = ploy.toString();
			OperateLog operLog = this.setOperationLog(Constant.OPERATE_MODULE_PLOY);
			operateLogService.saveOperateLog(operLog);
		}		
		
		ploy= null;
		return queryPloyList();
		//return SUCCESS;
	}
	/*
	public String deletePloy(String ids){
		if(StringUtils.isNotBlank(ids)){
			try {
				
				boolean flag = ployService.deletePloyByIds(ids);
				if(flag){
					return SUCCESS;
				}
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}
		return NONE;
	}
	*/
	/*
	public String queryPloyList(){
		String returnPath = "";
		initData();
	
		if (pagePloy==null)
		{
			pagePloy  =  new PageBeanDB();
		}
		try {
			List<Integer> positionPackageList;
			String customerids  = null;
			String postionPackageids  = null;
			UserLogin userLogin  = (UserLogin)ServletActionContext.getRequest().getSession().getAttribute("USER_LOGIN_INFO");
			if (userLogin==null)
			{
				userLogin = new UserLogin();
				userLogin.setRoleType(2);
				List<Integer> tempList =new ArrayList<Integer>();
				for (int i=1;i<40;i++)
				{
					tempList.add(i);
				}
				userLogin.setPositionIds(tempList);
			}
			
			if (userLogin.getRoleType()==1)
			{
				customerids = userLogin.getCustomerId().toString();
			}
			else
			{
				positionPackageList = userLogin.getPositionIds();
				if (positionPackageList!=null && positionPackageList.size()>0)
				{
					postionPackageids="";
					for (int i=0;i<positionPackageList.size();i++)
					{
						postionPackageids = postionPackageids+positionPackageList.get(i);
						if (i<positionPackageList.size()-1)
						{
							postionPackageids = postionPackageids+",";
						}
					}
				}
			}
			pagePloy = ployService.queryPloyList(ploy,customerids, postionPackageids,adPosition,pagePloy.getPageSize(), pagePloy.getPageNo());
			returnPath=SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			returnPath=NONE;
		}
		
		return returnPath;
	}
	*/
	
	public String queryPloyList(){
		initData();
		String returnPath = "";
		if (pagePloy==null){
			pagePloy  =  new PageBeanDB();
		}
		try {
			String userIds = StringUtil.objListToString(getLoginUser().getAccessUserIds(), ",", "-1");
			pagePloy = ployService.getAdPloyList(ploy,adPosition,pagePloy.getPageSize(), pagePloy.getPageNo(), userIds);
			returnPath=SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			returnPath=NONE;
		}
		return returnPath;
	}
	
	
	public String queryCheckPloyList(){
		if(null == ploy){
			ploy = new PloyBackup();
		}
		ploy.setAuditFlag(true);
		return queryPloyList();
	}
	
	/*
	 	public String queryCheckPloyList(){
		String returnPath = "";
		initData();
		//PageBeanDB page = null;
		if (pagePloy==null)
		{
			pagePloy  =  new PageBeanDB();
		}
		
		try {
		//	int count = ployService.getployCount(conditionMap);
			
			pagePloy = ployService.queryCheckPloyList(ploy,null,adPosition, null, pagePloy.getPageSize(), pagePloy.getPageNo());
//System.out.println("mark");		
			//	List<Ploy> lstPloy = ployService.getAllPloyList(conditionMap,page.getBegin(), page.getEnd());
		//	request.setAttribute("page", page);
		//	request.setAttribute("list", lstPloy);
		} catch (Exception e) {
			e.printStackTrace();
			returnPath=NONE;
		}
		
		return SUCCESS;
	}
	 */
	
	public String checkPloyState()
	{
		try
		{
			ployService.CheckPloy(ploy);
			message = "common.save.success";//保存成功
		}catch(Exception e){
			message = "common.save.failed";//保存失败
			operResult = Constant.OPERATE_FAIL;
		}
		finally
		{
			operInfo = ploy.toString();  //"策略ID："+ploy.getPloyId();
			if (ploy.getState().equals("1"))
			{
				operType = "operate.auditok";
			}
			else
			{
				operType = "operate.auditno";
			}
			OperateLog operLog = this.setOperationLog(Constant.OPERATE_MODULE_PLOY);
			operateLogService.saveOperateLog(operLog);
		}
		ploy=null;
		queryCheckPloyList();
		return SUCCESS;
	}
	public String queryExistPloyList(){
		String returnPath = "";
		try {
		//	int count = ployService.getployCount(conditionMap);
			
			List ployList = ployService.queryExitsPloyList(ploy);
			String retJson = Obj2JsonUtil.list2json(ployList);
			print(retJson);
			//	List<Ploy> lstPloy = ployService.getAllPloyList(conditionMap,page.getBegin(), page.getEnd());
			//	request.setAttribute("page", page);
			//	request.setAttribute("list", lstPloy);
		} catch (Exception e) {
			returnPath=NONE;
		}
		
		return NONE;
	}
	
	public String queryContractList()
	{
		//查询操作员可操作广告商ID
		//contract.setId(1);
		List<Customer> customerList;
		String customerids  = "";
		customerList = (List<Customer>) ServletActionContext.getRequest().getSession().getAttribute("customer");
		if (customerList!=null && customerList.size()>0)
		{
			for (int i=0;i<customerList.size();i++)
			{
				customerids = customerids+customerList.get(i).getId();
				if (i<customerList.size()-1)
				{
					customerids = customerids+",";
				}
			}
		}
		pageContract =  ployService.queryContract(contract,customerids,pageContract.getPageSize(),pageContract.getPageNo());
		return SUCCESS;
	}
	public String queryAdPostionList()
	{
		//查询操作员可操作广告商ID
		//contract.setId(1);
		List<Customer> customerList;
		String customerids  = "";
		customerList = (List<Customer>) ServletActionContext.getRequest().getSession().getAttribute("customer");
		if (customerList!=null && customerList.size()>0)
		{
			for (int i=0;i<customerList.size();i++)
			{
				customerids = customerids+customerList.get(i).getId();
				if (i<customerList.size()-1)
				{
					customerids = customerids+",";
				}
			}
		}
		pageAdPosition = ployService.queryAdPosition(customerids,contract.getId(), adPosition, pageAdPosition.getPageSize(),pageAdPosition.getPageNo());
		return SUCCESS;
	}
	public String queryRuleList()
	{
		//查询操作员可操作广告商ID
		//contract.setId(1);
		pageRule = ployService.queryAdPositionRule(contract.getId(), adPosition.getId(), rule,pageRule.getPageSize(),pageRule.getPageNo());
		return SUCCESS;
	}

	public String addqueryContractList()
	{
		//查询操作员可操作广告商ID
		//contract.setId(1);
		List<Customer> customerList;
		String customerids  = "";
		customerList = (List<Customer>) ServletActionContext.getRequest().getSession().getAttribute("customer");
		if (customerList!=null && customerList.size()>0)
		{
			for (int i=0;i<customerList.size();i++)
			{
				customerids = customerids+customerList.get(i).getId();
				if (i<customerList.size()-1)
				{
					customerids = customerids+",";
				}
			}
		}
		pageContract =  ployService.queryContract(contract,customerids,pageContract.getPageSize(),pageContract.getPageNo());
		//pageContract =  ployService.queryContract(contract,pageContract.getPageSize(),pageContract.getPageNo());
		return SUCCESS;
	}
	public String addqueryAdPostionList()
	{
		//查询操作员可操作广告商ID
	//	contract.setId(1);
		List<Customer> customerList;
		String customerids  = "";
		customerList = (List<Customer>) ServletActionContext.getRequest().getSession().getAttribute("customer");
		if (customerList!=null && customerList.size()>0)
		{
			for (int i=0;i<customerList.size();i++)
			{
				customerids = customerids+customerList.get(i).getId();
				if (i<customerList.size()-1)
				{
					customerids = customerids+",";
				}
			}
		}
		pageAdPosition = ployService.queryAdPosition(customerids,contract.getId(), adPosition, pageAdPosition.getPageSize(),pageAdPosition.getPageNo());
		return SUCCESS;
	}
	public String addqueryRuleList()
	{
		//查询操作员可操作广告商ID
	//	contract.setId(1);
		pageRule = ployService.queryAdPositionRule(contract.getId(), adPosition.getId(), rule,pageRule.getPageSize(),pageRule.getPageNo());
		return SUCCESS;
	}
	
	/**
	 * 将内容写入对应的response中
	 * @param str 存有播出单列表的字符串
	 */
	private void print(String str) {
		try {
			ServletActionContext.getResponse().setCharacterEncoding("utf-8");
			ServletActionContext.getResponse().getOutputStream().write(str.getBytes("utf-8"));
		} catch (Exception e) {
			logger.error("send response error"+e);
		}
	}


	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	public PloyService getPloyService() {
		return ployService;
	}

	public void setPloyService(PloyService ployService) {
		this.ployService = ployService;
	}

	public PageBeanDB getPagePloy() {
		return pagePloy;
	}

	public void setPagePloy(PageBeanDB pagePloy) {
		this.pagePloy = pagePloy;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public AdvertPosition getAdPosition() {
		return adPosition;
	}

	public void setAdPosition(AdvertPosition adPosition) {
		this.adPosition = adPosition;
	}

	public MarketingRule getRule() {
		return rule;
	}

	public void setRule(MarketingRule rule) {
		this.rule = rule;
	}

	public PageBeanDB getPageContract() {
		return pageContract;
	}

	public void setPageContract(PageBeanDB pageContract) {
		this.pageContract = pageContract;
	}

	public PageBeanDB getPageAdPosition() {
		return pageAdPosition;
	}

	public void setPageAdPosition(PageBeanDB pageAdPosition) {
		this.pageAdPosition = pageAdPosition;
	}

	public PageBeanDB getPageAdPositionType() {
		return pageAdPositionType;
	}

	public void setPageAdPositionType(PageBeanDB pageAdPositionType) {
		this.pageAdPositionType = pageAdPositionType;
	}

	public PageBeanDB getPageRule() {
		return pageRule;
	}

	public void setPageRule(PageBeanDB pageRule) {
		this.pageRule = pageRule;
	}
	
	public List<PloyAreaChannel> getLstPloyAreaChannel() {
		return lstPloyAreaChannel;
	}

	public void setLstPloyAreaChannel(List<PloyAreaChannel> lstPloyAreaChannel) {
		this.lstPloyAreaChannel = lstPloyAreaChannel;
	}

	public String getAreasJson() {
		return areasJson;
	}

	public void setAreasJson(String areasJson) {
		this.areasJson = areasJson;
	}
	public int getContractId() {
		return contractId;
	}

	public void setContractId(int contractId) {
		this.contractId = contractId;
	}

	public int getPostionId() {
		return postionId;
	}

	public void setPostionId(int postionId) {
		this.postionId = postionId;
	}

	public int getRuleId() {
		return ruleId;
	}

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public String getAreaids() {
		return areaids;
	}

	public void setAreaids(String areaids) {
		this.areaids = areaids;
	}
	public List<ReleaseArea> getLstReleaseArea() {
		return lstReleaseArea;
	}

	public PageBeanDB getPageArea() {
		return pageArea;
	}

	public void setPageArea(PageBeanDB pageArea) {
		this.pageArea = pageArea;
	}

	public PageBeanDB getPageChannel() {
		return pageChannel;
	}

	public void setPageChannel(PageBeanDB pageChannel) {
		this.pageChannel = pageChannel;
	}

	public void setLstReleaseArea(List<ReleaseArea> lstReleaseArea) {
		this.lstReleaseArea = lstReleaseArea;
	}
	
	public List<ChannelInfo> getLstChannelInfo() {
		return lstChannelInfo;
	}

	public void setLstChannelInfo(List<ChannelInfo> lstChannelInfo) {
		this.lstChannelInfo = lstChannelInfo;
	}
	public PreciseMatchService getPreciseservice() {
		return preciseservice;
	}
	public void setPreciseservice(PreciseMatchService preciseservice) {
		this.preciseservice = preciseservice;
	}
	public PageBeanDB getPagePreciseMatch() {
		return pagePreciseMatch;
	}
	public void setPagePreciseMatch(PageBeanDB pagePreciseMatch) {
		this.pagePreciseMatch = pagePreciseMatch;
	}
	public String getPloysJson() {
		return ploysJson;
	}
	public void setPloysJson(String ploysJson) {
		this.ploysJson = ploysJson;
	}
	public OperateLogService getOperateLogService() {
		return operateLogService;
	}
	public void setOperateLogService(OperateLogService operateLogService) {
		this.operateLogService = operateLogService;
	}
	public String getPostionJson() {
		return postionJson;
	}
	public void setPostionJson(String postionJson) {
		this.postionJson = postionJson;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getChannelgroup() {
		return channelgroup;
	}
	public void setChannelgroup(String channelgroup) {		
		this.channelgroup = channelgroup;
	}
	public PageBeanDB getPageCustomer() {
		return pageCustomer;
	}
	public void setPageCustomer(PageBeanDB pageCustomer) {
		this.pageCustomer = pageCustomer;
	}
	public PloyTimeCGroup getPloyTimeCGroup() {
		return ployTimeCGroup;
	}
	public void setPloyTimeCGroup(PloyTimeCGroup ployTimeCGroup) {
		this.ployTimeCGroup = ployTimeCGroup;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public PreciseUiBean getPreciseUiBean() {
		return preciseUiBean;
	}
	public void setPreciseUiBean(PreciseUiBean preciseUiBean) {
		this.preciseUiBean = preciseUiBean;
	}
	public PageBeanDB getPageLocation() {
		return pageLocation;
	}
	public void setPageLocation(PageBeanDB pageLocation) {
		this.pageLocation = pageLocation;
	}
	
	public PreciseUiBean getAreaChannelUiBean() {
		return areaChannelUiBean;
	}
	public void setAreaChannelUiBean(PreciseUiBean areaChannelUiBean) {
		this.areaChannelUiBean = areaChannelUiBean;
	}
	
	
	public PageBeanDB getPageReleaseLocation() {
		return pageReleaseLocation;
	}
	public void setPageReleaseLocation(PageBeanDB pageReleaseLocation) {
		this.pageReleaseLocation = pageReleaseLocation;
	}
	
	public String getBchannelgroup() {
		return bchannelgroup;
	}
	public void setBchannelgroup(String bchannelgroup) {
		this.bchannelgroup = bchannelgroup;
	}
	public PageBeanDB getPageMenutype() {
		return pageMenutype;
	}
	public void setPageMenutype(PageBeanDB pageMenutype) {
		this.pageMenutype = pageMenutype;
	}
	/**
	 * 导出ADI
	 * @return
	 */
	
	public String exportcontentTVN(){
		String actionObject="";
		
		if (tvnexport == null) {
			log.error("no tvn");
			return null;
	
		}else{
			tvnexport=tvnexport.replace(" ", "");
			tvnexport=tvnexport.trim();
			tvnexport = tvnexport.replace(",", System.getProperty("line.separator"));
			return "exportcontentTVN";
		}
		
	}
	
	public String getTvnexport() {
		return tvnexport;
	}
	public void setTvnexport(String tvnexport) {
		this.tvnexport = tvnexport;
	}
	public InputStream getInputStreamu(){
		InputStream is = null;
		try {
			String data=this.tvnexport;
			if(data==null){
				data="";
			}
			is = new ByteArrayInputStream(data.getBytes("utf-8"));
		} catch (Exception e) {
			log.error("", e);
		}
		
		return is;
	}
	
	public String getFileName(){
		String str="adi";
		try
		{
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
	        String ss = df.format(new Date());
			str=ss;
		}catch (Exception e)
		{
			
		}
		return str+".txt";
	}
	public String getChannelGroupType() {
		return channelGroupType;
	}
	public void setChannelGroupType(String channelGroupType) {
		this.channelGroupType = channelGroupType;
	}

}
