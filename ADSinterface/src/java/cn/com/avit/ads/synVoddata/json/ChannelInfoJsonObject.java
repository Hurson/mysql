package cn.com.avit.ads.synVoddata.json;

import java.util.List;

/**
 * 频道对应的JSON对象
 * @author dell
 *
 */
public class ChannelInfoJsonObject {

	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public List<ChannelInfo> getChannels() {
		return channels;
	}
	public void setChannels(List<ChannelInfo> channels) {
		this.channels = channels;
	}
	private String result;
	private String desc ;
	private List<ChannelInfo> channels;	
	

}
