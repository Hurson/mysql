package com.dvnchina.advertDelivery.meterial.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.meterial.bean.MaterialCategory;
import com.dvnchina.advertDelivery.meterial.bean.MaterialType;
import com.dvnchina.advertDelivery.meterial.service.MeterialManagerService;
import com.dvnchina.advertDelivery.meterial.service.MeterialOperatorService;
import com.dvnchina.advertDelivery.model.ImageMeta;
import com.dvnchina.advertDelivery.model.MessageMeta;
import com.dvnchina.advertDelivery.model.Resource;
import com.dvnchina.advertDelivery.model.VideoMeta;
import com.dvnchina.advertDelivery.position.bean.AdvertPosition;
import com.dvnchina.advertDelivery.position.service.PositionService;
import com.dvnchina.advertDelivery.utils.ConfigureProperties;
import com.dvnchina.advertDelivery.utils.json.Obj2JsonUtil;
import com.opensymphony.xwork2.ActionSupport;

public class MeterialOperatorAction extends ActionSupport implements ServletRequestAware{
	
	private static final long serialVersionUID = -3666982468062423696L;
	private static Logger logger = Logger.getLogger(MeterialOperatorAction.class);
	private static char ONLINE_STATE = '2';
	private static char OFFLINE_STATE = '3';
	private HttpServletRequest request;
	private Resource meterialQuery;
	private List<MaterialType> materialTypeList;
	private PageBeanDB page = new PageBeanDB();
	private MeterialOperatorService meterialOperatorService;
	private PositionService positionService;
	private MessageMeta textMeta;
	private ImageMeta imageMeta;
	private VideoMeta videoMeta;
	private Resource material = new Resource();
	private List<MaterialCategory> materialCategoryList;
	private static ConfigureProperties config = ConfigureProperties.getInstance();
	private String sssspath;

	private MeterialManagerService meterialManagerService;
	

	/**
	 * 查询台账列表
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public String queryMeterialUponLineList(){
		// 添加默认的查询条件 以上线
		
		materialCategoryList=meterialManagerService.getMaterialCategoryList();
		page =meterialOperatorService.queryMeterialUponLineList(page.getPageNo(), page.getPageSize(), meterialQuery);
		return SUCCESS;
		
	}
	
	/**
	 * 根据ID获取台账信息
	 * @param id
	 * @return ContractAccountsService
	 */
	public String getMeterialUponLineById(){
		initDate();
        materialCategoryList=meterialManagerService.getMaterialCategoryList();
        
        int materialId = Integer.valueOf(request.getParameter("materialId"));      
        material = meterialManagerService.getMaterialByID(materialId);
        AdvertPosition adPositionQuery = positionService.getAdvertPosition(material.getAdvertPositionId());
		//返回广告位的JSON信息，用于预览
		getRequest().setAttribute("positionJson", Obj2JsonUtil.object2json(adPositionQuery));
        if(material.getResourceType()==0){
            //图片
            imageMeta=meterialManagerService.getImageMetaByID(material.getResourceId());
            String sd = imageMeta.getTemporaryFilePath()+"/"+imageMeta.getName();
        }
        if(material.getResourceType()==1){
            //视频
        	videoMeta = meterialManagerService.getVideoMetaByID(material.getResourceId());
        }
        if(material.getResourceType()==2){
            //文字
            textMeta=meterialManagerService.getTextMetaByID(material.getResourceId());
            if(textMeta.getContent()!=null){
                byte[] contentBlob =textMeta.getContent();
                textMeta.setContentMsg(new String(contentBlob));
            }
        }
        if(material.getResourceType()==3){
           //问卷    
        }

        String ip=config.getValueByKey("ftp.ip");
        String path=config.getValueByKey("materila.ftp.tempPath");
        path=path.substring(5, path.length());
        String viewPath="http://"+ip+path;
        if(videoMeta != null){
        	sssspath = viewPath+"/"+videoMeta.getName();
        }
        
        request.setAttribute("viewPath", viewPath);
        
        //List<PositionAD> positionADList = contractManagerService.getPositionADByContractID(contractId);
        request.setAttribute("material", material);
        //saveTag=0;
        //areasJson = Obj2JsonUtil.list2json(positionADList);

		return SUCCESS;
	}
	
	public List<MaterialType> getMaterialTypeList() {
		return materialTypeList;
	}

	public void setMaterialTypeList(List<MaterialType> materialTypeList) {
		this.materialTypeList = materialTypeList;
	}

	public MessageMeta getTextMeta() {
		return textMeta;
	}

	public void setTextMeta(MessageMeta textMeta) {
		this.textMeta = textMeta;
	}

	public ImageMeta getImageMeta() {
		return imageMeta;
	}

	public void setImageMeta(ImageMeta imageMeta) {
		this.imageMeta = imageMeta;
	}

	public VideoMeta getVideoMeta() {
		return videoMeta;
	}

	public void setVideoMeta(VideoMeta videoMeta) {
		this.videoMeta = videoMeta;
	}

	/**
	 * 素材重新上线
	 * @return
	 */
	public String reloadMeterialOnline(){
		String ids = request.getParameter("ids");
		String opinion = null;
		try {
			opinion = new String(request.getParameter("opinion").getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String userId = (String) this.getRequest().getSession().getAttribute("userId");
		Date auditDate = Calendar.getInstance().getTime();
		if(material == null){
			material = new Resource();
		}
		material.setExaminationOpintions(opinion);
		material.setAuditDate(auditDate);
		material.setAuditTaff(userId==null?"0":userId);
		// 填写意见
		meterialOperatorService.writeVerifyOpinion(ONLINE_STATE, material, ids);
		return SUCCESS;
	}
	
	/**
	 * 素材下线，只有未绑定定订单的的资产信息，运营商可以进行下线处理
	 * @return
	 */
	public String unloadMeterialOnline(){
		String opinion = null;
		try {
			opinion = new String(request.getParameter("opinion").getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Date auditDate = Calendar.getInstance().getTime();
		String userId = (String) this.getRequest().getSession().getAttribute("userId");
		String ids = request.getParameter("ids");
		if(material == null){
			material = new Resource();
		}
		material.setExaminationOpintions(opinion);
		material.setAuditDate(auditDate);
		material.setAuditTaff(userId==null?"0":userId);
		// 填写意见
		meterialOperatorService.writeVerifyOpinion(OFFLINE_STATE, material, ids);
		return SUCCESS;
	}
	
	/**
	 * 对下线的素材进行删除,前台已过滤，上线的素材是不能删除的。
	 */
	public String deleteMeterialOffline(){
		String ids = request.getParameter("ids");
		meterialOperatorService.deleteMeterialOffline(ids);
		return SUCCESS;
	}
	
	/**
	 * 查看指定ID的素材是否被订单绑定
	 * @return json
	 */
	public void hasBindedOrder(){
		String ids = request.getParameter("ids");
		ids = "("+ids+")";
		int count = meterialOperatorService.hasBindedOrder(ids);
		String strJson = "{\"count\":"+count+"}";
		renderJson(strJson);
	}
	
	/**
	 * render json
	 */
	private void renderJson(String content) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(content);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @description: 初始化素材类型数据 
	 * @return 
	 * List<MaterialType>
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-9 下午03:31:31
	 */
	public void initDate(){
	    if(materialTypeList==null){
	        materialTypeList = new ArrayList<MaterialType>();
	    }

        MaterialType materialType0= new MaterialType();
        MaterialType materialType1= new MaterialType();
        MaterialType materialType2= new MaterialType();
        MaterialType materialType3= new MaterialType();
        materialType0.setId(0);
        materialType0.setTypeName("图片");
        materialType1.setId(1);
        materialType1.setTypeName("视频");
        materialType2.setId(2);
        materialType2.setTypeName("文字");
        materialType3.setId(3);
        materialType3.setTypeName("调查问卷");
        materialTypeList.add(materialType0);
        materialTypeList.add(materialType1);
        materialTypeList.add(materialType2);
        materialTypeList.add(materialType3);
        
	}
	
	/********************************************************************************/
	
	public HttpServletRequest getRequest() {
		return request;
	}


	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}


	public PageBeanDB getPage() {
		return page;
	}


	public void setPage(PageBeanDB page) {
		this.page = page;
	}

	public Resource getMeterialQuery() {
		return meterialQuery;
	}

	public void setMeterialQuery(Resource meterialQuery) {
		this.meterialQuery = meterialQuery;
	}

	public Resource getMaterial() {
		return material;
	}

	public void setMaterial(Resource material) {
		this.material = material;
	}	public List<MaterialCategory> getMaterialCategoryList() {
		return materialCategoryList;
	}

	public void setMaterialCategoryList(List<MaterialCategory> materialCategoryList) {
		this.materialCategoryList = materialCategoryList;
	}
	public MeterialOperatorService getMeterialOperatorService() {
		return meterialOperatorService;
	}

	public void setMeterialOperatorService(MeterialOperatorService service) {
		this.meterialOperatorService = service;
	}

	public Resource getResource() {
		return material;
	}

	public void setResource(Resource resource) {
		this.material = resource;
	}

	public MeterialManagerService getMeterialManagerService() {
		return meterialManagerService;
	}

	public void setMeterialManagerService(
			MeterialManagerService meterialManagerService) {
		this.meterialManagerService = meterialManagerService;
	}	
	

	public PositionService getPositionService() {
		return positionService;
	}

	public void setPositionService(PositionService positionService) {
		this.positionService = positionService;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
		
	}

	public String getSssspath() {
		return sssspath;
	}

	public void setSssspath(String sssspath) {
		this.sssspath = sssspath;
	}

}
