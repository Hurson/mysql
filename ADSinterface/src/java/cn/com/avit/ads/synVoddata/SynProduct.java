package cn.com.avit.ads.synVoddata;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import cn.com.avit.ads.synVoddata.bean.NetWorkinfo;
import cn.com.avit.ads.synVoddata.bean.Productinfo;
import cn.com.avit.ads.synVoddata.json.Product;
import cn.com.avit.ads.synVoddata.json.ProductJsonObject;
import cn.com.avit.ads.synVoddata.service.ProductinfoService;

import com.google.gson.Gson;

public class SynProduct {
	@Inject
	private ProductinfoService productinfoService;
	@Value("${vod.ip}")
	private String ip;
	@Value("${vod.port}")
    private String port;
	
	
	
	private Logger logger = Logger.getLogger(SynProduct.class);
	public void generateProductData()
	{
		List<NetWorkinfo> allArea =NetWorkUtil.getInstince().getAllArea();
		for(NetWorkinfo netWorkinfo :allArea){
		String netWorkID = netWorkinfo.getAreaCode();
		String[] str =HttpUtil.get(ip, port, "/payUI/GetProducts?areaCode="+netWorkID);
		if(str[0].equals("200")){
		String jsonStr = str[1];
		 Gson gson = new Gson();
		 if(logger.isDebugEnabled()){ 
		logger.debug("json is ::::::::  "+ str[1]);
		}
		 ProductJsonObject po = gson.fromJson(str[1], ProductJsonObject.class);
		if(po.getResult().equals("0")){
		productinfoService.deleteProductList(netWorkID);
		for(Product product :po.getProducts()){
			Productinfo p = new Productinfo();
			p.setProductId(product.getProductId());
			p.setProductName(p.getProductName());
			p.setAreaCode(netWorkID);
			productinfoService.insertProductInfo(p);
		}
		}else{
           logger.error("获取区域"+netWorkinfo.getAreaName()+",areaCode="+netWorkinfo.getAreaCode()+"产品失败,原因："+po.getDesc());
		}
		
		}else{			
			logger.error("获取区域"+netWorkinfo.getAreaName()+",areaCode="+netWorkinfo.getAreaCode()+"产品失败");
			
		}
		}
		productinfoService.execProc();
		
	}

	

}
