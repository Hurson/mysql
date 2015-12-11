package com.avit.dtmb.material.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.avit.dtmb.material.bean.DResource;
import com.avit.dtmb.material.service.MaterialService;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.common.BaseAction;
import com.dvnchina.advertDelivery.meterial.bean.MaterialCategory;
import com.dvnchina.advertDelivery.meterial.service.MeterialManagerService;
import com.dvnchina.advertDelivery.position.bean.ImageSpecification;
import com.dvnchina.advertDelivery.position.bean.VideoSpecification;
import com.dvnchina.advertDelivery.utils.json.Obj2JsonUtil;
@ParentPackage("default")
@Namespace("/dmaterial")
@Scope("prototype")
@Controller
public class MeterialAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3629860605255061545L;
	@Resource
	private MaterialService materialService;
	@Resource
	private MeterialManagerService meterialManagerService;
	private PageBeanDB page;
	private DResource meterialQuery;
	private List<MaterialCategory> materialCategoryList;
	private PageBeanDB adPositionPage;
	private Integer advertPositionId;
	
	
	
	
	
	public MeterialAction(){
		
	}
	@Action(value = "queryMaterialList",results={@Result(name="success",location="/page/material/new/Dmaterial/DmaterialList.jsp")})
	public String queryMaterialList(){
		materialCategoryList=meterialManagerService.getMaterialCategoryList();
		if(page==null){
			page = new PageBeanDB();
		}
		try{
			if(meterialQuery==null){
                meterialQuery=new DResource();
            }
			page = materialService.queryDMaterialList(meterialQuery,page.getPageNo(), page.getPageSize());
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 进入添加页面
	 * 
	 */
	@Action(value = "initAdd",results={@Result(name="success",location="/page/material/new/Dmaterial/addMaterial.jsp")})
	public String initAdd(){
		materialCategoryList = meterialManagerService.getMaterialCategoryList();
		return SUCCESS;
	}
	
	/**
	 * 选择子广告位
	 * @return 
	 * @return
	 */
	@Action(value = "selectAdPosition",results={@Result(name="success",location="/page/material/new/Dmaterial/DselectAdPosition.jsp")})
	public String selectAdPosition(){
		 if (adPositionPage==null)
	        {
	            adPositionPage  =  new PageBeanDB();
	        }
		 try{
			 adPositionPage = materialService.queryPositonList(adPositionPage.getPageNo(), adPositionPage.getPageSize());
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 return SUCCESS;
	}
	/**
	 * 根据广告位获取视频规格
	 * @return 
	 * @return
	 */
	@Action(value = "getVideo")
	public String getVideo(){
		VideoSpecification videoSpecification = materialService.getVideoSpc(advertPositionId);
		Map<String,String> resultMap = new HashMap<String,String>();
        resultMap.put("videoFileDuration",videoSpecification.getDuration().toString());
        print(Obj2JsonUtil.map2json(resultMap));
		return NONE;
	}
	/**
	 * 根据广告位获取图片规格
	 * @param str
	 */
	@Action(value = "getImg")
	public String getImg(){
		ImageSpecification imageSpecification = materialService.getImageMateSpeci(advertPositionId);
	    Map<String,String> resultMap = new HashMap<String,String>();
	    resultMap.put("imageFileWidth",imageSpecification.getImageWidth());
        resultMap.put("imageFileHigh",imageSpecification.getImageHeight());
        resultMap.put("imageFileSize",imageSpecification.getFileSize());
        print(Obj2JsonUtil.map2json(resultMap));
		return NONE;
	}
	private void print(String str) {
		try {
			ServletActionContext.getResponse().setCharacterEncoding("utf-8");
			ServletActionContext.getResponse().getOutputStream().write(str.getBytes("utf-8"));
		} catch (Exception e) {
			System.out.println("send response error");
		}
	}
	
	public PageBeanDB getPage() {
		return page;
	}
	public void setPage(PageBeanDB page) {
		this.page = page;
	}
	public DResource getMeterialQuery() {
		return meterialQuery;
	}
	public void setMeterialQuery(DResource meterialQuery) {
		this.meterialQuery = meterialQuery;
	}
	public List<MaterialCategory> getMaterialCategoryList() {
		return materialCategoryList;
	}
	public void setMaterialCategoryList(List<MaterialCategory> materialCategoryList) {
		this.materialCategoryList = materialCategoryList;
	}
	public PageBeanDB getAdPositionPage() {
		return adPositionPage;
	}
	public void setAdPositionPage(PageBeanDB adPositionPage) {
		this.adPositionPage = adPositionPage;
	}
	public Integer getAdvertPositionId() {
		return advertPositionId;
	}
	public void setAdvertPositionId(Integer advertPositionId) {
		this.advertPositionId = advertPositionId;
	}
	
}
