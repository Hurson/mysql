package cn.com.avit.ads.syndata;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;

public class SynDataUtil {
	public static Connection getADSConnection()  {
		InputStream in = null;
		Connection conn = null;
		try {
			in = new FileInputStream(new File(SynDataUtil.class
					.getClassLoader().getResource("jdbc.properties").getFile()));
			Properties p = new Properties();
			p.load(in);
			Class.forName(p.getProperty("ads.jdbc.driverClass"));
			conn = DriverManager.getConnection(p.getProperty("ads.jdbc.url"),
					p.getProperty("ads.jdbc.username"),
					p.getProperty("ads.jdbc.password"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				

			}

		}

		return conn;

	}

	public static Object toNotNullObject(Object str) {
		if (str == null || str.toString().equalsIgnoreCase("null")) {
			return "";

		} else {
			return str;

		}
	}

	public static Timestamp toDateObject(Object input) {
		Timestamp returnObj = null;
		if (input == null) {
			java.util.Date date = new java.util.Date();
			returnObj = new Timestamp(date.getTime());

		} else {
			if (input instanceof oracle.sql.TIMESTAMP) {
				oracle.sql.TIMESTAMP test = (oracle.sql.TIMESTAMP) input;
				try {
					returnObj = new Timestamp(test.dateValue().getTime());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (input instanceof oracle.sql.DATE) {
				oracle.sql.DATE test = (oracle.sql.DATE) input;
				returnObj = test.timestampValue();

			}
			if (input instanceof java.sql.Date) {
				java.sql.Date test = (java.sql.Date) input;
				returnObj = new Timestamp(test.getTime());

			}

		}
		return returnObj;

	}

	public static Long toLongObject(Object input, Long defaultValue) {
		Long returnObj = null;
		if (input == null) {
			if (defaultValue != null) {
				returnObj = defaultValue;
			} else {
				returnObj = new Long(0);
			}
		} else {
			returnObj = (Long) returnObj;
		}
		return returnObj;

	}

	public static Object toBigDecimalObject(Object input,
			BigDecimal defaultValue) {
		Object returnObj = null;
		if (input == null) {
			if (defaultValue != null) {
				returnObj = defaultValue;
			} else {
				returnObj = new BigDecimal(0);
			}
		} else {

			returnObj = input;

		}
		return returnObj;

	}

	public static Connection getCPSConnection()  {
		InputStream in = null;
		Connection conn = null;
		try {
			in = new FileInputStream(new File(SynDataUtil.class
					.getClassLoader().getResource("jdbc.properties").getFile()));
			Properties p = new Properties();
			p.load(in);
			Class.forName(p.getProperty("c3p0.jdbc.driverClass"));
			conn = DriverManager.getConnection(p.getProperty("c3p0.jdbc.url"),
					p.getProperty("c3p0.cps.jdbc.username"),
					p.getProperty("c3p0.cps.jdbc.password"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {

			}

		}

		return conn;

	}

	public static Connection getBsmpConnection() {
		InputStream in = null;
		Connection conn = null;

		try {
			in = new FileInputStream(new File(SynDataUtil.class
					.getClassLoader().getResource("jdbc.properties").getFile()));
			Properties p = new Properties();
			p.load(in);
			Class.forName(p.getProperty("bsmp.jdbc.driverClass"));
			conn = DriverManager.getConnection(p.getProperty("bsmp.jdbc.url"),
					p.getProperty("bsmp.jdbc.username"),
					p.getProperty("bsmp.jdbc.password"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {

			}

		}

		return conn;

	}

	public static Connection getNpvrConnection() {
		InputStream in = null;
		Connection conn = null;
		try {
			in = new FileInputStream(new File(SynDataUtil.class
					.getClassLoader().getResource("jdbc.properties").getFile()));
			Properties p = new Properties();
			p.load(in);
			Class.forName(p.getProperty("npvr.jdbc.driverClass"));
			conn = DriverManager.getConnection(p.getProperty("npvr.jdbc.url"),
					p.getProperty("npvr.jdbc.username"),
					p.getProperty("npvr.jdbc.password"));
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return conn;

	}

	public static Connection getEPGConnection()  {
		InputStream in = null;
		Connection conn = null;
		try {
			in = new FileInputStream(new File(SynDataUtil.class
					.getClassLoader().getResource("jdbc.properties").getFile()));
			Properties p = new Properties();
			p.load(in);
			Class.forName(p.getProperty("epg.jdbc.driverClass"));
			conn = DriverManager.getConnection(p.getProperty("epg.jdbc.url"),
					p.getProperty("epg.jdbc.username"),
					p.getProperty("epg.jdbc.password"));
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return conn;

	}
}
