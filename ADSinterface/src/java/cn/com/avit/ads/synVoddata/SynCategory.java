package cn.com.avit.ads.synVoddata;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import cn.com.avit.ads.synVoddata.bean.CategoryInfo;
import cn.com.avit.ads.synVoddata.bean.NetWorkinfo;
import cn.com.avit.ads.synVoddata.json.Category;
import cn.com.avit.ads.synVoddata.json.CategoryJsonObject;
import cn.com.avit.ads.synVoddata.service.CategoryService;

import com.google.gson.Gson;

public class SynCategory {
	@Inject
	private CategoryService categoryService;
	@Value("${vod.ip}")
	private String ip;
	@Value("${vod.port}")
    private String port;
	
	private Logger logger = Logger.getLogger(SynCategory.class);
	public void generateCategoryData()
	{
		List<NetWorkinfo> allArea =NetWorkUtil.getInstince().getAllArea();
		for(NetWorkinfo netWorkinfo :allArea){
		String netWorkID = netWorkinfo.getAreaCode();
		String[] str =HttpUtil.get(ip, port, "/payUI/GetFolderContents?areaCode="+netWorkID);
		if(str[0].equals("200")){
		 Gson gson = new Gson();
		 if(logger.isDebugEnabled()){ 
		logger.debug("json is ::::::::  "+ str[1]);
		}
		 CategoryJsonObject co = gson.fromJson(str[1], CategoryJsonObject.class);
		if(co.getResult().equals("0")){
			
			categoryService.deleteCategoryList(netWorkID);
			List<Category> categorys = co.getCategory();
			parseJson(categorys, netWorkID, 0L);
		
		}else{
           logger.error("获取区域"+netWorkinfo.getAreaName()+",areaCode="+netWorkinfo.getAreaCode()+"产品失败,原因："+co.getDesc());
		}
		
		}else{			
			logger.error("获取区域"+netWorkinfo.getAreaName()+",areaCode="+netWorkinfo.getAreaCode()+"产品失败");
			
		}
		}
		categoryService.execCate();
		
	}

	private void parseJson(List<Category> dataList, String netWorkID, Long parentId){
		for(Category cat : dataList){
			CategoryInfo cain = new CategoryInfo();
			cain.setResourceId(cat.getFolderId());
			cain.setResourceName(cat.getFolderName());
			cain.setResourceType(cat.getFolderType());
			cain.setAreaCode(netWorkID);
			cain.setParentId(parentId);
			categoryService.insertCategoryInfo(cain);
			
			if(cat.getFolderType().equals("0")){
				parseJson(cat.getChildFolder(), netWorkID,new Long(cat.getFolderId()).longValue());
			}
			
		}
	}
	
	public static void main(String[] args){
		String jsonStr = "{ 'result': '0', 'desc': '获取栏目信息成功',"
							+ "category:["
							+"	{'folderId': '1','folderName': '电影','folderType': '1','childFolder': []"
							+ "	},"
							+ "	{'folderId': '26','folderName': '测试电影栏目','folderType': '0',"
							+"	'childFolder': ["
							+"	{'folderId': '28','folderName': '动作','folderType': '1',"
							+"	'childFolder': []"
							+"	}"
							+"]"
							+"}"
							+"]"
							+"}";
		Gson gson = new Gson();
		CategoryJsonObject co = gson.fromJson(jsonStr, CategoryJsonObject.class);
		//System.out.println(co.getCategory().get(1).getChildFolder().get(0).getFolderName());
		//parseJson(co.getCategory(),"001",0L);
	}
	

}
