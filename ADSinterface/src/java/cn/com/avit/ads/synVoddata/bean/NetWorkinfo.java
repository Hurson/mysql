package cn.com.avit.ads.synVoddata.bean;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;


/**
 * NetWorkinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_release_area"
    ,catalog="ads"
)

public class NetWorkinfo  implements java.io.Serializable {


    // Fields    

     private Long id;
     private String areaCode;
     private String areaName;
     private String parentCode;
     private String locationtype;
     private String locationCode;
     private String ocsId;


    // Constructors

    /** default constructor */
    public NetWorkinfo() {
    }

	/** minimal constructor */
    public NetWorkinfo(String areaCode) {
        this.areaCode = areaCode;
    }
    
    /** full constructor */
    public NetWorkinfo(String areaCode, String areaName, String parentCode, String locationtype, String locationCode, String ocsId) {
        this.areaCode = areaCode;
        this.areaName = areaName;
        this.parentCode = parentCode;
        this.locationtype = locationtype;
        this.locationCode = locationCode;
        this.ocsId = ocsId;
    }

   
    // Property accessors
    @GenericGenerator(name="generator", strategy="increment")@Id @GeneratedValue(generator="generator")
    
    @Column(name="ID", unique=true, nullable=false)

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="AREA_CODE", nullable=false, length=12)

    public String getAreaCode() {
        return this.areaCode;
    }
    
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    
    @Column(name="AREA_NAME", length=200)

    public String getAreaName() {
        return this.areaName;
    }
    
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
    
    @Column(name="PARENT_CODE", length=12)

    public String getParentCode() {
        return this.parentCode;
    }
    
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
    
    @Column(name="LOCATIONTYPE", length=2)

    public String getLocationtype() {
        return this.locationtype;
    }
    
    public void setLocationtype(String locationtype) {
        this.locationtype = locationtype;
    }
    
    @Column(name="LOCATION_CODE", length=20)

    public String getLocationCode() {
        return this.locationCode;
    }
    
    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }
    
    @Column(name="OCS_ID", length=22)

    public String getOcsId() {
        return this.ocsId;
    }
    
    public void setOcsId(String ocsId) {
        this.ocsId = ocsId;
    }
   








}