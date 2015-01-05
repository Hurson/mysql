package cn.com.avit.ads.synVoddata.json;

import java.util.List;
import java.util.Map;

/**
 * 产品对应的JSON对象
 * @author dell
 *
 */
public class UserTypeInfoJsonObject {


	private String result;
	private String desc ;
	private List<LocationObject> locations;
	private List<UserLevelObject> userLevels;
	private List<IndustryCategoryObject> industryCategorys;
	public List<LocationObject> getLocations() {
		return locations;
	}
	public void setLocations(List<LocationObject> locations) {
		this.locations = locations;
	}
	public List<UserLevelObject> getUserLevels() {
		return userLevels;
	}
	public void setUserLevels(List<UserLevelObject> userLevels) {
		this.userLevels = userLevels;
	}
	public List<IndustryCategoryObject> getIndustryCategorys() {
		return industryCategorys;
	}
	public void setIndustryCategorys(List<IndustryCategoryObject> industryCategorys) {
		this.industryCategorys = industryCategorys;
	}

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

}
