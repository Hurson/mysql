package com.avit.ads.pushads.uix.service.impl;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.avit.ads.pushads.task.bean.TReleaseArea;
import com.avit.ads.pushads.uix.dao.AreaDao;
import com.avit.ads.pushads.uix.dao.UixDao;
import com.avit.ads.pushads.uix.json.JsonResponse;
import com.avit.ads.pushads.uix.service.UixService;
import com.avit.ads.util.InitConfig;
import com.avit.ads.util.json.Json2ObjUtil;
import com.avit.ads.util.type.UIUpdate;
import com.avit.ads.util.warn.WarnHelper;

@Service
public class UixServiceImpl implements UixService {
		
	private Log log = LogFactory.getLog(this.getClass());
	
	//private final static String HTTP_PLUS_ESCAPE = "%2B";
	
	@Autowired
	private UixDao uixDao;	
	@Autowired
	private AreaDao areaDao;
	@Autowired
	private WarnHelper warnHelper;
	
	
	public boolean sendUiUpdateMsg(String mod, String areaCode, Integer type, String updatePath) {
		String url = InitConfig.getConfigMap().get("nit.interface.address");
		HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(bulidGetUrl(url, mod, areaCode, type, updatePath));
        getMethod.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");    
             
        int times = 3; //三次发送不成功，则告警
        
        while(times > 0){
        	 try {       	
             	int responsCode = httpClient.executeMethod(getMethod);
     			if(200 == responsCode){
     				String responseBody = new String(getMethod.getResponseBodyAsString().getBytes("GBK"));
     				
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
   	 	warnHelper.writeWarnMsgToDb("【连续三次不能访问UI更新服务器】" + "request url: " + url);
		return false;
	}
	
	/*
	public boolean sendUiUpdateMsg(String mod, String areaCode, Integer type, String updatePath) {
		String url = InitConfig.getConfigMap().get("nit.interface.address");
		HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        postMethod.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");    
       
        postMethod.setRequestBody(initPostParams(mod, areaCode, type, updatePath));  
        
        int times = 3; //三次发送不成功，则告警
        
        while(times > 0){
        	 try {       	
             	int responsCode = httpClient.executeMethod(postMethod);
     			if(200 == responsCode){
     				String responseBody = new String(postMethod.getResponseBodyAsString().getBytes("GBK"));
     				
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
   	 	warnHelper.writeWarnMsgToDb("【连续三次不能访问UI更新服务器】" + "request url: " + url);
		return false;
	}
	
	
	
	private NameValuePair[] initPostParams(String mod, String areaCode, Integer updateType, String updatePath){
		
		TReleaseArea areaEntity = areaDao.getAreaByAreaCode(areaCode);	
		String onid = areaEntity.getLocationCode();
		String ocsid = areaEntity.getOcsId();
		String tsid = areaEntity.getTsId();
		
		String typeParam = "";
		String versionParam = "";
		String pathParam = "";
		
		for(UIUpdate entry : UIUpdate.values()){
			int type = entry.getType();
			int version = uixDao.getUiVersion(areaCode, type);
			if(type == updateType){
				version++;
			}else if(0 == version){
				continue;
			}
			String path = "/65535.65535." + ocsid + "/" + entry.getFileName();
			
			typeParam += type + "+";
			versionParam += version + "+";
			pathParam += path + "+";
		}
		
	    NameValuePair[] params = { new NameValuePair("mod",mod),  
                new NameValuePair("areacode",areaCode),  
                new NameValuePair("onid",onid),  
                new NameValuePair("adctrl","1"), 
                new NameValuePair("type",reBuildStr(typeParam)),
                new NameValuePair("version", reBuildStr(versionParam)),
                new NameValuePair("tsid", tsid),   //不需要
                new NameValuePair("ocsid", ocsid), //不需要
                new NameValuePair("path", reBuildStr(pathParam)) //"/65535.65535.499/initPic-c.iframe"
	    };  
		return params;
	}
	
	*/
	
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
		
		String typeParam = "";
		String versionParam = "";
		String pathParam = "";
		
		for(UIUpdate entry : UIUpdate.values()){
			int type = entry.getType();
			int version = uixDao.getUiVersion(areaCode, type);
			if(type == updateType){
				version = (version + 1) % 255;
			}else if(0 == version){
				continue;
			}
			String path = "/65535.65535." + ocsid + "/" + entry.getFileName();
			
			typeParam += type + "+";
			versionParam += version + "+";
			pathParam += path + "+";
		}
		
		return url + "?mod=" + mod + "&areacode=" + areaCode + "&onid=" + onid + "&adctrl=1&type=" + reBuildStr(typeParam)
				+ "&version=" + reBuildStr(versionParam) + "&path=" + reBuildStr(pathParam) + "&tsid=1&ocsid=1";
	} 
}
