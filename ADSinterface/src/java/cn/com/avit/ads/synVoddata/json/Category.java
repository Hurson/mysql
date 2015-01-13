package cn.com.avit.ads.synVoddata.json;

import java.util.List;

public class Category {

private String folderId;
	
	private String folderName;
	
	private String folderType;
	
	private List<Category> childFolder;

	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getFolderType() {
		return folderType;
	}

	public void setFolderType(String folderType) {
		this.folderType = folderType;
	}

	public List<Category> getChildFolder() {
		return childFolder;
	}

	public void setChildFolder(List<Category> childFolder) {
		this.childFolder = childFolder;
	}
	
	
}
