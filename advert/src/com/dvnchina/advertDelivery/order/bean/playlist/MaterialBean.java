package com.dvnchina.advertDelivery.order.bean.playlist;

import com.dvnchina.advertDelivery.ploy.bean.TPreciseMatch;


public class MaterialBean {
	private Integer id;
	private int type;
	private String path;
//	private String content;
//	private String url;
	private int loopNo;
	private String playLocation;
	private String fileSize;
	private TPreciseMatch precise;
	private TextMate text;
	private int preciseType;//0对应精准，1对应策略
	private String startTime;
	private String endTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

//	public String getContent() {
//		return content;
//	}
//
//	public void setContent(Blob content) {
//		InputStream in;
//		StringBuffer buf = new StringBuffer();
//		try {
//			in = content.getBinaryStream();
//			byte[] buff = new byte[(int) content.length()];
//			while (in.read(buff) > 0) {
//				buf.append(new String(buff, "gbk"));
//			}
//			this.content = buf.toString();
//			in.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	public String getUrl() {
//		return url;
//	}
//
//	public void setUrl(String url) {
//		this.url = url;
//	}

	public int getLoopNo() {
		return loopNo;
	}

	public void setLoopNo(int loopNo) {
		this.loopNo = loopNo;
	}

	public String getPlayLocation() {
		return playLocation;
	}

	public void setPlayLocation(String playLocation) {
		this.playLocation = playLocation;
	}

	public TPreciseMatch getPrecise() {
		return precise;
	}

	public void setPrecise(TPreciseMatch precise) {
		this.precise = precise;
	}

	public TextMate getText() {
		return text;
	}

	public void setText(TextMate text) {
		this.text = text;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public int getPreciseType() {
		return preciseType;
	}

	public void setPreciseType(int preciseType) {
		this.preciseType = preciseType;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
