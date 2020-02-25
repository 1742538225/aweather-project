package com.id0304.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface CallbackService {
    //同步回调
    Map<String, String> syn(HttpServletRequest request);

    //异步回调
    String asyn(HttpServletRequest request);
}
