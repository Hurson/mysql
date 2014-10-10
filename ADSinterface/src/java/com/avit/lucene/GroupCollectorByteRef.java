package com.avit.lucene;
import java.io.IOException;

import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.TopDocsCollector;
import org.apache.lucene.util.BytesRef;

public  class GroupCollectorByteRef extends TopDocsCollector {

 Collector collector;
 int docBase;

 private static BytesRef[] fcb;
 private GroupBytesRef gf = new GroupBytesRef();// 保存分组统计结果
 GroupCollectorByteRef(Collector topDocsCollector)
 throws IOException {
	super(null);
	collector = topDocsCollector;
}

 GroupCollectorByteRef(Collector topDocsCollector, BytesRef[] fieldCache)
 throws IOException {
	super(null);
	collector = topDocsCollector;
	this.fcb = fieldCache;
}
 @Override
 public void collect(int doc) throws IOException {
	  collector.collect(doc);
	  // 因为doc是每个segment的文档编号，需要加上docBase才是总的文档编号
	  int docId = doc + docBase;
	  // 添加的GroupField中，由GroupField负责统计每个不同值的数目
	  //gf.addValue(String.valueOf(docId));
//	  System.out.println("docId="+docId);
	  gf.addValue(fcb[docId]);
 }
//
// @Override
// public void setNextReader(IndexReader reader, int docBase)throws IOException {
//	 //super.setNextReader(reader, docBase);
//	  collector.setNextReader(reader, docBase);
//	  this.docBase = docBase;
// }
 @Override
 public void setNextReader(AtomicReaderContext cxt)
   throws IOException {
	 //super.setNextReader(cxt);
	  collector.setNextReader(cxt);
	  docBase = cxt.docBase;
	 // this.docBase = docBase;
 }
 @Override
 public void setScorer(Scorer scorer) throws IOException {
  collector.setScorer(scorer);
 }

 @Override
 public boolean acceptsDocsOutOfOrder() {
  return collector.acceptsDocsOutOfOrder();
 }
public static BytesRef[] getFcb() {
	return fcb;
}
public static void setFcb(BytesRef[] fcbs) {
	fcb = fcbs;
}
public GroupBytesRef getGf() {
	return gf;
}
public void setGf(GroupBytesRef gf) {
	this.gf = gf;
}

 
}