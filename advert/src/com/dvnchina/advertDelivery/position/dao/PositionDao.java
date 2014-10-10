package com.dvnchina.advertDelivery.position.dao;

import java.util.List;
import java.util.Map;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.BaseDao;
import com.dvnchina.advertDelivery.model.ContractAD;
import com.dvnchina.advertDelivery.model.ContractADBackup;
import com.dvnchina.advertDelivery.position.bean.AdvertPosition;
import com.dvnchina.advertDelivery.position.bean.ImageSpecification;
import com.dvnchina.advertDelivery.position.bean.PositionPackage;
import com.dvnchina.advertDelivery.position.bean.QuestionnaireSpecification;
import com.dvnchina.advertDelivery.position.bean.TextSpecification;
import com.dvnchina.advertDelivery.position.bean.VideoSpecification;

public interface PositionDao extends BaseDao{
	
	/**
	 * 分页获取广告包信息
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryPositionPackageList(int pageNo, int pageSize);
	
	/**
	 * 根据ID获取广告位包信息
	 * @param id
	 * @return
	 */
	public PositionPackage getPositionPackageById(Integer id);
	
	/**
	 * 根据子广告位ID获取广告位包类型
	 * @param adId
	 * @return
	 */
	public Integer getPackageTypeByAdId(Integer adId);
	
	/**
	 * 获取子广告位详情
	 * @param id
	 * @return
	 */
	public AdvertPosition getAdvertPosition(Integer id);
	
	/**
	 * 根据ID获取图片规格
	 * @param id
	 * @return
	 */
	public ImageSpecification getImageSpeById(Integer id);
	
	/**
	 * 修改子广告位信息（坐标、宽高、描述、背景图片）
	 * @param advertPosition
	 */
	public void updateAdvertPosition(AdvertPosition advertPosition);
	
	/**
	 * 根据ID获取视频规格
	 * @param id
	 * @return
	 */
	public VideoSpecification getVideoSpeById(Integer id);
	
	/**
	 * 根据ID获取字幕规格
	 * @param id
	 * @return
	 */
	public TextSpecification getTextSpeById(Integer id);
	
	/**
	 * 根据ID获取问卷规格
	 * @param id
	 * @return
	 */
	public QuestionnaireSpecification getQuestionnaireSpeById(Integer id);
	
	/**
	 * 根据广告位包ID获取子广告位列表
	 * @param packageId
	 * @return
	 */
	public List<AdvertPosition> findADPositionByPackage(Integer packageId);
	
	/**
	 * 查询广告位包对应的合同占用情况
	 * @param ca
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB viewPositionOccupy(ContractAD ca, int pageNo, int pageSize);
	
	/**
     * 查询广告位包对应的合同占用情况(查询临时表数据)
     * @param ca
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageBeanDB viewPositionOccupyForContractManager(ContractADBackup ca, int pageNo, int pageSize);

    /**
	 * 根据传入的广告位包查找所有广告位包包含的广告位
	 * @param packageIds
	 * @return
	 */
    public List<AdvertPosition> findADPositionsByPackages(String packageIds);

}
