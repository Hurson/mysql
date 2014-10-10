package com.dvnchina.advertDelivery.awaitDoing.action;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.dvnchina.advertDelivery.accounts.service.ContractAccountsService;
import com.dvnchina.advertDelivery.action.BaseActionSupport;
import com.dvnchina.advertDelivery.awaitDoing.service.AwaitDoingService;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.constant.ResourceMetasConstant;
import com.dvnchina.advertDelivery.meterial.service.MeterialManagerService;
import com.dvnchina.advertDelivery.model.UserLogin;
import com.dvnchina.advertDelivery.order.bean.Order;
import com.dvnchina.advertDelivery.order.service.OrderService;
import com.dvnchina.advertDelivery.ploy.service.PloyService;
import com.dvnchina.advertDelivery.position.bean.AdvertPosition;
import com.dvnchina.advertDelivery.position.service.PositionService;
import com.dvnchina.advertDelivery.sysconfig.service.BaseConfigService;
import com.dvnchina.advertDelivery.warn.bean.WarnInfo;
import com.dvnchina.advertDelivery.warn.service.WarnService;

public class AwaitDoing extends BaseActionSupport<Object> implements
		ServletRequestAware {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(AwaitDoing.class);
	private HttpServletRequest request;
	private MeterialManagerService meterialManagerService;
	private PloyService ployService;
	private OrderService orderService;
	private ContractAccountsService contractAccountsService;
	private PositionService positionService;
	private BaseConfigService baseConfigService;
	private AwaitDoingService awaitDoingService;
	private int orderAuditAwaiting;
	private int materialAuditAwaiting;
	private int ployAuditAwaiting;
	private int expireingContractCount;
	private String value;
	private List<String> lstMarginFreePosition;
	private List<Order> questionnaireOrderList;
	private String positionIds;
	private String contractIds = "";
	private WarnService warnService;
	private List<WarnInfo> warnInfoList;
	private PageBeanDB page = null;

	/**
	 * @description: 获取首页代办提醒需要的数据
	 * @return 
	 */
	public String getHomePageAwaitingDoing() {
		UserLogin userLogin = (UserLogin) this.getRequest().getSession().getAttribute("USER_LOGIN_INFO");
		try {
			lstMarginFreePosition = getFreePositionRemind(userLogin);
		} catch (Exception e) {
			lstMarginFreePosition = null;
			logger.error("查询空档广告位出错", e);
		}
		try {
			// 资产审核代办事项
			materialAuditAwaiting = meterialManagerService.queryMaterialWaitingAuditCount(positionIds);
		} catch (Exception e) {
			logger.error("查询待办素材审核时出错", e);
			materialAuditAwaiting = 0;
		}
		try {
			// 投放策略审核代办事项
			ployAuditAwaiting = ployService.getWaitingAuditPloyCount(positionIds);
		} catch (Exception e) {
			logger.error("查询待办策略审核时出错", e);
			ployAuditAwaiting = 0;
		}
		try {
			// 订单审核代办事项提醒
			orderAuditAwaiting = orderService.getWaitingAuditOrderCount(positionIds);
		} catch (Exception e) {
			logger.error("查询待办订单审核时出错", e);
			orderAuditAwaiting = 0;
		}
		try {
			// 合同欠费停播提醒代办事情
			Date shiftDate = this.addDay(getCurDay(), 7);
			expireingContractCount = contractAccountsService.getExpireingContractCount(contractIds, getCurDay(), shiftDate);
		} catch (Exception e) {
			logger.error("查询待办将要到期欠费的合同时出错", e);
			expireingContractCount = 0;
		}
		
		try{
			warnInfoList = warnService.getEntityList();
		}catch(Exception e){
			logger.error("查询告警信息出错", e);
			warnInfoList = new ArrayList<WarnInfo>();
		}
		
		//获取到达门限值的问卷订单列表
		findQuestionnaireOrderList(userLogin);
		return SUCCESS;
	}

	/**
	 * 获取当前登录用户的广告位排期空档
	 * @param userLogin
	 */
	private List<String> getFreePositionRemind(UserLogin userLogin) {
		int type = userLogin.getRoleType();
		String ids = "";
		value = baseConfigService.getBaseConfigByCode(ResourceMetasConstant.FREE_POSITION_REMIND);
		Date today = getCurDay();
		Date shiftDate = this.addDay(today, Integer.valueOf(value));
		List<String> lstMarginFreePositions = null;
		if(type == 2){
			
			List<Integer> lstPositionIds = userLogin.getPositionIds();
			for (int i = 0, j = lstPositionIds.size(); i < j; i++) {
				if(i == j -1){
					ids += lstPositionIds.get(i);
				}else{
					ids += lstPositionIds.get(i) + ",";
				}
			}
			positionIds = "";
			List<AdvertPosition> lstPositions = positionService.findADPositionsByPackages(ids);
			for (int i = 0, j = lstPositions.size(); i < j; i++) {
				if(i == j -1){
					positionIds += lstPositions.get(i).getId();
				}else{
					positionIds += lstPositions.get(i).getId() + ",";
				}
			}
			lstMarginFreePositions = orderService.getFreePositionRemindOrders(positionIds, lstPositions, today, shiftDate);
		}else if(type == 1){
			List<Object[]> lstPositionIds = orderService.getCustomerPositions(userLogin.getCustomerId(), today, shiftDate);
			Map<String, Object[]> map = new HashMap<String, Object[]>();
			if(lstPositionIds == null){
				return null;
			}
			for (int i = 0, j = lstPositionIds.size(); i < j; i++) {
				Object[] obj = lstPositionIds.get(i);
				if(i == j -1){
					ids += obj[0];
				}else{
					ids += obj[0] + ",";
				}
				if(contractIds.indexOf((BigDecimal)obj[3]+"") < 0){
					contractIds += (BigDecimal)obj[3]+ ",";
				}
				map.put((BigDecimal)obj[0]+"", obj);
			}
			if(!contractIds.isEmpty()){
				contractIds = contractIds.substring(0, contractIds.length()-1);
			}
			
			String positionIds = "";
			List<AdvertPosition> lstPositions = positionService.findADPositionsByPackages(ids);
			Map<String, Object[]> mapPostion = new HashMap<String, Object[]>();
			for (int i = 0, j = lstPositions.size(); i < j; i++) {
				AdvertPosition advertPosition = lstPositions.get(i);
				if(i == j -1){
					positionIds += advertPosition.getId();
				}else{
					positionIds += advertPosition.getId() + ",";
				}
				Object[] temp = map.get(advertPosition.getPositionPackageId()+"");
				if(temp != null){
					mapPostion.put(advertPosition.getId()+"", temp);
				}
			}
			lstMarginFreePositions = orderService.getCustomerFreePositions(positionIds, contractIds, lstPositions, mapPostion, today, shiftDate);
		}
		return lstMarginFreePositions;
	}
	
	/**
	 * 获取到达门限值的问卷订单列表
	 * @param userLogin
	 * @return
	 */
	private void findQuestionnaireOrderList(UserLogin userLogin) {
		int type = userLogin.getRoleType();
		if(type == 2){//运营商
			positionIds = getPositionIdsByUser(userLogin);
		}else if(type == 1){//广告商
			List<Object[]> positionContractList = awaitDoingService.getPositionContractByCustomer(userLogin.getCustomerId());
			if(positionContractList != null && positionContractList.size()>0){
				StringBuffer positionSB = new StringBuffer();
				StringBuffer contractSB = new StringBuffer();
				for(Object[] obj : positionContractList){
					positionSB.append(toInteger(obj[0])).append(",");//子广告位ID
					contractSB.append(toInteger(obj[1])).append(",");//合同ID
				}
				positionIds = positionSB.toString();
				positionIds = positionIds.substring(0,positionIds.length()-1);
				contractIds = contractSB.toString();
				contractIds = contractIds.substring(0,contractIds.length()-1);
			}
		}
		if(StringUtils.isNotBlank(positionIds)){
			questionnaireOrderList = awaitDoingService.getQuestionnaireOrderList(positionIds,contractIds);
		}
	}
	
	/**
	 * BigDecimal to Integer
	 * @param obj
	 * @return Integer
	 */
	private Integer toInteger(Object obj) {
		Integer value = null;
		if (obj != null) {
			if (obj instanceof BigDecimal) {
				value = ((BigDecimal) obj).intValue();
			} else if (obj instanceof BigInteger) {
				value = ((BigInteger) obj).intValue();
			} else if (obj instanceof Integer) {
				value = ((Integer) obj).intValue();
			}
		}
		return value;
	}
	
	/**
	 * 运营商，根据登录用户信息获取可操作的广告Id
	 * @param userLogin
	 * @return
	 */
	private String getPositionIdsByUser(UserLogin userLogin){
		String positionIds = "";
		List<Integer> positionPkgIds = userLogin.getPositionIds();
		
		if(positionPkgIds != null && positionPkgIds.size()>0){
			StringBuffer pkgSB = new StringBuffer();
			for(Integer pkgId : positionPkgIds){
				pkgSB.append(pkgId).append(",");
			}
			ids = pkgSB.toString();
			ids = ids.substring(0,ids.length()-1);
			
			List<AdvertPosition> positionList = positionService.findADPositionsByPackages(ids);
			if(positionList != null && positionList.size()>0){
				StringBuffer pSB = new StringBuffer();
				for(AdvertPosition position : positionList){
					pSB.append(position.getId()).append(",");
				}
				positionIds = pSB.toString();
				positionIds = positionIds.substring(0,positionIds.length()-1);
			}
		}
		
		
		return positionIds;
	}

	private Date addDay(Date date, int n){
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        
        calendar.add(Calendar.DAY_OF_MONTH, n);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND,59);
        return new Date(calendar.getTimeInMillis());
    }
	
	private Date getCurDay(){
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTime();
	}
	public String showWarn(){
		if(page == null){
			page = new PageBeanDB();
		}
		page=warnService.queryWarning(page.getPageNo(), page.getPageSize());
		return SUCCESS;
	}
	public void delWarn(){
		try{
			ids = getRequest().getParameter("ids");
			warnService.deleteWarnInfo(ids);
			renderText("0");
		}catch(Exception e){
			renderText("-1");
			e.printStackTrace();
		}
	}
	
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;

	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public int getOrderAuditAwaiting() {
		return orderAuditAwaiting;
	}

	public void setOrderAuditAwaiting(int orderAuditAwaiting) {
		this.orderAuditAwaiting = orderAuditAwaiting;
	}

	public int getMaterialAuditAwaiting() {
		return materialAuditAwaiting;
	}

	public void setMaterialAuditAwaiting(int materialAuditAwaiting) {
		this.materialAuditAwaiting = materialAuditAwaiting;
	}

	public int getPloyAuditAwaiting() {
		return ployAuditAwaiting;
	}

	public void setPloyAuditAwaiting(int ployAuditAwaiting) {
		this.ployAuditAwaiting = ployAuditAwaiting;
	}

	public MeterialManagerService getMeterialManagerService() {
		return meterialManagerService;
	}

	public void setMeterialManagerService(
			MeterialManagerService meterialManagerService) {
		this.meterialManagerService = meterialManagerService;
	}

	public PloyService getPloyService() {
		return ployService;
	}

	public void setPloyService(PloyService ployService) {
		this.ployService = ployService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public ContractAccountsService getContractAccountsService() {
		return contractAccountsService;
	}

	public void setContractAccountsService(
			ContractAccountsService contractAccountsService) {
		this.contractAccountsService = contractAccountsService;
	}

	public int getExpireingContractCount() {
		return expireingContractCount;
	}

	public void setExpireingContractCount(int expireingContractCount) {
		this.expireingContractCount = expireingContractCount;
	}

	public List<String> getLstMarginFreePosition() {
		return lstMarginFreePosition;
	}

	public void setLstMarginFreePosition(List<String> lstMarginFreePosition) {
		this.lstMarginFreePosition = lstMarginFreePosition;
	}

	public PositionService getPositionService() {
		return positionService;
	}

	public void setPositionService(PositionService positionService) {
		this.positionService = positionService;
	}

	public BaseConfigService getBaseConfigService() {
		return baseConfigService;
	}

	public void setBaseConfigService(BaseConfigService baseConfigService) {
		this.baseConfigService = baseConfigService;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<Order> getQuestionnaireOrderList() {
		return questionnaireOrderList;
	}

	public void setQuestionnaireOrderList(List<Order> questionnaireOrderList) {
		this.questionnaireOrderList = questionnaireOrderList;
	}

	public void setAwaitDoingService(AwaitDoingService awaitDoingService) {
		this.awaitDoingService = awaitDoingService;
	}

	
	public WarnService getWarnService() {
		return warnService;
	}

	public void setWarnService(WarnService warnService) {
		this.warnService = warnService;
	}

	public List<WarnInfo> getWarnInfoList() {
		return warnInfoList;
	}

	public void setWarnInfoList(List<WarnInfo> warnInfoList) {
		this.warnInfoList = warnInfoList;
	}

	public PageBeanDB getPage() {
		return page;
	}

	public void setPage(PageBeanDB page) {
		this.page = page;
	}
	
	
	
}
