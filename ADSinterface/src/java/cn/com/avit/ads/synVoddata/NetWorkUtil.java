package cn.com.avit.ads.synVoddata;

import java.util.List;

import javax.inject.Inject;

import cn.com.avit.ads.synVoddata.bean.NetWorkinfo;
import cn.com.avit.ads.synVoddata.service.NetWorkinfoService;

public class NetWorkUtil {
	private static NetWorkUtil netWorkUtil;
	@Inject
	NetWorkinfoService netWorkinfoService;
	private static List<NetWorkinfo> allArea;
	
	private NetWorkUtil(){
		
	}
	
	public static NetWorkUtil getInstince(){
		if(netWorkUtil==null){
			netWorkUtil = new NetWorkUtil();
		}
		return netWorkUtil;
	}

	public List<NetWorkinfo> getAllArea(){
		if(allArea==null){
			allArea =netWorkinfoService.getAllNetWorkinfos();
		}
		return allArea;
	}
}
