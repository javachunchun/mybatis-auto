package com.lcc.mybatis.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liuchunchun on 2018/11/28.
 */
public class StringUtil {

    public static final char UNDERLINE = '_';

    /**
     * 驼峰格式字符串转换为下划线格式字符串
     *
     * @param param
     * @return
     */
    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线格式字符串转换为驼峰格式字符串
     *
     * @param param
     * @return
     */
    public static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线格式字符串转换为驼峰格式字符串2
     *
     * @param param
     * @return
     */
    public static String underlineToCamel2(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        StringBuilder sb = new StringBuilder(param);
        Matcher mc = Pattern.compile("_").matcher(param);
        int i = 0;
        while (mc.find()) {
            int position = mc.end() - (i++);
            sb.replace(position - 1, position + 1, sb.substring(position, position + 1).toUpperCase());
        }
        return sb.toString();
    }

    /*
    * 判空操作，有一个是空则返回true
    * */
    public static boolean isNull(Object ... params){
        boolean result = false;

        List<Boolean> resultList = new ArrayList<Boolean>();
        for (Object param : params) {
            boolean resultTemp = false;

            if (param == null) {
                resultTemp = true;
            }

            if (param instanceof String) {
                if (param.equals("")) {
                    resultTemp = true;
                }
            } else if (param instanceof List) {
                if (((List) param).isEmpty()) {
                    resultTemp = true;
                }
            }

            resultList.add(resultTemp);
        }

        Iterator<Boolean> iterator = resultList.iterator();
        while (iterator.hasNext()) {
            result = result || iterator.next();
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println(underlineToCamel("id_tet"));
        System.out.println(underlineToCamel("idTet"));
    }
}
