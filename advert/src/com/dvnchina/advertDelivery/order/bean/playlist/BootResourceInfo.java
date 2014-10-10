package com.dvnchina.advertDelivery.order.bean.playlist;

import java.util.List;

public class BootResourceInfo {
	
	private String video;
	private String pic;
	
	private List<BootImageInfo> pics;

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public List<BootImageInfo> getPics() {
		return pics;
	}

	public void setPics(List<BootImageInfo> pics) {
		this.pics = pics;
	}


}
