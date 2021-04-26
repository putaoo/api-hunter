package com.wangxiaobao.apihunter.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wangxiaobao.apihunter.constant.HttpFrame;
import com.wangxiaobao.apihunter.httpcontext.HttpContext;
import com.wangxiaobao.apihunter.httpcontext.HttpContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class AroundAdvice implements MethodInterceptor {

    @Autowired
    private HttpContextHolder httpContextHolder;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        HttpContext.HttpContextBuilder contextBuilder = HttpContext
                .builder()
                .httpFrame(HttpFrame.SpringMvc)
                .requestTime(System.currentTimeMillis())
                .requestMethod(invocation.getMethod().getName());

        Object[] params = invocation.getArguments();
        Object param = params[0];

        final Object requestParam = param;
        contextBuilder.requestBody(objectMapper.writeValueAsString(requestParam));

        Object result = invocation.proceed();

        contextBuilder.responseTime(System.currentTimeMillis())
                .responseBody(objectMapper.writeValueAsString(result));

        httpContextHolder.publish(contextBuilder.build());

        return result;
    }
}
