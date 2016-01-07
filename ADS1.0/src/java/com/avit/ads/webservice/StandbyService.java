package com.avit.ads.webservice;

import javax.jws.WebService;

@WebService
public interface StandbyService {
	public abstract void sendStandByToUnt();
	
	public void clearUnt(String id);

}
