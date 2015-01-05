package cn.com.avit.ads.synVoddata;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpUtil {
	private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);
	
	private String url;
	private String str;
	
	HttpUtil(String url, String str){
		this.url=url;
		this.str=str;
	}

    public static boolean isEmptyString(String str) {
        if (str == null) {
            return true;
        }

        if (str.length() > 0) {
            return false;
        }

        return true;
    }

	private String[] sendPost(){
		String[] r=new String[0];
		
		//if(CommonUtil.isEmptyString(this.str) && CommonUtil.isEmptyString(this.url)){
		if(isEmptyString(this.url)){
			log.debug("\n\nsend string is '"+str+"'\nurl is '"+url+"'");
		}else{
			//HttpClient httpclient = new HttpClient();
			HttpClient httpclient = Http.getClient();
			PostMethod post = new PostMethod(url);
			
			try {
				if(!isEmptyString(this.str))
					post.setRequestEntity(new ByteArrayRequestEntity(str.trim().getBytes(Constants.CHAR_ENCODING)));
				else
					post.setRequestEntity(new ByteArrayRequestEntity("".trim().getBytes(Constants.CHAR_ENCODING)));
				post.setRequestHeader("Content-type", "text/xml; charset="+Constants.CHAR_ENCODING);
				
				
				HttpClientParams params = new HttpClientParams();
				
				//params.setSoTimeout(3000);
				params.setContentCharset(Constants.CHAR_ENCODING);
				httpclient.setParams(params);
				
				int responsCode=httpclient.executeMethod(post);
				byte[] b=post.getResponseBody();
				String responsSstr = new String(b,Constants.CHAR_ENCODING);
				
				r=new String[2];
				
				r[0]=responsCode+"";
				r[1]=responsSstr;
			} catch (Exception e) {
				log.error("HTTP Connection Exception", e);
			}finally{
				try{
					post.releaseConnection();
					httpclient.getHttpConnectionManager().closeIdleConnections(0);
					
					post=null;
					httpclient=null;
				}catch (Exception e)
				{
					log.error("Close HTTP Connection Exception", e);
				}
				
			}
		}
        
		return r;
	}
	
	/**
	 * @param url
	 * @param str
	 * @return 返回一个永不为null的数组，元素个数为0或2，第一个为HTTP状态码，第二个为返回的String
	 */
	public static String[] post(String url, String str){
		return new HttpUtil(url, str).sendPost();
	}
	
	/**
	 * @param ip
	 * @param port
	 * @param path
	 * @param str
	 * @return 返回一个永不为null的数组，元素个数为0或2，第一个为HTTP状态码，第二个为返回的String
	 */
	public static String[] post(String ip, String port, String path, String str){
		return new HttpUtil("http://"+ip+":"+port+"/"+path, str).sendPost();
	}
	
	public static String[] get(String ip, String port, String path){
		return new HttpUtil("http://"+ip+":"+port+"/"+path, null).sendGet();
    }
	
	String[] sendGet(){
            String[] r=new String[0];
            
            //if(CommonUtil.isEmptyString(this.str) && CommonUtil.isEmptyString(this.url)){
            if(isEmptyString(this.url)){
                    log.debug("\n\nsend string is '"+str+"'\nurl is '"+url+"'");
            }else{
                    //HttpClient httpclient = new HttpClient();
                    HttpClient httpclient = Http.getClient();
                    GetMethod post = new GetMethod(url);
                    log.debug("sendurl="+url);
                    try {
                            post.setRequestHeader("Content-type", "text/xml; charset="+Constants.CHAR_ENCODING);
                            
                            
                            HttpClientParams params = new HttpClientParams();
                            
                            //params.setSoTimeout(3000);
                            params.setContentCharset(Constants.CHAR_ENCODING);
                            httpclient.setParams(params);
                            
                            int responsCode=httpclient.executeMethod(post);
                            byte[] b=post.getResponseBody();
                            String responsSstr = new String(b,Constants.CHAR_ENCODING);
                            
                            r=new String[2];
                            
                            r[0]=responsCode+"";
                            r[1]=responsSstr;
                            log.debug(responsSstr.replace("\r\n\r\n", ""));
                    } catch (Exception e) {
                            log.error("HTTP Connection Exception", e);
                    }finally{
                            try{
                                    post.releaseConnection();
                                    httpclient.getHttpConnectionManager().closeIdleConnections(0);
                                    
                                    post=null;
                                    httpclient=null;
                            }catch (Exception e)
                            {
                                    log.error("Close HTTP Connection Exception", e);
                            }
                            
                    }
            }
    
            return r;
    }
	
	public static void main(String[] args) {
	    String[] response = get("10.84.253.27","9005","/stbservlet?attribute=ewf_json_ote_get_product_offering&home_id=86&product_offering_id=88");
	    
	    System.out.println(response[0] + "-----" + response[1]  );
        }
}
