package cn.com.avit.ads.synVoddata.bean;
// default package

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;


/**
 * ChannelInfoNPVR entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_channelinfo_npvr_temp"
    ,catalog="ads"
)

public class ChannelInfoNPVR  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String channelId;
     private String channelCode;
     private String channelType;
     private String channelName;
     private String serviceId;
     private String channelLanguage;
     private String channelLogo;
     private String keyword;
     private String locationCode;
     private String locationName;
     private Timestamp createTime;
     private Timestamp modifyTime;
     private String state;
     private Long tsId;
     private Long networkId;
     private String channelDesc;
     private Boolean isPlayback;
     private String summaryshort;


    // Constructors

    /** default constructor */
    public ChannelInfoNPVR() {
    }

	/** minimal constructor */
    public ChannelInfoNPVR(String channelId, String channelName) {
        this.channelId = channelId;
        this.channelName = channelName;
    }
    
    /** full constructor */
    public ChannelInfoNPVR(String channelId, String channelCode, String channelType, String channelName, String serviceId, String channelLanguage, String channelLogo, String keyword, String locationCode, String locationName, Timestamp createTime, Timestamp modifyTime, String state, Long tsId, Long networkId, String channelDesc, Boolean isPlayback, String summaryshort) {
        this.channelId = channelId;
        this.channelCode = channelCode;
        this.channelType = channelType;
        this.channelName = channelName;
        this.serviceId = serviceId;
        this.channelLanguage = channelLanguage;
        this.channelLogo = channelLogo;
        this.keyword = keyword;
        this.locationCode = locationCode;
        this.locationName = locationName;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
        this.state = state;
        this.tsId = tsId;
        this.networkId = networkId;
        this.channelDesc = channelDesc;
        this.isPlayback = isPlayback;
        this.summaryshort = summaryshort;
    }

   
    // Property accessors
    @GenericGenerator(name="generator", strategy="increment")@Id @GeneratedValue(generator="generator")
    
    @Column(name="ID", unique=true, nullable=false)

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    @Column(name="CHANNEL_ID", nullable=false, length=20)

    public String getChannelId() {
        return this.channelId;
    }
    
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
    
    @Column(name="CHANNEL_CODE", length=20)

    public String getChannelCode() {
        return this.channelCode;
    }
    
    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }
    
    @Column(name="CHANNEL_TYPE", length=200)

    public String getChannelType() {
        return this.channelType;
    }
    
    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }
    
    @Column(name="CHANNEL_NAME", nullable=false, length=20)

    public String getChannelName() {
        return this.channelName;
    }
    
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
    
    @Column(name="SERVICE_ID", length=20)

    public String getServiceId() {
        return this.serviceId;
    }
    
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
    
    @Column(name="CHANNEL_LANGUAGE", length=20)

    public String getChannelLanguage() {
        return this.channelLanguage;
    }
    
    public void setChannelLanguage(String channelLanguage) {
        this.channelLanguage = channelLanguage;
    }
    
    @Column(name="CHANNEL_LOGO", length=200)

    public String getChannelLogo() {
        return this.channelLogo;
    }
    
    public void setChannelLogo(String channelLogo) {
        this.channelLogo = channelLogo;
    }
    
    @Column(name="KEYWORD", length=254)

    public String getKeyword() {
        return this.keyword;
    }
    
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    
    @Column(name="LOCATION_CODE", length=256)

    public String getLocationCode() {
        return this.locationCode;
    }
    
    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }
    
    @Column(name="LOCATION_NAME", length=20)

    public String getLocationName() {
        return this.locationName;
    }
    
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
    
    @Column(name="CREATE_TIME", length=19)

    public Timestamp getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
    
    @Column(name="MODIFY_TIME", length=19)

    public Timestamp getModifyTime() {
        return this.modifyTime;
    }
    
    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }
    
    @Column(name="STATE", length=1)

    public String getState() {
        return this.state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    @Column(name="TS_ID", precision=10, scale=0)

    public Long getTsId() {
        return this.tsId;
    }
    
    public void setTsId(Long tsId) {
        this.tsId = tsId;
    }
    
    @Column(name="NETWORK_ID", precision=10, scale=0)

    public Long getNetworkId() {
        return this.networkId;
    }
    
    public void setNetworkId(Long networkId) {
        this.networkId = networkId;
    }
    
    @Column(name="CHANNEL_DESC", length=254)

    public String getChannelDesc() {
        return this.channelDesc;
    }
    
    public void setChannelDesc(String channelDesc) {
        this.channelDesc = channelDesc;
    }
    
    @Column(name="IS_PLAYBACK", precision=1, scale=0)

    public Boolean getIsPlayback() {
        return this.isPlayback;
    }
    
    public void setIsPlayback(Boolean isPlayback) {
        this.isPlayback = isPlayback;
    }
    
    @Column(name="SUMMARYSHORT", length=254)

    public String getSummaryshort() {
        return this.summaryshort;
    }
    
    public void setSummaryshort(String summaryshort) {
        this.summaryshort = summaryshort;
    }
   








}