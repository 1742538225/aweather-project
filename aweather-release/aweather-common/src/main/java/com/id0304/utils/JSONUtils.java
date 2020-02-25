package com.id0304.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.List;

/**
 * @Author WuZhengHua
 * @Description TODO json工具类
 * @Date 2019/7/14 13:49
 */
public class JSONUtils {
    /**
     * @Author WuZhengHua
     * @Description TODO list集合转json字符串
     * @Date 14:04 2019/7/14
     **/
    public static String ListToJsonString(List list) {
        String listJson = JSON.toJSONString(list);
        JSONArray jsonArray = JSONArray.parseArray(listJson);
        String json = jsonArray.toJSONString();
        return json;
    }
}
