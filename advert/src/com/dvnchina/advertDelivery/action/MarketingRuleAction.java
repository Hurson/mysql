package com.dvnchina.advertDelivery.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.alibaba.fastjson.JSON;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.bean.marketingRule.AddRuleBean;
import com.dvnchina.advertDelivery.bean.marketingRule.AreaBean;
import com.dvnchina.advertDelivery.bean.marketingRule.ChannelBean;
import com.dvnchina.advertDelivery.bean.marketingRule.MarketingRuleBean;
import com.dvnchina.advertDelivery.constant.LoginConstant;
import com.dvnchina.advertDelivery.model.AdvertPosition;
import com.dvnchina.advertDelivery.model.MarketingRule;
import com.dvnchina.advertDelivery.service.MarketingRuleService;
import com.dvnchina.advertDelivery.service.UploadMaterialService;
import com.dvnchina.advertDelivery.utils.CookieUtils;
import com.dvnchina.advertDelivery.utils.json.Obj2JsonUtil;
import com.dvnchina.advertDelivery.utils.xml.MarkteingRuleConfig;

public class MarketingRuleAction extends BaseActionSupport<Object> implements ServletRequestAware {
	
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private Logger logger = Logger.getLogger(MarketingRuleAction.class);
	private MarketingRuleService marketingRuleService;
	private UploadMaterialService getPosition;
	private MarketingRule marketingRule;
	private String ruleParam;
	private  List<MarketingRuleBean> marketingRuleList ;
	private static final Map<String, ArrayList<MarketingRuleBean>> startTimeMap  = MarkteingRuleConfig.startTimeMap;
	private static final Map<String, ArrayList<MarketingRuleBean>> endTimeMap  = MarkteingRuleConfig.endTimeMap;
	/**
	 * 添加营销规则
	 * @return
	 */
	public String addMarketingRule(){
		ArrayList<MarketingRuleBean> startTimeList= startTimeMap.get("startTime");
		ArrayList<MarketingRuleBean> endTimeList= endTimeMap.get("endTime");
		request.setAttribute("startTimeList", startTimeList);
		request.setAttribute("endTimeList", endTimeList);
		return SUCCESS;
	}
	
	/**
	 * 添加营销规则
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String insertMarketingRule(){
		Map<String,String> resultMap = null;
		Map responseResult = new HashMap();
		marketingRule = new MarketingRule();
		try {
			ruleParam = URLDecoder.decode(ruleParam, "UTF-8");
			AddRuleBean ruleBean = parseJson(ruleParam);
			if (ruleBean!=null) {
				List<AreaBean> areaList = ruleBean.getBindingArea();
				for(AreaBean area : areaList){
					List<ChannelBean> channelList = area.getChannel();
					if(channelList !=null&&channelList.size()>0){
						for(ChannelBean channelId : channelList){
							String startTime = ruleBean.getStartTime();
							if(startTime.length() < 6){
								startTime += ":00"; 
							}
							marketingRule.setStartTime(startTime);
							String endTime = ruleBean.getEndTime();
							if(endTime.length() < 6){
								endTime += ":00"; 
							}
							marketingRule.setEndTime(endTime);
							marketingRule.setOperationId(getUserId());
							marketingRule.setMarketingRuleId(Integer.valueOf(ruleBean.getRuleId()));
							marketingRule.setMarketingRuleName(ruleBean.getRuleName());
							marketingRule.setPositionId(Integer.valueOf(ruleBean.getPositionId()));
							marketingRule.setLocationId(Integer.valueOf(area.getId()));
							marketingRule.setChannelId(Integer.valueOf(channelId.getId()));
							marketingRule.setCreateTime(new Date());
							marketingRule.setState(1);
							resultMap = marketingRuleService.insertMarketingRule(marketingRule);
						}
					}else{
						String startTime = ruleBean.getStartTime();
						if(startTime.length() < 6){
							startTime += ":00"; 
						}
						marketingRule.setStartTime(startTime);
						String endTime = ruleBean.getEndTime();
						if(endTime.length() < 6){
							endTime += ":00"; 
						}
						marketingRule.setEndTime(endTime);
						marketingRule.setOperationId(getUserId());
						marketingRule.setMarketingRuleId(Integer.valueOf(ruleBean.getRuleId()));
						marketingRule.setMarketingRuleName(ruleBean.getRuleName());
						marketingRule.setPositionId(Integer.valueOf(ruleBean.getPositionId()));
						marketingRule.setLocationId(Integer.valueOf(area.getId()));
						marketingRule.setCreateTime(new Date());
						marketingRule.setState(1);
						resultMap = marketingRuleService.insertMarketingRule(marketingRule);
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			resultMap.put("flag","error");
			logger.error("参数解析出现异常",e);
		}
		responseResult.put("handleResult",resultMap);
		String json = JSON.toJSONString(responseResult);
		this.renderJson(json);
		return NONE;
	}
	
	public static AddRuleBean parseJson(String jsonString){
		AddRuleBean ruleBean = null;
		
		if(StringUtils.isNotBlank(jsonString)){
			ruleBean=JSON.toJavaObject(JSON.parseObject(jsonString), AddRuleBean.class);
		}
		return ruleBean;
	}
	
	/**
	 * 修改营销规则
	 * @return
	 */ 
	public String updateMarketingRule(){
		String ruleId = getRequest().getParameter("ruleId");
		Map<String, List<ChannelBean>> ruleMap = new HashMap<String, List<ChannelBean>>();
		List<MarketingRuleBean> ruleList = null;
		MarketingRuleBean ruleBean = null;
		if(StringUtils.isNotBlank(ruleId)){
			try {
				ruleList = marketingRuleService.getMarketingRuleById(Integer.valueOf(ruleId));
			} catch (Exception e) {
				logger.error("修改营销规则出现异常",e);
			}
		}
		List<ChannelBean> channelList = null;
		if(ruleList != null&&ruleList.size()>0){
			for(MarketingRuleBean rule:ruleList){
				String key = rule.getAreaId()+"#"+rule.getAreaName();
				channelList = ruleMap.get(key);
				ChannelBean channel = new ChannelBean();
				channel.setId(rule.getChannelId());
				channel.setChannelName(rule.getChannelName());
				if(channelList != null&&channelList.size()>0){
					channelList.add(channel);
				}else{
					channelList = new ArrayList<ChannelBean>();
					channelList.add(channel);
					ruleMap.put(key, channelList);
				}
			}
			
			ruleBean = ruleList.get(0);
		}
		ArrayList<MarketingRuleBean> startTimeList= startTimeMap.get("startTime");
		ArrayList<MarketingRuleBean> endTimeList= endTimeMap.get("endTime");
		request.setAttribute("startTimeList", startTimeList);
		request.setAttribute("endTimeList", endTimeList);
//		request.setAttribute("ruleList", ruleList);
		request.setAttribute("ruleMap", ruleMap);
		request.setAttribute("rule", ruleBean);
		return SUCCESS;
	}
	
	/**
	 * 保存修改的营销规则
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String saveUpdateMarketingRule(){
		Map<String,String> resultMap = null;
		Map responseResult = new HashMap();
		marketingRule = new MarketingRule();
		try {
			ruleParam = URLDecoder.decode(ruleParam, "UTF-8");
			AddRuleBean ruleBean = parseJson(ruleParam);
			if (ruleBean!=null) {
				List<AreaBean> areaList = ruleBean.getBindingArea();
				for(AreaBean area : areaList){
					List<ChannelBean> channelList = area.getChannel();
					if(channelList !=null&&channelList.size()>0){
						for(ChannelBean channelId : channelList){
							String startTime = ruleBean.getStartTime();
							if(startTime.length() < 6){
								startTime += ":00"; 
							}
							marketingRule.setStartTime(startTime);
							String endTime = ruleBean.getEndTime();
							if(endTime.length() < 6){
								endTime += ":00"; 
							}
							marketingRule.setId(Integer.valueOf(ruleBean.getId()));
							marketingRule.setEndTime(endTime);
							marketingRule.setOperationId(getUserId());
							marketingRule.setMarketingRuleId(Integer.valueOf(ruleBean.getRuleId()));
							marketingRule.setMarketingRuleName(ruleBean.getRuleName());
							marketingRule.setPositionId(Integer.valueOf(ruleBean.getPositionId()));
							marketingRule.setLocationId(Integer.valueOf(area.getId()));
							marketingRule.setChannelId(Integer.valueOf(channelId.getId()));
							marketingRule.setCreateTime(new Date());
							marketingRule.setState(1);
							resultMap = marketingRuleService.updateMarketingRule(marketingRule);
						}
					}else{
						String startTime = ruleBean.getStartTime();
						if(startTime.length() < 6){
							startTime += ":00"; 
						}
						marketingRule.setStartTime(startTime);
						String endTime = ruleBean.getEndTime();
						if(endTime.length() < 6){
							endTime += ":00"; 
						}
						marketingRule.setId(Integer.valueOf(ruleBean.getId()));
						marketingRule.setEndTime(endTime);
						marketingRule.setOperationId(getUserId());
						marketingRule.setMarketingRuleId(Integer.valueOf(ruleBean.getRuleId()));
						marketingRule.setMarketingRuleName(ruleBean.getRuleName());
						marketingRule.setPositionId(Integer.valueOf(ruleBean.getPositionId()));
						marketingRule.setLocationId(Integer.valueOf(area.getId()));
						marketingRule.setCreateTime(new Date());
						marketingRule.setState(1);
						resultMap = marketingRuleService.updateMarketingRule(marketingRule);
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			resultMap.put("flag","error");
			logger.error("参数解析出现异常",e);
		}
		responseResult.put("handleResult",resultMap);
		String json = JSON.toJSONString(responseResult);
		this.renderJson(json);
		return NONE;
	}
	
	/**
	 * 删除营销规则
	 * @return
	 */
	public String deleteMarketingRule(){
		String id = getRequest().getParameter("id");
		if(StringUtils.isNotBlank(id)){
			try {
				marketingRuleService.deleteMarketingRuleById(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return NONE;
	}
	
	/**
	 * 批量删除营销规则
	 * @return
	 */
	public String deleteMarketingRuleBatch(){
		int result = 0;
		String ids = this.request.getParameter("ids");
		String id[] = ids.split(",");
		if(null!=ids && !"".equals(ids)){
			for(int i=0;i<id.length;i++){
				if(!"".equals(id[i]) && id[i] != null){						
					marketingRuleService.deleteMarketingRuleById(id[i]);
				}
				
			}	
		}
		
		String json  = "{\"result\":"+result+"}";
		renderJson(json);
		return NONE;
	}
	
	/**
	 * 上线营销规则
	 * @return
	 */
	public String upLineMarketingRule(){
		String id = getRequest().getParameter("id");
		if(StringUtils.isNotBlank(id)){
			try {
				marketingRuleService.upLineMarketingRule(id);
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}
		return NONE;
	}
	
	/**
	 * 下线营销规则
	 * @return
	 */
	public String downLineMarketingRule(){
		String id = getRequest().getParameter("id");
		if(StringUtils.isNotBlank(id)){
			try {
				marketingRuleService.downLineMarketingRule(id);
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}
		return NONE;
	}
	
	/**
	 * 展示营销规则列表
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public String getAllMarketingRuleList(){
		String returnPath = "";
		int count = 0;
		PageBeanDB page = null;
		List<MarketingRuleBean> areaList;
		String contractBindingFlag = request.getParameter("contractBindingFlag");
		String positionId = request.getParameter("positionId"); 
		String ruleName = request.getParameter("ruleName");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		//此参数供绑定规则时使用										
		//String chooseMarketRulesElement = request.getParameter("chooseMarketRulesElement");
		//此参数供绑定规则时使用
		//String currentIndex = request.getParameter("currentIndex");
		//此参数供绑定规则时使用
		//String startRow = request.getParameter("startRow");
		//此参数供绑定规则时使用
		//String endRow = request.getParameter("endRow");
		//此参数供绑定规则时使用
		String positionIndexFlag=request.getParameter("positionIndexFlag");
		//此参数供绑定营销规则时使用
		String saveOrUpdateFlag=request.getParameter("saveOrUpdateFlag");
		
		Map conditionMap = new HashMap();
		
		try {
			
			if (StringUtils.isNotBlank(positionId)&&Integer.valueOf(positionId)>0) {
				conditionMap.put("POSITION_ID", Integer.valueOf(positionId));
				request.setAttribute("positionId",positionId);
			}
			
			if (StringUtils.isNotBlank(ruleName)) {
				conditionMap.put("RULE_NAME", ruleName);
				request.setAttribute("ruleName",ruleName);
			}
			
			if (StringUtils.isNotBlank(startTime)) {
				conditionMap.put("START_TIME", startTime);
				request.setAttribute("startTime",startTime);
			}
			
			if (StringUtils.isNotBlank(endTime)) {
				conditionMap.put("END_TIME", endTime);
				request.setAttribute("endTime",endTime);
			}
			
			/*if (StringUtils.isNotBlank(chooseMarketRulesElement)) {
				request.setAttribute("chooseMarketRulesElement",chooseMarketRulesElement);
			}
			
			if (StringUtils.isNotBlank(currentIndex)) {
				request.setAttribute("currentIndex",currentIndex);
			}
			
			if (StringUtils.isNotBlank(startRow)) {
				request.setAttribute("startRow",startRow);
			}
			
			if (StringUtils.isNotBlank(endRow)) {
				request.setAttribute("endRow",endRow);
			}*/
			
			List<AdvertPosition> postionList = getPosition.getAllPosition();
			
			if (StringUtils.isNotBlank(positionIndexFlag)) {
				request.setAttribute("positionIndexFlag",positionIndexFlag);
			}
			
			if (StringUtils.isNotBlank(saveOrUpdateFlag)) {
				request.setAttribute("saveOrUpdateFlag",saveOrUpdateFlag);
			}
			
			if (StringUtils.isNotBlank(contractBindingFlag)) {
				if(marketingRule!=null){
					
					if(StringUtils.isNotBlank(marketingRule.getMarketingRuleName())){
						conditionMap.put("RULE_NAME", marketingRule.getMarketingRuleName());
					}	
					
				}
				count=marketingRuleService.getMarketingRuleCount(conditionMap);
				page = new PageBeanDB(count, pageNo);
				areaList = marketingRuleService.getAreaList(page.getBegin(),page.getEnd());
				request.setAttribute("areaList", areaList);
				marketingRuleList = marketingRuleService.page(conditionMap,page.getBegin(), page.getEnd());
				returnPath = "bandingMarketRule";
			}else{
				count = marketingRuleService.getMarketingRuleCount(conditionMap);
				page = new PageBeanDB(count, pageNo);
				marketingRuleList = marketingRuleService.page(conditionMap,page.getBegin(), page.getEnd());
				returnPath= SUCCESS;
			}
			request.setAttribute("postionList", postionList);
			request.setAttribute("rules", marketingRuleList);
			request.setAttribute("page", page);
		} catch (Exception e) {
			logger.error("查询规则异常",e);
			returnPath=NONE;
		}
		
		return returnPath;
	}
	
	/**
	 * 查询地区
	 * @return
	 */
	public String queryArea(){
		
		String positionId = request.getParameter("positionId");
		List<MarketingRuleBean> areaList;
		int count;
		PageBeanDB page;
		try {
			if(positionId != null&&!"".equals(positionId)){
				count = marketingRuleService.getAreaCount(positionId);
				page = new PageBeanDB(count,pageNo);
				areaList = marketingRuleService.getAreaList(positionId,page.getBegin(),page.getEnd());
				request.setAttribute("positionId", positionId);
			}else{
				count = marketingRuleService.getAreaCount();
				page = new PageBeanDB(count,pageNo);
				areaList = marketingRuleService.getAreaList(page.getBegin(),page.getEnd());
			}
			request.setAttribute("areaList", areaList);
			request.setAttribute("page", page);
		} catch (Exception e) {
			logger.error("获取数据列表时出现异常",e);
		}
		return "area";
	}
	
	/**
	 * 查询频道
	 * @return
	 */
	public String queryChannel(){
		
		String positionId = request.getParameter("positionId");
		String areaId = request.getParameter("areaId");
		String areaIndexFlag = request.getParameter("areaIndexFlag");
		List<MarketingRuleBean> channelList;
		int count;
		PageBeanDB page;
		try {
			if(positionId != null&&!"".equals(positionId)&&areaId != null&&!"".equals(areaId)){
				count = marketingRuleService.getChannelCount(positionId,areaId);
				page = new PageBeanDB(count,pageNo);
				channelList = marketingRuleService.getChannelList(positionId,areaId,page.getBegin(),page.getEnd());
				request.setAttribute("positionId", positionId);
				request.setAttribute("areaId", areaId);
				request.setAttribute("areaIndexFlag", areaIndexFlag);
			}else if(positionId != null&&!"".equals(positionId)){
				count = marketingRuleService.getChannelCount(positionId);
				page = new PageBeanDB(count,pageNo);
				channelList = marketingRuleService.getChannelList(positionId,page.getBegin(),page.getEnd());
				request.setAttribute("positionId", positionId);
			}else{
				count = marketingRuleService.getChannelCount();
				page = new PageBeanDB(count,pageNo);
				channelList = marketingRuleService.getChannelList(page.getBegin(),page.getEnd());
			}
			request.setAttribute("channelList", channelList);
			request.setAttribute("page", page);
		} catch (Exception e) {
			logger.error("获取数据列表时出现异常",e);
		}
		return "channel";
	}
	
	/**
	 * 查询地区
	 * @return
	 */
	public String queryRule(){
		
		String positionId = request.getParameter("positionId");
		List<MarketingRuleBean> ruleList;
		int count;
		PageBeanDB page;
		try {
			count = marketingRuleService.getRuleCount(positionId);
			page = new PageBeanDB(count,pageNo);
			ruleList = marketingRuleService.getRuleList(positionId,page.getBegin(),page.getEnd());
			request.setAttribute("positionId", positionId);
			request.setAttribute("ruleList", ruleList);
			request.setAttribute("page", page);
		} catch (Exception e) {
			logger.error("获取数据列表时出现异常",e);
		}
		return "rule";
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
	 * 渲染返回信息到页面
	 * @param result  boolean
	 * 				消息返回结果
	 * @param msg	  String
	 * 				消息详细内容
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	private String renderMsg(boolean result ,String msg){
		Map map = new HashMap(); 
		map.put("result", result);
		map.put("msg", msg);
		return Obj2JsonUtil.object2json(map);
	}
	
	/**
	 * render JSON  方法重载
	 */
	public void renderJson(String content) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(content);
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;	
	}

	public MarketingRuleService getMarketingRuleService() {
		return marketingRuleService;
	}

	public void setMarketingRuleService(MarketingRuleService marketingRuleService) {
		this.marketingRuleService = marketingRuleService;
	}

	public List<MarketingRuleBean> getMarketingRuleList() {
		return marketingRuleList;
	}

	public void setMarketingRuleList(List<MarketingRuleBean> marketingRuleList) {
		this.marketingRuleList = marketingRuleList;
	}

	public MarketingRule getMarketingRule() {
		return marketingRule;
	}

	public void setMarketingRule(MarketingRule marketingRule) {
		this.marketingRule = marketingRule;
	}

	public String getRuleParam() {
		return ruleParam;
	}

	public void setRuleParam(String ruleParam) {
		this.ruleParam = ruleParam;
	}

	public UploadMaterialService getGetPosition() {
		return getPosition;
	}

	public void setGetPosition(UploadMaterialService getPosition) {
		this.getPosition = getPosition;
	}
	
}
