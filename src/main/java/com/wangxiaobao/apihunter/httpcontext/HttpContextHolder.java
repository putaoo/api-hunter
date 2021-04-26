package com.wangxiaobao.apihunter.httpcontext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpContextHolder {

    private HttpContextListener httpContextListener;
    private ExecutorService executorService;

    public HttpContextHolder(HttpContextListener httpContextListener) {
        this.httpContextListener = httpContextListener;
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public void publish(HttpContext httpContext){
        executorService.execute(() -> httpContextListener.listen(httpContext));
    }
}
