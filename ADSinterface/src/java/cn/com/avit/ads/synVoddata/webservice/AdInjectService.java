package cn.com.avit.ads.synVoddata.webservice;

public interface AdInjectService {
	public ResultJson sendAdInject(String assetCode, String fileAddress);
	public ResultJson getAdAssetInfo(String assetCode);

}
