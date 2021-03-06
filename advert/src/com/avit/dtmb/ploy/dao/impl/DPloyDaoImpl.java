package com.avit.dtmb.ploy.dao.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Repository;

import com.avit.dtmb.channelGroup.bean.DChannelGroup;
import com.avit.dtmb.ploy.bean.DPloy;
import com.avit.dtmb.ploy.dao.DPloyDao;
import com.avit.dtmb.position.bean.DAdPosition;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
import com.dvnchina.advertDelivery.model.ReleaseArea;
import com.dvnchina.advertDelivery.model.UserLogin;
import com.dvnchina.advertDelivery.sysconfig.bean.UserIndustryCategory;
@Repository("dPloyDao")
public class DPloyDaoImpl extends BaseDaoImpl implements DPloyDao {

	@Override
	public PageBeanDB queryDTMBPloyList(DPloy ploy, int pageNo, int pageSize) {
		String hql = "from DPloy ploy where 1 = 1";
		if(ploy != null && StringUtils.isNotBlank(ploy.getPloyName())){
			hql += " and ploy.ployName like '%" + ploy.getPloyName() + "%'";
		}
		if(ploy != null && StringUtils.isNotBlank(ploy.getPositionName())){
			hql += " and ploy.dposition.positionName= like '%" + ploy.getPositionName() + "%'";
		}
		if(ploy != null && StringUtils.isNotBlank(ploy.getStatus())){
			hql += " and ploy.status= '" + ploy.getStatus() + "'";
		}
		
		HttpSession session = ServletActionContext.getRequest().getSession();
		UserLogin userLogin = (UserLogin)session.getAttribute("USER_LOGIN_INFO");
		List<Integer> accessUserId = userLogin.getAccessUserIds();
		
		hql +=" and ploy.operatorId in ("+accessUserId.toString().replaceAll("\\[|\\]|\\s", "")+")";
		hql +=" and ploy.dposition.id in (" + userLogin.getDtmbPositionIds()+")";
		
		hql += " order by ploy.id desc";
		return this.getPageList2(hql, null, pageNo, pageSize);
	}
	@Override
	public void saveDTMBPloy(DPloy ploy) {
		this.saveOrUpdate(ploy);
		String hql = "delete from DPloyDetail detail where detail.ployId is null";
		this.executeByHQL(hql, null);
	}
	@Override
	public DPloy getDTMBPloy(Integer id) {
		
		return (DPloy)this.get(DPloy.class, id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DAdPosition> queryPositionList() {
		
		HttpSession session = ServletActionContext.getRequest().getSession();
		UserLogin userLogin = (UserLogin)session.getAttribute("USER_LOGIN_INFO");
		
		String hql = "from DAdPosition dp where dp.id in ("+userLogin.getDtmbPositionIds()+")";
		return (List<DAdPosition>)this.getListForAll(hql, null);
	}
	@Override
	public DAdPosition getPositionByCode(String positionCode) {
		String hql = "from DAdPosition ap where ap.positionCode = ?";
		return (DAdPosition)this.get(hql, positionCode);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ReleaseArea> listReleaseArea() {
		
		HttpSession session = ServletActionContext.getRequest().getSession();
		UserLogin userLogin = (UserLogin)session.getAttribute("USER_LOGIN_INFO");
		
		String hql = "select area from ReleaseArea area,UserArea usa where area.areaCode=usa.releaseAreacode and usa.userId=?";
		return (List<ReleaseArea>)this.getListForAll(hql, new Object[]{userLogin.getUserId()});
	}
	@Override
	public DPloy getDPloyByName(String ployName) {
		String hql = "from DPloy ploy where ploy.ployName=?";
		return (DPloy)this.get(hql, ployName);
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ReleaseArea> getUserAccessArea(Integer userId) {
		String hql = "select area from ReleaseArea area,UserArea usa where area.areaCode=usa.releaseAreacode and usa.userId=" + userId;
		return (List<ReleaseArea>)this.getListForAll(hql, null);
	}
	@Override
	public PageBeanDB queryUserIndustryList(UserIndustryCategory userIndustryCategory, int pageNo, int pageSize) {
		String hql = "from UserIndustryCategory indu where 1=1";
		if(userIndustryCategory != null && StringUtils.isNotBlank(userIndustryCategory.getUserIndustryCategoryValue())){
			hql += " and indu.userIndustryCategoryValue like '%" + userIndustryCategory.getUserIndustryCategoryValue() + "%'";
		}
		return this.getPageList2(hql, null, pageNo, pageSize);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> checkOrderRelPloy(Integer ployId) {
		String sql = "select od.id from d_order od where od.ploy_id = ? and od.state<>'7'";
		return (List<Integer>)this.getDataBySql(sql, new Object[]{ployId});
	}
	@Override
	public PageBeanDB queryChanelGroupList(DChannelGroup channelGroup,int pageNo, int pageSize) {
		String hql = "from DChannelGroup chgr where 1= 1";
		if(channelGroup != null && StringUtils.isNotBlank(channelGroup.getName())){
			hql += " and chgr.name like '%" + channelGroup.getName() + "%'";
		}
		hql += " order by chgr.id desc";
		return this.getPageList2(hql, null, pageNo, pageSize);
	}

}
