package com.wangxiaobao.apihunter.aop;

import com.wangxiaobao.apihunter.config.ConfigProperties;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@EnableConfigurationProperties(ConfigProperties.class)
public class SpringMvcAdvisor {

    @Bean
    @ConditionalOnProperty(prefix = "api.hunter.springmvc",name = "enable",havingValue = "true")
    public AroundAdvice aroundAdvice(){
        return new AroundAdvice();
    }

    @Bean
    @ConditionalOnProperty(prefix = "api.hunter.springmvc",name = "enable",havingValue = "true")
    @Order(0)
    public AspectJExpressionPointcutAdvisor pointcutAdvisor(ConfigProperties configProperties,AroundAdvice aroundAdvice) {
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(configProperties.getSpringmvc().getPointcut());
        advisor.setAdvice(aroundAdvice);
        return advisor;
    }
}
