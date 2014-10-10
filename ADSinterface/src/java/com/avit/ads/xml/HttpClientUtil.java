package com.avit.ads.xml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.avit.common.exception.ApplicationException;
import com.avit.common.util.SystemProperties;


/**
 * @author weimmy
 * @date 2012-5-17
 * @version 1.0
 **/
public class HttpClientUtil {
	private static Log log = LogFactory.getLog(HttpClientUtil.class);

	private static final String CHARACTERENCODING = "UTF-8";

	public static void postXML(String url, String requestXml) throws Exception {
		int statusCode = -1;
		String responseXMl = null;
		PostMethod post =null;
		HttpClient httpClient = null;
		try {
			//统一编码UTF-8
			byte[] requestXML = requestXml.getBytes(CHARACTERENCODING);
			
			RequestEntity entity = new ByteArrayRequestEntity(requestXML,
					"text/xml; charset=" + CHARACTERENCODING);

			// 发送请求
			httpClient = new HttpClient();
			post = new PostMethod(url);
			post.setRequestEntity(entity);
			statusCode = httpClient.executeMethod(post);

			if (statusCode == 200) {// 成功返回
				byte responseByte[] = post.getResponseBody();
				responseXMl = new String(responseByte);
				if(log.isDebugEnabled()){
					log.debug("response = " + responseXMl);
				}
			} else {// 失败返回
				byte responseByte[] = post.getResponseBody();
				responseXMl = new String(responseByte);
				
				log.error("StatusCode="+statusCode+"; and response = " + responseXMl);
				throw new Exception(statusCode+",send xml error");
			}
		} catch (Exception ex) {
			log.error("Send XML to"+url+" Error!", ex);
			throw ex;
		}finally{
			if(post != null) {
				post.releaseConnection();
			}
			if(httpClient != null){
				httpClient.getHttpConnectionManager().closeIdleConnections(0);
			}
		}
	}

	public static String sentXML(String url, String requestXml) throws ApplicationException {
		int statusCode = -1;
		String responseXMl = null;
		PostMethod post =null;
		HttpClient httpClient = null;
		try {
			//统一编码UTF-8
			byte[] requestXML = requestXml.getBytes(CHARACTERENCODING);
			
			RequestEntity entity = new ByteArrayRequestEntity(requestXML,
					"text/xml; charset=" + CHARACTERENCODING);

			// 发送请求
			httpClient = new HttpClient();
			post = new PostMethod(url);
			post.setRequestEntity(entity);
			statusCode = httpClient.executeMethod(post);

			if (statusCode == 200) {// 成功返回
				byte responseByte[] = post.getResponseBody();
				responseXMl = new String(responseByte);

			} else {// 失败返回
				throw new ApplicationException(statusCode,"send xml error");
			}
			
			return responseXMl;
		} catch (ApplicationException ex) {
			throw new ApplicationException(ex.getCode(),ex.getCodeMessage());
		}catch (IOException ex) {
			log.error("Send XML to"+url+" Error!", ex);
			throw new ApplicationException(500,"unkonw error");
		}catch (Exception ex) {
			log.error("Send XML to"+url+" Error!", ex);
			throw new ApplicationException(500,"unkonw error");
		}finally{
			if(post != null) {
				post.releaseConnection();
			}
			if(httpClient != null){
				httpClient.getHttpConnectionManager().closeIdleConnections(0);
			}
		}
	}

	
	/**
     * 从Request中获取内容
     * @param request
     * @return
     * @throws IOException
     */
    public static String getRequestContent(HttpServletRequest request) throws IOException {
        
        StringBuffer sb = new StringBuffer();    
        BufferedReader br = null;  
        try {
            br = new BufferedReader(new InputStreamReader(request.getInputStream(),CHARACTERENCODING));
            String tmp = "";
            while((tmp=br.readLine())!= null) {
                    sb.append(tmp);
            }
         } catch (IOException e) {
             throw e;
         } finally {
             if (br != null) {           
                 br.close();
             }
         }

         return sb.toString();
    }

}
