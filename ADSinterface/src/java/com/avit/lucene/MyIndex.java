package com.avit.lucene;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

import com.avit.ads.syncreport.bean.LuceneBean;

public class MyIndex {
	
	private Logger logger = Logger.getLogger(MyIndex.class);
	
	// 索引存放路径
	private String indexPath;

	private Directory indexDir;

	private IndexWriter indexWriter;


	public MyIndex(String indexPath) {
		this.indexPath = indexPath;
		try {
			indexDir = new SimpleFSDirectory(new File(this.indexPath));
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_44, new StandardAnalyzer(Version.LUCENE_44));
			indexWriter = new IndexWriter(indexDir, iwc);
		} catch (IOException e) {
			logger.error("open index folder error");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}	

	public void linuxRun(String cmd, String outpath) {
		try {
			String dir = outpath;
			File workdir = new File(dir);
			Process pro = Runtime.getRuntime().exec(cmd, null, workdir);
			pro.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	//LuceneBean属性太多，很多都是不必要的，建索引影响性能, 直接使用addLuceneBeanToIndex方法
	public <T> void addBeanToIndex(T t){
		Method[] methods = t.getClass().getMethods();
		Document doc = new Document();
		try{
			for(Method method : methods){
				String methodName = method.getName();
				if(methodName.startsWith("get") && !methodName.equals("getClass")){
					String key = method.getName().substring(3).toLowerCase();
					String value = (String)method.invoke(t);  //全部是String类型
					if(null == value){
						value = "";
					}
					Field field = new StringField(key, value, Field.Store.YES);
					doc.add(field);
				}
			}
			indexWriter.addDocument(doc);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} 
	}
	
	public void addLuceneBeanToIndex(LuceneBean entity){
		Document doc = new Document();
		Field field1 = new StringField("tvn", entity.getTvn(), Field.Store.YES);
		Field field2 = new StringField("position_code", entity.getPosition_code(), Field.Store.YES);
		Field field3 = new StringField("contentid", entity.getContentid(), Field.Store.YES);
		Field field4 = new StringField("hourStr", entity.getHourStr(), Field.Store.YES);
		doc.add(field1);
		doc.add(field2);
		doc.add(field3);
		doc.add(field4);
		try {
			indexWriter.addDocument(doc);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}


	public String getIndexPath() {
		return indexPath;
	}

	public Directory getIndexDir() {
		return indexDir;
	}
	
	public IndexWriter getIndexWriter() {
		return indexWriter;
	}
	
}
