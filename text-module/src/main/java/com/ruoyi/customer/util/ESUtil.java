package com.ruoyi.customer.util;

import com.alibaba.fastjson.JSONObject;

/**
 * @Description TODO
 * @Author xianpei.qin
 * @date 2021/03/01 13:23
 */
public class ESUtil {

    public String processResult(String result){
        JSONObject jsonObject = JSONObject.parseObject(result);
        Object hits = jsonObject.get("hits");

        return "";


    }

}
