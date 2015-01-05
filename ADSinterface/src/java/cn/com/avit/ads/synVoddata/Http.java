package cn.com.avit.ads.synVoddata;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

public class Http {
	private static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
	private static HttpClient client = new HttpClient(connectionManager);
	private static Http http = new Http();
	static synchronized HttpClient getClient() {
		return client;
	}
	private Http() {
		configureClient();
	}
	public static Http getInstance() {
		return http;
	}
	private void configureClient() {
		int maxThreadsTotal = 100;
		int maxThreadsPerHost = 10;
		HttpConnectionManagerParams params = connectionManager.getParams();
		params.setConnectionTimeout(2000);
		params.setSoTimeout(10000);
		params.setMaxTotalConnections(maxThreadsTotal);
		if (maxThreadsTotal > maxThreadsPerHost) {
			params.setDefaultMaxConnectionsPerHost(maxThreadsPerHost);
		} else {
			params.setDefaultMaxConnectionsPerHost(maxThreadsTotal);
		}
	}	
}
