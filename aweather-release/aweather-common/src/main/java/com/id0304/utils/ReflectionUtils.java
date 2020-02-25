package com.id0304.utils;

import java.lang.reflect.Field;
import java.sql.Timestamp;

/**
 * @Author WuZhengHua
 * @Description TODO 反射工具类,用于拼接sql语句
 * @Date 19:39 2019/7/13
 **/
public class ReflectionUtils {
    /**
     * @Author WuZhengHua
     * @Description TODO 返回父类主键,返回(id=值)
     * @Date 19:42 2019/7/13
     **/
    public static String fatherMajorFieldAndValue(Object obj){
        if(obj==null){
            return null;
        }
        Class classInfo = obj.getClass();
        Field[] fatherFields = classInfo.getSuperclass().getDeclaredFields();
        String s = getFieldAndValue(obj,fatherFields);
        return s.substring(0,s.indexOf(","));
    }

    /**
     * @Author WuZhengHua
     * @Description TODO 返回子类+父类的属性名=值,返回(属性=值,属性=值,属性=值,属性=值,...)
     * @Date 19:36 2019/7/13
     **/
    public static String fatherAndSonFieldAndValue(Object obj){
        if(obj==null){
            return null;
        }
        Class classInfo = obj.getClass();
        Field[] sonFields = classInfo.getDeclaredFields();
        String s1 = getFieldAndValue(obj,sonFields);
        Field[] fatherFields = classInfo.getSuperclass().getDeclaredFields();
        String s2 = getFieldAndValue(obj,fatherFields);
        return s1+","+s2;
    }
    
    /**
     * @Author WuZhengHua
     * @Description TODO 返回子类+父类的属性名,返回(属性名,属性名,属性名,属性名...)
     * @Date 19:35 2019/7/13
     **/
    public static String fatherAndSonField(Object obj) {
        if (obj == null) {
            return null;
        }
        //获取class文件
        Class classInfo = obj.getClass();
        //获取当前类的属性sql
        Field[] sonFields = classInfo.getDeclaredFields();
        String s1 = getField(sonFields);
        Field[] fatherFields = classInfo.getSuperclass().getDeclaredFields();
        String s2 = getField(fatherFields);
        return s1 + "," + s2;
    }

    /**
     * @Author WuZhengHua
     * @Description TODO 拼接子类+父类的值,返回(值,值,值,值...)
     * @Date 19:35 2019/7/13
     **/
    public static String fatherAndSonFieldValue(Object obj) {
        if (obj == null) {
            return null;
        }
        //获取class文件
        Class classInfo = obj.getClass();
        //获取当前类的属性sql
        Field[] sonFields = classInfo.getDeclaredFields();
        String s1 = getFieldValue(obj, sonFields);
        Field[] fatherFields = classInfo.getSuperclass().getDeclaredFields();
        String s2 = getFieldValue(obj, fatherFields);
        return s1 + "," + s2;
    }

    /**
     * @Author WuZhengHua
     * @Description TODO 获取属性名,返回(属性名,属性名,属性名,属性名...),用于拼接insert中into table子句
     * @Date 19:34 2019/7/13
     **/
    public static String getField(Field[] declaredFields) {
        StringBuffer sf = new StringBuffer();
        for (int i = 0; i < declaredFields.length; ++i) {
            sf.append(declaredFields[i].getName());
            if (i < declaredFields.length - 1) {
                sf.append(",");
            }
        }
        return sf.toString();
    }

    /**
     * @Author WuZhengHua
     * @Description TODO 获取值,返回(值,值,值,值...),用于拼接insert中value子句
     * @Date 19:33 2019/7/13
     **/
    public static String getFieldValue(Object obj, Field[] declaredFields) {
        StringBuffer sf = new StringBuffer();
        for (int i = 0; i < declaredFields.length; ++i) {
            //获取属性值
            try {
                //运行操作私有属性
                declaredFields[i].setAccessible(true);

                Field field = declaredFields[i];
                Object value = field.get(obj);

                //标识类型是否为String类型
                boolean flag = false;
                if (value != null && (value instanceof String||value instanceof Timestamp)) {
                    flag = true;
                }
                if (flag) {
                    sf.append("'" + value + "'");
                } else {
                    sf.append(value);
                }
                if (i < declaredFields.length - 1) {
                    sf.append(",");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sf.toString();
    }
    
    /**
     * @Author WuZhengHua
     * @Description TODO 获取属性和其值,返回(属性=值,属性=值,属性=值,属性=值...),用于拼接update ...set子句 
     * @Date 19:32 2019/7/13
     **/
    public static String getFieldAndValue(Object obj,Field[] declaredFields) {
        StringBuffer sf = new StringBuffer();
        for (int i = 0; i < declaredFields.length; ++i) {
            //获取属性值
            try {
                //运行操作私有属性
                declaredFields[i].setAccessible(true);

                Field field = declaredFields[i];
                Object value = field.get(obj);

                //标识类型是否为String类型
                boolean flag = false;
                if (value != null && (value instanceof String||value instanceof Timestamp)) {
                    flag = true;
                }
                if (flag) {
                    sf.append(declaredFields[i].getName()+"='" + value + "'");
                } else {
                    sf.append(declaredFields[i].getName()+"="+value);
                }
                if (i < declaredFields.length - 1) {
                    sf.append(",");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sf.toString();
    }
}