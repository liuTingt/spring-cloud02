package com.example.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sun.misc.Request;

import javax.servlet.http.HttpServletResponse;

/***
 * 对于意外抛出的异常又会导致没有控制台输出也没有任何响应信息的情况出现，可以使用error类型的过滤器捕获
 * 这些异常信息，并根据异常信息在请求上下文中注入需要返回给客户端的错误描述
 *
 */

@Component
@Slf4j
public class ErrorFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "error";
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
        Throwable throwable = context.getThrowable();
        log.error("this is a error filter {}", throwable.getCause().getMessage());
        context.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        context.set("error.exception", throwable.getCause());
        return null;
    }
}
