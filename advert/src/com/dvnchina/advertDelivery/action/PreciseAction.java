package com.dvnchina.advertDelivery.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.dvnchina.advertDelivery.bean.precise.PreciseBean;
import com.dvnchina.advertDelivery.model.PreciseMatch;
import com.dvnchina.advertDelivery.service.PreciseService;
import com.dvnchina.advertDelivery.utils.json.Obj2JsonUtil;
import com.dvnchina.advertDelivery.utils.xml.PreciseConfig;

public class PreciseAction extends BaseActionSupport<Object> implements ServletRequestAware {
	
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private Logger logger = Logger.getLogger(PreciseAction.class);
	private PreciseService preciseService;
	private  List<PreciseBean> preciseList ;
	private static final Map<String, ArrayList<PreciseMatch>> typeMap  = PreciseConfig.typeMap;
	/**
	 *  1添加精准	 * @return
	 */
	public String addPrecise(){
		ArrayList<PreciseMatch> typeList = typeMap.get("type");
		request.setAttribute("typeList", typeList);
		return SUCCESS;
	}
	
	/**
	 * 插入精准
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String insertPrecise(){
		Map<String,String> resultMap = null;
		Map responseResult = new HashMap();
		String preciseName = getRequest().getParameter("preciseName");
		String priority = getRequest().getParameter("priority");
		String type = getRequest().getParameter("type");
		String ployId = getRequest().getParameter("ployId");
		String product = getRequest().getParameter("product");
		String productName = getRequest().getParameter("productName");
		if(StringUtils.isNotBlank(product)&&StringUtils.isNotBlank(productName)){
			PreciseMatch precise = new PreciseMatch();
			precise.setMatchName(preciseName);
			precise.setPriority(Integer.valueOf(priority));
			precise.setType(Integer.valueOf(type));
			precise.setPloyId(Integer.valueOf(ployId));
			precise.setProductCode(product);
			precise.setProductName(productName);
			try {
				resultMap = preciseService.insertPrecise(precise);
			} catch (Exception e) {
				resultMap.put("flag","error");
				logger.error("参数解析出现异常",e);
			}
		}
		String channel = getRequest().getParameter("channel");
		String channelName = getRequest().getParameter("channelName");
		if(StringUtils.isNotBlank(channel)&&StringUtils.isNotBlank(channelName)){
			PreciseMatch precise = new PreciseMatch();
			precise.setMatchName(preciseName);
			precise.setPriority(Integer.valueOf(priority));
			precise.setType(Integer.valueOf(type));
			precise.setPloyId(Integer.valueOf(ployId));
			precise.setBtvChannelId(channel);
			precise.setBtvChannelName(channelName);
			try {
				resultMap = preciseService.insertPrecise(precise);
			} catch (Exception e) {
				resultMap.put("flag","error");
				logger.error("参数解析出现异常",e);
			}
		}
		String keyword = getRequest().getParameter("keyword");
		if(StringUtils.isNotBlank(keyword)){
			PreciseMatch precise = new PreciseMatch();
			precise.setMatchName(preciseName);
			precise.setPriority(Integer.valueOf(priority));
			precise.setType(Integer.valueOf(type));
			precise.setPloyId(Integer.valueOf(ployId));
			precise.setKey(keyword);
			try {
				resultMap = preciseService.insertPrecise(precise);
			} catch (Exception e) {
				resultMap.put("flag","error");
				logger.error("参数解析出现异常",e);
			}
		}
		String assetSort = getRequest().getParameter("assetSort");
		String assetSortName = getRequest().getParameter("assetSortName");
		if(StringUtils.isNotBlank(assetSort)&&StringUtils.isNotBlank(assetSortName)){
			PreciseMatch precise = new PreciseMatch();
			precise.setMatchName(preciseName);
			precise.setPriority(Integer.valueOf(priority));
			precise.setType(Integer.valueOf(type));
			precise.setPloyId(Integer.valueOf(ployId));
			precise.setAssetSortId(assetSort);
			//precise.setAssetSortName(assetSortName);
			try {
				resultMap = preciseService.insertPrecise(precise);
			} catch (Exception e) {
				resultMap.put("flag","error");
				logger.error("参数解析出现异常",e);
			}
		}
		String userArea = getRequest().getParameter("userArea");
		String userIndustrys = getRequest().getParameter("userIndustrys");
		String userLevels = getRequest().getParameter("userLevels");
		String userAreaName = getRequest().getParameter("userAreaName");
		String userIndustrysName = getRequest().getParameter("userIndustrysName");
		String userLevelsName = getRequest().getParameter("userLevelsName");
		String tvnNumber = getRequest().getParameter("tvnNumber");
		if((StringUtils.isNotBlank(userArea)&&StringUtils.isNotBlank(userAreaName))||(StringUtils.isNotBlank(userIndustrys)&&StringUtils.isNotBlank(userIndustrysName))||
				(StringUtils.isNotBlank(userLevels)&&StringUtils.isNotBlank(userLevelsName))||StringUtils.isNotBlank(tvnNumber)){
			PreciseMatch precise = new PreciseMatch();
			precise.setMatchName(preciseName);
			precise.setPriority(Integer.valueOf(priority));
			precise.setType(Integer.valueOf(type));
			precise.setPloyId(Integer.valueOf(ployId));
			precise.setUserArea(userArea);
			precise.setUserAreaName(userAreaName);
			precise.setUserIndustrys(userIndustrys);
			precise.setUserIndustrysName(userIndustrysName);
			precise.setUserLevels(userLevels);
			precise.setUserLevelsName(userLevelsName);
			precise.setTvnNumber(tvnNumber);
			try {
				resultMap = preciseService.insertPrecise(precise);
			} catch (Exception e) {
				resultMap.put("flag","error");
				logger.error("参数解析出现异常",e);
			}
		}
		
		String category = getRequest().getParameter("category");
		String categoryName = getRequest().getParameter("categoryName");
		if(StringUtils.isNotBlank(category)&&StringUtils.isNotBlank(categoryName)){
			PreciseMatch precise = new PreciseMatch();
			precise.setMatchName(preciseName);
			precise.setPriority(Integer.valueOf(priority));
			precise.setType(Integer.valueOf(type));
			precise.setPloyId(Integer.valueOf(ployId));
			precise.setCategoryId(category);
			//precise.setCategoryName(categoryName);
			try {
				resultMap = preciseService.insertPrecise(precise);
			} catch (Exception e) {
				resultMap.put("flag","error");
				logger.error("参数解析出现异常",e);
			}
		}
		responseResult.put("handleResult",resultMap);
		String json = JSON.toJSONString(responseResult);
		this.renderJson(json);
		return NONE;
	}
	
	
	/**
	 * 修改精准
	 * @return
	 */ 
	public String updatePrecise(){
		String id = getRequest().getParameter("id");
		PreciseBean precise = null;
		if(StringUtils.isNotBlank(id)){
			
			try {
				precise = preciseService.getPreciseById(Integer.valueOf(id));
			} catch (Exception e) {
				logger.error("修改营销规则出现异常",e);
			}
		}
		ArrayList<PreciseMatch> typeList = typeMap.get("type");
		request.setAttribute("typeList", typeList);
		request.setAttribute("precise", precise);
		return SUCCESS;
	}
	
	/**
	 * 保存修改的精准
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String saveUpdatePrecise(){
		Map<String,String> resultMap = null;
		Map responseResult = new HashMap();
		String id = getRequest().getParameter("id");
		String preciseName = getRequest().getParameter("preciseName");
		String priority = getRequest().getParameter("priority");
		String type = getRequest().getParameter("type");
		String ployId = getRequest().getParameter("ployId");
		String product = getRequest().getParameter("product");
		String productName = getRequest().getParameter("productName");
		if(StringUtils.isNotBlank(product)&&StringUtils.isNotBlank(productName)){
			PreciseMatch precise = new PreciseMatch();
			precise.setId(Integer.valueOf(id));
			precise.setMatchName(preciseName);
			precise.setPriority(Integer.valueOf(priority));
			precise.setType(Integer.valueOf(type));
			precise.setPloyId(Integer.valueOf(ployId));
			precise.setProductCode(product);
			precise.setProductName(productName);
			try {
				resultMap = preciseService.updatePrecise(precise);
				resultMap.put("ployId",ployId);
			} catch (Exception e) {
				resultMap.put("flag","error");
				logger.error("参数解析出现异常",e);
			}
		}
		String channel = getRequest().getParameter("channel");
		String channelName = getRequest().getParameter("channelName");
		if(StringUtils.isNotBlank(channel)&&StringUtils.isNotBlank(channelName)){
			PreciseMatch precise = new PreciseMatch();
			precise.setId(Integer.valueOf(id));
			precise.setMatchName(preciseName);
			precise.setPriority(Integer.valueOf(priority));
			precise.setType(Integer.valueOf(type));
			precise.setPloyId(Integer.valueOf(ployId));
			precise.setBtvChannelId(channel);
			precise.setBtvChannelName(channelName);
			try {
				resultMap = preciseService.updatePrecise(precise);
				resultMap.put("ployId",ployId);
			} catch (Exception e) {
				resultMap.put("flag","error");
				logger.error("参数解析出现异常",e);
			}
		}
		String keyword = getRequest().getParameter("keyword");
		if(StringUtils.isNotBlank(keyword)){
			PreciseMatch precise = new PreciseMatch();
			precise.setId(Integer.valueOf(id));
			precise.setMatchName(preciseName);
			precise.setPriority(Integer.valueOf(priority));
			precise.setType(Integer.valueOf(type));
			precise.setPloyId(Integer.valueOf(ployId));
			precise.setKey(keyword);
			try {
				resultMap = preciseService.updatePrecise(precise);
				resultMap.put("ployId",ployId);
			} catch (Exception e) {
				resultMap.put("flag","error");
				logger.error("参数解析出现异常",e);
			}
		}
		String assetSort = getRequest().getParameter("assetSort");
		String assetSortName = getRequest().getParameter("assetSortName");
		if(StringUtils.isNotBlank(assetSort)&&StringUtils.isNotBlank(assetSortName)){
			PreciseMatch precise = new PreciseMatch();
			precise.setId(Integer.valueOf(id));
			precise.setMatchName(preciseName);
			precise.setPriority(Integer.valueOf(priority));
			precise.setType(Integer.valueOf(type));
			precise.setPloyId(Integer.valueOf(ployId));
			precise.setAssetSortId(assetSort);
			//precise.setAssetSortName(assetSortName);
			try {
				resultMap = preciseService.updatePrecise(precise);
				resultMap.put("ployId",ployId);
			} catch (Exception e) {
				resultMap.put("flag","error");
				logger.error("参数解析出现异常",e);
			}
		}
		String userArea = getRequest().getParameter("userArea");
		String userIndustrys = getRequest().getParameter("userIndustrys");
		String userLevels = getRequest().getParameter("userLevels");
		String userAreaName = getRequest().getParameter("userAreaName");
		String userIndustrysName = getRequest().getParameter("userIndustrysName");
		String userLevelsName = getRequest().getParameter("userLevelsName");
		String tvnNumber = getRequest().getParameter("tvnNumber");
		if((StringUtils.isNotBlank(userArea)&&StringUtils.isNotBlank(userAreaName))||(StringUtils.isNotBlank(userIndustrys)&&StringUtils.isNotBlank(userIndustrysName))||
				(StringUtils.isNotBlank(userLevels)&&StringUtils.isNotBlank(userLevelsName))||StringUtils.isNotBlank(tvnNumber)){
			PreciseMatch precise = new PreciseMatch();
			precise.setId(Integer.valueOf(id));
			precise.setMatchName(preciseName);
			precise.setPriority(Integer.valueOf(priority));
			precise.setType(Integer.valueOf(type));
			precise.setPloyId(Integer.valueOf(ployId));
			precise.setUserArea(userArea);
			precise.setUserAreaName(userAreaName);
			precise.setUserIndustrys(userIndustrys);
			precise.setUserIndustrysName(userIndustrysName);
			precise.setUserLevels(userLevels);
			precise.setUserLevelsName(userLevelsName);
			precise.setTvnNumber(tvnNumber);
			try {
				resultMap = preciseService.updatePrecise(precise);
				resultMap.put("ployId",ployId);
			} catch (Exception e) {
				resultMap.put("flag","error");
				logger.error("参数解析出现异常",e);
			}
		}
		
		String category = getRequest().getParameter("category");
		String categoryName = getRequest().getParameter("categoryName");
		if(StringUtils.isNotBlank(category)&&StringUtils.isNotBlank(categoryName)){
			PreciseMatch precise = new PreciseMatch();
			precise.setId(Integer.valueOf(id));
			precise.setMatchName(preciseName);
			precise.setPriority(Integer.valueOf(priority));
			precise.setType(Integer.valueOf(type));
			precise.setPloyId(Integer.valueOf(ployId));
			precise.setCategoryId(category);
			//precise.setCategoryName(categoryName);
			try {
				resultMap = preciseService.updatePrecise(precise);
				resultMap.put("ployId",ployId);
			} catch (Exception e) {
				resultMap.put("flag","error");
				logger.error("参数解析出现异常",e);
			}
		}
		responseResult.put("handleResult",resultMap);
		String json = JSON.toJSONString(responseResult);
		this.renderJson(json);
		return NONE;
	}
	
	/**
	 * 删除精准
	 * @return
	 */
	public String deletePrecise(){
		String alreadyChoose = getRequest().getParameter("alreadyChoose");
		if(StringUtils.isNotBlank(alreadyChoose)){
			String[] ids = alreadyChoose.split(",");
			for(String id:ids){
				try {
					preciseService.deletePreciseById(id);
				} catch (RuntimeException e) {
					logger.error("删除精准时出现异常",e);
				}
			}
		}
		return NONE;
	}
	
	/**
	 * 展示精准列表
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public String getAllPreciseList(){
		String returnPath = "";
		int count = 0;
		PageBeanDB page = null;
		String matchName = request.getParameter("matchName");
		String ployId = request.getParameter("ployId");
		Map conditionMap = new HashMap();
		
		try {
			if (StringUtils.isNotBlank(matchName)) {
				conditionMap.put("MATCH_NAME", matchName);
				request.setAttribute("matchName",matchName);
			}
			count = preciseService.getPreciseCount(conditionMap,ployId);
			page = new PageBeanDB(count, pageNo);
			preciseList = preciseService.page(ployId,conditionMap,page.getBegin(), page.getEnd());
			returnPath= SUCCESS;
			request.setAttribute("precises", preciseList);
			request.setAttribute("page", page);
		} catch (Exception e) {
			logger.error("查询精准异常",e);
			returnPath=NONE;
		}
		
		return returnPath;
	}
	
	/**
	 * 查询产品
	 * @return
	 */
	public String queryProduct(){
		
		String productName = request.getParameter("productName");
		List<PreciseBean> productList;
		int count;
		PageBeanDB page;
		try {
			count = preciseService.getProductCount(productName);
			page = new PageBeanDB(count,pageNo);
			productList = preciseService.getProductList(productName,page.getBegin(),page.getEnd());
			request.setAttribute("productName", productName);
			request.setAttribute("productList", productList);
			request.setAttribute("page", page);
		} catch (Exception e) {
			logger.error("获取数据列表时出现异常",e);
		}
		return "product";
	}
	
	/**
	 * 查询回看频道
	 * @return
	 */
	public String queryChannel(){
		
		String channelName = request.getParameter("channelName");
		List<PreciseBean> channelList;
		int count;
		PageBeanDB page;
		try {
			count = preciseService.getChannelCount(channelName);
			page = new PageBeanDB(count,pageNo);
			channelList = preciseService.getChannelList(channelName,page.getBegin(),page.getEnd());
			request.setAttribute("channelName", channelName);
			request.setAttribute("channelList", channelList);
			request.setAttribute("page", page);
		} catch (Exception e) {
			logger.error("获取数据列表时出现异常",e);
		}
		return "channel";
	}
	
	/**
	 * 查询影片分类
	 * @return
	 */
	public String queryAssetSort(){
		
		String assetSortName = request.getParameter("assetSortName");
		List<PreciseBean> assetSortList;
		int count;
		PageBeanDB page;
		try {
			count = preciseService.getAssetSortCount(assetSortName);
			page = new PageBeanDB(count,pageNo);
			assetSortList = preciseService.getAssetSortList(assetSortName,page.getBegin(),page.getEnd());
			request.setAttribute("assetSortName", assetSortName);
			request.setAttribute("assetSortList", assetSortList);
			request.setAttribute("page", page);
		} catch (Exception e) {
			logger.error("获取数据列表时出现异常",e);
		}
		return "assetSort";
	}
	
	/**
	 * 查询关键字
	 * @return
	 */
	public String queryKeyword(){
		
		String keyword = request.getParameter("keyword");
		List<PreciseBean> keywordList;
		int count;
		PageBeanDB page;
		try {
			count = preciseService.getKeywordCount(keyword);
			page = new PageBeanDB(count,pageNo);
			keywordList = preciseService.getKeywordList(keyword,page.getBegin(),page.getEnd());
			request.setAttribute("keyword", keyword);
			request.setAttribute("keywordList", keywordList);
			request.setAttribute("page", page);
		} catch (Exception e) {
			logger.error("获取数据列表时出现异常",e);
		}
		return "keyword";
	}
	
	/**
	 * 查询用户区域
	 * @return
	 */
	public String queryUserArea(){
		
		String locationName = request.getParameter("userAreaName");
		List<PreciseBean> userAreaList;
		int count;
		PageBeanDB page;
		try {
			count = preciseService.getUserAreaCount(locationName);
			page = new PageBeanDB(count,pageNo);
			userAreaList = preciseService.getUserAreaList(locationName,page.getBegin(),page.getEnd());
			request.setAttribute("userAreaName", locationName);
			request.setAttribute("userAreaList", userAreaList);
			request.setAttribute("page", page);
		} catch (Exception e) {
			logger.error("获取数据列表时出现异常",e);
		}
		return "userArea";
	}
	
	/**
	 * 查询用户行业
	 * @return
	 */
	public String queryUserIndustrys(){
		
		String userIndustrysName = request.getParameter("userIndustrysName");
		List<PreciseBean> userIndustrysList;
		int count;
		PageBeanDB page;
		try {
			count = preciseService.getUserIndustrysCount(userIndustrysName);
			page = new PageBeanDB(count,pageNo);
			userIndustrysList = preciseService.getUserIndustrysList(userIndustrysName,page.getBegin(),page.getEnd());
			request.setAttribute("userIndustrysName", userIndustrysName);
			request.setAttribute("userIndustrysList", userIndustrysList);
			request.setAttribute("page", page);
		} catch (Exception e) {
			logger.error("获取数据列表时出现异常",e);
		}
		return "userIndustrys";
	}
	
	/**
	 * 查询用户级别
	 * @return
	 */
	public String queryUserRank(){
		
		String userRankName = request.getParameter("userRankName");
		List<PreciseBean> userRankList;
		int count;
		PageBeanDB page;
		try {
			count = preciseService.getUserRankCount(userRankName);
			page = new PageBeanDB(count,pageNo);
			userRankList = preciseService.getUserRankList(userRankName,page.getBegin(),page.getEnd());
			request.setAttribute("userRankName", userRankName);
			request.setAttribute("userRankList", userRankList);
			request.setAttribute("page", page);
		} catch (Exception e) {
			logger.error("获取数据列表时出现异常",e);
		}
		return "userRank";
	}
	
	/**
	 * 查询节点信息
	 * @return
	 */
	public String queryPlatCategory(){
		
		String platCategoryName = request.getParameter("platCategoryName");
		List<PreciseBean> platCategoryList;
		int count;
		PageBeanDB page;
		try {
			count = preciseService.getPlatCategoryCount(platCategoryName);
			page = new PageBeanDB(count,pageNo);
			platCategoryList = preciseService.getPlatCategoryList(platCategoryName,page.getBegin(),page.getEnd());
			request.setAttribute("platCategoryName", platCategoryName);
			request.setAttribute("platCategoryList", platCategoryList);
			request.setAttribute("page", page);
		} catch (Exception e) {
			logger.error("获取数据列表时出现异常",e);
		}
		return "platCategory";
	}
	
	/**
	 * 查询策略
	 * @return
	 */
	public String queryPloy(){
		
		String ployName = request.getParameter("ployName");
		List<PreciseBean> ployList;
		int count;
		PageBeanDB page;
		try {
			count = preciseService.getPloyCount(ployName);
			page = new PageBeanDB(count,pageNo);
			ployList = preciseService.getPloyList(ployName,page.getBegin(),page.getEnd());
			request.setAttribute("ployName", ployName);
			request.setAttribute("ployList", ployList);
			request.setAttribute("page", page);
		} catch (Exception e) {
			logger.error("获取数据列表时出现异常",e);
		}
		return "ploy";
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

	public PreciseService getPreciseService() {
		return preciseService;
	}

	public void setPreciseService(PreciseService preciseService) {
		this.preciseService = preciseService;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;	
	}

	public List<PreciseBean> getPreciseList() {
		return preciseList;
	}

	public void setPreciseList(List<PreciseBean> preciseList) {
		this.preciseList = preciseList;
	}
	
}
