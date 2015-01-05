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
 * Productinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_productinfo_temp"
    ,catalog="ads"
)

public class Productinfo  implements java.io.Serializable {


    // Fields    

     private Long id;
     private String productId;
     private String productName;
     private String productDesc;
     private String price;
     private String billingModelName;
     private String billingModelId;
     private String billingModelType;
     private String spId;
     private String isPackage;
     private String posterUrl;
     private String type;
     private String bizId;
     private String bizDesc;
     private Timestamp createTime;
     private Timestamp modifyTime;
     private String state;
     private String netWorkID;


    // Constructors

    /** default constructor */
    public Productinfo() {
    }

    
    /** full constructor */
    public Productinfo(String productId, String productName, String productDesc, String price, String billingModelName, String billingModelId, String billingModelType, String spId, String isPackage, String posterUrl, String type, String bizId, String bizDesc, Timestamp createTime, Timestamp modifyTime, String state,String networkID) {
        this.productId = productId;
        this.productName = productName;
        this.productDesc = productDesc;
        this.price = price;
        this.billingModelName = billingModelName;
        this.billingModelId = billingModelId;
        this.billingModelType = billingModelType;
        this.spId = spId;
        this.isPackage = isPackage;
        this.posterUrl = posterUrl;
        this.type = type;
        this.bizId = bizId;
        this.bizDesc = bizDesc;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
        this.state = state;
    }

    @Column(name="NETWORKID", length=20)
    public String getNetWorkID() {
		return netWorkID;
	}


	public void setNetWorkID(String netWorkID) {
		this.netWorkID = netWorkID;
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
    
    @Column(name="PRODUCT_ID", length=254)

    public String getProductId() {
        return this.productId;
    }
    
    public void setProductId(String productId) {
        this.productId = productId;
    }
    
    @Column(name="PRODUCT_NAME", length=254)

    public String getProductName() {
        return this.productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    @Column(name="PRODUCT_DESC", length=254)

    public String getProductDesc() {
        return this.productDesc;
    }
    
    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }
    
    @Column(name="PRICE", length=254)

    public String getPrice() {
        return this.price;
    }
    
    public void setPrice(String price) {
        this.price = price;
    }
    
    @Column(name="BILLING_MODEL_NAME", length=254)

    public String getBillingModelName() {
        return this.billingModelName;
    }
    
    public void setBillingModelName(String billingModelName) {
        this.billingModelName = billingModelName;
    }
    
    @Column(name="BILLING_MODEL_ID", length=100)

    public String getBillingModelId() {
        return this.billingModelId;
    }
    
    public void setBillingModelId(String billingModelId) {
        this.billingModelId = billingModelId;
    }
    
    @Column(name="BILLING_MODEL_TYPE", length=100)

    public String getBillingModelType() {
        return this.billingModelType;
    }
    
    public void setBillingModelType(String billingModelType) {
        this.billingModelType = billingModelType;
    }
    
    @Column(name="SP_ID", length=100)

    public String getSpId() {
        return this.spId;
    }
    
    public void setSpId(String spId) {
        this.spId = spId;
    }
    
    @Column(name="IS_PACKAGE", length=1)

    public String getIsPackage() {
        return this.isPackage;
    }
    
    public void setIsPackage(String isPackage) {
        this.isPackage = isPackage;
    }
    
    @Column(name="POSTER_URL", length=254)

    public String getPosterUrl() {
        return this.posterUrl;
    }
    
    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
    
    @Column(name="TYPE", length=20)

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    @Column(name="BIZ_ID", length=254)

    public String getBizId() {
        return this.bizId;
    }
    
    public void setBizId(String bizId) {
        this.bizId = bizId;
    }
    
    @Column(name="BIZ_DESC", length=254)

    public String getBizDesc() {
        return this.bizDesc;
    }
    
    public void setBizDesc(String bizDesc) {
        this.bizDesc = bizDesc;
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
   








}