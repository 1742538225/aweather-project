package com.id0304.utils;

import com.alibaba.fastjson.JSONObject;
import com.id0304.constants.BaseApiConstants;

import java.util.Map;

public class ResultUtil {
    /**
     * @Author WuZhengHua
     * @Description TODO 传入报文返回data部分(json字符串格式) 常用于转换为map集合
     * @Date 12:57 2019/7/14
     **/
    public static Object getResultMap(Map<String, Object> map) {
        Integer code = (Integer) map.get(BaseApiConstants.HTTP_CODE_NAME);
        if (code.equals(BaseApiConstants.HTTP_200_CODE)) {
            Object object = map.get(BaseApiConstants.HTTP_DATA_NAME);
            return object;
        }
        return null;
    }
}
