package com.avit.ads.dtmb.service.impl;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avit.ads.dtmb.dao.DUixDao;
import com.avit.ads.dtmb.service.DUixService;
import com.avit.ads.pushads.task.bean.TReleaseArea;
import com.avit.ads.pushads.uix.dao.AreaDao;
import com.avit.ads.pushads.uix.json.JsonResponse;
import com.avit.ads.util.InitConfig;
import com.avit.ads.util.json.Json2ObjUtil;
import com.avit.ads.util.type.UIUpdate;
import com.avit.ads.util.warn.WarnHelper;

@Service
public class DUixServiceImpl implements DUixService {
		
	private Log log = LogFactory.getLog(this.getClass());
	
	//private final static String HTTP_PLUS_ESCAPE = "%2B";
	
	@Autowired
	private DUixDao uixDao;	
	@Autowired
	private AreaDao areaDao;
	@Autowired
	private WarnHelper warnHelper;
	
	//这个方法写的还是有点问题的，mod只能是play，而不能是del或get
	public boolean sendUiUpdateMsg(String mod, String areaCode, Integer type, String updatePath) {
		String url = InitConfig.getConfigMap().get("nit.interface.address");
		HttpClient httpClient = new HttpClient();
		//设置超时
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(60000);
        GetMethod getMethod = new GetMethod(bulidGetUrl(url, mod, areaCode, type, updatePath));
        getMethod.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");    
             
        int times = 3; //三次发送不成功，则告警
        
        while(times > 0){
        	 try {       	
             	int responsCode = httpClient.executeMethod(getMethod);
     			if(200 == responsCode){
     				String responseBody = new String(getMethod.getResponseBodyAsString().getBytes("UTF-8"));
     				
     				JsonResponse respEntity = (JsonResponse) Json2ObjUtil.getObject4JsonString(responseBody, JsonResponse.class);
     				if(respEntity.getRet().equals("0")){
     					uixDao.updateVersion(areaCode, type);
     					log.info("往区域" + areaCode + "发送UI更新成功！");
     					return true;
     				}else{
     					log.error("往区域" + areaCode + "发送UI更新失败：" + respEntity.getRet_msg());
     					return false; 
     				}
     			}
				Thread.sleep(3000);//3s后重发请求
				times--;	
				
     		} catch (Exception e) {
     			times--;
     			log.error("往区域" + areaCode + "发送UI更新出现异常", e);
     		} 
        }
        //三次连接不上，告警	
   	 	warnHelper.writeWarnMsgToDb(areaCode, "【连续三次不能访问UI更新服务器】" + "request url: " + url);
		return false;
	}
	
	
	public boolean sendUiUpdateMsg(String mod, String areaCode, Integer type, String updatePath, boolean isDefault) {
		String url = InitConfig.getConfigMap().get("nit.interface.address");
		HttpClient httpClient = new HttpClient();
		String updateUrl = bulidGetUrl(url, mod, areaCode, type, updatePath);
		if(isDefault){
			updateUrl = updateUrl.replace("adctrl=1", "adctrl=0");
		}
        GetMethod getMethod = new GetMethod(updateUrl);
        getMethod.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");    
             
        int times = 3; //三次发送不成功，则告警
        
        while(times > 0){
        	 try {       	
             	int responsCode = httpClient.executeMethod(getMethod);
     			if(200 == responsCode){
     				String responseBody = new String(getMethod.getResponseBodyAsString().getBytes("UTF-8"));
     				
     				JsonResponse respEntity = (JsonResponse) Json2ObjUtil.getObject4JsonString(responseBody, JsonResponse.class);
     				if(respEntity.getRet().equals("0")){
     					uixDao.updateVersion(areaCode, type);
     					log.info("往区域" + areaCode + "发送UI更新成功！");
     					return true;
     				}else{
     					log.error("往区域" + areaCode + "发送UI更新失败：" + respEntity.getRet_msg());
     					return false; 
     				}
     			}
				Thread.sleep(3000);//3s后重发请求
				times--;	
				
     		} catch (Exception e) {
     			times--;
     			log.error("往区域" + areaCode + "发送UI更新出现异常", e);
     		} 
        }
        //三次连接不上，告警	
   	 	warnHelper.writeWarnMsgToDb(areaCode, "【连续三次不能访问UI更新服务器】" + "request url: " + url);
		return false;
	}



	private  String reBuildStr(String str){
		if(str.endsWith("+")){
			str = str.substring(0, str.length()-1);
		}
		//return str.replace("+", HTTP_PLUS_ESCAPE);
		return str;
	}
	
	private String bulidGetUrl(String url, String mod, String areaCode, Integer updateType, String updatePath){
		TReleaseArea areaEntity = areaDao.getAreaByAreaCode(areaCode);	
		String onid = areaEntity.getLocationCode();
		String ocsid = areaEntity.getOcsId();
		//String tsid = areaEntity.getTsId();
		String adCtrl = areaEntity.getAdCtrl();
		
		String typeParam = "";
		String versionParam = "";
		String pathParam = "";
		
		for(UIUpdate entry : UIUpdate.values()){
			int type = entry.getType();
			int version;
			if(type == updateType){
				version = uixDao.getUiVersion(areaCode, type);
				version = (version + 1) % 255;
			}else{
				version = uixDao.getAvailableVersion(areaCode, type);
				if(0 == version){
					continue;
				}
			}
			String path = "/65535.65535." + ocsid + "/" + entry.getFileName();
			
			typeParam += type + "+";
			versionParam += version + "+";
			pathParam += path + "+";
		}
		
		return url + "?mod=" + mod + "&areacode=" + areaCode + "&onid=" + onid + "&adctrl=" + adCtrl + "&type=" + reBuildStr(typeParam)
				+ "&version=" + reBuildStr(versionParam) + "&path=" + reBuildStr(pathParam) + "&tsid=1&ocsid=1";
	}

	public boolean delUiUpdateMsg(String areaCode, Integer type, String updatePath, boolean isDefault) {
		uixDao.abolishVersion(areaCode, type);
		TReleaseArea areaEntity = areaDao.getAreaByAreaCode(areaCode);	
		String onid = areaEntity.getLocationCode();
		String ocsid = areaEntity.getOcsId();
		int version = uixDao.getUiVersion(areaCode, type);
		String path = "/65535.65535." + ocsid + "/" + updatePath;
		
		String url = InitConfig.getConfigMap().get("nit.interface.address");
		url += "?mod=del&areacode=" + areaCode + "&onid=" + onid + "&adctrl=1&type=" + type + "&version=" + version + "&path=" + path + "&tsid=1&ocsid=1";
		
		if(isDefault){
			url = url.replace("adctrl=1", "adctrl=0");
		}
		
		HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(url);
        getMethod.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");   
		
        int times = 3; //三次发送不成功，则告警
        
        while(times > 0){
        	 try {       	
             	int responsCode = httpClient.executeMethod(getMethod);
     			if(200 == responsCode){
     				String responseBody = new String(getMethod.getResponseBodyAsString().getBytes("UTF-8"));
     				
     				JsonResponse respEntity = (JsonResponse) Json2ObjUtil.getObject4JsonString(responseBody, JsonResponse.class);
     				if(respEntity.getRet().equals("0")){
     					log.info("区域" + areaCode + "描述符删除操作成功！");
     					return true;
     				}else{
     					log.error("区域" + areaCode + "描述符删除失败：" + respEntity.getRet_msg());
     					return false; 
     				}
     			}
				Thread.sleep(3000);//3s后重发请求
				times--;	
				
     		} catch (Exception e) {
     			times--;
     			log.error("往区域" + areaCode + "发送UI更新出现异常", e);
     		} 
        }
        //三次连接不上，告警	
   	 	warnHelper.writeWarnMsgToDb(areaCode, "【连续三次不能访问UI更新服务器】" + "request url: " + url);
		return false;

	} 
	
	
	
}
