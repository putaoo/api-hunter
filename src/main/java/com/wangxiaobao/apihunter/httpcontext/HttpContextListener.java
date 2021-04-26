package com.wangxiaobao.apihunter.httpcontext;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpContextListener{

    public void listen(HttpContext httpContext){
        log.info("httpContext : {}", JSONObject.toJSONString(httpContext));
    }
}
