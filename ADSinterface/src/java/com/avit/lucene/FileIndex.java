package com.avit.lucene;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.LogByteSizeMergePolicy;
import org.apache.lucene.index.LogMergePolicy;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class FileIndex {
	private Logger logger = Logger.getLogger(FileIndex.class);
	// 临时索引目录
	private String indexPath;
	// 载入类
	@SuppressWarnings("rawtypes")
	private Map classMap;
	// 内存搜索空间
	private Directory dir;
	private RAMDirectory dircach;
	private IndexWriter cachwriter;
	private Directory tmp;// 临时存储
	private static final ReentrantLock lock = new ReentrantLock();

	public FileIndex(String indexPath) {
		this.indexPath = indexPath;

		if (dir == null)
			dir = new RAMDirectory();
		if (classMap == null)
			classMap = new HashMap<String, String>();
		try {
			tmp = FSDirectory.open(new File(indexPath));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public FileIndex(String indexPath, Class clazz) {
		logger.info("FileIndex init start");

		this.indexPath = indexPath;
		// dir =new RAMDirectory();
		// 只初始化一次
		// if(dircach==null)
		// dircach =new RAMDirectory();
		if (classMap == null) {
			classMap = new HashMap<String, String>();
		}
		try {
			// linuxRun("rm -f *",indexPath);
			classMap.put(clazz.getName(), clazz.getName());
			logger.info("start FSDirectory.open" + indexPath);
			// 只初始化一次
			if (dir != null) {
				dir.close();
			}
			dir = FSDirectory.open(new File(indexPath));
			String[] aaa = dir.listAll();
			for (int i = 0; i < aaa.length; i++) {
				// System.out.println(aaa[i]);
				dir.deleteFile(aaa[i]);
			}
			logger.info("end FSDirectory.open" + indexPath);

			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_44);
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_44,analyzer);
			iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
			IndexWriter writer = new IndexWriter(dir, iwc);
			// writer.deleteAll();
			writer.deleteDocuments(new Term("className", clazz.getName()));
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("FileIndex init end");

	}

	public void clearData(String indexPath, Class clazz) {
		this.indexPath = indexPath;
		// dir =new RAMDirectory();
		// 只初始化一次
		// if(dircach==null)
		try {
			// linuxRun("rm -f *",indexPath);
			logger.info("start FSDirectory.delete" + indexPath);
			// 只初始化一次
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_44);
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_44,
					analyzer);
			IndexWriter writer = new IndexWriter(dir, iwc);
			// writer.deleteAll();
			writer.deleteDocuments(new Term("className", clazz.getName()));
			writer.commit();
			writer.close();
			String[] aaa = dir.listAll();
			for (int i = 0; i < aaa.length; i++) {
				System.out.println(aaa[i]);
				// dir.deleteFile(aaa[i]);
			}
			logger.info("end FSDirectory.delete" + indexPath);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("FileIndex init end");
	}

	public String getIndexPath() {
		return indexPath;
	}

	// 创建索引
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void createIndex(List<Object> list, Class clazz) throws IOException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		if (list == null || list.size() < 1) {
			return;
		}
		classMap.put(clazz.getName(), clazz.getName());
		IndexWriter writer = createWriter();
		addIndex(writer, list, clazz);
		writer.close();
	}

	@SuppressWarnings("rawtypes")
	private static void addIndex(IndexWriter writer, List<Object> list,
			Class clazz) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, IOException {
		for (Object obj : list) {
			Document doc = new Document();
			Method m[] = clazz.getMethods();

			for (Method method : m) {
				if (method.getName().startsWith("get")
						&& !method.getName().equals("getClass")) {
					String value = method.invoke(obj, null).toString();
					String key = method.getName().substring(3).toLowerCase();
					int intValue = -1;
					Field field = null;
					// 载入方案只处理了int和字符,需根据VO类需求增加
					try {
						if (method.getReturnType().toString().equals("int"))
							intValue = Integer.parseInt(value);
					} catch (Exception e) {

					}
					if (intValue != -1) {
						field = new IntField(key, intValue, Field.Store.YES);
					} else
						field = new TextField(key, value, Field.Store.YES);
					doc.add(field);
				}
			}
			doc.add(new StringField("className", clazz.getName(), Field.Store.YES));
			writer.addDocument(doc);
		}
	}

	// 获取索引对象
	public Directory getDir() {
		return dir;
	}

	// 覆盖正式索引
	@SuppressWarnings("rawtypes")
	public void updateIndex(Class clazz) throws IOException {
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_44);
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_44,
				analyzer);
		IndexWriter writer = new IndexWriter(dir, iwc);
		writer.deleteDocuments(new Term("className", clazz.getName()));
		writer.addIndexes(tmp);
		writer.close();
	}

	// 覆盖正式索引
	@SuppressWarnings("rawtypes")
	public void addDirIndex(RAMDirectory ramdir) throws IOException {
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_44);
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_44,
				analyzer);
		iwc.setMaxBufferedDocs(10000);
		LogMergePolicy mergePolicy = new LogByteSizeMergePolicy();
		// //设置segment添加文档(Document)时的合并频率 MergeFactor的默认值是10，
		// //值较小,建立索引的速度就较慢
		// //值较大,建立索引的速度就较快,>10适合批量建立索引
		mergePolicy.setMergeFactor(1000);
		// //设置segment最大合并文档(Document)数默认Integer.MAX_VALUE，
		// //值较小有利于追加索引的速度
		// //值较大,适合批量建立索引和更快的搜索
		mergePolicy.setMaxMergeDocs(500000);
		// 启用复合式索引文件格式,合并多个segment
		// mergePolicy.setUseCompoundFile(true);
		iwc.setMergePolicy(mergePolicy);
		lock.lock();
		IndexWriter writer = new IndexWriter(dir, iwc);
		writer.addIndexes(ramdir);
		writer.close();
		lock.unlock();
	}

	private IndexWriter createWriter() throws IOException {
		// Analyzer analyzer = new ClassicAnalyzer(Version.LUCENE_44);
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_44);
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_44,
				analyzer);
		iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
		IndexWriter writer = new IndexWriter(tmp, iwc);

		return writer;
	}

	// 创建索引
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void createCacheIndex(List<Object> list, Class clazz)
			throws IOException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		if (list == null || list.size() < 1) {
			return;
		}
		classMap.put(clazz.getName(), clazz.getName());
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_44);
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_44,
				analyzer);
		// //最大缓存文档数
		// SetMaxBufferedDocs是控制写入一个新的segment前内存中保存的document的数目，设置较大的数目可以加快建索引速度，默认为10。
		iwc.setMaxBufferedDocs(10000);
		LogMergePolicy mergePolicy = new LogByteSizeMergePolicy();
		// //设置segment添加文档(Document)时的合并频率 MergeFactor的默认值是10，
		// //值较小,建立索引的速度就较慢
		// //值较大,建立索引的速度就较快,>10适合批量建立索引
		mergePolicy.setMergeFactor(1000);
		// //设置segment最大合并文档(Document)数默认Integer.MAX_VALUE，
		// //值较小有利于追加索引的速度
		// //值较大,适合批量建立索引和更快的搜索
		// mergePolicy.setMaxMergeDocs(500000);
		// 启用复合式索引文件格式,合并多个segment
		// mergePolicy.setUseCompoundFile(true);
		iwc.setMergePolicy(mergePolicy);

		// System.out.println("dir.listAll().length:"+dir.sizeInBytes());
		// System.out.println("writer.numDocs:"+writer.numDocs());

		if (cachwriter.numDocs() > 80000) {
			Analyzer fanalyzer = new StandardAnalyzer(Version.LUCENE_44);
			IndexWriterConfig fiwc = new IndexWriterConfig(Version.LUCENE_44,
					fanalyzer);
			fiwc.setMergePolicy(mergePolicy);
			lock.lock();
			IndexWriter fswriter = new IndexWriter(dir, fiwc);
			fswriter.addIndexes(dircach);
			fswriter.close();
			lock.unlock();
			cachwriter.close();
			dircach = new RAMDirectory();
			cachwriter = new IndexWriter(dircach, iwc);
		}
		addCacheIndex(cachwriter, list, clazz);
		cachwriter.close();
	}

	// 创建索引
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void createCacheIndex(List<Object> list, Class clazz,
			IndexWriter writer) throws IOException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		if (list == null || list.size() < 1) {
			return;
		}
		lock.lock();
		classMap.put(clazz.getName(), clazz.getName());
		lock.unlock();
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_44);
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_44,
				analyzer);
		// //最大缓存文档数
		// SetMaxBufferedDocs是控制写入一个新的segment前内存中保存的document的数目，设置较大的数目可以加快建索引速度，默认为10。
		// iwc.setMaxBufferedDocs(10000);
		// LogMergePolicy mergePolicy = new LogByteSizeMergePolicy();
		// // //设置segment添加文档(Document)时的合并频率 MergeFactor的默认值是10，
		// // //值较小,建立索引的速度就较慢
		// // //值较大,建立索引的速度就较快,>10适合批量建立索引
		// mergePolicy.setMergeFactor(1000);
		// // //设置segment最大合并文档(Document)数默认Integer.MAX_VALUE，
		// // //值较小有利于追加索引的速度
		// // //值较大,适合批量建立索引和更快的搜索
		// mergePolicy.setMaxMergeDocs(500000);
		// //启用复合式索引文件格式,合并多个segment
		// //mergePolicy.setUseCompoundFile(true);
		// iwc.setMergePolicy(mergePolicy);

		// IndexWriter writer = new IndexWriter(ramdir,iwc);
		// System.out.println("dir.listAll().length:"+dir.sizeInBytes());
		// System.out.println("writer.numDocs:"+writer.numDocs());

		// if (cachwriter.numDocs()>80000)
		// {
		// Analyzer fanalyzer = new StandardAnalyzer(Version.LUCENE_44);
		// IndexWriterConfig fiwc = new IndexWriterConfig(Version.LUCENE_44,
		// fanalyzer);
		// fiwc.setMergePolicy(mergePolicy);
		// IndexWriter fswriter = new IndexWriter(dir,fiwc);
		// fswriter.addIndexes(dircach);
		// fswriter.close();
		// cachwriter.isLocked(dircach);
		// cachwriter.close();
		// dircach=new RAMDirectory();
		// cachwriter = new IndexWriter(dircach,iwc);
		// }
		addCacheIndex(writer, list, clazz);
		// writer.close();
	}

	@SuppressWarnings("rawtyCachepes")
	private static void addCacheIndex(IndexWriter writer, List<Object> list,
			Class clazz) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, IOException {
		for (Object obj : list) {
			Document doc = new Document();
			Method m[] = clazz.getMethods();

			for (Method method : m) {
				if (method.getName().startsWith("get")
						&& !method.getName().equals("getClass")) {

					String value = "";
					String key = "";
					try {
						value = method.invoke(obj, null).toString();
						key = method.getName().substring(3).toLowerCase();
					} catch (Exception e) {
						System.out.println();
					}
					int intValue = -1;
					Field field = null;
					// 载入方案只处理了int和字符,需根据VO类需求增加
					try {
						if (method.getReturnType().toString().equals("int"))
							intValue = Integer.parseInt(value);
					} catch (Exception e) {

					}
					if (intValue != -1) {
						field = new IntField(key, intValue, Field.Store.YES);
					} else
						field = new TextField(key, value, Field.Store.YES);
					doc.add(field);
				}
			}
			doc.add(new StringField("className", clazz.getName(),
					Field.Store.YES));
			writer.addDocument(doc);
		}
	}

	public void linuxRun(String cmd, String outpath) {
		try {
			String dir = outpath;// .substring(0,outpath.lastIndexOf(File.separator));
			File workdir = new File(dir);
			// Process pro1= Runtime.getRuntime().exec("cd "+dir);
			// pro1.waitFor();linuxRun
			Process pro = Runtime.getRuntime().exec(cmd, null, workdir);
			pro.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
