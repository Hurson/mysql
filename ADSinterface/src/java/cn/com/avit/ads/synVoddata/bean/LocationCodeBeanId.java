package cn.com.avit.ads.synVoddata.bean;
// default package

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * LocationCodeBeanId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class LocationCodeBeanId  implements java.io.Serializable {


    // Fields    

     private Long locationcode;
     private String locationname;
     private Long parentlocation;
     private String locationtype;
     private String areacode;


    // Constructors

    /** default constructor */
    public LocationCodeBeanId() {
    }

    
    /** full constructor */
    public LocationCodeBeanId(Long locationcode, String locationname, Long parentlocation, String locationtype, String areacode) {
        this.locationcode = locationcode;
        this.locationname = locationname;
        this.parentlocation = parentlocation;
        this.locationtype = locationtype;
        this.areacode = areacode;
    }

   
    // Property accessors

    @Column(name="LOCATIONCODE", precision=12, scale=0)

    public Long getLocationcode() {
        return this.locationcode;
    }
    
    public void setLocationcode(Long locationcode) {
        this.locationcode = locationcode;
    }

    @Column(name="LOCATIONNAME", length=200)

    public String getLocationname() {
        return this.locationname;
    }
    
    public void setLocationname(String locationname) {
        this.locationname = locationname;
    }

    @Column(name="PARENTLOCATION", precision=12, scale=0)

    public Long getParentlocation() {
        return this.parentlocation;
    }
    
    public void setParentlocation(Long parentlocation) {
        this.parentlocation = parentlocation;
    }

    @Column(name="LOCATIONTYPE", length=2)

    public String getLocationtype() {
        return this.locationtype;
    }
    
    public void setLocationtype(String locationtype) {
        this.locationtype = locationtype;
    }

    @Column(name="areacode", length=20)

    public String getAreacode() {
        return this.areacode;
    }
    
    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof LocationCodeBeanId) ) return false;
		 LocationCodeBeanId castOther = ( LocationCodeBeanId ) other; 
         
		 return ( (this.getLocationcode()==castOther.getLocationcode()) || ( this.getLocationcode()!=null && castOther.getLocationcode()!=null && this.getLocationcode().equals(castOther.getLocationcode()) ) )
 && ( (this.getLocationname()==castOther.getLocationname()) || ( this.getLocationname()!=null && castOther.getLocationname()!=null && this.getLocationname().equals(castOther.getLocationname()) ) )
 && ( (this.getParentlocation()==castOther.getParentlocation()) || ( this.getParentlocation()!=null && castOther.getParentlocation()!=null && this.getParentlocation().equals(castOther.getParentlocation()) ) )
 && ( (this.getLocationtype()==castOther.getLocationtype()) || ( this.getLocationtype()!=null && castOther.getLocationtype()!=null && this.getLocationtype().equals(castOther.getLocationtype()) ) )
 && ( (this.getAreacode()==castOther.getAreacode()) || ( this.getAreacode()!=null && castOther.getAreacode()!=null && this.getAreacode().equals(castOther.getAreacode()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getLocationcode() == null ? 0 : this.getLocationcode().hashCode() );
         result = 37 * result + ( getLocationname() == null ? 0 : this.getLocationname().hashCode() );
         result = 37 * result + ( getParentlocation() == null ? 0 : this.getParentlocation().hashCode() );
         result = 37 * result + ( getLocationtype() == null ? 0 : this.getLocationtype().hashCode() );
         result = 37 * result + ( getAreacode() == null ? 0 : this.getAreacode().hashCode() );
         return result;
   }   





}