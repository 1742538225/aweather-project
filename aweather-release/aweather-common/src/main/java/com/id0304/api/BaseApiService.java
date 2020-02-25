package com.id0304.api;

import com.id0304.constants.BaseApiConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用base api父类
 */
public class BaseApiService {
    /**
     * 返回错误(无数据)
     *
     * @return
     */
    public static Map<String, Object> setResultError() {
        return setResult(BaseApiConstants.HTTP_500_CODE, BaseApiConstants.HTTP_ERROR_NAME, null);
    }

    /**
     * 返回错误(带数据)
     *
     * @return
     */
    public static Map<String, Object> setResultErrorData(Object data) {
        return setResult(BaseApiConstants.HTTP_500_CODE, BaseApiConstants.HTTP_ERROR_NAME, data);
    }

    /**
     * 返回错误(携带参数错误)
     *
     * @return
     */
    public static Map<String, Object> setResultErrorParam(Object data) {
        return setResult(BaseApiConstants.HTTP_400_CODE, BaseApiConstants.HTTP_ERROR_NAME, data);
    }

    /**
     * 返回成功(无数据)
     *
     * @return
     */
    public static Map<String, Object> setResultSuccess() {
        return setResult(BaseApiConstants.HTTP_200_CODE, BaseApiConstants.HTTP_SUCCESS_NAME, null);
    }

    /**
     * 返回成功(带数据)
     *
     * @return
     */
    public static Map<String, Object> setResultSuccessData(Object data) {
        return setResult(BaseApiConstants.HTTP_200_CODE, BaseApiConstants.HTTP_SUCCESS_NAME, data);
    }

    /**
     * 返回失败(无数据)
     *
     * @return
     */
    public static Map<String, Object> setResultFail() {
        return setResult(BaseApiConstants.HTTP_202_CODE, BaseApiConstants.HTTP_FAIL_NAME, null);
    }

    /**
     * 返回失败(有数据)
     *
     * @return
     */
    public static Map<String, Object> setResultFailData(Object data) {
        return setResult(BaseApiConstants.HTTP_202_CODE, BaseApiConstants.HTTP_FAIL_NAME, data);
    }

    /**
     * 自定义返回,供上面直接调用
     *
     * @param code
     * @param msg
     * @param data
     * @return
     */
    private static Map<String, Object> setResult(Integer code, String msg, Object data) {
        HashMap<String, Object> result = new HashMap<>();
        result.put(BaseApiConstants.HTTP_CODE_NAME, code);
        result.put(BaseApiConstants.HTTP_MESSAGE_NAME, msg);
        if (data != null)
            result.put(BaseApiConstants.HTTP_DATA_NAME, data);
        return result;
    }

}