package com.dvnchina.advertDelivery.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;

import com.dvnchina.advertDelivery.bean.cpsPosition.CategoryBean;
import com.dvnchina.advertDelivery.constant.CPSConstant;
import com.dvnchina.advertDelivery.constant.PageConstant;
import com.dvnchina.advertDelivery.model.AdvertPosition;
import com.dvnchina.advertDelivery.service.AdvertPositionService;
import com.dvnchina.advertDelivery.service.CPSPositionService;
import com.dvnchina.advertDelivery.utils.page.PageBean;
import com.dvnchina.advertDelivery.utils.page.PageUtils;


public class CPSPositionAction extends BaseActionSupport<Object>{
	
	private static final long serialVersionUID = 7842919731174823576L;

	private Logger logger = Logger.getLogger(CPSPositionAction.class);
	
	private CPSPositionService cpsPositionService;
	
	private List<AdvertPosition> listCPSCatgory;
	
	private AdvertPosition advertPosition;
	
	private AdvertPositionService advertPositionService;
	
	/**
	 * 页号
	 */
	protected int pageNo;
	
	
	public String stageSyncCategoryInfo2(){
		String flag="";
	//	String flag = SUCCESS;
		Map categoryBeanMap = null;
		HttpServletRequest request = getRequest();
		String hasError = null;
		Document document = null;
		
		/**
		 * 无效模板-无广告位
		 */
		Map<String,CategoryBean> invalidsTemplate_no_positionMap = new HashMap<String,CategoryBean>();
		/**
		 * 无效模板-无对应模板
		 */
		Map<String,CategoryBean> invalidsTemplate_no_templateMap = new HashMap<String,CategoryBean>();
		
		try {
			//从cps中获取数据
			//TwowayConstant.SYNC_CPS_CATEGORY_PATH : 加载从cps中同步数据的接口地址 这个从配置文件中读取cps 接口  
			//.getDocumentFromCps() 以输入流方式从cps接口中获取数据
			
			//Document document = null;
			//TwowayConstant.SYNC_CPS_CATEGORY_PATH = advert.sync.category.path=http://localhost:12010/iscp/userValidateServlet
			document = cpsPositionService.getDocumentFromCps(CPSConstant.SYNC_CPS_CATEGORY_PATH);
			//将所有状态更新为待删除状态[0、待删除]
			//TwowayConstant.WAIT_BE_DELETED_CATEGORY:待删除状态 0 
			
//这不可以省略？？？？？？？？？？？？？？、
			
//			cpsPositionService.updateaAllCategoryBeanOperationType(CPSConstant.WAIT_BE_DELETED_CATEGORY);
			//分段从cps中同步数据
			//需要注意：这里返回的值为 Map 类型
			//已经把相关的操作保存到数据库了
			
			//这里返回的resultMap 也就是 categoryBeanMap，其包括三种类型的：有效节点，无效模板---无广告位， 无效模板---无对应模板 中的所有情况
           
			categoryBeanMap = cpsPositionService.updateCategoryAndTemplateInfoStage(document);
			
			//三种情况，这里只对其中的2种无效情况进行分析，分别进行判断
			if(categoryBeanMap!=null&&categoryBeanMap.size()>0){
				//TwowayConstant.INVALIDS_CATEGORY_TEMPLATE_NO_TEMPLATE 无效节点-模板无对应模板
				
				//第一种：无效节点-模板无对应模板，若存在
				if(categoryBeanMap.get(CPSConstant.INVALIDS_CATEGORY_TEMPLATE_NO_TEMPLATE)!=null){
					//拿到无效节点的属性集合
					invalidsTemplate_no_templateMap = (Map<String,CategoryBean>)categoryBeanMap.get(CPSConstant.INVALIDS_CATEGORY_TEMPLATE_NO_TEMPLATE);
					if(invalidsTemplate_no_templateMap.size()>0){
						//这个可能就是一个 标识信息
						hasError = "yes";
//为什么要把所有无效节点的属性集合 都Request中？？？
						request.setAttribute("noTemplateMap",invalidsTemplate_no_templateMap);
						//将标志信息放到Resquest中
						request.setAttribute("hasError",hasError);
					}
				}
				
				//第二种情况：无效节点-模板无广告位
				if(categoryBeanMap.get(CPSConstant.INVALIDS_CATEGORY_TEMPLATE_NO_POSITION)!=null){
					invalidsTemplate_no_positionMap = (Map<String,CategoryBean>)categoryBeanMap.get(CPSConstant.INVALIDS_CATEGORY_TEMPLATE_NO_POSITION);
					if(invalidsTemplate_no_positionMap.size()>0){
						hasError = "yes";
						request.setAttribute("noPositionMap",invalidsTemplate_no_positionMap);
						request.setAttribute("hasError",hasError);
					}
				}
			}else{
				//如果同步过来的信息，返回的结果集，这里是Map，则直接给出提示语句。
				hasError = "yes";
				request.setAttribute("hasError",hasError);
			}
			
			//查询待删除的记录
	//		List<CategoryBean> categoryBeanList=cpsPositionService.queryWaitBeDeleteCategory();
			//更新T_RULES表中记录的状态，这里更新的记录全都是 待删除 状态，根据上面刚查询出来的结果集
			//在从cps同步资源时，删除节点所留广告位资源至为无效，这里是置为 0。
			//根据节点Category 的Id 和 TreeName 以及 节点 Position 的 Id 值。筛选中 
			//UPDATE_RULES_STATUS_FAILURE = 0 
			//更新 规则表 t_rules
//			rulesService.updateRulesStatus(CPSConstant.UPDATE_RULES_STATUS_FAILURE,categoryBeanList);
			//删除待删除记录
			//删除这张表 t_category_template_info
//          cpsPositionService.deleteWaitBeDeleteCategory();
			
		} catch (Exception e) {
		//	flag = ERROR;
			logger.error("从cps中同步数据异常",e);
	//		queryCategoryInfo();
			hasError = "yes";
			request.setAttribute("hasError",hasError);
		}
	//查看同步过来的节点信息
	//将一些后台中的属性放到Request中，还有就是分页功能的一些属性
		//	queryCategoryInfo();
	//分段从cps中同步数据
	//categoryBeanMap = cpsPositionService.updateCategoryAndTemplateInfoStage(document);
		
		
		/*if(advertPosition == null ){
			advertPosition = new AdvertPosition();
		}
		*/
		
		
		this.queryPage();
		
		
		//int count = cpsPositionService.getCPSCategoryCount(advertPosition,"");
		
		//PageBeanDB page = new PageBeanDB(count, pageNo);
		
	//	listCPSCatgory = advertPositionService.find(new HashMap());
		
	//	ListCPSCatgory = cpsPositionService.listCPSCategoryMgr(advertPosition,0,-1,"");
		
	//	this.getRequest().setAttribute("page", page);
		
		//return flag;
		
		//return null;
		
		return "list";
	}
	
	
	
	/**
	 * 查询广告位
	 * @return
	 */
	public String queryPage(){
		
		HttpServletRequest request = this.getRequest();
		//清空request中原有记录
		request.setAttribute("advertPosition",null);
		
		String currentPage=request.getParameter("currentPage");
		
		String queryConditionTypeId=request.getParameter("positionTypeId");
		
		String queryConditionName=request.getParameter("positionName");
		
		String contractBindingFlag = request.getParameter("contractBindingFlag");
		
	//	String returnPath = "";
		
		int count = 0;
		int pageNB = 1;
		PageBean pageBean = null;
		int pageSize = PageConstant.PAGE_SIZE;
		
		if (StringUtils.isNotBlank(currentPage)) {
			pageNB = Integer.valueOf(currentPage);
		}
		
		Map conditionMap = new HashMap(); 
		
		try {
			if (StringUtils.isNotBlank(queryConditionTypeId)) {
				queryConditionTypeId=URLDecoder.decode(queryConditionTypeId,"UTF-8");
				conditionMap.put("POSITION_TYPE_ID", Integer.valueOf(queryConditionTypeId));
				request.setAttribute("positionTypeId",queryConditionTypeId);
			}
			if (StringUtils.isNotBlank(queryConditionName)) {
				queryConditionName=URLDecoder.decode(queryConditionName,"UTF-8");
				conditionMap.put("POSITION_NAME", queryConditionName);
				request.setAttribute("positionName",queryConditionName);
			}
			
			count = advertPositionService.queryCount(conditionMap);
			List<AdvertPosition> positionList = advertPositionService.page(
					conditionMap, (pageNB - 1) * PageConstant.PAGE_SIZE, pageNB
							* PageConstant.PAGE_SIZE);
			pageBean = PageUtils.getPageBean2(pageNB, pageSize, count,positionList);
			request.setAttribute("positionList", pageBean.getList());
			request.setAttribute("page", pageBean);
		} catch (Exception e) {
			logger.error("获取数据列表时出现异常",e);
		}
		//return "page";
		return "list";
	}
	
	
	public String list(){
		
		if(advertPosition == null ){
			advertPosition = new AdvertPosition();
		}
		
		listCPSCatgory = advertPositionService.find(new HashMap());
		
		return "list";
		
	}
	
	
	
	/**
	 * 删除信息
	 */
	public String deleteInfo(){
		
		int count = 0;
		
		String id = this.getRequest().getParameter("cId");
		
		count = advertPositionService.remove(Integer.parseInt(id));
		
		if(count>0){
			log.debug("删除广告位信息成功");
		}else{
			log.debug("删除广告位失败，id为，id="+id);
		}
		
		//返回客户端
		this.returnMessage("记录成功");
		
		return null;
	}
	
	//AJAX 返回客户端方法
	public void returnMessage(String msg){
		try {
			
			logger.debug("returnMessage 被调用");
			
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(msg);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	/**
	 * 取得request
	 * @return
	 */
	public HttpServletRequest getRequest(){
		HttpServletRequest request = ServletActionContext.getRequest();
		return request;
	}

	public CPSPositionService getCpsPositionService() {
		return cpsPositionService;
	}

	public void setCpsPositionService(CPSPositionService cpsPositionService) {
		this.cpsPositionService = cpsPositionService;
	}


	public List<AdvertPosition> getListCPSCatgory() {
		return listCPSCatgory;
	}

	public void setListCPSCatgory(List<AdvertPosition> listCPSCatgory) {
		this.listCPSCatgory = listCPSCatgory;
	}

	public AdvertPosition getAdvertPosition() {
		return advertPosition;
	}

	public void setAdvertPosition(AdvertPosition advertPosition) {
		this.advertPosition = advertPosition;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public AdvertPositionService getAdvertPositionService() {
		return advertPositionService;
	}

	public void setAdvertPositionService(AdvertPositionService advertPositionService) {
		this.advertPositionService = advertPositionService;
	}
	
}






