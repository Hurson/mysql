package com.avit.lucene;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.AtomicReader;
import org.apache.lucene.index.BinaryDocValues;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.SlowCompositeReaderWrapper;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FieldCache;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopDocsCollector;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.search.grouping.GroupDocs;
import org.apache.lucene.search.grouping.GroupingSearch;
import org.apache.lucene.search.grouping.TopGroups;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;

import com.avit.ads.syncreport.bean.ContentPush;
import com.avit.ads.syncreport.bean.TReportData;




public class Search {
	//搜索空间
	//IKAnalyzer 中文分词包,中文断句
	private Directory dir;
	IndexReader reader = null;
	private IndexSearcher searcher = null;
	//分析器
	private Analyzer analyzer = null;
	//每页大小
	private int page_size = 10;
	//当前页
	private int current = 1;
	//首次搜索获取数据量
	public final static int RECORD_NUM=10;
	
	//BytesRef[] bytesRefarray=null;
	public Search(Directory dir) throws IOException{
		this.dir = dir;
		init();
	}
	
	public void setPageInfo(int current,int pageSize){
		this.current = current;
		this.page_size = pageSize;
	}
	
	//汉字模糊查询，英文数字精确查询;英文，数字模糊查询，请使用通配符方案
	public PageBean Query(String key,String value) throws IOException, ParseException{
		QueryParser parser = new QueryParser(Version.LUCENE_44, key, analyzer);
		Query query = parser.parse(value);
		return printQueryResult(query);		
	}
	
	//汉字模糊查询，英文，数字模糊查询，请使用通配符方案
	public PageBean TermQuery(String key,String value) throws IOException{
		//org.apache.lucene.analysis.standard.ClassicAnalyzer;
		 Query query = new TermQuery(new Term(key,value));
		 return printQueryResult(query);		
	}
	
	//字符区间查询
	public PageBean TermQueryRange(String key,String begin,String end) throws IOException{
		Query query = new TermRangeQuery(key,new BytesRef(begin.getBytes()),new BytesRef(end.getBytes()),true,true);
		return printQueryResult(query);		
	}
	
	//数字区间查询
	public PageBean TermQueryNumber(String key,int begin,int end) throws IOException{
		Query query = NumericRangeQuery.newIntRange(key,begin,end,true,true);
		return printQueryResult(query);		
	}
	
	//通配符查询，英文，数字模糊查询，请使用通配符方案
	public PageBean WildcardQuery(String key,String value) throws IOException{
		Query query = new WildcardQuery(new Term(key,value));
		return printQueryResult(query);		
	}
	//多条件查询，只可用户完整匹配，如年份，分类，区域组合
	//可通过完整匹配CLASSNAME，达到对单个LIST进行搜索的效果
	public PageBean MultiQuery(String[] key,String[] value) throws ParseException, IOException{
		//MultiFieldQueryParser mfq = new MultiFieldQueryParser(Version.LUCENE_44, key, analyzer);
		Query query=MultiFieldQueryParser.parse(Version.LUCENE_44, value,key,analyzer);
		return printQueryResult(query);		
	}
	
	/*
	 * 多条件查询，查询出匹配文档的数目
	 */
	public int MultiQuery4Count(String[] keys,String[] values){
		int hitCount = 0;
		try {
			BooleanQuery bq = new BooleanQuery();
			if (keys!=null && keys.length>0){
				for(int i=0;i< keys.length;i++){
					QueryParser parser = new QueryParser(Version.LUCENE_44, keys[i], analyzer);
					Query query = parser.parse(values[i]);
					bq.add(query, BooleanClause.Occur.MUST);
				}
			}
			TopDocs results = searcher.search(bq, RECORD_NUM);		
			hitCount = results.totalHits;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hitCount;
	}
	
	public void groupQuery4Count(String groupField, String[] keys,String[] values, TReportData entity) throws Exception{
		GroupingSearch groupingSearch = new GroupingSearch(groupField);
		groupingSearch.setAllGroups(true);
		groupingSearch.setFillSortFields(false);
		groupingSearch.setGroupSort(Sort.INDEXORDER);
		groupingSearch.setIncludeScores(false);
		groupingSearch.disableCaching();
		
		BooleanQuery bq = new BooleanQuery();
		if (keys!=null && keys.length>0){
			for(int i=0;i< keys.length;i++){
				QueryParser parser = new QueryParser(Version.LUCENE_44, keys[i], analyzer);
				Query query = parser.parse(values[i]);
				bq.add(query, BooleanClause.Occur.MUST);
			}
		}
		TopGroups<BytesRef> result = groupingSearch.search(searcher, bq, 0, 5000000);
		int effectiveCount = 0;
		int reachCount = 0;
		
		Integer reachCountI = result.totalGroupCount;   //巨坑，居然返回null，而不是返回0
		if(null != reachCountI){                        
			reachCount = reachCountI;
		}
		
		GroupDocs<BytesRef>[] gds = result.groups;		
		if(null != gds){
			for(GroupDocs<BytesRef> gd : gds){
				int groupSize = gd.totalHits;
				effectiveCount += groupSize/3;
			}
		}	
		entity.setReacheCount((long)reachCount);
		entity.setEffCount((long)effectiveCount);
	}
	
	
	//多条件查询，+ 区间条件
	//可通过完整匹配CLASSNAME，达到对单个LIST进行搜索的效果
	public PageBean MultiQueryExRange(String[] keys,String[] values,String key,String begin,String end) throws ParseException, IOException{
		BooleanQuery bq = new BooleanQuery();
		Query query2 = new TermRangeQuery(key,new BytesRef(begin.getBytes()),new BytesRef(end.getBytes()),true,true);
		
		if (keys!=null && keys.length>0)
		{
			for(int i=0;i< keys.length;i++){
				//此处不能用Term
				QueryParser parser = new QueryParser(Version.LUCENE_44, keys[i], analyzer);
				Query query = parser.parse(values[i]);
				bq.add(query, BooleanClause.Occur.MUST);
			}
		}
		bq.add(query2,BooleanClause.Occur.MUST);
		return printQueryResult(bq);	
	}
	//多条件查询+模糊英文，数字查询
	//可通过完整匹配CLASSNAME，达到对单个LIST进行搜索的效果
	public PageBean MultiQueryExRegex(String[] keys,String[] values,String key,String value) throws ParseException, IOException{
		//MultiFieldQueryParser mfq = new MultiFieldQueryParser(Version.LUCENE_44, keys, analyzer);
		Query query=MultiFieldQueryParser.parse(Version.LUCENE_44, values,keys,analyzer);
		Query query2 = new WildcardQuery(new Term(key,value));
		BooleanQuery bq = new BooleanQuery();
		bq.add(query, BooleanClause.Occur.MUST);
		bq.add(query2,BooleanClause.Occur.MUST);
		return printQueryResult(bq);	
	}
	//多条件查询+模糊中文
	public PageBean MultiQueryEx(String[] keys,String[] values,String key,String value) throws ParseException, IOException{
		//MultiFieldQueryParser mfq = new MultiFieldQueryParser(Version.LUCENE_44, keys, analyzer);
		Query query=MultiFieldQueryParser.parse(Version.LUCENE_44, values,keys,analyzer);
		Query query2 = new TermQuery(new Term(key,value));
		BooleanQuery bq = new BooleanQuery();
		bq.add(query, BooleanClause.Occur.MUST);
		bq.add(query2,BooleanClause.Occur.MUST);
		return printQueryResult(bq);
	}
	//带排序方案
	public PageBean WildcardQuery(String key,String value,String sort) throws IOException{
		Query query = new WildcardQuery(new Term(key,value));
		return sortQueryResult(query,sort);
	}
	//模糊搜索满足前提的汉字
	public PageBean MultiQuerySHOULD(String[] keys,String[] values) throws IOException{
		BooleanQuery bq = new BooleanQuery();
		if(keys!=null&&values!=null&&keys.length==values.length){
			for(int i = 0;i<keys.length;i++){
				Query query = new TermQuery(new Term(keys[i],values[i]));
				bq.add(query,BooleanClause.Occur.SHOULD);
			}
		}
		return printQueryResult(bq);
	}
	//获取满足条件的个数
	private int getTotal(String[] should,String[] shouldValue,String[] must,String[] mustValue) throws IOException{		
		BooleanQuery bq = new BooleanQuery();
		if(should!=null&&shouldValue!=null&&should.length==shouldValue.length){
			for(int i = 0;i<should.length;i++){
				Query query = new TermQuery(new Term(should[i],shouldValue[i]));
				bq.add(query,BooleanClause.Occur.SHOULD);
			}
		}
		if(must!=null&&mustValue!=null&&must.length==mustValue.length){
			for(int i = 0;i<must.length;i++){
				Query query = new TermQuery(new Term(must[i],mustValue[i]));
				bq.add(query,BooleanClause.Occur.MUST);
			}
		}
		
		return searcher.search(bq, RECORD_NUM).totalHits;
	}
	
	//sort
	private PageBean sortQueryResult(Query query,String field) throws IOException{
		TopDocs results = searcher.search(query, RECORD_NUM);
		SortField sort = new SortField("age",SortField.Type.INT);
		searcher.search(query, null, RECORD_NUM,new Sort(sort));
		int numTotalHits = results.totalHits;
		if(numTotalHits>RECORD_NUM && current*page_size>RECORD_NUM){
			results = searcher.search(query,current*page_size);
		}
		return buildPageBean(results);
	}
	
	private PageBean printQueryResult(Query query) throws IOException{
		TopDocs results = searcher.search(query, RECORD_NUM);		
		int numTotalHits = results.totalHits;
		if(numTotalHits>RECORD_NUM && current*page_size>RECORD_NUM){
//			results = searcher.search(query,current*page_size);
			results = searcher.search(query,numTotalHits);
		}
		return buildPageBean(results);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private PageBean buildPageBean(TopDocs results) throws IOException{
		PageBean page = new PageBean();
		page.setPageNo(current);
		page.setPageSize(page_size);
		page.setRowCount(results.totalHits);		
		List data = new ArrayList();
		
		ScoreDoc[] hits = results.scoreDocs;
		for(int i=(current-1)*page_size;i<hits.length&&i<current*page_size;i++){
			Document doc = searcher.doc(hits[i].doc);
			try{
				//Object obj = ClassLoader.getSystemClassLoader().loadClass(doc.get("className")).newInstance();
				Object obj = Class.forName(doc.get("className")).newInstance();
				
				Method ms[] = obj.getClass().getMethods();
				for(Method method:ms){
					if(method.getName().startsWith("set")){						
						Class[] types = method.getParameterTypes();
						if(types!=null && types.length==1){
							String field = method.getName().substring(3).toLowerCase();
							String value = doc.get(field);
							//类型增加需扩展
							if(types[0].getName().indexOf("String")!=-1){
								method.invoke(obj, new Object[]{value});
							}else if(types[0].getName().indexOf("int")!=-1){
								method.invoke(obj, new Object[]{Integer.valueOf(value)});
							}else{
								System.out.println(types[0].getName()+ ":type not support ,pls add !");
							}
						}					
					}						
				}
				
				data.add(obj);
				
			}catch(ClassNotFoundException ex){
				ex.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		page.setData(data);
		return page;
	}
	

	
	private void init() throws IOException{
		reader = DirectoryReader.open(dir);
		searcher = new IndexSearcher(reader);
		analyzer = new StandardAnalyzer(Version.LUCENE_44);		
	}
	
	public void close() throws IOException{
		if(reader!=null){
			reader.close();
			reader = null;
			try
			{
				GroupCollectorByteRef.setFcb(null);
			}
			catch(Exception e)
			{
				
			}
		}
	}
	public void createbytesRef()
	{
		try
		{
			AtomicReader atomicReader = SlowCompositeReaderWrapper.wrap(reader); 
			//final BytesRef[] 
			BytesRef[] bytesRefarray= new BytesRef[atomicReader.maxDoc()];
			   
			   // SortedDocValues sortedDocValues=FieldCache.DEFAULT.getTermsIndex(atomicReader,  "fenlei");
			    BinaryDocValues values = FieldCache.DEFAULT.getTerms(atomicReader,  "tvn");
			    for (int i=0;i<atomicReader.maxDoc();i++)
			    {
			 	   BytesRef br=new BytesRef();  
			 	   values.get(i, br);
			 	   bytesRefarray[i]=br;
			    }
			    GroupCollectorByteRef.setFcb(bytesRefarray);
		}
		catch(Exception e)
		{
			
		}
	}
	//多条件查询，+ 区间条件
	//可通过完整匹配CLASSNAME，达到对单个LIST进行搜索的效果 
	public ContentPush MultiQueryGroupBy(String[] keys,String[] values,String field, int topCount) throws ParseException, IOException{
		BooleanQuery bq = new BooleanQuery();
		//Query query = new Query();
		if (keys!=null && keys.length>0)
		{
			for(int i=0;i< keys.length;i++){
				//此处不能用Term
				QueryParser parser = new QueryParser(Version.LUCENE_44, keys[i], analyzer);
				Query query = parser.parse(values[i]);
			//	groupBy(query,field,topCount);
				bq.add(query, BooleanClause.Occur.MUST);
			}
		}			
//		QueryParser parser = new QueryParser(Version.LUCENE_44, "position_code", analyzer);
//		Query query = parser.parse("02042");
//		bq.add(query, BooleanClause.Occur.MUST);
//		groupBy(query,field,topCount);
		return groupBy(bq,field,topCount);	
	}
	
	public ContentPush groupBy(Query query, String field, int topCount) {
		  Map<String, Integer> map = new HashMap<String, Integer>();
		  ContentPush  retContentPush = new ContentPush();
		  Long effcount=0L;
		  Long reachcount=0L;
		//  IndexSearcher indexSearcher=null;
		  long begin = System.currentTimeMillis();		 
		  try {
		  
		      TopDocsCollector collector = TopScoreDocCollector.create(topCount,false);
			   final String[] fc = {"12","122"};
//			   AtomicReader atomicReader = SlowCompositeReaderWrapper.wrap(reader);  
//			   final BytesRef[] bytesRefarray= new BytesRef[atomicReader.maxDoc()];
//			   
//			   // SortedDocValues sortedDocValues=FieldCache.DEFAULT.getTermsIndex(atomicReader,  "fenlei");
//			    BinaryDocValues values = FieldCache.DEFAULT.getTerms(atomicReader,  "tvn");
//			    for (int i=0;i<atomicReader.maxDoc();i++)
//			    {
//			 	   BytesRef br=new BytesRef();  
//			 	   values.get(i, br);
//			 	   bytesRefarray[i]=br;
//			    }
			    GroupCollectorByteRef groupCollector = new GroupCollectorByteRef(collector);
			    searcher.search(query, groupCollector);			   
			    
			    for (BytesRef key : groupCollector.getGf().getCountMap().keySet()) {
				   Integer hits  =groupCollector.getGf().getCountMap().get(key);
				   if (hits>=3)
			       {
			    	   effcount++;
			       }
			       reachcount++;		   
			   }		   
		  } 
		  catch (IOException e) {
			  e.printStackTrace();
		  }
		  long end = System.currentTimeMillis();
		 // System.out.println("group by time :" + (end - begin) + "ms");
		  retContentPush.setReachCount(reachcount);
		  retContentPush.setEffectiveCount(effcount);
		  return retContentPush;
		 }
	
//	
//	
//	public Map<String, Integer> groupBy(Query query, String field, int topCount) {
//		  Map<String, Integer> map = new HashMap<String, Integer>();
//		  
//		  long begin = System.currentTimeMillis();
//		  int topNGroups = topCount;
//		  int groupOffset = 0;
//		  int maxDocsPerGroup = 100;
//		  int withinGroupOffset = 0;
//		  try {
//		   TermFirstPassGroupingCollector c1 = new TermFirstPassGroupingCollector(field, Sort.RELEVANCE, topNGroups);
//		   boolean cacheScores = true; 
//		   double maxCacheRAMMB = 4.0;
//		   CachingCollector cachedCollector = CachingCollector.create(c1, cacheScores, maxCacheRAMMB); 
//		   indexSearcher.search(query, cachedCollector);
//		   Collection<SearchGroup<String>> topGroups = c1.getTopGroups(groupOffset, true);
//		   if (topGroups == null) { 
//		    return null;
//		   } 
//		   TermSecondPassGroupingCollector c2 = new TermSecondPassGroupingCollector(field, topGroups, Sort.RELEVANCE, Sort.RELEVANCE, maxDocsPerGroup, true, true, true);
//		   if (cachedCollector.isCached()) {
//		    // Cache fit within maxCacheRAMMB, so we can replay it: 
//		    cachedCollector.replay(c2); 
//		   } else {
//		       // Cache was too large; must re-execute query: 
//		    indexSearcher.search(query, c2);
//		   }
//		   
//		   TopGroups<String> tg = c2.getTopGroups(withinGroupOffset);
//		   GroupDocs<String>[] gds = tg.groups;
//		   for(GroupDocs<String> gd : gds) {
//		    map.put(gd.groupValue, gd.totalHits);
//		   }
//		  } catch (IOException e) {
//		   e.printStackTrace();
//		  }
//		  long end = System.currentTimeMillis();
//		  System.out.println("group by time :" + (end - begin) + "ms");
//		  return map;
//		 }
}
