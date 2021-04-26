package com.wangxiaobao.apihunter.httpcontext;

import com.wangxiaobao.apihunter.constant.HttpFrame;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.net.URI;

@Data
@Builder
public class HttpContext {

    private HttpFrame httpFrame;

    private String requestMethod;

    private long requestTime;

    private String requestHeader;

    private String requestBody;

    private String requestParams;

    private long responseTime;

    private String responseBody;

    private HttpStatus responseStatus;

    private String url;

    private URI uri;

    private String protocol;
}
