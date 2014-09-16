package com.avit.ads.pushads.task.bean;

import java.util.List;

public class StartMaterial {
	private String video;
	private String pic ;
	private List<ImageInfo> pics;
	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public List<ImageInfo> getPics() {
		return pics;
	}

	public void setPics(List<ImageInfo> pics) {
		this.pics = pics;
	}
	
}
