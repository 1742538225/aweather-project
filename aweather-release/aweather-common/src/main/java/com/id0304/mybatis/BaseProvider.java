package com.id0304.mybatis;
import com.id0304.utils.ReflectionUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class BaseProvider {
    //自定义封装sql插入语句
    public String save(Map<String, Object> map) {
        //实体类
        Object obj = map.get("obj");
        //表名称
        String table = (String) map.get("table");
        //生成添加的sql语句,使用反射机制
        //步骤:使用反射机制加载所有属性
        SQL sql = new SQL() {
            {
                INSERT_INTO(table);
                VALUES(ReflectionUtils.fatherAndSonField(obj), ReflectionUtils.fatherAndSonFieldValue(obj));
            }
        };
        return sql.toString();
    }
    //自定义封装sql修改语句
    public String update(Map<String, Object> map) {
        //实体类
        Object obj = map.get("obj");
        //表名称
        String table = (String) map.get("table");
        //生成添加的sql语句,使用反射机制
        //步骤:使用反射机制加载所有属性
        String oldSetString = ReflectionUtils.fatherAndSonFieldAndValue(obj);    //包含修改created属性值,需要去除
        String replaceString = oldSetString.substring(oldSetString.indexOf(",created="),oldSetString.indexOf(",updated=")); //获取 ,created=? 这段字符串
        String setString = oldSetString.replace(replaceString, ""); //将 ,created=? 这段字符串用空格代替掉即可取消修改这个属性
        String whereString = ReflectionUtils.fatherMajorFieldAndValue(obj);
        SQL sql = new SQL() {
            {
                UPDATE(table);
                SET(setString);
                WHERE(whereString);
            }
        };
        String sqlString = sql.toString();
        return sqlString;
    }

}