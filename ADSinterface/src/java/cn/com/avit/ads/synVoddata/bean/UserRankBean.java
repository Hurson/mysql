package cn.com.avit.ads.synVoddata.bean;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;


/**
 * UserRankBean entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_user_rank_temp"
    ,catalog="ads"
)

public class UserRankBean  implements java.io.Serializable {


    // Fields    

     private Long id;
     private String userRankCode;
     private String userRankName;
     private String description;
     private String areacode;


    // Constructors

    /** default constructor */
    public UserRankBean() {
    }

    
    /** full constructor */
    public UserRankBean(String userRankCode, String userRankName, String description, String areacode) {
        this.userRankCode = userRankCode;
        this.userRankName = userRankName;
        this.description = description;
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
    
    @Column(name="USER_RANK_CODE", length=20)

    public String getUserRankCode() {
        return this.userRankCode;
    }
    
    public void setUserRankCode(String userRankCode) {
        this.userRankCode = userRankCode;
    }
    
    @Column(name="USER_RANK_NAME", length=50)

    public String getUserRankName() {
        return this.userRankName;
    }
    
    public void setUserRankName(String userRankName) {
        this.userRankName = userRankName;
    }
    
    @Column(name="DESCRIPTION")

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Column(name="areacode", length=20)

    public String getAreacode() {
        return this.areacode;
    }
    
    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }
   








}