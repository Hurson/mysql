package cn.com.avit.ads.synVoddata.json;

import java.util.List;
import java.util.Map;

/**
 * 产品对应的JSON对象
 * @author dell
 *
 */
public class ProductJsonObject {
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	private String result;
	private String desc ;
	private List<Product> products;	
	

}
