package com.dvnchina.advertDelivery.test;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * 增量同步bsmp数据
 *
 */
public class SysProductTest{	

	 public static void main(String[] args){
		 
	 }
		 public static String channelchange2(String jsonStr, String path)throws IOException {
			 // 得到请求报文的二进制数据
			 byte[] data = jsonStr.getBytes();
			 java.net.URL url = new java.net.URL(path);
			 //openConnection() 返回丄1�7丄1�7 URLConnection 对象，它表示刄1�7 URL 扄1�7引用的远程对象的连接
			 java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();// 打开丄1�7个连掄1�7
			 conn.setRequestMethod("POST");// 设置post方式请求
			 conn.setConnectTimeout(5 * 1000);// 设置连接超时时间丄1�7�1�7  JDK1.5以上才有此方泄1�7
			 conn.setReadTimeout(600 * 1000);// 设置读取超时时间丄1�7秄1�7 JDK1.5以上才有此方泄1�7
			 // 打算使用 URL 连接进行输出，则射1�7 DoOutput 标志设置丄1�7 true
			 conn.setDoOutput(true);
			 // 这里只设置内容类型与内容长度的头字段根据传�1�7�内容决宄1�7
			 // 内容类型Content-Type:
			 // application/x-www-form-urlencoded、text/xml;charset=GBK
			 // 内容长度Content-Length: 38
			 conn.setRequestProperty("Content-Type", "text/xml;charset=GBK");
			 conn.setRequestProperty("Content-Length", String.valueOf(data.length));
			 OutputStream outStream = conn.getOutputStream();// 返回写入到此连接的输出流
			 // 把二进制数据写入是输出流
			 outStream.write(data);
			 // 内存中的数据刷入
			 outStream.flush();
			 //关闭浄1�7
			 outStream.close();
			
			 String msg = "";// 保存调用http服务后的响应信息
			 // 如果请求响应码是200，则表示成功
			 if (conn.getResponseCode() == 200) {
			     // HTTP服务端返回的编码是UTF-8,故必须设置为UTF-8,保持编码统一,否则会出现中文乱砄1�7
			     BufferedReader in = new BufferedReader(new InputStreamReader(
			             (InputStream) conn.getInputStream(), "UTF-8"));//返回从此打开的连接读取的输入浄1�7
			     msg = in.readLine();
			     in.close();
			 }
			 conn.disconnect();// 断开连接
			 System.out.println("msg:"+msg);
			 return msg;
	     }
}
