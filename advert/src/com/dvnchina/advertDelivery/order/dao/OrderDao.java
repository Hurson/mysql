package com.dvnchina.advertDelivery.order.dao;

import java.util.Date;
import java.util.List;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.channelGroup.bean.TChannelGroup;
import com.dvnchina.advertDelivery.dao.BaseDao;
import com.dvnchina.advertDelivery.model.Contract;
import com.dvnchina.advertDelivery.model.ImageMeta;
import com.dvnchina.advertDelivery.model.ImageReal;
import com.dvnchina.advertDelivery.model.ReleaseArea;
import com.dvnchina.advertDelivery.model.ResourceReal;
import com.dvnchina.advertDelivery.model.VideoReal;
import com.dvnchina.advertDelivery.order.bean.AreaResource;
import com.dvnchina.advertDelivery.order.bean.DtvFtpInfo;
import com.dvnchina.advertDelivery.order.bean.Order;
import com.dvnchina.advertDelivery.order.bean.OrderMaterialRelation;
import com.dvnchina.advertDelivery.order.bean.OrderMaterialRelationTmp;
import com.dvnchina.advertDelivery.order.bean.playlist.MaterialBean;
import com.dvnchina.advertDelivery.ploy.bean.Ploy;
import com.dvnchina.advertDelivery.ploy.bean.TPreciseMatch;
import com.dvnchina.advertDelivery.position.bean.AdvertPosition;
import com.dvnchina.advertDelivery.sysconfig.bean.ChannelInfo;

public interface OrderDao extends BaseDao{
	
	/**
	 * 分页查询订单信息
	 * @param order
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryOrderList(Order order,int pageNo, int pageSize);
	
	/**
	 * 分页查询审核订单信息
	 * @param order
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryOrderAuditList(Order order,int pageNo, int pageSize);
	
	/**
	 * 根据广告商ID分页查询合同信息
	 * @param customId
	 * @param contract
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryContractList(Integer customId,Contract contract,int pageNo, int pageSize);
	
	/**
	 * 根据合同分页查询广告位信息
	 * @param position
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryPositionList(AdvertPosition position,int pageNo, int pageSize);
	
	/**
	 * 分页查询策略信息
	 * @param ploy
	 * @param adPackageType
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryPloyList(Ploy ploy,Integer adPackageType,int pageNo, int pageSize);
	
	/**
	 * 根据策略ID  ployId获取子策略
	 * @param ployId
	 * @return
	 */
	public List<Ploy> getSubPloyListByPloy(Integer ployId);
	
	/**
	 * 根据策略ID获取精准列表
	 * @param ployId
	 * @return
	 */
	public List<TPreciseMatch> queryPreciseListByPloy(Integer ployId);
	
	/**
	 * 查询素材信息
	 * @param resource
	 * @return
	 */
	public List<ResourceReal> queryResourceList(ResourceReal resource);
	
	/**
	 * 查询素材信息
	 * @param ids
	 * @return
	 */
	public List<ResourceReal> getResourceListByIds(String ids);
	
	/**
	 * 根据ID获取视频规格
	 * @param id
	 * @return
	 */
	public VideoReal getVideoRealById(Integer id);
	
	/**
	 * 根据ID获取图片规格
	 * @param id
	 * @return
	 */
	public ImageReal getImageRealById(Integer id);
	
	/**
	 * 根据日期范围，广告位ID和要排除的订单主键查询订单记录数
	 * 
	 * @param start
	 *            开始日期
	 * @param end
	 *            结束日期
	 * @param positionId
	 *            广告位ID
	 * @param orderId
	 *            订单主键
	 * @return 订单记录数
	 */
	public int getOrderNumByDate(String start, String end, Integer positionId,Integer orderId);
	
	public int getOrderNumByDateAndArea(String start, String end, Integer positionId,Integer orderId);
	
	/**
	 * 判断是否存在响应区域的订单
	 * @param start
	 * @param end
	 * @param positionId
	 * @param orderId
	 * @param ployId
	 * @return
	 */
	public boolean exsiteAreaOrder(String start, String end, Integer positionId,Integer orderId, Integer ployId);
	
	/**
	 * 根据区域、时段、频道组判断是否有冲突的订单
	 * @param positionId
	 * @param orderCode
	 * @return
	 */
	public boolean exsiteOrder(String start, String end,Integer positionId,String orderCode);
	
	/**
	 * 根据订单ID获取订单信息
	 * @param id
	 * @return
	 */
	public Order getOrderById(Integer id);
	
	/**
	 * 获取订单与素材关系列表
	 * @param rel
	 * @return
	 */
	public List<OrderMaterialRelation> getOrderMaterialRelList(OrderMaterialRelation rel);
	
	/**
	 * 获取开机订单与素材关系列表
	 * @param rel
	 * @return
	 */
	public List<OrderMaterialRelation> getBootOrderMaterialRelList(OrderMaterialRelation rel);
	
	/**
	 * 根据订单号删除订单素材关系
	 * @param orderId
	 */
	public void delOrderMaterialRelation(Integer orderId);
	
	/**
	 * 根据订单ID查询订单列表信息
	 * @param ids
	 * @return
	 */
	public List<Order> findOrderListByIds(String ids);
	
	/**
	 * 根据订单id删除订单关系记录
	 * @param ids
	 */
	public void deleteRelationByIds(String ids);
	
	/**
	 * 根据订单id删除订单
	 * @param ids
	 */
	public void deleteOrderByIds(String ids);
	
	/**
	 * 根据订单id修改订单状态
	 * @param orderId
	 * @param orderState
	 * @param userId
	 */
	public void updateOrderState(Integer orderId, String orderState,Integer userId);
	
	/**
	 * 根据广告位类型获取图片素材文件总大小
	 * @param positionType 广告位类型
	 * @param order  订单对象
	 * @param mateIds  素材IDS
	 * @return
	 */
	public long getImageFileSumSize(int positionType,Order order,String mateIds);
	
	/**
	 * 根据广告位类型获取视频素材文件总大小
	 * @param positionType 广告位类型
	 * @param order  订单对象
	 * @return
	 */
	public long getVideoFileSumSize(int positionType,Order order);
	
	/**
	 * 根据订单ID获取DTV发送FTP需要的信息
	 * @param orderId
	 * @return
	 */
	public List<DtvFtpInfo> getDtvFtpInfoList(Integer orderId);
	
	/**
	 * 获取区域列表
	 * @return
	 */
	public List<ReleaseArea> getAreaList();

	/**
	 * @description: 首页代办获取待审批的订单的总数
	 * @return 待审批的订单的总数
	 */
	public int getWaitingAuditOrderCount(String ids);
	
	/**
	 * 根据订单ID获取待恢复订单列表
	 * @param ids
	 * @return
	 */
	public List<Order> findRestoreOrderByIds(String ids);
	
	/**
	 * 根据订单id集合修改订单状态
	 * 
	 * @param ids
	 *            订单id集合
	 * @param orderState
	 *            订单状态
	 * */
	public void updateOrdersState(List<Integer> ids, String orderState);
	
	/**
	 * 根据素材ID获取素材信息
	 * @param id
	 * @return
	 */
	public MaterialBean getMaterialById(Integer id);
	
	/**
	 * 根据策略ID查询策略列表
	 * @param ployId
	 * @return
	 */
	public List<Ploy> getPloyByPloyId(Integer ployId);
	
	/**
	 * 根据策略IDS  策略列表
	 * @param ids
	 * @return
	 */
	public List<Ploy> getPloyListByIds(String ids);
	
	/**
	 * 根据频道组ID获取频道serviceId列表
	 * @param groupId
	 * @param channel
	 * @return
	 */
	public List<String> getServiceIdList(Integer groupId,ChannelInfo channel);

	/**
	 * 根据广告位和位移天数查找空档订单日期
	 * @param positionIds
	 * @param shiftDate
	 * @return
	 */
	public List<Order> getFreePositionRemindOrders(String positionIds, Date today, Date  shiftDate);

	/**
	 * 根据广告位和位移天数查找空档订单日期
	 * @param positionIds
	 * @param shiftDate
	 * @return
	 */
	public List<Object[]> getCustomerPositions(Integer customerId, Date today, Date  shiftDate);

	public List<Order> getCustomerFreePositionRemindOrders(String positionIds,
			Date today, Date shiftDate, String contractIds);
	
	/**
	 * 获取问卷订单已请求的记录数
	 * @param orderId
	 * @return
	 */
	public int getQuestionnaireCount(Integer orderId);
	
	/**
	 * 保存问卷订单在代办中已阅记录
	 * @param orderId
	 */
	public void saveRealQuestionnaire(Integer orderId);
	/**
	 * 根据策略ID获取NPVR频道组列表
	 * @param ployId
	 * @return
	 */
	public List<TChannelGroup> getNPVRChannelGroupListByPloyId(Integer ployId);
	
	/**
	 * 根据策略ID获取频道组列表
	 * @param ployId
	 * @return
	 */
	public List<TChannelGroup> getChannelGroupListByPloyId(Integer ployId);
	
	
	/**
	 * 查询订单可选素材列表信息
	 * @param areaResource
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryNPVRAreaResourceList(OrderMaterialRelationTmp omRelTmp, int pageNo, int pageSize);
	/**
	 * 查询订单可选素材列表信息
	 * @param areaResource
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryAreaResourceList(OrderMaterialRelationTmp omRelTmp, int pageNo, int pageSize);
	
	public PageBeanDB queryRadioResourceList(OrderMaterialRelationTmp omRelTmp, int pageNo, int pageSize);
	
	public PageBeanDB queryBootResourceDetailList(OrderMaterialRelationTmp omRelTmp, int pageNo, int pageSize);
	
	/**
	 * 查询回看菜单广告订单可选素材列表信息
	 * @param omRelTmp
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryLookResourceList(OrderMaterialRelationTmp omRelTmp, int pageNo, int pageSize);
	
	/**
	 * 查询插播广告订单可选素材列表信息
	 * @param omRelTmp
	 * @param positionId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryInstreamResourceList(OrderMaterialRelationTmp omRelTmp,int positionId, int pageNo, int pageSize);
	
	/**
	 * 查询暂停广告订单可选素材列表信息
	 * @param omRelTmp
	 * @param positionId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryPauseResourceList(OrderMaterialRelationTmp omRelTmp,int positionId, int pageNo, int pageSize);
	
	/**
	 * 查询请求式广告订单可选素材列表信息
	 * @param omRelTmp
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryReqResourceList(OrderMaterialRelationTmp omRelTmp, int pageNo, int pageSize);
	
	/**
	 * 添加订单和素材临时关系数据
	 * @param orderCode
	 * @param ployId
	 */
	public void insertOrderMateRelTmp(String orderCode,int ployId,int positionId);
	/**
	 * 修改订单时添加订单和素材临时关系数据
	 * @param orderCode
	 * @param ployId
	 */
	public void insertOrderMateRelTmp2(String orderCode,int ployId,int positionId);
	
	/**
	 * 修改全时段的开始、结束时段
	 */
	public void updateAllTimeOrderMateRelTmp();
	
	/**
	 * 添加回看回放菜单广告订单和素材临时关系数据
	 * @param orderCode
	 * @param ployId
	 */
	public void insertLookBackOrderMateRelTmp(String orderCode,int ployId,int positionId);
	public void insertLookBackOrderMateRelTmp2(String orderCode,int ployId,int positionId);
	
	/**
	 * 添加回看回放菜单广告订单和素材临时关系数据 点播随片
	 * @param orderCode
	 * @param ployId
	 */
	public void insertFollowOrderMateRelTmp(String orderCode,int ployId,int positionId);
	public void insertFollowOrderMateRelTmp2(String orderCode,int ployId,int positionId);
	
	/**
	 * 添加插播广告订单和素材临时关系数据
	 * @param orderCode
	 * @param ployId
	 * @param instreamNumber
	 */
	public void insertInstreamOrderMateRelTmp(String orderCode,int ployId,int instreamNumber);
	public void insertInstreamOrderMateRelTmp2(String orderCode,int ployId,int instreamNumber);
	
	/**
	 * 添加回看回放暂停广告订单和素材临时关系数据
	 * @param orderCode
	 * @param ployId
	 */
	public void insertPauseOrderMateRelTmp(String orderCode,int ployId);
	public void insertPauseOrderMateRelTmp2(String orderCode,int ployId);
	
	/**
	 * 添加请求式广告订单和素材临时关系数据
	 * @param orderCode
	 * @param ployId
	 */
	public void insertReqOrderMateRelTmp(String orderCode,int ployId);
	public void insertReqOrderMateRelTmp2(String orderCode,int ployId);
	
	/**
	 * 添加  开机图片 广告订单和素材临时关系数据
	 * @param orderCode
	 * @param ployId
	 */
	public void insertBootOrderMateRelTmp(String orderCode,int ployId);
	public void insertBootOrderMateRelTmp2(String orderCode,int ployId);
	
	/**
	 * 轮询菜单图片广告位中的订单和素材临时关系数据
	 * @param orderCode
	 * @param ployId
	 * @param loops
	 */
	public void loopMenuPosition(String orderCode,int ployId,int loops);
	public void loopMenuPosition2(String orderCode,int ployId,int loops);
	
	/*
	 * 一个广告有多张图片，显示在不同位置
	 */
	public void multiposition(String orderCode,int ployId,int positionCount);
	
	/**
	 * 保存订单和素材临时数据
	 * @param ids
	 * @param mateId
	 */
	public void saveOrderMateRelTmp(String ids, Integer mateId);
	
	public void saveBootOrderMateRelTmp(Integer mateId, String orderCode, String locations, String areas);
	
	public void saveDefaultBootOrderMateRelTmp(Integer mateId, String orderCode, String areas);

	public List<AreaResource> getSelectMaterialJsonByOrderId(String id);
	
	/**
	 * 将临时订单和素材关系数据保存到正式表
	 * @param orderId
	 * @param orderCode
	 */
	public void saveOrderMaterialRelationFromTmp(Integer orderId, String orderCode);
	
	/**
	 * 删除订单和素材临时关系数据
	 * @param orderCode
	 */
	public void delOrderMateRelTmp(String orderCode);

	public PageBeanDB getSelectedResource(AreaResource areaResource,
			int pageNo, int pageSize);
	
	/**
	 * 查询订单和素材临时关系数据
	 * @param orderCode
	 * @return
	 */
	public List<OrderMaterialRelationTmp> getOrderMaterialRelationTmpList(String orderCode);

	public String queryResourcePath(String metaId);
	public String queryResourcePath2(String metaId);

	public PageBeanDB queryLookResourceListbyPre(
			OrderMaterialRelationTmp omRelTmp, int pageNo, int pageSize);

	public PageBeanDB queryTheAreaResourceList(
			OrderMaterialRelationTmp omRelTmp, Integer ployId, int pageNo,
			int pageSize);
	
	public PageBeanDB queryBootPicResourceList(OrderMaterialRelationTmp omRelTmp, int pageNo,int pageSize);

	
	

	public void insertLookRepalyOrderMateRelTmp(String orderCode, int ployId,int positionId);
	public void insertLookRepalyOrderMateRelTmp2(String orderCode, int ployId,int positionId);
	
	
	public List getSelectedLoopPicNumList(String orderCode);
	
	public List valiIsHasSuCai(String orderCode);
}
