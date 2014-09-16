package com.avit.ads.util.json;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

public class RcUtil {
        /**
         * 判断是否为空 if null || "" return true else false;
         * @return
         */
        public static boolean isEmpty(String obj) {
                if (obj == null || "".equals(obj) || "undefined".equals(obj)) {
                        return true;
                } else {
                        return false;
                }
        }

        /**
         * 分离字符串取值
         * @param field
         *            字段名
         * @param regex
         *            分隔符
         * @return
         */
        public static String[] split(String field, String regex) {
                if (!isEmpty(field)) {
                        String str[] = field.split(regex);
                        return str;
                }
                return null;
        }

        /**
         * 获取数组某个下标的值
         * @param obj
         * @param suffix
         * @return
         */
        public static String getObject(String[] obj, int suffix) {
                if (obj != null) {
                        if (2 != obj.length) {
                                return "";
                        } else {
                                return obj[suffix];
                        }
                }
                return "";
        }

        /**
         * 截取字符串取值
         * @param status
         * @return
         */
        public static String[] substring(String status) {
                if (isEmpty(status)) {
                        return null;
                }
                String fd[] = split(status, "\\.");
                if (fd == null) {
                        return null;
                }
                String newStr = fd[1].substring(fd[1].indexOf("["), fd[1].length());
                newStr = newStr.replace("[", "").replace("]", "");
                String obj[] = new String[] { fd[1].substring(0, fd[1].indexOf("[")),
                                newStr };
                return obj;
        }

        /**
         * @param source
         *            YYYY-MM-DD
         * @return
         */
        public static Date ToDate(String source) {
                if (isEmpty(source)) {
                        return null;
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                        return dateFormat.parse(source);
                } catch (ParseException e) {
                        e.printStackTrace();
                }
                return null;
        }

        /**
         * @param source
         *            日期
         * @param dateFormat
         *            日期格式
         * @return
         */
        public static Date string2date(String source, String dateFormat) {
                if (isEmpty(source)) {
                        return null;
                }
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
                try {
                        return sdf.parse(source);
                } catch (ParseException e) {
                        e.printStackTrace();
                }
                return null;
        }

        @SuppressWarnings("unchecked")
        public static Object populate(Class cls, HttpServletRequest request)
                        throws IllegalAccessException, InvocationTargetException,
                        InstantiationException {
                Object obj = cls.newInstance();
                BeanUtils.populate(obj, request.getParameterMap());
                return obj;
        }

}

