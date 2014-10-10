package com.dvnchina.advertDelivery.utils.cron;

import java.util.HashMap;
import java.util.Map;

/**
 * 这个类封装了一些Quartz时间规则的常量，便于自己使用，定义比较灵活，可以根据您的具体情况扩展。 Quartz时间规则常量类 CronExRelated
 * 
 * @author chennaidong
 */
@SuppressWarnings("unchecked")
public class CronExRelated {

	public static final String _BLANKSPACE=" "; 
	
	/**
	 * 使用星号(*) 指示着你想在这个域上包含所有合法的值
	 */
	public static final String _EVERY = "*";
	
	/**
	 * ? 号只能用在日和周域上，但是不能在这两个域上同时使用。
	 */
	public static final String _ANY = "?";
	
	/**
	 * 中划线 (-) 用于指定一个域的范围。域的值不允许回卷，所以像 50-10 这样的值是不允许的
	 */
	public static final String _RANGES = "-";
	
	/**
	 * 斜杠 (/) 是用于时间表的递增的。
	 */
	public static final String _INCREMENTS = "/";
	
	/**
	 * 逗号 (,) 是用来在给某个域上指定一个值列表的。表达式样例：0 0,15,30,45 * * * ? 意义：每刻钟触发一次 trigger
	 */
	public static final String _ADDITIONAL = ",";
	
	/**
	 * L 说明了某域上允许的最后一个值。它仅被日和周域支持。
	 */
	public static final String _LAST = "L";
	
	/**
	 * W 字符代表着平日 (Mon-Fri)，并且仅能用于日域中。
	 */
	public static final String _WEEKDAY = "W";
	
	
	/**
	 * # 字符仅能用于周域中。它用于指定月份中的第几周的哪一天。例如，如果你指定周域的值为 6#3，它意思是某月的第三个周五 (6=星期五，#3意味着月份中的第三周)。另一个例子 2#1 意思是某月的第一个星期一 (2=星期一，#1意味着月份中的第一周)。注意，假如你指定 #5，然而月份中没有第 5 周，那么该月不会触发。
	 */
	public static final String _THENTH = "#";
	
	/**
	 * 日历
	 */
	public static final String _CALENDAR = "calendar";

	public static final String _TYPE = "type";

	/**
	 * 0 0 6 ? * 1#1 ? monthly 0 0 6 ? * 1 ? weekly 0 0 6 30 7 ? 2006 useDefined
	 */
	static String[] headTitle = { "TYPE", "SECONDS", "MINUTES", "HOURS", "DAYOFMONTH", "MONTH", "DAYOFWEEK", "YEAR" };

	/**
	 * cron expression special characters Map specialCharacters
	 */
	
	public static Map specialCharacters;

	static {
		specialCharacters = new HashMap(10);
		specialCharacters.put(_EVERY, "*");
		specialCharacters.put(_ANY, "?");
		specialCharacters.put(_RANGES, "-");
		specialCharacters.put(_INCREMENTS, "/");
		specialCharacters.put(_ADDITIONAL, ",");
		specialCharacters.put(_LAST, "L");
		specialCharacters.put(_WEEKDAY, "W");
		specialCharacters.put(_THENTH, "#");
		specialCharacters.put(_CALENDAR, "C");

		specialCharacters.put(_TYPE, headTitle);
	}

	public static void set(String ex, int index) {
		((String[]) specialCharacters.get(_TYPE))[index] = ex;
	}

}
