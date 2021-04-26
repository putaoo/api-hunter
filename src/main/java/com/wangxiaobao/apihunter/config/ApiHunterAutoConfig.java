package com.wangxiaobao.apihunter.config;

import com.wangxiaobao.apihunter.httpcontext.HttpContextHolder;
import com.wangxiaobao.apihunter.httpcontext.HttpContextListener;
import com.wangxiaobao.apihunter.interceptors.FeignInterceptor;
import com.wangxiaobao.apihunter.interceptors.RestTemplateInterceptor;
import com.wangxiaobao.apihunter.interceptors.Retrifit2Interceptor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(ConfigProperties.class)
public class ApiHunterAutoConfig {

    @Bean
    @ConditionalOnProperty(prefix = "api.hunter",name = "feign-enable",havingValue = "true")
    public FeignInterceptor feignInterceptor(ObjectFactory<HttpMessageConverters> messageConverters){
        return new FeignInterceptor(messageConverters);
    }

    @Bean("hunterRestTemplate")
    @ConditionalOnProperty(prefix = "api.hunter",name = "rest-template-enable",havingValue = "true")
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(60*1000);
        requestFactory.setReadTimeout(60*1000);
        restTemplate.setRequestFactory(requestFactory);
        restTemplate.getInterceptors().add(new RestTemplateInterceptor());
        return restTemplate;
    }

    @Bean
    @ConditionalOnProperty(prefix = "api.hunter",name = "retrifit2-enable",havingValue = "true")
    public Retrifit2Interceptor retrifit2Interceptor(){
        return new Retrifit2Interceptor();
    }

    @Bean
    @ConditionalOnExpression("${api.hunter.feign-enable:true} || " +
            "${api.hunter.rest-template-enable:true} ||" +
            "${api.hunter.retrifit2-enable:true} ||" +
            "${api.hunter.springmvc.enable:true}")
    public HttpContextHolder httpContextHolder(){
        return new HttpContextHolder(new HttpContextListener());
    }
}
