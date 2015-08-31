package com.dvnchina.advertDelivery.ploy.action;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.channelGroup.bean.TChannelGroup;
import com.dvnchina.advertDelivery.common.BaseAction;
import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.log.bean.OperateLog;
import com.dvnchina.advertDelivery.log.service.OperateLogService;
import com.dvnchina.advertDelivery.model.ChannelInfo;
import com.dvnchina.advertDelivery.model.ReleaseArea;
import com.dvnchina.advertDelivery.npvrChannelGroup.bean.TNpvrChannelGroup;
import com.dvnchina.advertDelivery.ploy.bean.TAssetinfo;
import com.dvnchina.advertDelivery.ploy.bean.TCategoryinfo;
import com.dvnchina.advertDelivery.ploy.bean.TChannelinfoNpvr;
import com.dvnchina.advertDelivery.ploy.bean.TLoopbackCategory;
import com.dvnchina.advertDelivery.ploy.bean.TPreciseMatchBk;
import com.dvnchina.advertDelivery.ploy.bean.TProductinfo;
import com.dvnchina.advertDelivery.ploy.service.PreciseMatchService;
import com.dvnchina.advertDelivery.position.bean.AdvertPosition;
import com.dvnchina.advertDelivery.sysconfig.bean.UserIndustryCategory;
import com.dvnchina.advertDelivery.sysconfig.bean.UserRank;
import com.dvnchina.advertDelivery.order.bean.MenuType;

public class PreciseMatchAction extends BaseAction implements ServletRequestAware{
	private HttpServletRequest request;
	private Logger logger = Logger.getLogger(PreciseMatchAction.class);
	private OperateLogService operateLogService = null;
	
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}
	/**
	 * 将内容写入对应的response中
	 * @param str 存有播出单列表的字符串
	 */
	private void print(String str) {
		try {
			ServletActionContext.getResponse().setCharacterEncoding("utf-8");
			ServletActionContext.getResponse().getOutputStream().write(str.getBytes("utf-8"));
		} catch (Exception e) {
			logger.error("send response error"+e);
		}
	}

	PreciseMatchService preciseservice;
	TPreciseMatchBk precise;
	MenuType menuType;
	Long ployId;
	Long preciseType;
	Long positionId;
	String dataids;
	ChannelInfo channel;
	TProductinfo product;
	TChannelinfoNpvr npvrChannel;
	TLoopbackCategory lookbackCategory;
	TAssetinfo asset;
	TCategoryinfo assetCategory;
	ReleaseArea releaseArea;//用户投放区域
	UserIndustryCategory userIndustry;//用户行业
	UserRank userRank;//用户类别
	TChannelGroup channelGroup;
	TNpvrChannelGroup npvrChannelGroup;
	
	String preciseMatchIds;
	String channelIds;
	String productIds;
	String npvrChannelIds;
	String lookbackCategoryIds;
	String assetIds;
	String assetCategoryIds;
	String assetKeys;
	
	String releaseAreaIds;//用户投放区域
	String userIndustryIds;//用户行业
	String userRankIds;//用户类
	String typeName;//nvod主界面类型名称
	
	List   productList,channelList,npvrChannelList,lookbackCategoryList,assetList,assetCategoryList,assetKeyList;
	List   releaseAreaList,userIndustryList,userRankList,channelGroupList;
	List   preciseTypeList;
	private PageBeanDB pagePreciseMatch =  new PageBeanDB();
	private PageBeanDB pageChannel=  new PageBeanDB() ;
	private PageBeanDB pageNpvrChannel =  new PageBeanDB();
	private PageBeanDB pageLookBackColumn=  new PageBeanDB() ;
	private PageBeanDB pageAssetCategory=  new PageBeanDB() ;	
	private PageBeanDB pageAsset=  new PageBeanDB() ;
	private PageBeanDB pageProduct=  new PageBeanDB() ;
	private PageBeanDB pagereleaseArea=  new PageBeanDB() ;	
	private PageBeanDB pageuserIndustry=  new PageBeanDB() ;
	private PageBeanDB pageuserRank=  new PageBeanDB() ;
	private PageBeanDB pageMenuType = new PageBeanDB();
	private PageBeanDB pageChannelGroup=  new PageBeanDB() ;
	public void initData()
	{
		//初始化  
		//查询产品  类型为回看产品 
		pageProduct = preciseservice.queryProductList(ployId.toString(), product,pageProduct.getPageSize(), pageProduct.getPageNo());
		
		//查询所有直播频道
		pageChannel = preciseservice.queryChannelList(ployId.toString(), channel, null, pageProduct.getPageSize(), pageProduct.getPageNo());
		
		//查询所有回放频道
		pageNpvrChannel = preciseservice.queryNpvrChannelList(ployId.toString(), npvrChannel, null, pageProduct.getPageSize(), pageProduct.getPageNo());
		
		//查询所有回看栏目
		pageLookBackColumn = preciseservice.queryColumnList(ployId.toString(), lookbackCategory,  pageLookBackColumn.getPageSize(), pageLookBackColumn.getPageNo());
		 
		//查询所有影片类别
		pageAssetCategory =preciseservice.queryAssetCategoryList(ployId.toString(),assetCategory,  pageAssetCategory.getPageSize(), pageAssetCategory.getPageNo());
		//查询影片
		
		pageAsset = preciseservice.queryAssetList(ployId.toString(),asset, null, pageAsset.getPageSize(), pageAsset.getPageNo());
		//查询影片关键字 此关键字由用户手动输入
		
		//查询受众 暂不处理
		
		
	}
	public String queryPreciseList(){
		
		//initData();
		if (precise==null)
		{
			precise = new TPreciseMatchBk();
			precise.setPloyId(ployId);
		}
		else
		{
			precise.setPloyId(ployId);
		}
	
		
		pagePreciseMatch = preciseservice.queryPreciseList(precise, pagePreciseMatch.getPageSize(), pagePreciseMatch.getPageNo());
		
			
		/*lstPloyAreaChannel= ployService.getAreaChannelsByPloyID(ployId);
		request.setAttribute("ploy", ploy);
		areasJson = Obj2JsonUtil.list2json(lstPloyAreaChannel);
		ArrayList<test> array = new ArrayList<test>();
		test ss = new test();
		for (int i = 0; i < 3; i++) {
			ss.setArg1(""+i);
			ss.setArg2(""+i);
			ss.setArg3(i);
			ss.setArg4(i);
			array.add(ss);
		}
		request.setAttribute("select", array);
		*/
		return SUCCESS;
	}
public String queryCheckPreciseList(){
		
		//initData();
		if (precise==null)
		{
			precise = new TPreciseMatchBk();
			precise.setPloyId(ployId);
		}
		else
		{
			precise.setPloyId(ployId);
		}
	
		
		pagePreciseMatch = preciseservice.queryPreciseList(precise,100, pagePreciseMatch.getPageNo());
		
			
		/*lstPloyAreaChannel= ployService.getAreaChannelsByPloyID(ployId);
		request.setAttribute("ploy", ploy);
		areasJson = Obj2JsonUtil.list2json(lstPloyAreaChannel);
		ArrayList<test> array = new ArrayList<test>();
		test ss = new test();
		for (int i = 0; i < 3; i++) {
			ss.setArg1(""+i);
			ss.setArg2(""+i);
			ss.setArg3(i);
			ss.setArg4(i);
			array.add(ss);
		}
		request.setAttribute("select", array);
		*/
		return SUCCESS;
	}
	/**
	 * 获取精准详情
	 * @return
	 */
	public String getPreciseMatch(){
		
		//添加时页面设置 精准ID为0
		//= precise.getPrecisetype();
		precise = preciseservice.getPreciseMatchByID(precise.getId());
		preciseTypeList = new ArrayList();
		
		if (lookbackCategory==null)
		{
			lookbackCategory = new TLoopbackCategory();
		}
		if (assetCategory==null)
		{
			assetCategory = new TCategoryinfo();
		}
		
		if (precise==null)
		{
			precise = new TPreciseMatchBk();
			precise.setPloyId(ployId);	
			AdvertPosition adPosition = preciseservice.getAdvertPositionByID(positionId);
			//MessageReal temp= new MessageReal();
			//String areasJson = Obj2JsonUtil.bean2json(temp);
			//areasJson = Obj2JsonUtil.bean2json(temp);
			if (null!=adPosition)
			{
				if (adPosition.getIsLookbackProduct().equals(1))
				{
					preciseTypeList.add("1");//产品
					precise.setPrecisetype(Long.valueOf(1));
					if (product==null)
					{
						product = new TProductinfo();
					}
					product.setType("lookback");
				}
				//
				if (adPosition.getIsAsset().equals(1))
				{
					preciseTypeList.add("1");//产品
					preciseTypeList.add("3");//受众
					preciseTypeList.add("2");//关键字
					preciseTypeList.add("4");//影片类别
					preciseTypeList.add("8");//影片
					if (Long.valueOf(adPosition.getPositionCode())%2==0)
					{
						assetCategory.setType("0");
					}
					else
					{
						assetCategory.setType("1");
					}
					
					precise.setPrecisetype(Long.valueOf(8));
				}
				if (adPosition.getIsPlayback().equals(1))
				{
					preciseTypeList.add("5");//回放频道
					precise.setPrecisetype(Long.valueOf(5));
				}
				if (adPosition.getIsColumn().equals(1))
				{
					preciseTypeList.add("6");//回看栏目
					precise.setPrecisetype(Long.valueOf(6));
					if (Long.valueOf(adPosition.getPositionCode())%2==0)
					{
						lookbackCategory.setCategoryType("0");
					}
					else
					{
						lookbackCategory.setCategoryType("1");
					}					
				}
				if (adPosition.getIsChannel().equals(1))
				{
					//preciseTypeList.add("7");//直播频道
					//precise.setPrecisetype(Long.valueOf(7));
					preciseTypeList.add("10");//直播频道
					precise.setPrecisetype(Long.valueOf(10));
				}
				if (adPosition.getIsFreq().equals(1))
				{
					//preciseTypeList.add("7");//音频频道
					//precise.setPrecisetype(Long.valueOf(7));
					preciseTypeList.add("7");//直播频道
					precise.setPrecisetype(Long.valueOf(7));
					
					//设置频道类型属性为 音频频道
					if (channel==null)
					{
						channel = new ChannelInfo();
					}
					channel.setChannelType("音频直播类业务");
				}
				if (adPosition.getIsFollowAsset().equals(1))
				{
					preciseTypeList.add("8");//点播随片
					precise.setPrecisetype(Long.valueOf(8));
				}
				if (adPosition.getIsArea().equals(1))
				{
					preciseTypeList.add("9");//DTV 区域
					precise.setPrecisetype(Long.valueOf(9));
				}
			}
			
			
			
			
			/*precise.setPrecisetype(Long.valueOf(preciseType));
			if (preciseType==8)
			{
			preciseTypeList.add("2");
			preciseTypeList.add("4");
			preciseTypeList.add("8");
			}
			else
			{
				precise.setPrecisetype(Long.valueOf(preciseType));
				preciseTypeList.add(precise.getPrecisetype());
			}*/
		}
		else
		{
			AdvertPosition adPosition = preciseservice.getAdvertPositionByID(positionId);
			if (adPosition.getIsLookbackProduct().equals(1))
			{
				preciseTypeList.add("1");//产品
				if (product==null)
				{
					product = new TProductinfo();
				}
				product.setType("lookback");
			}
			if (adPosition.getIsFreq().equals(1))
			{
				//preciseTypeList.add("7");//音频频道
				preciseTypeList.add("7");//直播频道
				//设置频道类型属性为 音频频道
				if (channel==null)
				{
					channel = new ChannelInfo();
				}
				channel.setChannelType("音频直播类业务");
			}
			if (Long.valueOf(adPosition.getPositionCode())%2==0)
			{
				lookbackCategory.setCategoryType("0");
				assetCategory.setType("0");
			}
			else
			{
				lookbackCategory.setCategoryType("1");
				assetCategory.setType("1");
			}
			
			preciseTypeList.add(precise.getPrecisetype());
		}		
		
		String temp = precise.getProductId();
		String[] tempArray ;
		if (temp!=null && !temp.equals(""))
		{
			tempArray=temp.split(",");
			productList = new ArrayList();
			Collections.addAll(productList, tempArray);
		}
		pageProduct = preciseservice.queryProductList(ployId.toString(), product,1000, 1);
		
		temp = precise.getAssetName();
		if (temp!=null && !temp.equals(""))
		{
			tempArray=temp.split(",");
			assetList = new ArrayList();
			Collections.addAll(assetList, tempArray);
		}
		//查询影片		
		pageAsset = preciseservice.queryAssetList(ployId.toString(),asset, temp, 1000, 1);
		
		
		//查询所有直播频道
		temp = precise.getDtvChannelId();
		if (temp!=null && !temp.equals(""))
		{
			tempArray=temp.split(",");
			channelList = new ArrayList();
			Collections.addAll(channelList, tempArray);
		}
		
		pageChannel = preciseservice.queryChannelList(ployId.toString(), channel, temp, 1000, 1);
		//查询所有回放频道
		temp = precise.getPlaybackChannelId();
		if (temp!=null && !temp.equals(""))
		{
			tempArray=temp.split(",");
			npvrChannelList = new ArrayList();
			Collections.addAll(npvrChannelList, tempArray);
		}
		pageNpvrChannel = preciseservice.queryNpvrChannelList(ployId.toString(), npvrChannel, temp, 1000, 1);
		
		//查询所有回看栏目
		temp = precise.getLookbackCategoryId();
		if (temp!=null && !temp.equals(""))
		{
			tempArray=temp.split(",");
			lookbackCategoryList = new ArrayList();
			Collections.addAll(lookbackCategoryList, tempArray);
		}
		pageLookBackColumn = preciseservice.queryColumnList(ployId.toString(), lookbackCategory,  1000, 1);
		 
		//查询所有影片类别
		temp = precise.getAssetSortId();
		if (temp!=null && !temp.equals(""))
		{
			tempArray=temp.split(",");
			assetCategoryList = new ArrayList();
			Collections.addAll(assetCategoryList, tempArray);
		}
		pageAssetCategory =preciseservice.queryAssetCategoryList(ployId.toString(),assetCategory, 1000, 1);
		
		temp = precise.getAssetKey();
		if (temp!=null && !temp.equals(""))
		{
			tempArray=temp.split(",");
			assetKeyList = new ArrayList();
			Collections.addAll(assetKeyList, tempArray);
		}
		
		//查询所有区域
		temp = precise.getUserArea();
		if (temp!=null && !temp.equals(""))
		{
			tempArray=temp.split(",");
			releaseAreaList = new ArrayList();
			Collections.addAll(releaseAreaList, tempArray);
		}
		pagereleaseArea =preciseservice.queryreleaseAreaList(releaseArea , temp,1000, 1);
		//查询所有行业
		temp = precise.getUserindustrys();
		if (temp!=null && !temp.equals(""))
		{
			tempArray=temp.split(",");
			userIndustryList = new ArrayList();
			Collections.addAll(userIndustryList, tempArray);
		}
		pageuserIndustry =preciseservice.queryuserIndustryList(userIndustry , temp,1000, 1);
		//查询所有用户类型
		temp = precise.getUserlevels();
		if (temp!=null && !temp.equals(""))
		{
			tempArray=temp.split(",");
			userRankList = new ArrayList();
			Collections.addAll(userRankList, tempArray);
		}
		pageuserRank =preciseservice.queryuserRankList(userRank , temp,1000, 1);
		
		//查询所有频道分组
		temp = precise.getGroupId();
		if (temp!=null && !temp.equals(""))
		{
			tempArray=temp.split(",");
			channelGroupList = new ArrayList();
			Collections.addAll(channelGroupList, tempArray);
		}
		pageChannelGroup =preciseservice.queryChannelGroupList(channelGroup , temp,1000, 1);
		
		/*lstPloyAreaChannel= ployService.getAreaChannelsByPloyID(ployId);
		request.setAttribute("ploy", ploy);
		areasJson = Obj2JsonUtil.list2json(lstPloyAreaChannel);
		ArrayList<test> array = new ArrayList<test>();
		test ss = new test();
		for (int i = 0; i < 3; i++) {
			ss.setArg1(""+i);
			ss.setArg2(""+i);
			ss.setArg3(i);
			ss.setArg4(i);
			array.add(ss);
		}
		request.setAttribute("select", array);
		*/
		return SUCCESS;
	}
	
	
	
	/**
	 * 获取精准详情
	 * @return
	 */
	public String getPreciseMatchinfo(){
		
		//添加时页面设置 精准ID为0
		//= precise.getPrecisetype();
		precise = preciseservice.getPreciseMatchByID(precise.getId());
		preciseTypeList = new ArrayList();
		
		if (precise==null)
		{
			precise = new TPreciseMatchBk();
			precise.setPloyId(ployId);	
			AdvertPosition adPosition = preciseservice.getAdvertPositionByID(positionId);
			
			if (null!=adPosition)
			{
				if (adPosition.getIsLookbackProduct().equals(1))
				{
					preciseTypeList.add("1");//产品
					precise.setPrecisetype(Long.valueOf(1));
					if (product==null)
					{
						product = new TProductinfo();
					}
					product.setType("lookback");
				}
				//
				if (adPosition.getIsAsset().equals(1))
				{
					preciseTypeList.add("1");//产品
					preciseTypeList.add("3");//受众
					preciseTypeList.add("2");//关键字
					preciseTypeList.add("4");//影片类别
					preciseTypeList.add("8");//影片
					precise.setPrecisetype(Long.valueOf(8));
				}
				if (adPosition.getIsPlayback().equals(1))
				{
					preciseTypeList.add("5");//回放频道
					precise.setPrecisetype(Long.valueOf(5));
				}
				if (adPosition.getIsColumn().equals(1))
				{
					preciseTypeList.add("6");//回看栏目
					precise.setPrecisetype(Long.valueOf(6));
				}
				if (adPosition.getIsChannel().equals(1))
				{
					//preciseTypeList.add("7");//直播频道
					//precise.setPrecisetype(Long.valueOf(7));
					preciseTypeList.add("10");//直播频道
					precise.setPrecisetype(Long.valueOf(10));
				}
				if (adPosition.getIsFreq().equals(1))
				{
					preciseTypeList.add("7");//音频频道
					precise.setPrecisetype(Long.valueOf(7));
					//设置频道类型属性为 音频频道
					if (channel==null)
					{
						channel = new ChannelInfo();
					}
					channel.setChannelType("音频直播类业务");
				}
				if (adPosition.getIsFollowAsset().equals(1))
				{
					preciseTypeList.add("8");//点播随片
					precise.setPrecisetype(Long.valueOf(8));
				}
				if (adPosition.getIsArea().equals(1))
				{
					preciseTypeList.add("9");//点播随片
					precise.setPrecisetype(Long.valueOf(9));
				}
			}
		}
		else
		{
			AdvertPosition adPosition = preciseservice.getAdvertPositionByID(positionId);
			if (adPosition.getIsLookbackProduct().equals(1))
			{
				preciseTypeList.add("1");//产品
				if (product==null)
				{
					product = new TProductinfo();
				}
				product.setType("lookback");
			}
			if (adPosition.getIsFreq().equals(1))
			{
				preciseTypeList.add("7");//音频频道
				//设置频道类型属性为 音频频道
				if (channel==null)
				{
					channel = new ChannelInfo();
				}
				channel.setChannelType("音频直播类业务");
			}
			preciseTypeList.add(precise.getPrecisetype());
		}		
		
		String temp = precise.getProductId();
		String[] tempArray ;
		if (temp!=null && !temp.equals(""))
		{
			tempArray=temp.split(",");
			productList = new ArrayList();
			Collections.addAll(productList, tempArray);
		}
		pageProduct = preciseservice.queryProductList(ployId.toString(), product,1000, 1);
		
		temp = precise.getAssetName();
		if (temp!=null && !temp.equals(""))
		{
			tempArray=temp.split(",");
			assetList = new ArrayList();
			Collections.addAll(assetList, tempArray);
		}
		//查询影片		
		pageAsset = preciseservice.queryAssetList(ployId.toString(),asset, temp, 1000, 1);
		
		
		//查询所有直播频道
		temp = precise.getDtvChannelId();
		if (temp!=null && !temp.equals(""))
		{
			tempArray=temp.split(",");
			channelList = new ArrayList();
			Collections.addAll(channelList, tempArray);
		}
		
		pageChannel = preciseservice.queryChannelList(ployId.toString(), channel, temp, 1000, 1);
		//查询所有回放频道
		temp = precise.getPlaybackChannelId();
		if (temp!=null && !temp.equals(""))
		{
			tempArray=temp.split(",");
			npvrChannelList = new ArrayList();
			Collections.addAll(npvrChannelList, tempArray);
		}
		pageNpvrChannel = preciseservice.queryNpvrChannelList(ployId.toString(), npvrChannel, temp, 1000, 1);
		
		//查询所有回看栏目
		temp = precise.getLookbackCategoryId();
		if (temp!=null && !temp.equals(""))
		{
			tempArray=temp.split(",");
			lookbackCategoryList = new ArrayList();
			Collections.addAll(lookbackCategoryList, tempArray);
		}
		pageLookBackColumn = preciseservice.queryColumnList(ployId.toString(), lookbackCategory,  1000, 1);
		 
		//查询所有影片类别
		temp = precise.getAssetSortId();
		if (temp!=null && !temp.equals(""))
		{
			tempArray=temp.split(",");
			assetCategoryList = new ArrayList();
			Collections.addAll(assetCategoryList, tempArray);
		}
		pageAssetCategory =preciseservice.queryAssetCategoryList(ployId.toString(),assetCategory, 1000, 1);
		
		temp = precise.getAssetKey();
		if (temp!=null && !temp.equals(""))
		{
			tempArray=temp.split(",");
			assetKeyList = new ArrayList();
			Collections.addAll(assetKeyList, tempArray);
		}
		
		//查询所有区域
		temp = precise.getUserArea();
		if (temp!=null && !temp.equals(""))
		{
			tempArray=temp.split(",");
			releaseAreaList = new ArrayList();
			Collections.addAll(releaseAreaList, tempArray);
		}
		pagereleaseArea =preciseservice.queryreleaseAreaList(releaseArea , temp,1000, 1);
		//查询所有行业
		temp = precise.getUserindustrys();
		if (temp!=null && !temp.equals(""))
		{
			tempArray=temp.split(",");
			userIndustryList = new ArrayList();
			Collections.addAll(userIndustryList, tempArray);
		}
		pageuserIndustry =preciseservice.queryuserIndustryList(userIndustry , temp,1000, 1);
		//查询所有用户类型
		temp = precise.getUserlevels();
		if (temp!=null && !temp.equals(""))
		{
			tempArray=temp.split(",");
			userRankList = new ArrayList();
			Collections.addAll(userRankList, tempArray);
		}
		pageuserRank =preciseservice.queryuserRankList(userRank , temp,1000, 1);
		
		
		//查询所有频道分组
		temp = precise.getGroupId();
		if (temp!=null && !temp.equals(""))
		{
			tempArray=temp.split(",");
			channelGroupList = new ArrayList();
			Collections.addAll(channelGroupList, tempArray);
		}
		pageChannelGroup =preciseservice.queryChannelGroupList(channelGroup , temp,1000, 1);
		
		return SUCCESS;
	}
	public String checkName()
	{
		if (preciseservice.checkName(precise.getMatchName(), precise.getId()))
		{
			print("1");
		}
		else
		{
			print("0");
		}
		return null;
	}
	public String saveOrUpdate()
	{
		/*if (assetIds!=null)
		{
			assetIds=trim(assetIds);
		}
		if (assetKeys!=null)
		{
			assetKeys=trim(assetKeys);
		}
		if (assetCategoryIds!=null)
		{
			assetCategoryIds=trim(assetCategoryIds);
		}
		if (channelIds!=null)
		{
			channelIds=trim(channelIds);
		}
		if (npvrChannelIds!=null)
		{
			npvrChannelIds=trim(npvrChannelIds);
		}
		if (lookbackCategoryIds!=null)
		{
			lookbackCategoryIds=trim(lookbackCategoryIds);
		}
		if (productIds!=null)
		{
			productIds=trim(productIds);
		}
		*/
		try
		{
			precise.setAssetName(trim(precise.getAssetName()));
			precise.setAssetKey(trim(precise.getAssetKey()));
			precise.setAssetSortId(trim(precise.getAssetSortId()));
			precise.setDtvChannelId(trim(precise.getDtvChannelId()));
			precise.setLookbackCategoryId(trim(precise.getLookbackCategoryId()));
			precise.setPlaybackChannelId(trim(precise.getPlaybackChannelId()));
			precise.setProductId(trim(precise.getProductId()));
			precise.setUserArea(trim(precise.getUserArea()));
			precise.setUserindustrys(trim(precise.getUserindustrys()));
			precise.setUserlevels(trim(precise.getUserlevels()));
			precise.setGroupId(trim(precise.getGroupId()));
			if (precise.getId()!=null && precise.getId()!=0)
			{
				operType = "operate.update";
			}
			else
			{
				operType = "operate.add";
			}
			
			preciseservice.saveOrUpdate(precise);
			message = "common.save.success";//保存成功
		}catch(Exception e){
			message = "common.save.failed";//保存失败
			operResult = Constant.OPERATE_FAIL;
			e.printStackTrace();
		}
		finally
		{
			operInfo = precise.toString();
			OperateLog operLog = this.setOperationLog(Constant.OPERATE_MODULE_PLOY);
			operateLogService.saveOperateLog(operLog);
		}		
		precise=null;
		
		queryPreciseList();
		return SUCCESS;
	}
	public OperateLogService getOperateLogService() {
		return operateLogService;
	}
	public void setOperateLogService(OperateLogService operateLogService) {
		this.operateLogService = operateLogService;
	}
	public String deletePrecise()
	{
		try
		{
			dataids = trim(dataids);
			preciseservice.deletePrecise(dataids);
			message = "common.delete.success";//保存成功
		}catch(Exception e){
			message = "common.delete.failed";//保存失败
			operResult = Constant.OPERATE_FAIL;
			e.printStackTrace();
		}
		finally
		{
			operInfo ="删除精准：IDS="+ dataids;
			operType = "operate.delete";
			OperateLog operLog = this.setOperationLog(Constant.OPERATE_MODULE_PLOY);
			operateLogService.saveOperateLog(operLog);
		}		
		queryPreciseList();
		return SUCCESS;
	}
	public String queryDTVChannel()
	{
		//查询所有直播频道
		/*
		try
		{
			//channel.setChannelType(new String(channel.getChannelType().getBytes("ISO-8859-1"),"utf8"));
		}
		catch(Exception e)
		{
			
		}*/
		pageChannel = preciseservice.queryChannelList(ployId.toString(), channel, null, pageChannel.getPageSize(), pageChannel.getPageNo());
		
		return SUCCESS;
	}
	public String queryNpvrChannel()
	{
			//查询所有回放频道
		pageNpvrChannel = preciseservice.queryNpvrChannelList(ployId.toString(), npvrChannel, null, pageNpvrChannel.getPageSize(), pageNpvrChannel.getPageNo());
		return SUCCESS;
	}
	public String queryLookBackColumn()
	{
		
		//查询所有回看栏目
		pageLookBackColumn = preciseservice.queryColumnList(ployId.toString(), lookbackCategory,  pageLookBackColumn.getPageSize(), pageLookBackColumn.getPageNo());
		return SUCCESS;
	}
	public String queryAssetCategory()
	{

		//查询所有影片类别
		pageAssetCategory =preciseservice.queryAssetCategoryList(ployId.toString(), assetCategory, pageAssetCategory.getPageSize(), pageAssetCategory.getPageNo());
		return SUCCESS;
	}
	
	public String getReleaseAreaIds() {
		return releaseAreaIds;
	}
	public void setReleaseAreaIds(String releaseAreaIds) {
		this.releaseAreaIds = releaseAreaIds;
	}
	public String getUserIndustryIds() {
		return userIndustryIds;
	}
	public void setUserIndustryIds(String userIndustryIds) {
		this.userIndustryIds = userIndustryIds;
	}
	public String getUserRankIds() {
		return userRankIds;
	}
	public void setUserRankIds(String userRankIds) {
		this.userRankIds = userRankIds;
	}
	public String queryAsset()
	{
		pageAsset = preciseservice.queryAssetList(ployId.toString(),asset, null, pageAsset.getPageSize(), pageAsset.getPageNo());
		return SUCCESS;
	}
	public String queryProduct()
	{
		if (product==null)
		{
			product = new TProductinfo();
		}
		if (product.getType()!=null && !product.getType().equals(""))
		{
			
		}
		else
		{
			product.setType("vod");
		}
		pageProduct = preciseservice.queryProductList(ployId.toString(), product,pageProduct.getPageSize(), pageProduct.getPageNo());
		return SUCCESS;
	}
	
	public String queryReleaseArea()
	{

		//查询所有影片类别
		pagereleaseArea =preciseservice.queryreleaseAreaList(releaseArea, null, pagereleaseArea.getPageSize(), pagereleaseArea.getPageNo());
		return SUCCESS;
	}
	public String queryUserIndustry()
	{
		pageuserIndustry = preciseservice.queryuserIndustryList(userIndustry, null, pageuserIndustry.getPageSize(), pageuserIndustry.getPageNo());
		return SUCCESS;
	}
	public String queryUserRank()
	{
		pageuserRank = preciseservice.queryuserRankList(userRank, null, pageuserRank.getPageSize(), pageuserRank.getPageNo());
		return SUCCESS;
	}
	public String queryChannelGroup()
	{
		pageChannelGroup = preciseservice.queryChannelGroupList(channelGroup, null, pageChannelGroup.getPageSize(), pageChannelGroup.getPageNo());
		return SUCCESS;
	}
	/**
	 * 
	 * @return
	 */
	
	public String queryNpvrChannelGroup()
	{
		pageChannelGroup = preciseservice.queryNpvrChannelGroupList(npvrChannelGroup, null, pageChannelGroup.getPageSize(), pageChannelGroup.getPageNo());
		return SUCCESS;
	}
	
	
	public String queryBChannelGroup()
	{
		pageChannelGroup = preciseservice.queryChannelGroupList(channelGroup, null, pageChannelGroup.getPageSize(), pageChannelGroup.getPageNo());
		return SUCCESS;
	}
	public String queryMenuType(){
		pageMenuType = preciseservice.queryTypelist(pageMenuType.getPageSize(), pageMenuType.getPageNo());
		return SUCCESS;
	}
	private String trim(String instr)
	{
		if (null!=instr)
		{
			return instr.replace(" ", "");
		}
		else
			return null;
		
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public PreciseMatchService getPreciseservice() {
		return preciseservice;
	}
	public void setPreciseservice(PreciseMatchService preciseservice) {
		this.preciseservice = preciseservice;
	}
	public Long getPloyId() {
		return ployId;
	}
	public void setPloyId(Long ployId) {
		this.ployId = ployId;
	}
	public Long getPositionId() {
		return positionId;
	}
	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}
	public ChannelInfo getChannel() {
		return channel;
	}
	public void setChannel(ChannelInfo channel) {
		this.channel = channel;
	}
	public TProductinfo getProduct() {
		return product;
	}
	public void setProduct(TProductinfo product) {
		this.product = product;
	}
	public TChannelinfoNpvr getNpvrChannel() {
		return npvrChannel;
	}
	public void setNpvrChannel(TChannelinfoNpvr npvrChannel) {
		this.npvrChannel = npvrChannel;
	}
	public TLoopbackCategory getLookbackCategory() {
		return lookbackCategory;
	}
	public void setLookbackCategory(TLoopbackCategory lookbackCategory) {
		this.lookbackCategory = lookbackCategory;
	}
	public TAssetinfo getAsset() {
		return asset;
	}
	public void setAsset(TAssetinfo asset) {
		this.asset = asset;
	}
	public TCategoryinfo getAssetCategory() {
		return assetCategory;
	}
	public void setAssetCategory(TCategoryinfo assetCategory) {
		this.assetCategory = assetCategory;
	}
	public String getPreciseMatchIds() {
		return preciseMatchIds;
	}
	public void setPreciseMatchIds(String preciseMatchIds) {
		this.preciseMatchIds = preciseMatchIds;
	}
	public String getChannelIds() {
		return channelIds;
	}
	public void setChannelIds(String channelIds) {
		this.channelIds = channelIds;
	}
	public String getProductIds() {
		return productIds;
	}
	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}
	public void setProductIds(String[] productIds) {
		this.productIds = productIds[0];
	}
	public String getNpvrChannelIds() {
		return npvrChannelIds;
	}
	public void setNpvrChannelIds(String npvrChannelIds) {
		this.npvrChannelIds = npvrChannelIds;
	}
	public String getLookbackCategoryIds() {
		return lookbackCategoryIds;
	}
	public void setLookbackCategoryIds(String lookbackCategoryIds) {
		this.lookbackCategoryIds = lookbackCategoryIds;
	}
	public String getAssetIds() {
		return assetIds;
	}
	public void setAssetIds(String assetIds) {
		this.assetIds = assetIds;
	}
	public String getAssetCategoryIds() {
		return assetCategoryIds;
	}
	public void setAssetCategoryIds(String assetCategoryIds) {
		this.assetCategoryIds = assetCategoryIds;
	}
	public String getAssetKeys() {
		return assetKeys;
	}
	public void setAssetKeys(String assetKeys) {
		this.assetKeys = assetKeys;
	}
	public PageBeanDB getPagePreciseMatch() {
		return pagePreciseMatch;
	}
	public void setPagePreciseMatch(PageBeanDB pagePreciseMatch) {
		this.pagePreciseMatch = pagePreciseMatch;
	}
	public PageBeanDB getPageChannel() {
		return pageChannel;
	}
	public void setPageChannel(PageBeanDB pageChannel) {
		this.pageChannel = pageChannel;
	}
	public PageBeanDB getPageNpvrChannel() {
		return pageNpvrChannel;
	}
	public void setPageNpvrChannel(PageBeanDB pageNpvrChannel) {
		this.pageNpvrChannel = pageNpvrChannel;
	}
	public PageBeanDB getPageLookBackColumn() {
		return pageLookBackColumn;
	}
	public void setPageLookBackColumn(PageBeanDB pageLookBackColumn) {
		this.pageLookBackColumn = pageLookBackColumn;
	}
	public PageBeanDB getPageAssetCategory() {
		return pageAssetCategory;
	}
	public void setPageAssetCategory(PageBeanDB pageAssetCategory) {
		this.pageAssetCategory = pageAssetCategory;
	}
	public PageBeanDB getPageAsset() {
		return pageAsset;
	}
	public void setPageAsset(PageBeanDB pageAsset) {
		this.pageAsset = pageAsset;
	}
	public PageBeanDB getPageProduct() {
		return pageProduct;
	}
	public void setPageProduct(PageBeanDB pageProduct) {
		this.pageProduct = pageProduct;
	}
	public TPreciseMatchBk getPrecise() {
		return precise;
	}
	public void setPrecise(TPreciseMatchBk precise) {
		this.precise = precise;
	}
	public String getDataids() {
		return dataids;
	}
	public void setDataids(String dataids) {
		this.dataids = dataids;
	}
	public List getProductList() {
		return productList;
	}
	public void setProductList(List productList) {
		this.productList = productList;
	}
	public List getChannelList() {
		return channelList;
	}
	public void setChannelList(List channelList) {
		this.channelList = channelList;
	}
	public List getNpvrChannelList() {
		return npvrChannelList;
	}
	public void setNpvrChannelList(List npvrChannelList) {
		this.npvrChannelList = npvrChannelList;
	}
	public List getLookbackCategoryList() {
		return lookbackCategoryList;
	}
	public void setLookbackCategoryList(List lookbackCategoryList) {
		this.lookbackCategoryList = lookbackCategoryList;
	}
	public List getAssetList() {
		return assetList;
	}
	public void setAssetList(List assetList) {
		this.assetList = assetList;
	}
	public List getAssetCategoryList() {
		return assetCategoryList;
	}
	public void setAssetCategoryList(List assetCategoryList) {
		this.assetCategoryList = assetCategoryList;
	}
	public List getAssetKeyList() {
		return assetKeyList;
	}
	public void setAssetKeyList(List assetKeyList) {
		this.assetKeyList = assetKeyList;
	}
	
	public List getPreciseTypeList() {
		return preciseTypeList;
	}
	public void setPreciseTypeList(List preciseTypeList) {
		this.preciseTypeList = preciseTypeList;
	}
	public Long getPreciseType() {
		return preciseType;
	}
	public void setPreciseType(Long preciseType) {
		this.preciseType = preciseType;
	}
	public ReleaseArea getReleaseArea() {
		return releaseArea;
	}
	public void setReleaseArea(ReleaseArea releaseArea) {
		this.releaseArea = releaseArea;
	}
	public UserIndustryCategory getUserIndustry() {
		return userIndustry;
	}
	public void setUserIndustry(UserIndustryCategory userIndustry) {
		this.userIndustry = userIndustry;
	}
	public UserRank getUserRank() {
		return userRank;
	}
	public void setUserRank(UserRank userRank) {
		this.userRank = userRank;
	}
	public List getReleaseAreaList() {
		return releaseAreaList;
	}
	public void setReleaseAreaList(List releaseAreaList) {
		this.releaseAreaList = releaseAreaList;
	}
	public List getUserIndustryList() {
		return userIndustryList;
	}
	public void setUserIndustryList(List userIndustryList) {
		this.userIndustryList = userIndustryList;
	}
	public List getUserRankList() {
		return userRankList;
	}
	public void setUserRankList(List userRankList) {
		this.userRankList = userRankList;
	}
	public PageBeanDB getPagereleaseArea() {
		return pagereleaseArea;
	}
	public void setPagereleaseArea(PageBeanDB pagereleaseArea) {
		this.pagereleaseArea = pagereleaseArea;
	}
	public PageBeanDB getPageuserIndustry() {
		return pageuserIndustry;
	}
	public void setPageuserIndustry(PageBeanDB pageuserIndustry) {
		this.pageuserIndustry = pageuserIndustry;
	}
	public PageBeanDB getPageuserRank() {
		return pageuserRank;
	}
	public void setPageuserRank(PageBeanDB pageuserRank) {
		this.pageuserRank = pageuserRank;
	}
	public TChannelGroup getChannelGroup() {
		return channelGroup;
	}
	public void setChannelGroup(TChannelGroup channelGroup) {
		this.channelGroup = channelGroup;
	}
	public List getChannelGroupList() {
		return channelGroupList;
	}
	public void setChannelGroupList(List channelGroupList) {
		this.channelGroupList = channelGroupList;
	}
	public PageBeanDB getPageChannelGroup() {
		return pageChannelGroup;
	}
	public void setPageChannelGroup(PageBeanDB pageChannelGroup) {
		this.pageChannelGroup = pageChannelGroup;
	}
	public TNpvrChannelGroup getNpvrChannelGroup() {
		return npvrChannelGroup;
	}
	public void setNpvrChannelGroup(TNpvrChannelGroup npvrChannelGroup) {
		this.npvrChannelGroup = npvrChannelGroup;
	}
	
	
	
}
