package cn.com.avit.ads.synVoddata;

import cn.com.avit.ads.synVoddata.json.ProductJsonObject;

import com.google.gson.Gson;

public class TestGson {
  public static void main(String[] args){
  String str =" {\"result\":\"0\", \"desc\":\"成功\"," +
		"\"products\":[{"
  	
      +"\"productId\":\"1\","
      +  "\"productName\":\"高清影院包月\"},{"+
 
  
      "\"productId\":\"2\","+
     "\"productName\":\"标清包月\""+
  "}"+
"]"+
"}";
  Gson gson = new Gson();
  
  System.out.println("json is ::::::::  "+ str);
  ProductJsonObject po = gson.fromJson(str, ProductJsonObject.class);
  System.out.print(po.getResult());
  
  

	  
  }
}
