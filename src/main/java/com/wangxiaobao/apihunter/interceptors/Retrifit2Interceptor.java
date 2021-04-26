package com.wangxiaobao.apihunter.interceptors;

import com.wangxiaobao.apihunter.httpcontext.HttpContextHolder;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@Slf4j
public class Retrifit2Interceptor implements Interceptor {

    @Autowired
    private HttpContextHolder httpContextHolder;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        return response;
    }
}
