package cn.com.avit.ads.synVoddata.bean;
// default package

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * LocationCodeBean entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_location_code_temp"
    ,catalog="ads"
)

public class LocationCodeBean  implements java.io.Serializable {


    // Fields    

     private LocationCodeBeanId id;


    // Constructors

    /** default constructor */
    public LocationCodeBean() {
    }

    
    /** full constructor */
    public LocationCodeBean(LocationCodeBeanId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="locationcode", column=@Column(name="LOCATIONCODE", precision=12, scale=0) ), 
        @AttributeOverride(name="locationname", column=@Column(name="LOCATIONNAME", length=200) ), 
        @AttributeOverride(name="parentlocation", column=@Column(name="PARENTLOCATION", precision=12, scale=0) ), 
        @AttributeOverride(name="locationtype", column=@Column(name="LOCATIONTYPE", length=2) ), 
        @AttributeOverride(name="areacode", column=@Column(name="AREACODE", length=20) ) } )

    public LocationCodeBeanId getId() {
        return this.id;
    }
    
    public void setId(LocationCodeBeanId id) {
        this.id = id;
    }
   








}