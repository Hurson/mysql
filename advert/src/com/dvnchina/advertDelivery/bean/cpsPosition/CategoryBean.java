package com.dvnchina.advertDelivery.bean.cpsPosition;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;


public class CategoryBean implements Serializable, Cloneable{

	private static final long serialVersionUID = -1468497542436480934L;
	
	private Integer id;
	
	// category部分开始
	/**
	 * cId为category表主键
	 */
	private Integer ccId;
	/**
	 * categoryId收视分析系统使用
	 */
	private String ccategoryId;
	/**
	 * 节点名称
	 */
	private String ccategoryName;
	/**
	 * 模板id
	 */
	private Integer ctemplateId;
	/**
	 * 区域码
	 */
	private Integer cnetworkId;
	/**
	 * 用户级别
	 */
	private Integer cuserLevel;
	/**
	 * 行业类别
	 */
	private Integer cindustryType;
	/**
	 * type为树上展示各种类型【 type:类型 8:分类 7：业务 6：应用 5：频道 4： 3：产品 2：资源包 1：资源 】
	 */
	private Integer ctype;
	
	/**
	 * 修改时间
	 */
	private String cmodifyTime;
	
	// category部分结束

	// template部分开始
	/**
	 * 模板名称
	 */
	private String ttemplateName;

	/**
	 * 效果图链接地址
	 */
	private String teffectPictureUrl;

	/**
	 * 模板类型 0 标清 1 高清
	 */
	private Integer ttemplateType;
	// template部分结束

	// position部分开始
	/**
	 * 广告位名称
	 */
	private String pcname;
	/**
	 * 广告位宽度
	 */
	private Integer pwidth;
	/**
	 * 广告位高度
	 */
	private Integer pheight;
	/**
	 * y坐标
	 */
	private Integer ptop;
	/**
	 * x坐标
	 */
	private Integer pleft;
	/**
	 * 0 category 1 template 2 position
	 */
	private Integer pdateType;
	
	/**
	 * 广告位Id
	 */
	private Integer pId;
	
	/**
	 * 描述
	 */
	private String pdescribe;
	/**
	 * 图片规格ID
	 */
	private Integer ppictureMaterialSpeciId;
	/**
	 * 视频规格ID
	 */
	private Integer pvideoMaterialSpeciId;
	/**
	 * 文字规格ID
	 */
	private Integer pcontentMaterialSpeciId;
	/**
	 * 问卷规格ID
	 */
	private Integer pquestionMaterialSpeciId;
	/**
	 * 是否叠加
	 */
	private Integer pisOverlying;

	/**
	 * 广告位编码
	 */
	private String pcCode;
	
	/**
	 * 广告位标识
	 */
	private String peigenValue;
	
	/**
	 * 修改时间
	 */
//	private Date pmodifyTime;
	
	private String pmodifyTime;
	
	/**
	 * 广告位类型编码
	 */
	private String ppositionTypeCode;
	
	// position部分结束
	/**
	 * 创建时间
	 */
	private Timestamp createTime;
	/**
	 * 修改时间
	 */
//	private Timestamp modifyTimeT;
	
	private String modifyTime;
	
	/**
	 * 状态 0 新建 1、修改 2、删除
	 */
	private Integer state;
	/**
	 * 树id
	 */
	private Integer cTreeId;
	/**
	 * 树名称
	 */
	private String cTreeName;

	/*@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((peigenValue == null) ? 0 : peigenValue.hashCode());
		result = prime * result
				+ ((pmodifyTime == null) ? 0 : pmodifyTime.hashCode());
		return result;
	}*/

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final CategoryBean other = (CategoryBean) obj;
		if (peigenValue == null) {
			if (other.peigenValue != null)
				return false;
		} else if (!peigenValue.equals(other.peigenValue))
			return false;
		if (pmodifyTime == null) {
			if (other.pmodifyTime != null)
				return false;
		} else if (!pmodifyTime.equals(other.pmodifyTime))
			return false;
		return true;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Override
	public Object clone() {
		Object o = null;
		try {
			o = (CategoryBean) super.clone();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}

	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		return stringBuffer.append("id=").append(id).append("\t ccId=").append(
				ccId).append("\t ccategoryId=").append(ccategoryId).append(
				"\t ccategoryName=").append(ccategoryName).append(
				"\t ctemplateId=").append(ctemplateId).append("\t cnetworkId=")
				.append(cnetworkId).append("\t cuserLevel=").append(cuserLevel)
				.append("\t cindustryType=").append(cindustryType).append(
						"\t ctype=").append(ctype).append("\t ttemplateName=")
				.append(ttemplateName).append("\t teffectPictureUrl=").append(
						teffectPictureUrl).append("\t ttemplateType=").append(
						ttemplateType).append("\t pcname=").append(pcname)
				.append("\t pwidth=").append(pwidth).append("\t pheight=")
				.append(pheight).append("\t ptop=").append(ptop).append(
						"\t pleft=").append(pleft).append("\t createTime=")
				.append(createTime).append("\t modifyTime=").append(modifyTime)
				.append("\t state=").append(state).toString();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCcId() {
		return ccId;
	}

	public void setCcId(Integer ccId) {
		this.ccId = ccId;
	}

	public String getCcategoryId() {
		return ccategoryId;
	}

	public void setCcategoryId(String ccategoryId) {
		this.ccategoryId = ccategoryId;
	}

	public String getCcategoryName() {
		return ccategoryName;
	}

	public void setCcategoryName(String ccategoryName) {
		this.ccategoryName = ccategoryName;
	}

	public Integer getCtemplateId() {
		return ctemplateId;
	}

	public void setCtemplateId(Integer ctemplateId) {
		this.ctemplateId = ctemplateId;
	}

	public Integer getCnetworkId() {
		return cnetworkId;
	}

	public void setCnetworkId(Integer cnetworkId) {
		this.cnetworkId = cnetworkId;
	}

	public Integer getCuserLevel() {
		return cuserLevel;
	}

	public void setCuserLevel(Integer cuserLevel) {
		this.cuserLevel = cuserLevel;
	}

	public Integer getCindustryType() {
		return cindustryType;
	}

	public void setCindustryType(Integer cindustryType) {
		this.cindustryType = cindustryType;
	}

	public Integer getCtype() {
		return ctype;
	}

	public void setCtype(Integer ctype) {
		this.ctype = ctype;
	}

	public String getTtemplateName() {
		return ttemplateName;
	}

	public void setTtemplateName(String ttemplateName) {
		this.ttemplateName = ttemplateName;
	}

	public String getTeffectPictureUrl() {
		return teffectPictureUrl;
	}

	public void setTeffectPictureUrl(String teffectPictureUrl) {
		this.teffectPictureUrl = teffectPictureUrl;
	}

	public Integer getTtemplateType() {
		return ttemplateType;
	}

	public void setTtemplateType(Integer ttemplateType) {
		this.ttemplateType = ttemplateType;
	}

	public String getPcname() {
		return pcname;
	}

	public void setPcname(String pcname) {
		this.pcname = pcname;
	}

	public Integer getPwidth() {
		return pwidth;
	}

	public void setPwidth(Integer pwidth) {
		this.pwidth = pwidth;
	}

	public Integer getPheight() {
		return pheight;
	}

	public void setPheight(Integer pheight) {
		this.pheight = pheight;
	}

	public Integer getPtop() {
		return ptop;
	}

	public void setPtop(Integer ptop) {
		this.ptop = ptop;
	}

	public Integer getPleft() {
		return pleft;
	}

	public void setPleft(Integer pleft) {
		this.pleft = pleft;
	}

	public Integer getPdateType() {
		return pdateType;
	}

	public void setPdateType(Integer pdateType) {
		this.pdateType = pdateType;
	}

	public Integer getPId() {
		return pId;
	}

	public void setPId(Integer id) {
		pId = id;
	}
	
	public String getPdescribe() {
		return pdescribe;
	}

	public void setPdescribe(String pdescribe) {
		this.pdescribe = pdescribe;
	}

	public Integer getPpictureMaterialSpeciId() {
		return ppictureMaterialSpeciId;
	}

	public void setPpictureMaterialSpeciId(Integer ppictureMaterialSpeciId) {
		this.ppictureMaterialSpeciId = ppictureMaterialSpeciId;
	}

	public Integer getPvideoMaterialSpeciId() {
		return pvideoMaterialSpeciId;
	}

	public void setPvideoMaterialSpeciId(Integer pvideoMaterialSpeciId) {
		this.pvideoMaterialSpeciId = pvideoMaterialSpeciId;
	}

	public Integer getPcontentMaterialSpeciId() {
		return pcontentMaterialSpeciId;
	}

	public void setPcontentMaterialSpeciId(Integer pcontentMaterialSpeciId) {
		this.pcontentMaterialSpeciId = pcontentMaterialSpeciId;
	}

	public Integer getPquestionMaterialSpeciId() {
		return pquestionMaterialSpeciId;
	}

	public void setPquestionMaterialSpeciId(Integer pquestionMaterialSpeciId) {
		this.pquestionMaterialSpeciId = pquestionMaterialSpeciId;
	}

	public Integer getPisOverlying() {
		return pisOverlying;
	}

	public void setPisOverlying(Integer pisOverlying) {
		this.pisOverlying = pisOverlying;
	}

	public String getPcCode() {
		return pcCode;
	}

	public void setPcCode(String pcCode) {
		this.pcCode = pcCode;
	}
	
	public String getPeigenValue() {
		return peigenValue;
	}

	public void setPeigenValue(String peigenValue) {
		this.peigenValue = peigenValue;
	}

	public String getPmodifyTime() {
		return pmodifyTime;
	}

	public void setPmodifyTime(String pmodifyTime) {
		this.pmodifyTime = pmodifyTime;
	}

	public int compareValue() {
	
		Long id = this.ccId.longValue();
		
	//	Integer id = this.ccId;
		
		try {
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date modifyTimeDate = dateFormat.parse(this.getModifyTime());
			Timestamp modifyTimeT = new Timestamp(modifyTimeDate.getTime());
			
			if(this.modifyTime != null){
				id += modifyTimeT.getTime();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return id.hashCode();
	}
	
	
	
	
	@Override
	public int hashCode() {
		
		String eigenValue = this.peigenValue;
		
		try {
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			String modifyTimeString = this.getModifyTime();
			
			Date modifyTimeDate = dateFormat.parse(modifyTimeString);
			
			Timestamp modifyTimeT = new Timestamp(modifyTimeDate.getTime());
			
			if(this.modifyTime != null){
				eigenValue += modifyTimeT.getTime();
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return eigenValue.hashCode();
	}
	
	public Integer getCTreeId() {
		return cTreeId;
	}

	public void setCTreeId(Integer treeId) {
		cTreeId = treeId;
	}

	public String getCTreeName() {
		return cTreeName;
	}

	public void setCTreeName(String treeName) {
		cTreeName = treeName;
	}

	public String getCmodifyTime() {
		return cmodifyTime;
	}

	public void setCmodifyTime(String cmodifyTime) {
		this.cmodifyTime = cmodifyTime;
	}

	public String getPpositionTypeCode() {
		return ppositionTypeCode;
	}

	public void setPpositionTypeCode(String ppositionTypeCode) {
		this.ppositionTypeCode = ppositionTypeCode;
	}
	
}







