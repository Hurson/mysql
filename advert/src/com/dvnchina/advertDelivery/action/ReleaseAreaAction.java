package com.dvnchina.advertDelivery.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.model.ReleaseArea;
import com.dvnchina.advertDelivery.service.ReleaseAreaService;

public class ReleaseAreaAction extends BaseActionSupport<Object>{
	
	private Logger logger = Logger.getLogger(ReleaseAreaAction.class);
	
	private static final long serialVersionUID = -8437729707853145449L;

	private ReleaseArea releaseArea;
	
	private ReleaseAreaService releaseAreaService;
	
	private List<ReleaseArea> listReleaseArea;
	
	private String cId;
	
	/**
	 * 页号
	 */
	protected int pageNo;
	
	
	/**
	 * 删除区域信息记录
	 * 
	 */
	public String deleteReleaseArea(){
		
		int count = 0;
		
		String id = this.getRequest().getParameter("cId");
		
		if(id != null){
			count =releaseAreaService.deleteReleaseAreaById(Integer.parseInt(id));
			if (count > 0) {
				logger.debug("删除用户成功，用户id为：" + id);
			} else {
				logger.debug("删除用户时发生异常，用户id为：" + id);
			}
		}else{
			logger.debug("----releaseArea的id 值 为空 ");
		}
		
		logger.debug("---deleteReleaseArea()调用完毕------");
		
		this.returnMessage("删除成功");
		
		return null;
	}
	
	
	/**
	 * 查询结果集，返回到页面上
	 * @return
	 */
	public String list(){
		
		if(releaseArea ==  null){
			releaseArea = new ReleaseArea();
		}
		
		int count = releaseAreaService.getReleaseAreaCount(releaseArea,"");
		
		PageBeanDB page = new PageBeanDB(count, pageNo);
		
		listReleaseArea = releaseAreaService.listReleaseAreaMgr(releaseArea, page.getBegin(),page.getPageSize(),"");
		
		this.getRequest().setAttribute("page",page);
		
		return "list";
	}

	// AJAX 返回客户端方法
	public void returnMessage(String msg) {
		try {

			logger.debug("returnMessage 被调用");

			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(msg);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String getCId() {
		return cId;
	}
	public void setCId(String id) {
		cId = id;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public ReleaseArea getReleaseArea() {
		return releaseArea;
	}
	public void setReleaseArea(ReleaseArea releaseArea) {
		this.releaseArea = releaseArea;
	}
	public ReleaseAreaService getReleaseAreaService() {
		return releaseAreaService;
	}
	public void setReleaseAreaService(ReleaseAreaService releaseAreaService) {
		this.releaseAreaService = releaseAreaService;
	}
	public List<ReleaseArea> getListReleaseArea() {
		return listReleaseArea;
	}
	public void setListReleaseArea(List<ReleaseArea> listReleaseArea) {
		this.listReleaseArea = listReleaseArea;
	}
}



