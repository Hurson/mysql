package cn.com.avit.ads.synepgdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_multicast_info")
public class MulticastInfo {
	/** 主键 */
	private Long id;
	
	/** 频道名称 */
	private String areaCode;
	
	/** 传输流ID */
	private Long tsId;
	
	/** 组播地址 */
	private String multicastIp;
	
	/**	组播端口 */
	private String multicastPort;
	
	/** 码率*/
	private String multicastBitrate;
	
	/** 流Id*/
	private int streamId;
	
	private String flag;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "AREA_CODE")
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
	@Column(name = "TS_ID")
	public Long getTsId() {
		return tsId;
	}
	public void setTsId(Long tsId) {
		this.tsId = tsId;
	}
	@Column(name = "MULTICAST_IP")
	public String getMulticastIp() {
		return multicastIp;
	}
	public void setMulticastIp(String multicastIp) {
		this.multicastIp = multicastIp;
	}
	@Column(name = "MULTICAST_PORT")
	public String getMulticastPort() {
		return multicastPort;
	}
	public void setMulticastPort(String multicastPort) {
		this.multicastPort = multicastPort;
	}
	@Column(name = "MULTICAST_BITRATE")
	public String getMulticastBitrate() {
		return multicastBitrate;
	}
	public void setMulticastBitrate(String multicastBitrate) {
		this.multicastBitrate = multicastBitrate;
	}
	@Column(name = "STREAM_ID")
	public int getStreamId() {
		return streamId;
	}
	public void setStreamId(int streamId) {
		this.streamId = streamId;
	}
	@Column(name = "FLAG")
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}	
	
}
