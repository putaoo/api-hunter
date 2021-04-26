package com.wangxiaobao.apihunter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api.hunter")
@Data
public class ConfigProperties {

    /**
     * spring mvc 开关
     */
    private Springmvc springmvc;
    /**
     * restTemplate 开关
     */
    private boolean restTemplateEnable;
    /**
     * feign 开关
     */
    private boolean feignEnable;
    /**
     * retrifit2 开关
     */
    private boolean retrifit2Enable;
    /**
     * DcReporter 配置
     */
    private DcReporter dcReporter;

    @Data
    public static class Springmvc{
        private boolean enable;
        private String pointcut;
    }

    @Data
    public static class DcReporter{
        private String appName;
        private String topic;
        private String applicationName;
    }
}
