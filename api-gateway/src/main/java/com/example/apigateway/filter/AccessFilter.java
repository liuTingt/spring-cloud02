package com.example.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;


import javax.servlet.http.HttpServletRequest;

/**
 * 自定义过滤器，需要常见具体的Bean才能启动该过滤器，如在主应用中天添加Bean
 */
@Slf4j
public class AccessFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        log.info("send {} request to {} ",request.getMethod(),request.getRequestURL().toString());

        Object accessToken = request.getParameter("accessToken");
        if(accessToken == null){
            log.warn("access token is empty");
            context.setSendZuulResponse(false);// 令zuul过滤请求，不对其进行路由
            context.setResponseStatusCode(401);// 设置返回的错误码
           // context.setResponseBody("校验失败");
            return null;
        }
        log.info("access token ok");
        return null;
    }
}
