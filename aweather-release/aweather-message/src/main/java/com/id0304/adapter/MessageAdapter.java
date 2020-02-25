package com.id0304.adapter;

import com.alibaba.fastjson.JSONObject;

//所有类型消息的适配器
public interface MessageAdapter {
    //接受消息
    void distribute(JSONObject jsonObject);
}
