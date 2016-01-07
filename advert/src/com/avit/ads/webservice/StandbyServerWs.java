package com.avit.ads.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(targetNamespace = "http://webservice.ads.avit.com/", name = "StandbyService")
public interface StandbyServerWs {
	@WebMethod(operationName="sendStandByToUnt")
	public abstract void sendStandByToUnt();
	
	@WebMethod(operationName="clearUnt")
	public void clearUnt(String id);

}
