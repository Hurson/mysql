package com.avit.dtmb.position.action;


import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.avit.dtmb.position.bean.DAdPosition;
import com.avit.dtmb.position.service.DPositionService;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.common.BaseAction;
import com.dvnchina.advertDelivery.position.bean.ImageSpecification;
import com.dvnchina.advertDelivery.position.bean.TextSpecification;
import com.dvnchina.advertDelivery.position.bean.VideoSpecification;

@ParentPackage("default")
@Namespace("/dposition")
@Scope("prototype")
@Controller
public class DPositionAction extends BaseAction{
	private PageBeanDB page;
	private DAdPosition adposition;
	private ImageSpecification imageSpe;
	private TextSpecification textSpe;
	private VideoSpecification videoSpe;
	private Integer type;
	private String flag = "1";
	/**
	 * 
	 */
	private static final long serialVersionUID = 6922043917965650341L;
	@Resource
	private DPositionService dPositionService;
	public DPositionAction(){
		System.out.println("init");
	}
	/**
	 * 查询DTMB广告位列表
	 * @author hudongyu
	 * @return
	 */
	@Action(value = "queryDPositionPackageList",results = {@Result(name = "success",location = "/page/position/dPositionList.jsp")})
	public  String queryDPositionPackageList(){
		if(page==null){
			page = new PageBeanDB();
		}
		try{
			page = dPositionService.queryDPositionList(page.getPageNo(), page.getPageSize());
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;	
	}
	/**
	 * 根据ID查询广告位信息
	 * @author hudongyu
	 * @return
	 */
	@Action(value = "queryDPositionById",results = {@Result(name = "success",location = "/page/position/DPositionDetail.jsp")})
	public String queryDPositionById(){
		try{
			adposition = dPositionService.GetDPositionById(id);
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 修改子广告位信息（坐标、宽高、描述、背景图片）
	 * @return
	 */
	@Action(value = "updateAdvertPosition",results = {@Result(name = "success",location = "queryDPositionPackageList.do",type = "redirect")})
	public String updateAdvertPosition(){
		try{
			dPositionService.updateAdvertPosition(adposition);
		}catch(Exception e){
			e.printStackTrace();
		}
		return queryDPositionPackageList();
	}
	@Action(value = "specificationAdpter",results = 
		{@Result(name = "IMG",location = "/page/position/editImageSpecification.jsp"),
		@Result(name = "TEXT",location = "/page/position/editTextSpecification.jsp"),
		@Result(name = "VIDEO",location = "/page/position/editVideoSpecification.jsp")
		})
	public String specificationAdpter(){
		String A;
		try{
			if(type==1){
				imageSpe = dPositionService.getImageSpeById(id);
				A = "IMG";
				return A;
			}else if(type==2){
				videoSpe = dPositionService.getVideoSpeById(id);
				A = "VIDEO";
				return A;
			}else if(type==3){
				textSpe = dPositionService.getTextSpeById(id);
				A = "TEXT";
				return A;
			}else{
				return ERROR;
			}
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
	}
	@Action(value = "getVideoSpe",results = {@Result(name = "success",location = "/page/position/editVideoSpecification.jsp")})
	public String getVideoSpe(){
		videoSpe = dPositionService.getVideoSpeById(id);
		return SUCCESS;
	}
	@Action(value = "saveImg",results = {@Result(name = "success",location = "queryDPositionPackageList.do",type = "redirect")})
	public String saveImg(){
		dPositionService.saveImageSpecification(imageSpe);
		return SUCCESS;
	}
	@Action(value = "saveVideo")
	public String saveVideo(){
		dPositionService.saveVideoSpecification(videoSpe);
		return SUCCESS;
	}
	@Action(value = "saveText")
	public String saveText(){
		dPositionService.saveTextSpecification(textSpe);
		return SUCCESS;
	}
	
	public DAdPosition getAdposition() {
		return adposition;
	}
	public void setAdposition(DAdPosition adposition) {
		this.adposition = adposition;
	}
	public PageBeanDB getPage() {
		return page;
	}
	public void setPage(PageBeanDB page) {
		this.page = page;
	}
	public ImageSpecification getImageSpe() {
		return imageSpe;
	}
	public void setImageSpe(ImageSpecification imageSpe) {
		this.imageSpe = imageSpe;
	}
	public TextSpecification getTextSpe() {
		return textSpe;
	}
	public void setTextSpe(TextSpecification textSpe) {
		this.textSpe = textSpe;
	}
	public void setVideoSpe(VideoSpecification videoSpe) {
		this.videoSpe = videoSpe;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
