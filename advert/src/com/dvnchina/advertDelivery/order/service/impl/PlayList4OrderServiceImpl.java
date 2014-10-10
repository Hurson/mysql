package com.dvnchina.advertDelivery.order.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.order.bean.Order;
import com.dvnchina.advertDelivery.order.bean.PutInPlayListBean;
import com.dvnchina.advertDelivery.order.bean.RequestPlayListBean;
import com.dvnchina.advertDelivery.order.dao.PlayList4OrderDao;
import com.dvnchina.advertDelivery.order.dao.PlayListGisDao;
import com.dvnchina.advertDelivery.order.dao.PlayListReqDao;
import com.dvnchina.advertDelivery.order.service.OrderService;
import com.dvnchina.advertDelivery.order.service.PlayList4OrderService;
import com.dvnchina.advertDelivery.sysconfig.service.BaseConfigService;


/**
 * @author liye
 *
 */
public class PlayList4OrderServiceImpl implements PlayList4OrderService {
	
	private PlayList4OrderDao playList4OrderDao;
	private PlayListGisDao playListGisDao;
	private PlayListReqDao playListReqDao;
	private BaseConfigService baseConfigService;
	
	private Order order = null;
	private OrderService orderService;
	
	/**
	 * 根据订单id和订单类型查询投放式播出单记录
	 * */
	public PutInPlayListBean getPutInPlayListByOrderId(Integer orderId){
		return playList4OrderDao.getPutInPlayListByOrderId(orderId);
	}
	
	/**
	 * 根据订单id和订单类型查询请求式播出单记录
	 * */
	public List<RequestPlayListBean> getRequestPlayListByOrderId(Integer orderId){
		return playList4OrderDao.getRequestPlayListByOrderId(orderId);
	}
	
	/**
	 * 检查播出单开始时间是否大于指定的时间
	 * @param orderId 订单编号
	 * @param date 时间
	 * @param orderType 订单类型
	 * @return 0-大于，1-小于
	 * */
	public int checkPlayListStartTime(Integer orderId, Date date,int orderType) {
		int aheadTime = 0;
		long startTime = 0;
		if (orderType == Constant.PUT_IN_ORDER) {
			aheadTime = new Integer(baseConfigService.getBaseConfigByCode("orderOpAheadTime"));
		}
		//根据订单ID查询投放式播出单的最小开始时间
		Date sTime = playList4OrderDao.getPutInPlayListStart(orderId);
		if (sTime != null) {
			startTime = sTime.getTime();
		} else {
			//根据订单ID查询请求式播出单的开始时间
			Map<String, Object> timeMap = playList4OrderDao.getRequestPlayListStart(orderId);
			if (timeMap != null) {
				Date beginDate = (Date) timeMap.get("BEGIN_DATE");
				String begin = (String) timeMap.get("BEGIN");
				Date beginTime = this.stringToTime(beginDate, begin);
				startTime = beginTime.getTime();
			}
		}
		if (startTime >= date.getTime() + aheadTime * 1000) {
			return 0;
		} else {
			return 1;
		}
	}
	
	/**
	 * 将类型为Date的日期和类型为String的时间转成Date型的日期时间
	 * */
	private Date stringToTime(Date date, String time) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = null;
		try {
			String d = sdf.format(date);
			dt = df.parse(d + " " + time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dt;
	}
	
	/**
	 * 根据策略，时间范围查询除指定订单id外的播出单对应的订单号
	 * @param ployId 策略编号
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param orderType 订单类型
	 * @param orderId 订单主键
	 * @return 订单号
	 * */
	public String getPlayListOrderNo(Integer ployId,Date startDate,Date endDate,Integer orderType,Integer orderId){
		String orderNo = null;
		List<Integer> ids = new ArrayList<Integer>();
		if (orderType == Constant.PUT_IN_ORDER) {

			List<Map<String, Object>> orders = playList4OrderDao.getPutInOrder(ployId, orderId);
			for (Map o : orders) {
				Date startTime = (Date) o.get("STARTTIME");
				Date endTime = (Date) o.get("ENDTIME");
				Date sTime = this.getYMD(startTime);
				Date eTime = this.getYMD(endTime);
				if ((sTime.getTime() >= startDate.getTime() && sTime.getTime() <= endDate
						.getTime())
						|| (sTime.getTime() <= startDate.getTime() && eTime
								.getTime() >= startDate.getTime())) {
					ids.add(new Integer(o.get("ORDER_ID").toString()));
				}
			}
		} else if (orderType == Constant.REQUEST_ORDER) {
			ids = playList4OrderDao.getRequestOrder(ployId, startDate, endDate, orderId);
		}
		if (ids.size() > 0) {
			orderNo = playList4OrderDao.getOrderNoById(ids);
		}
		return orderNo;
	}
	
	private Date getYMD(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String ymd = df.format(date);
		Date ymdDate = null;
		try {
			ymdDate = df.parse(ymd);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ymdDate;
	}
	
	/**
	 * 根据策略，时间范围查询播出单对应的订单号
	 * @param ployId 策略编号
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param orderType 订单类型
	 * @return 订单号
	 * */
	public String getPlayListOrderNo(Integer ployId,Date startDate,Date endDate,Integer orderType){
		String orderNo = null;
		List<Integer> ids = new ArrayList<Integer>();
		if (orderType == Constant.PUT_IN_ORDER) {

			List<Map<String, Object>> orders = playList4OrderDao.getPutInOrder(ployId);
			for (Map o : orders) {
				Date startTime = (Date) o.get("STARTTIME");
				Date endTime = (Date) o.get("ENDTIME");
				Date sTime = this.getYMD(startTime);// 播出单开始日期
				Date eTime = this.getYMD(endTime);// 播出单结束日期
				if ((sTime.getTime() >= startDate.getTime() && sTime.getTime() <= endDate
						.getTime())
						|| (sTime.getTime() <= startDate.getTime() && eTime
								.getTime() >= startDate.getTime())) {
					ids.add(new Integer(o.get("ORDER_ID").toString()));
				}
			}
		} else if (orderType == Constant.REQUEST_ORDER) {
			ids = playList4OrderDao.getRequestOrder(ployId, startDate, endDate);
		}
		if (ids.size() > 0) {
			orderNo = playList4OrderDao.getOrderNoById(ids);
		}
		return orderNo;
	}
	
	/**
	 * 根据订单编号查询订单对应的播出单类型
	 * @param orderId 订单编号
	 * @return 播出单类型码
	 */
	public Integer getTypeByOrderId(Integer orderId){
		List<Integer> pIds = playListGisDao.getPlayListIds(orderId);
		if (pIds != null && pIds.size() > 0) {
			return Constant.PUT_IN_ORDER;
		}
		pIds = playListReqDao.getPlayListIds(orderId);
		if (pIds != null && pIds.size() > 0) {
			return Constant.REQUEST_ORDER;
		}
		return null;
	}
	
	/**
	 * 查询执行完毕的投放式订单编号
	 * @return 订单编号集合
	 * */
	public List<Integer> getFinishedPutInPlayList(){
		List<Integer> ids = new ArrayList<Integer>();
		List<Map<String, Object>> ends = playList4OrderDao.getPutInPlayListEndTime();
		if(ends != null && ends.size()>0){
			for (Map end : ends) {
				if(end.get("ENDTIME") != null && end.get("ORDER_ID") != null){
					Date endTime = (Date) end.get("ENDTIME");
					if (endTime.getTime() <= new Date().getTime()) {
						Integer id = new Integer(end.get("ORDER_ID").toString());
						//下面原本只有ids.add(id);
						order=orderService.getOrderById(id);
						String state=order.getState();
						if(Constant.ORDER_PUBLISHED.equals(state)){
							ids.add(id);
						}						
						
					}
				}
			}
		}
		return ids;
	}
	
	/**
	 * 查询执行完毕的请求式订单编号
	 * @return 订单编号集合
	 * */
	public List<Integer> getFinishedRequestPlayList(){
		List<Integer> ids = new ArrayList<Integer>();
		List<Map<String, Object>> ends = playList4OrderDao.getRequestPlayListEndDate();
		if(ends != null && ends.size()>0){
			for (Map end : ends) {
				if(end.get("END_DATE") != null && end.get("END") != null && end.get("ORDER_ID")!=null){
					Date endDate = (Date) end.get("END_DATE");
					String endTime = (String) end.get("END");
					Date eTime = this.stringToTime(endDate, endTime);
					if (eTime.getTime() <= new Date().getTime()) {
						Integer id = new Integer(end.get("ORDER_ID").toString());
						//下面原本只有ids.add(id);
						order=orderService.getOrderById(id);
						String state=order.getState();
						if(Constant.ORDER_PUBLISHED.equals(state)){
							ids.add(id);
						}	
					}
				}
			}
		}
		return ids;
	}
	
	/**
	 * 将播出单状态为停用
	 * */
	public void setPlayListState(List<Integer> orderIds,int orderType){
		if (orderType == Constant.PUT_IN_ORDER) {
			playList4OrderDao.updatePutInPlayListsState(orderIds,4);
		} else if (orderType == Constant.REQUEST_ORDER) {
			playList4OrderDao.updateRequestPlayListsState(orderIds,4);
		}

	}
	
	public void setPlayList4OrderDao(PlayList4OrderDao playList4OrderDao) {
		this.playList4OrderDao = playList4OrderDao;
	}

	public void setPlayListGisDao(PlayListGisDao playListGisDao) {
		this.playListGisDao = playListGisDao;
	}

	public void setPlayListReqDao(PlayListReqDao playListReqDao) {
		this.playListReqDao = playListReqDao;
	}

	public void setBaseConfigService(BaseConfigService baseConfigService) {
		this.baseConfigService = baseConfigService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
}
