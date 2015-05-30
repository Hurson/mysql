package cn.com.avit.ads.synepgdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_channelinfo_sync")
public class ChannelInfoSync {
	/** 主键 */
	private Long channelId;
	
	/** 频道名称 */
	private String channelName;
	
	/** serviceID */
	private String serviceId;
	
	/** 传输流ID */
	private Long tsId;
	
	/** 频道类型 */
	private String channleType;
	
	/**网络Id*/
	private String networkId;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CHANNEL_ID", unique = true, nullable = false)
	public Long getChannelId() {
		return channelId;
	}
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	
	@Column(name = "CHANNEL_NAME")
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	@Column(name = "SERVICE_ID")
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	
	@Column(name = "TS_ID")
	public Long getTsId() {
		return tsId;
	}
	public void setTsId(Long tsId) {
		this.tsId = tsId;
	}	
	@Column(name="NETWORK_ID")
	public String getNetworkId() {
		return networkId;
	}
	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}
	@Column(name = "CHANNEL_TYPE")
	public String getChannleType() {
		return channleType;
	}
	public void setChannleType(String channleType) {
		this.channleType = channleType;
	}	
	
}
