package com.dvnchina.advertDelivery.position.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.constant.FileOperationConstant;
import com.dvnchina.advertDelivery.model.ContractAD;
import com.dvnchina.advertDelivery.model.ContractADBackup;
import com.dvnchina.advertDelivery.position.bean.AdvertPosition;
import com.dvnchina.advertDelivery.position.bean.ImageSpecification;
import com.dvnchina.advertDelivery.position.bean.PositionPackage;
import com.dvnchina.advertDelivery.position.bean.QuestionnaireSpecification;
import com.dvnchina.advertDelivery.position.bean.TextSpecification;
import com.dvnchina.advertDelivery.position.bean.VideoSpecification;
import com.dvnchina.advertDelivery.position.dao.PositionDao;
import com.dvnchina.advertDelivery.position.service.PositionService;
import com.dvnchina.advertDelivery.utils.json.Obj2JsonUtil;

public class PositionServiceImpl implements PositionService {
	
	private PositionDao positionDao;
	private Logger log = Logger.getLogger(PositionServiceImpl.class);
	/**
	 * 分页获取广告包信息
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryPositionPackageList(int pageNo, int pageSize){
		return positionDao.queryPositionPackageList(pageNo, pageSize);
	}
	
	/**
	 * 根据ID获取广告位包信息
	 * @param id
	 * @return
	 */
	public PositionPackage getPositionPackageById(Integer id){
		return positionDao.getPositionPackageById(id);
	}
	
	/**
	 * 根据子广告位ID获取广告位包类型
	 * @param adId
	 * @return
	 */
	public Integer getPackageTypeByAdId(Integer adId){
		return positionDao.getPackageTypeByAdId(adId);
	}
	
	/**
	 * 获取子广告位详情
	 * @param id
	 * @return
	 */
	public AdvertPosition getAdvertPosition(Integer id){
		AdvertPosition advertPosition = positionDao.getAdvertPosition(id);
		if(advertPosition!=null){
			PositionPackage positionPackage = this.getPositionPackageById(advertPosition.getPositionPackageId());
			advertPosition.setPositionPackageType(positionPackage.getPositionPackageType());
		}
		return advertPosition;
	}
	
	/**
	 * 根据ID获取图片规格
	 * @param id
	 * @return
	 */
	public ImageSpecification getImageSpeById(Integer id){
		return positionDao.getImageSpeById(id);
	}
	
	/**
	 * 保存视频规格
	 * @param videoSpe
	 */
	public void saveVideoSpecification(VideoSpecification videoSpe){
		positionDao.update(videoSpe);
	}
	
	/**
	 * 保存图片规格
	 * @param imageSpe
	 */
	public void saveImageSpecification(ImageSpecification imageSpe){
		positionDao.update(imageSpe);
	}
	
	/**
	 * 保存文字规格
	 * @param textSpe
	 */
	public void saveTextSpecification(TextSpecification textSpe){
		positionDao.update(textSpe);
	}
	
	/**
	 * 保存问卷规格
	 * @param questionnaireSpe
	 */
	public void saveQuestionnaireSpecification(QuestionnaireSpecification questionnaireSpe){
		positionDao.update(questionnaireSpe);
	}
	
	/**
	 * 拷贝本地文件至指定目录  不修改文件名
	 * @param localFilePath
	 * @param targetDirectory
	 * @param targetFileName
	 * @param uploadDir
	 * @return
	 */
	public String copyLocalFileTargetDirectory(String localFilePath,
			String targetDirectory, String targetFileName, String uploadDir) {
		
		File dir = new File(targetDirectory);
		
		//如果这个目录不存在，则创建它
		if(!dir.exists()){
			dir.mkdirs();
		}
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		FileInputStream fis  = null;
		FileOutputStream fos = null;
		
		Map<String,String> resultMap = new HashMap<String,String>();
		try {
			fis = new FileInputStream(localFilePath);
			bis = new BufferedInputStream(fis);
			fos = new FileOutputStream(new File(dir,targetFileName));
			bos = new BufferedOutputStream(fos);
			
			byte[] buf = new byte[4096];
			
			int len = -1;
			
			while((len = bis.read(buf))!=-1){
				bos.write(buf,0,len);
			}
			resultMap.put("result","true");
			
		} catch (Exception e) {
			resultMap.put("result","false");
			
			log.error("读取本地文件失败",e);
		}finally{
			try {
				if(bis!=null){
					bis.close();
				}
				
				if(bos!=null){
					bos.close();
				}
				
				if(fis!=null){
					fis.close();
				}
				
				if(fos!=null){
					fos.close();
				}
			} catch (Exception e) {
				resultMap.put("result","false");
				log.error("关闭IO时出现异常",e);
			}
		}
		String filePath = uploadDir+FileOperationConstant.FILE_SEPARATOR+targetFileName;
		filePath = filePath.replace("\\","/");
		resultMap.put("filepath",filePath);
		return Obj2JsonUtil.map2json(resultMap);
	}
	
	/**
	 * 修改子广告位信息（坐标、宽高、描述、背景图片）
	 * @param advertPosition
	 */
	public void updateAdvertPosition(AdvertPosition advertPosition){
		positionDao.updateAdvertPosition(advertPosition);
	}
	
	/**
	 * 根据ID获取视频规格
	 * @param id
	 * @return
	 */
	public VideoSpecification getVideoSpeById(Integer id){
		return positionDao.getVideoSpeById(id);
	}
	
	/**
	 * 根据ID获取字幕规格
	 * @param id
	 * @return
	 */
	public TextSpecification getTextSpeById(Integer id){
		return positionDao.getTextSpeById(id);
	}
	
	/**
	 * 根据ID获取问卷规格
	 * @param id
	 * @return
	 */
	public QuestionnaireSpecification getQuestionnaireSpeById(Integer id){
		return positionDao.getQuestionnaireSpeById(id);
	}
	
	/**
	 * 根据广告位包ID获取子广告位列表
	 * @param packageId
	 * @return
	 */
	public List<AdvertPosition> findADPositionByPackage(Integer packageId){
		return positionDao.findADPositionByPackage(packageId);
	}
	
	/**
	 * 查询广告位包对应的合同占用情况
	 * @param ca
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB viewPositionOccupy(ContractAD ca, int pageNo, int pageSize){
		return positionDao.viewPositionOccupy(ca,pageNo, pageSize);
	}
	
	/**
     * 查询广告位包对应的合同占用情况(查询临时表数据)
     * @param ca
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageBeanDB viewPositionOccupyForContractManager(ContractADBackup ca, int pageNo, int pageSize){
        return positionDao.viewPositionOccupyForContractManager(ca, pageNo, pageSize);
    }
	
	
	public void setPositionDao(PositionDao positionDao) {
		this.positionDao = positionDao;
	}
	
	/**
	 * 根据传入的广告位包查找所有广告位包包含的广告位
	 * @param packageIds
	 * @return
	 */
	public List<AdvertPosition> findADPositionsByPackages(String packageIds){
		 return positionDao.findADPositionsByPackages(packageIds);
	}
	
}
