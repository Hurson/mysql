package com.avit.ads.syncreport.bean;

public class ContentPush {
	//内容ID 可，分隔
	private String contentid;
	//终端接收次数
	private Long receiveCount;
	//到达次数 每天机顶盒次数 
	private Long reachCount;
	//有效次数  接收3次以上 终端数
	private Long effectiveCount;
	
	public ContentPush() {		
		super();
		receiveCount =0L;
		reachCount =0L;
		effectiveCount =0L;
		contentid ="";	
	}
	public String getContentid() {
		return contentid;
	}
	public void setContentid(String contentid) {
		this.contentid = contentid;
	}
	public Long getReceiveCount() {
		return receiveCount;
	}
	public void setReceiveCount(Long receiveCount) {
		this.receiveCount = receiveCount;
	}
	public Long getReachCount() {
		return reachCount;
	}
	public void setReachCount(Long reachCount) {
		this.reachCount = reachCount;
	}
	public Long getEffectiveCount() {
		return effectiveCount;
	}
	public void setEffectiveCount(Long effectiveCount) {
		this.effectiveCount = effectiveCount;
	}
	
	
}
