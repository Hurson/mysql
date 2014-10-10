package com.dvnchina.advertDelivery.service;

import java.util.List;
import java.util.Map;

import com.dvnchina.advertDelivery.bean.position.PositionBean;
import com.dvnchina.advertDelivery.model.AdvertPosition;
import com.dvnchina.advertDelivery.model.PositionOccupyStatesInfo;


public interface AdvertPositionService {
	/**
	 * 保存广告位信息
	 * @param advertPosition
	 * @return
	 */
	public String savePosition(AdvertPosition advertPosition);
	/**
	 * 批量保存
	 * @return
	 */
	public int[] saveBatchPosition(List<AdvertPosition> positionBean);
	/**
	 * @param start 开始
	 * @param end 结束
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AdvertPosition> page(Map condition, int start, int end);
	/**
	 * 查询记录数
	 * @param condition
	 * @return
	 */
	public int queryCount(Map condition);
	/**
	 * 根据指定条件进行查询
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AdvertPosition> find(Map condition);
	/**
	 * 删除广告位
	 * @param advertPositionId  
	 * @return
	 */
	public int remove(int advertPositionId);
	
	/**
	 * 更新广告位
	 * @param advertPositionAfter
	 * @return
	 */
	public int update(AdvertPosition advertPosition);
	/**
	 * 批量更新广告位
	 * @param advertPositionAfter
	 * @return
	 */
	public int[] updateBatch(List<AdvertPosition> advertPositionList);
	
	/**
	 * 根据特征列表值查询指定的广告位
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AdvertPosition> findPositionByEign(List<String> eignValueList);
	
	
	/**
	 * 根据资产表中的广告位id，查询所属的广告位
	 * @param advertPositionId
	 * @return
	 */
	public List<AdvertPosition> getAdvertPositionById(Integer advertPositionId);
	/**
	 * 查询广告位占用情况总记录数
	 * @param condition
	 * @return
	 */
	public int queryCount4PositionOccupyStatus(Integer status,Integer positionId,String startDate,String endDate);
	/**
	 * 分页查询广告位占用情况
	 * 1 已销售 以合同-广告位运行期表为参照表
	 * select mrule.id ruleIdP,mrule.rule_id as ruleId,mrule.position_id as positionId,mrule.rule_name as ruleName,to_char(mrule.start_time,'hh24:mi:ss') as startTime ,to_char(mrule.end_time,'hh24:mi:ss') as endTime,mrule.location_id as releaseAreaId,mrule.channel_id as channelId,mrule.state marketingRuleStates,area.area_code as releaseAreaCode,area.area_name as areaName,area.parent_code as parentCode from t_marketing_rule mrule inner join t_release_area area on mrule.location_id=area.id 
	 * 2 待销售 以营销规则表为参照表，营销规则中存在，合同-广告位运行期表中不存在
	 * 3 其它   广告位开始和结束投放时段和营销规则中定义的差集
	 * @param start 开始
	 * @param end 结束
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PositionOccupyStatesInfo> page4PositionOccupyStatus(Integer status,Integer positionId, int start, int end,String startDate,String endDate);
	/**
	 * 生成特征值
	 * @param type 0 标清 1高清
	 * @param typeCode 广告位类型 typeCode
	 * @return
	 */
	public List<String> generateCharacteristicIdentification(int type,String typeCode);
	/**
	 * 查看广告位的占用情况
	 * @param advertPositionList 广告位
	 * @return
	 */
	public List<Integer> getAdvertPositionOccupyStatus(List<AdvertPosition> advertPositionList);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public int[] removeBatchAdvertPosition(String[] ids);
	
}
