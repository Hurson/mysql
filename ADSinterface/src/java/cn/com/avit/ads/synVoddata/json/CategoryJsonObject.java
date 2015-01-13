package cn.com.avit.ads.synVoddata.json;

import java.util.List;

/**
 * 产品对应的JSON对象
 * @author dell
 *
 */
public class CategoryJsonObject {
	
	private String result;
	
	private String desc ;
	
	private List<Category> category;
		
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
	public List<Category> getCategory() {
		return category;
	}
	public void setCategory(List<Category> category) {
		this.category = category;
	}
	
}
