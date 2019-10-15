package com.example.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class ThrowExceptionFilter extends ZuulFilter {
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
        log.info("This is a pre filter, it will throw a RuntimeException");
        RequestContext context = RequestContext.getCurrentContext();
        try {
            doSomething();
        } catch (Exception e) {
            context.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            context.set("error.exception", e);
        }
        return null;
    }

    public void doSomething() {
        throw new RuntimeException("Exist some error...");
    }
}
