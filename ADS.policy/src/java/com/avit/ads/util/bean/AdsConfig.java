package com.avit.ads.util.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="AdsConfig")
public class AdsConfig {
	@XmlAttribute(name="preSecond")
	private int preSecond;
	//资源服务器配置 
	
	@XmlElement(name="AdResource")
	private AdResource adResource;
	//单向实时广告配置 导航条广告、 音量条广告、3  频道列表广告  、菜单图片广告 6张图片、菜单广告     频道收藏列表广告、 音频广告、 菜单外框、 预告提示
	@XmlElement(name="RealTimeAds")
	private RealTimeAds realTimeAds;
	//单向非实时广告配置  开机广告
	@XmlElement(name="UnRealTimeAds")
	private UnRealTimeAds unRealTimeAds;
	//单向非实时广告配置   回看菜单  点播菜单 点播随片
	@XmlElement(name="CpsAds")
	private CpsAds cpsAds;
	//单向非实时广告配置    回放菜单，
	@XmlElement(name="NpvrAds")
	private NpvrAds npvrAds;
	//NPVR FTP服务器配置
	@XmlElement(name="Npvr")
	private Npvr    npvr;
	
	//cps FTP服务器配置
	@XmlElement(name="Cps")
	private Cps cps;
	//ocs FTP服务器配置
	@XmlElement(name="Ocg")
	private List<Ocg> ocgList;
	@XmlElement(name="HttpServer")
	private HttpServer httpServer;
	@XmlElement(name="VideoPump")
	private VideoPump videoPump;
	
	public Cps getCps() {
		return cps;
	}
	public void setCps(Cps cps) {
		this.cps = cps;
	}
	public List<Ocg> getOcgList() {
		return ocgList;
	}
	public void setOcgList(List<Ocg> ocgList) {
		this.ocgList = ocgList;
	}
	public HttpServer getHttpServer() {
		return httpServer;
	}
	public void setHttpServer(HttpServer httpServer) {
		this.httpServer = httpServer;
	}
	public VideoPump getVideoPump() {
		return videoPump;
	}
	public void setVideoPump(VideoPump videoPump) {
		this.videoPump = videoPump;
	}
	public int getPreSecond() {
		return preSecond;
	}
	public void setPreSecond(int preSecond) {
		this.preSecond = preSecond;
	}
	public AdResource getAdResource() {
		return adResource;
	}
	public void setAdResource(AdResource adResource) {
		this.adResource = adResource;
	}
	public RealTimeAds getRealTimeAds() {
		return realTimeAds;
	}
	public void setRealTimeAds(RealTimeAds realTimeAds) {
		this.realTimeAds = realTimeAds;
	}
	public UnRealTimeAds getUnRealTimeAds() {
		return unRealTimeAds;
	}
	public void setUnRealTimeAds(UnRealTimeAds unRealTimeAds) {
		this.unRealTimeAds = unRealTimeAds;
	}
	public CpsAds getCpsAds() {
		return cpsAds;
	}
	public void setCpsAds(CpsAds cpsAds) {
		this.cpsAds = cpsAds;
	}
	public NpvrAds getNpvrAds() {
		return npvrAds;
	}
	public void setNpvrAds(NpvrAds npvrAds) {
		this.npvrAds = npvrAds;
	}
	public Npvr getNpvr() {
		return npvr;
	}
	public void setNpvr(Npvr npvr) {
		this.npvr = npvr;
	}

	
}
