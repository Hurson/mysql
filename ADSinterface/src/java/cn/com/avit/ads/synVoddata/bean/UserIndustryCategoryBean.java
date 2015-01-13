package cn.com.avit.ads.synVoddata.bean;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;


/**
 * UserIndustryCategoryBean entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_user_industry_category_temp"
    ,catalog="ads"
)

public class UserIndustryCategoryBean  implements java.io.Serializable {


    // Fields    

     private Long id;
     private String userIndustryCategoryCode;
     private String userIndustryCategoryValue;
     private String areacode;


    // Constructors

    /** default constructor */
    public UserIndustryCategoryBean() {
    }

    
    /** full constructor */
    public UserIndustryCategoryBean(String userIndustryCategoryCode, String userIndustryCategoryValue, String areacode) {
        this.userIndustryCategoryCode = userIndustryCategoryCode;
        this.userIndustryCategoryValue = userIndustryCategoryValue;
        this.areacode = areacode;
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
    
    @Column(name="USER_INDUSTRY_CATEGORY_CODE", length=5)

    public String getUserIndustryCategoryCode() {
        return this.userIndustryCategoryCode;
    }
    
    public void setUserIndustryCategoryCode(String userIndustryCategoryCode) {
        this.userIndustryCategoryCode = userIndustryCategoryCode;
    }
    
    @Column(name="USER_INDUSTRY_CATEGORY_VALUE", length=50)

    public String getUserIndustryCategoryValue() {
        return this.userIndustryCategoryValue;
    }
    
    public void setUserIndustryCategoryValue(String userIndustryCategoryValue) {
        this.userIndustryCategoryValue = userIndustryCategoryValue;
    }
    
    @Column(name="AREACODE", length=20)

    public String getAreacode() {
        return this.areacode;
    }
    
    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }
   








}