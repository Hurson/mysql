package com.dvnchina.advertDelivery.model;

/**
 * 投放区域管理
 * @author Weicl
 *
 */
public class ReleaseArea extends CommonObject {

	private static final long serialVersionUID = -4879127837142232709L;
	private Integer id;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 投放区域CODE
	 */
	
	private String areaCode;
	
	/**
	 * 投放区域名称
	 */
	private String areaName;
	
	/**
	 * 父级区域编码
	 */
	private String parentCode;
	/**
	 * 区域类型< - >LOCATIONTYPE
	 */
	private String locationType;
	/**
	 * 区域类型< - >LOCATIONTYPE
	 */
	private String locationCode;

	
	public ReleaseArea()
	{
		
	}
	public ReleaseArea(Integer id,String areaCode,String parentcode,String areaName)
	{
		this.id=id;
		this.areaCode=areaCode;
		this.parentCode=parentCode;
		this.areaName = areaName;
	}
	
	public ReleaseArea(Integer id, String areaCode, String areaName,
			String parentCode, String locationType, String locationCode) {
		super();
		this.id = id;
		this.areaCode = areaCode;
		this.areaName = areaName;
		this.parentCode = parentCode;
		this.locationType = locationType;
		this.locationCode = locationCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public Long getLareaCode() {
		return Long.valueOf(areaCode);
	}

	
}
