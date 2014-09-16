package com.avit.ads.pushads.task.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import com.avit.ads.pushads.task.bean.Uidesc;
import com.avit.ads.pushads.task.bean.UidescBean;

// TODO: Auto-generated Javadoc
/**
 * 发送文件缓存.
 * Key=filename
 * Data=List(播出单单号)
 * 启动播出单时，调用addFile,添加文件列表
 * 播出单结束，调用deleteFile，删除播出单对应记录，如无其他播出单时候用，直接删除资源文件
 */
public class UiDesMap {
	
	/**  
	 * 发送描述符缓存. 
	 * */
	private  static Uidesc hdUidesc=new Uidesc();
	private  static Uidesc sdUidesc=new Uidesc();
	private  static String defaultstartflag;
	
	private static List<UidescBean>  hdUidescBeanList= new ArrayList<UidescBean>();
	private static List<UidescBean>  sdUidescBeanList= new ArrayList<UidescBean>();
	
	/** 
	 * 读写锁
	 */
	private static final ReentrantLock lock = new ReentrantLock();

	public UiDesMap()
	{
		//添加默认素材　记录
		//**.JPG:0 
	}
	/**
	 * 添加高清描述符
	 * @return 返回1 成功 0 失败
	 */
	public static int addhdUiDesc(String type,String name,String areaCode)
	{
		lock.lock();
		hdUidesc.addDesc(type, name, areaCode);
		lock.unlock();
		return 1;
	}
	/**
	 * 添加标清描述符
	 * @return 返回1 成功 0 失败
	 */
	public static int addsdUiDesc(String type,String name,String areaCode)
	{
		lock.lock();
		sdUidesc.addDesc(type, name, areaCode);
		lock.unlock();
		return 1;
	}
	/**
	 * 删除高清描述符
	 * @return 返回1 成功 0 失败
	 */
	public static int deletehdUiDesc(String type,String name,String areaCode)
	{
		lock.lock();
		hdUidesc.delete(type, name, areaCode);
		lock.unlock();
		return 1;
	}
	/**
	 * 删除标清描述符
	 * @return 返回1 成功 0 失败
	 */
	public static int deletesdUiDesc(String type,String name,String areaCode)
	{
		lock.lock();
		sdUidesc.delete(type, name, areaCode);
		lock.unlock();
		return 1;
	}/**
	 * 删除标清描述符
	 * @return 返回1 成功 0 失败
	 */
	public static Uidesc getsdUiDesc()
	{
		return sdUidesc;
	}
	/**
	 * 删除标清描述符
	 * @return 返回1 成功 0 失败
	 */
	public static Uidesc gethdUiDesc()
	{
		return hdUidesc;
	}
	public static String getDefaultstartflag() {
		return defaultstartflag;
	}
	public static void setDefaultstartflag(String defaultstartflag) {
		
		lock.lock();
		UiDesMap.defaultstartflag = defaultstartflag;
		lock.unlock();
	}
	
	/**
	 * 添加高清描述符
	 * @return 返回1 成功 0 失败
	 */
	public static int addhdUiDesc(String type,String filename,String uipositioncode,String areaCode)
	{
		lock.lock();
		UidescBean temp = new UidescBean(type,filename,uipositioncode,areaCode);
		if (!hdUidescBeanList.contains(temp))
		{
			hdUidescBeanList.add(temp);
		}
		lock.unlock();
		return 1;
	}
	/**
	 * 添加标清描述符
	 * @return 返回1 成功 0 失败
	 */
	public static int addsdUiDesc(String type,String filename,String uipositioncode,String areaCode)
	{
		lock.lock();
		UidescBean temp = new UidescBean(type,filename,uipositioncode,areaCode);
		if (!sdUidescBeanList.contains(temp))
		{
			sdUidescBeanList.add(temp);
		}
		lock.unlock();
		return 1;
	}
	/**
	 * 删除高清描述符
	 * @return 返回1 成功 0 失败
	 */
	public static int deletehdUiDesc(String type,String filename,String uipositioncode,String areaCode)
	{
		lock.lock();
		UidescBean temp = new UidescBean(type,filename,uipositioncode,areaCode);
		if (hdUidescBeanList.contains(temp))
		{
			hdUidescBeanList.remove(temp);
		}
		lock.unlock();
		return 1;
	}
	/**
	 * 删除标清描述符
	 * @return 返回1 成功 0 失败
	 */
	public static int deletesdUiDesc(String type,String filename,String uipositioncode,String areaCode)
	{
		lock.lock();
		UidescBean temp = new UidescBean(type,filename,uipositioncode,areaCode);
		if (sdUidescBeanList.contains(temp))
		{
			sdUidescBeanList.remove(temp);
		}
		lock.unlock();
		return 1;
	}
	public static List<UidescBean> getsdUiDescList()
	{
		return sdUidescBeanList;
	}
	/**
	 * 删除标清描述符
	 * @return 返回1 成功 0 失败
	 */
	public static List<UidescBean> gethdUiDescList()
	{
		return hdUidescBeanList;
	}
}
