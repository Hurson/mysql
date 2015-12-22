package com.avit.dtmb.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import com.avit.dtmb.material.bean.DResource;
import com.avit.dtmb.order.bean.DOrder;
import com.avit.dtmb.order.bean.DOrderMateRel;
import com.avit.dtmb.order.bean.DOrderMateRelTmp;
import com.avit.dtmb.order.bean.PlayList;
import com.avit.dtmb.order.dao.DOrderDao;
import com.avit.dtmb.order.service.DOrderService;
import com.avit.dtmb.ploy.bean.DPloy;
import com.avit.dtmb.position.bean.DAdPosition;
import com.avit.dtmb.type.PloyType;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.model.Customer;
import com.dvnchina.advertDelivery.model.ImageMeta;
import com.dvnchina.advertDelivery.model.MessageMeta;
import com.dvnchina.advertDelivery.model.ReleaseArea;
import com.dvnchina.advertDelivery.model.VideoMeta;
import com.dvnchina.advertDelivery.order.bean.playlist.TextMate;
import com.dvnchina.advertDelivery.utils.config.ConfigureProperties;
import com.google.gson.Gson;
@Service
public class DOrderServiceImpl implements DOrderService {

	@Resource
	private DOrderDao dOrderDao;
	@Override
	public PageBeanDB queryDTMBOrderList(DOrder order, int pageNo, int pageSize) {
		return dOrderDao.queryDTMBOrderList(order, pageNo, pageSize);
	}

	@Override
	public DOrder getDTMBOrderById(Integer id) {
		DOrder order = (DOrder)dOrderDao.get(DOrder.class, id);
		dOrderDao.deleteDOrderMateRelTmp(order);
		dOrderDao.copyDOrderMateRelTmp(order);
		return order;
	}

	@Override
	public void saveDOrder(DOrder order) {
		dOrderDao.saveDOrder(order);
		this.saveOrderMateRel(order);

	}

	@Override
	public void deleteDOrder(List<String> ids) {
		for(String id : ids){
			DOrder order = (DOrder)dOrderDao.get(DOrder.class, Integer.valueOf(id.trim()));
			if("0".equals(order.getState())){
				dOrderDao.delete(order);
			}else{
				order.setState("2");
				dOrderDao.saveOrUpdate(order);
			}
		}

	}

	@Override
	public List<DAdPosition> queryPositionList() {
		
		return dOrderDao.queryPositionList();
	}

	@Override
	public PageBeanDB queryDTMBPloyList(DPloy ploy, int pageNo, int pageSize) {
		return dOrderDao.queryDTMBPloyList(ploy, pageNo, pageSize);
	}

	@Override
	public void insertDOrderMateRelTmp(DOrder order) {
		dOrderDao.deleteDOrderMateRelTmp(order);
		dOrderDao.insertDOrderMateRelTmp(order);
		
	}

	@Override
	public PageBeanDB queryDOrderMateRelTmpList(DOrderMateRelTmp omrTmp, int pageNo, int pageSize) {

		return dOrderDao.queryDOrderMateRelTmpList(omrTmp, pageNo, pageSize);
	}

	@Override
	public List<ReleaseArea> queryReleaseAreaList() {
		
		return dOrderDao.queryReleaseAreaList();
	}

	@Override
	public PageBeanDB queryDResourceList(DResource resource, int pageNo, int pageSize) {
		return dOrderDao.queryDResourceList(resource, pageNo, pageSize);
	}

	@Override
	public void saveOrderMateRelTmp(String ids, Integer id) {
		dOrderDao.saveOrderMateRelTmp(ids, id);
		
	}

	@Override
	public String getOrderResourceJson(DOrderMateRelTmp omrTmp) {
		List<DResource> resourceList = dOrderDao.getOrderResourceJson(omrTmp);
		Gson gson = new Gson();
		return gson.toJson(resourceList);
	}

	@Override
	public String auditDTMBPloy(DOrder order, String flag) {
		String result = "-1";
		
		DOrder dorder = (DOrder)dOrderDao.get(DOrder.class, order.getId());
		if("1".equals(flag)){
			dorder.setEndDate(order.getEndDate());
			
			if("0".equals(dorder.getState()) && insertPlayList(dorder) > 0){
				dorder.setState("6");
				result = "0";
			}else if("1".equals(dorder.getState()) && updatePlayListEndDate(dorder) > 0){
				dorder.setState("6");
				result = "0";
			}else if("2".equals(dorder.getState())){
				dorder.setEndDate(new Date());
				if(updatePlayListEndDate(dorder) > 0){
					dorder.setState("7");
					result = "0";
				}else{
					result = "-1";
				}
				
			}else{
				result = "-1";
			}
		
		}else if("-1".equals(flag)){
			int state = Integer.parseInt(dorder.getState()) + 3;
			dorder.setState(state + "");
		}else{
			result = "-1";
		}
		
		if("0".equals(result)){
			dorder.setAuditAdvice(order.getAuditAdvice());
			dOrderDao.saveDOrder(dorder);
		}
		
		return result;
		
		
	}
	private void saveOrderMateRel(DOrder order){
		dOrderDao.deleteDOrderMateRel(order);
		dOrderDao.saveDOrderMateRel(order);
		dOrderDao.deleteDOrderMateRelTmp(order);
		
	}

	@Override
	public PageBeanDB queryAuditDOrderList(DOrder order, int pageNo, int pageSize) {
		
		return dOrderDao.queryAuditDOrderList(order, pageNo, pageSize);
	}
	private int insertPlayList(DOrder order){
		List<DOrderMateRel> orderMateRelList = dOrderDao.getOrderMateRelList(order.getOrderCode());
		List<PlayList> playList = new ArrayList<PlayList>();
		for(DOrderMateRel omr : orderMateRelList){
			PlayList play = new PlayList();
			
			play.setAreaCode(omr.getAreaCode());
			play.setOrderCode(order.getOrderCode());
			play.setPositionCode(order.getDposition().getPositionCode());
			play.setStartDate(order.getStartDate());
			play.setEndDate(order.getEndDate());
			play.setStartTime(omr.getStartTime());
			play.setEndTime(omr.getEndTime());
			play.setIndexNum(omr.getIndexNum());
			play.setIsDefault(order.getIsDefault());
			play.setPloyType(omr.getPloyType());
			play.setTypeValue(getTypeValue(omr.getPloyType(), omr.getTypeValue()));
			play.setUserIndustries(getUserData(order.getDploy().getId(),PloyType.UserIndustry.getKey()));
			play.setUserLevels(getUserData(order.getDploy().getId(),PloyType.UserLevel.getKey()));
			play.setTvn(getUserData(order.getDploy().getId(),PloyType.UserTVNNO.getKey()));
			play.setResourceId(omr.getResource().getId());
			play.setResourcePath(getResourcePath(omr.getResource()));
			play.setStatus("0");
			
			playList.add(play);
			
		}
		int result = dOrderDao.saveAll(playList).size();
		return result;
	}
	private String getTypeValue(String type, String value){
		String values = "";
		if("3".equals(type) || "4".equals(type)){
			List<String> serviceIdList = dOrderDao.getChannelGroupServiceIds(Integer.valueOf(value));
			if(serviceIdList == null){
				return values;
			}
			for(String serviceId : serviceIdList){
				values += serviceId + ",";
			}
		}
		return values;
	}
	private String getUserData(Integer ployId, String type){
		String datas = "";
		List<String> dataList = dOrderDao.getPloyValueByType(ployId,type);
		if(dataList == null){
			return datas;
		}
		for(String data : dataList){
			datas += data + ",";
		}
		return datas;
	}
	
	private String getResourcePath(DResource resource){
		String matePath = ConfigureProperties.getInstance().get("materila.ftp.realPath");
		if(resource == null){
			return "";
		}
		String resourcePath = "";
		Integer resourceType = resource.getResourceType();
		switch(resourceType){
		case 0:
			ImageMeta image = (ImageMeta)dOrderDao.get(ImageMeta.class, resource.getResourceId());
			resourcePath =matePath+ "/" + image.getName();
			break;
		case 1:
			VideoMeta video = (VideoMeta)dOrderDao.get(VideoMeta.class, resource.getResourceId());
			resourcePath = matePath + "/" + video.getName();
			break;
		case 2:
			MessageMeta message = (MessageMeta)dOrderDao.get(MessageMeta.class, resource.getResourceId());
			TextMate textMate = new TextMate();
             if(message.getContent()!=null){
            	 try{
            		BeanUtils.copyProperties(textMate, message);
            		textMate.setContent(message.getContent());
            	 }catch(Exception e){
            		 e.printStackTrace();
            	 }
            	 Gson gson = new Gson();
            	 resourcePath = gson.toJson(textMate);
             }
		}
		return resourcePath;
	}
	private int updatePlayListEndDate(DOrder order){
		int result = dOrderDao.updatePlayListEndDate(order);
		return result;
	}

	@Override
	public String checkDOrderRule(DOrder order) {
		List<Integer> list = dOrderDao.checkDOrderRule(order);
		if(list != null && list.size() > 0){
			return "1";
		}
		return "0";
	}

	@Override
	public void delDOrderMateRelTmp(String ids) {
		dOrderDao.delDOrderMateRelTmp(ids);
		
	}

	@Override
	public List<Customer> getCustomerList() {
		
		return dOrderDao.getCustomerList();
	}

	@Override
	public String repushOrder(String orderCode) {
		if(dOrderDao.updatePlayListState(orderCode) > 0){
			if(dOrderDao.updateOrderState(orderCode) > 0){
				return "0";
			}
		}
		
		return "-1";
	}

	@Override
	public String previewResource(DResource resource) {
		DResource res = (DResource)dOrderDao.get(DResource.class, resource.getId());
		String matePath = this.getResourcePath(res);
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", res.getResourceType().toString());
		map.put("res", matePath);
		DAdPosition position = dOrderDao.getDPostionByPositionCode(resource.getPositionCode());
		Gson gson = new Gson();
		map.put("position", gson.toJson(position));
		
		return gson.toJson(map);
	}
	
}
