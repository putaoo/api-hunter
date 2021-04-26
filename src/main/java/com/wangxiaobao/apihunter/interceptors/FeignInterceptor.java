package com.wangxiaobao.apihunter.interceptors;

import com.wangxiaobao.apihunter.httpcontext.HttpContext;
import com.wangxiaobao.apihunter.httpcontext.HttpContextHolder;
import feign.FeignException;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.stream.Collectors;

@Slf4j
public class FeignInterceptor extends SpringDecoder implements RequestInterceptor {

    private final ThreadLocal<HttpContext> logMap = new ThreadLocal<>();

    @Autowired
    private HttpContextHolder httpContextHolder;

    public FeignInterceptor(ObjectFactory<HttpMessageConverters> messageConverters) {
        super(messageConverters);
    }

    @Override
    public void apply(RequestTemplate template) {
        HttpContext.HttpContextBuilder httpContextBuilder = HttpContext.builder()
                .requestTime(System.currentTimeMillis())
                .url(template.url())
                .requestMethod(template.method())
                .requestParams(template.queries().toString())
                .requestBody(template.requestBody().asString());

        logMap.set(httpContextBuilder.build());
    }

    @Override
    public Object decode(final Response response, Type type)throws IOException, FeignException {
        HttpContext httpContext = logMap.get();

        InputStream inputStream = response.body().asInputStream();
        String result = new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(Collectors.joining(System.lineSeparator()));
        httpContext.setResponseBody(result);

        httpContextHolder.publish(httpContext);

        logMap.remove();
        return super.decode(response,type);
    }
}
