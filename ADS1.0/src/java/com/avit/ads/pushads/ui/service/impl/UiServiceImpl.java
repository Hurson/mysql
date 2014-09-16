package com.avit.ads.pushads.ui.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avit.ads.pushads.ui.bean.nit.Nwconitconf;
import com.avit.ads.pushads.ui.bean.nit.Nwconitdesc;
import com.avit.ads.pushads.ui.bean.ui.UiNitAd;
import com.avit.ads.pushads.ui.bean.ui.UiOc;
import com.avit.ads.pushads.ui.bean.ui.UiVersion;
import com.avit.ads.pushads.ui.dao.NitDao;
import com.avit.ads.pushads.ui.dao.UiDao;
import com.avit.ads.pushads.ui.service.UiService;
import com.avit.ads.util.ConstantsAdsCode;
import com.avit.ads.util.InitConfig;
import com.avit.ads.util.bean.WarnInfo;
import com.avit.ads.util.warn.WarnHelper;
@Service("UiService")
public class UiServiceImpl implements UiService {
	private Log log = LogFactory.getLog(this.getClass());
	@Inject
	private UiDao uiDao;
	@Inject
	private NitDao nitDao;
	
	@Autowired
	private WarnHelper warnHelper;
	
	private static final String CHARACTERENCODING = "UTF-8";
	public boolean addUiDesc(List<String> types, List<String> files,String defaultstartflag) {
		// TODO Auto-generated method stub
		//查询UI版本号
		String desc="";
		boolean retflag=true;
		//读取配置文件 tsid serviceid
		
		//根据区域读取配置文件 nid
		
		//生成描述符
		try
		{
			//List<String> types=new ArrayList<String>();
			//List<String> files=new ArrayList<String>();;
			//types.add("1");
			//types.add("5");
			//files.add("/65535.123.1231/init-a.iframe");
			//files.add("/65535.123.1231/init-a.ts");
			
			byte bytes[] = new byte[200];// { 50, 0, -1, 28, -24 };
			short nid=0x1234;
			short len = 0x54;
			short version=0;
			short looplen=(short)types.size();
			short filelen=0x23;
			short tsid,serviceid;
			String filename="/65535.123.1231/init-a.frame";
			
			
			//读取 UI_OC 表获得OC数据列表的 nid tsid serviceid
			List<UiOc> listUiOc =uiDao.queryUiOcList();
			if (listUiOc==null || listUiOc.size()<=0)
			{
				return false;
			}
			List<UiNitAd> listUiAd=uiDao.queryUiNitAdList();
			//List<UiVersion> queryUiVersionAdList();
			//读取 ui_typeversion  对应版本号
			
			//读取 ui_nit_ad  获取列表  ts serviceid
			for (int nitindex=0;nitindex<listUiOc.size();nitindex++)
			{
				short dataindex=0;
				nid = 1001;//listUiOc.get(nitindex).getNetworkId().shortValue();
				int nidtemp = listUiOc.get(nitindex).getNetworkId().intValue();
				int tsidtemp = listUiOc.get(nitindex).getTsid().intValue();
				int serviceidtemp = listUiOc.get(nitindex).getServiceId().intValue();
				bytes[dataindex++]=(byte)0xc0;
				bytes[dataindex++]=(byte)(len&0x00ff);
				bytes[dataindex++]=(byte)(nid>>8);
				bytes[dataindex++]=(byte)(nid&0x00ff);
				if (defaultstartflag==ConstantsAdsCode.START24)
				{
					bytes[dataindex++]=(byte)0x01;
				}
				else
				{
					bytes[dataindex++]=(byte)0x00;
				}
				bytes[dataindex++]=(byte)0xff;
				bytes[dataindex++]=(byte)(looplen&0x00ff);
				
				for (int i=0;i<types.size();i++)
				{
					bytes[dataindex++]=(byte)Integer.valueOf(types.get(i)).intValue();
					//读取当前版本号
					UiVersion uiVersion =uiDao.getUiVersionByType(types.get(i));
					version = (short)(uiVersion.getVersion()+1);
					uiVersion.setVersion(version);
					//if (uiVersion.getVersion()>255)
					{
						//uiVersion.setVersion(Short.valueOf("0"));
					}
					//保存新版本号
					uiDao.updateVersion(uiVersion);
					version=(short)(version & 0xFF);			
					bytes[dataindex++]=(byte)version;
					
					String temppath="/"+nidtemp+"."+tsidtemp+"."+serviceidtemp+"/"+files.get(i);
					byte filebytes[]= temppath.getBytes("ISO-8859-1");
					bytes[dataindex++]=(byte)filebytes.length;			
					for (int j=0;j<filebytes.length;j++)
					{
						bytes[dataindex++]=filebytes[j];
					}			
				}
				tsid=0x1235;
				serviceid=0x5678;
				/* 新版本描述符 ，取消广告相关service
				for (int i=0;i<listUiAd.size();i++)
				{
					tsid=listUiAd.get(i).getTsid().shortValue();
					serviceid=listUiAd.get(i).getServiceId().shortValue();
					bytes[dataindex++]=(byte)(tsid>>8);
					bytes[dataindex++]=(byte)(tsid&0x00ff);
					bytes[dataindex++]=(byte)(serviceid>>8);
					bytes[dataindex++]=(byte)(serviceid&0x00ff);
				}
				*/
				len = (short)(dataindex-1);
				bytes[1]=(byte)(len&0x00ff);	
				desc= byte2HexStr(bytes).substring(0,(len+1)*2);
				Nwconitdesc nwcoNitdesc = new Nwconitdesc();
				//查找EPG是否有对应NId 
				Nwconitconf nwconitconf =nitDao.getNwconitconfByNid(String.valueOf(nid));
				if (nwconitconf==null)
				{
					nwconitconf =nitDao.getNwconitconfByNid(String.valueOf(20481));
				}
				if (nwconitconf==null)
				{
					nwconitconf = new Nwconitconf();
					nwconitconf.setId(14);
					//continue;
				}
				nwcoNitdesc.setNwconitconfid(nwconitconf.getId());
				nwcoNitdesc.setEnable(0);//默认是否需要发送，需确认
				nwcoNitdesc.setTsid(-1);
				nwcoNitdesc.setTag(192);
				log.info("nit desc :"+desc);
				desc =desc.substring(4);
				nwcoNitdesc.setLength(desc.length()/2);//字节长度
				nwcoNitdesc.setContent(desc);
				//写NIT描述符
				nitDao.addNitDesc(nwcoNitdesc);
			}
		}
		catch(Exception e)
		{
			log.error("write nit desc error"+e);
			retflag=false;
		}
		//写NIT描述符
		
		//更新UI版本号表
		
		//bytebytes[] = new byte[] { 50, 0, -1, 28, -24 };
		//StringisoString = new String(bytes, "ISO-8859-1");
		//byte[] isoret = isoString.getBytes("ISO-8859-1");
		return retflag;
	}
	/** 
     * bytes转换成十六进制字符串 
     * @param byte[] b byte数组 
     * @return String 每个Byte值之间空格分隔 
     */  
    public static String byte2HexStr(byte[] b)  
    {  
        String stmp="";  
        StringBuilder sb = new StringBuilder("");  
        for (int n=0;n<b.length;n++)  
        {  
            stmp = Integer.toHexString(b[n] & 0xFF);  
            sb.append((stmp.length()==1)? "0"+stmp : stmp);  
            sb.append("");  
        }  
        return sb.toString().toUpperCase().trim();  
    } 
    
    /**
	 * 往区域发送NID描述符插入信息
	 * @param bodyContent
	 * @param areaCode
	 * @return
	 */
    public String sendUiDesc(String bodyContent, String areaCode){
    	String nid = InitConfig.getAreaMap().get(areaCode);
    	String url = InitConfig.getConfigMap().get("nit.interface.address")+"?nid="+nid;
    	log.info("请求NIT描述符插入接口地址："+url);
    	log.info("请求NIT描述符插入接口内容："+bodyContent);
    	String ret = "3";
    	try{
	    	HttpClient httpclient = new HttpClient();
	        PostMethod post = new PostMethod(url);
	
	        post.setRequestEntity(new ByteArrayRequestEntity(bodyContent.getBytes(CHARACTERENCODING)));
	        post.setRequestHeader("Content-type", "text/xml; charset="+CHARACTERENCODING);
	
	        HttpClientParams params = new HttpClientParams();
	        params.setContentCharset(CHARACTERENCODING);
	        httpclient.setParams(params);
	        
	        int times = 3; //三次发送不成功，则告警
	        int responsCode = 0;
	        while(times > 0 ){
	        	responsCode = httpclient.executeMethod(post);
	        	if(200 == responsCode){        //如果能连上，直接返回
	        		byte[] b = post.getResponseBody();
			        String responseStr = new String(b, CHARACTERENCODING);
			        log.info("NIT描述符插入接口返回结果："+responseStr);
			        ret = responseStr.trim();
			        if(!"0".equals(ret)){          //如果连上了，但是请求不对，也告警
			        	WarnInfo warnEntity = new WarnInfo();
			    		warnEntity.setTime(new Date());
			    		int retCode = Integer.parseInt(ret);
			    		switch (retCode) {
						case 1:
							warnEntity.setContent("【1:UI网络ID不存在】" + "areaCode: " + areaCode + " requestBody: " + bodyContent);
							break;
						case 2:
							warnEntity.setContent("【2:UI网络ID配置不完整】" + "areaCode: " + areaCode + " requestBody: " + bodyContent);
							break;
						case 3:
							warnEntity.setContent("【3:UI其它错误】" + "areaCode: " + areaCode + " requestBody: " + bodyContent);
						default:
							warnEntity.setContent("【NIT描述符插入接口发送错误】" + "areaCode: " + areaCode + " requestBody: " + bodyContent);
							break;
						}
			    		warnEntity.setIsProcessed(0);	
			    		warnHelper.writeWarnInfoToDB(warnEntity);
			        }
	        		return ret;
	        	}
	        	Thread.sleep(3000); //3s后重发请求
	        	times--;	        	
	        }
	        //三次连接不上，告警
        	log.error("NIT描述符插入接口发送错误："+responsCode);
    		warnHelper.writeWarnMsgToDb("【连续三次不能访问UI服务器】" + "areaCode: " + areaCode + " requestBody: " + bodyContent);
	               
    	}catch(Exception e){
    		//e.printStackTrace();
    		log.error("NIT描述符插入接口异常："+e);
    		ret = "3";
    	}
        return ret;
	}
}
