package com.example.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.stereotype.Component;


/***
 * zuul.netflix.zuul.http.ZuulServlet的service方法定义了Zuul处理外部请求的过程。
 * Zuul处理外部请求过程时，在catch的异常处理中都会被error类型的过滤器进行处理，error类型的过滤器
 * 处理完毕之后，除了来自post阶段的异常之外，都会再被post过滤器进行处理，逻辑图（过滤器默认执行逻辑.jpg），
 *
 * 对于从post过滤器抛出异常的情况，在经过error过滤器处理之后，就没有其他的过滤器类型接手了，这就实现ErrorFilter类存在不足之处的根源。
 * 在ErrorFilter和ThrowException处理异常的方法中，非常核心的一点，在处理异常时，向请求上下文添加了一系列error.*参数，而这些参数真正起作用的
 * 地方是在post阶段的SendErrorFilter，在该过滤器中会使用这些参数来组织内容返回给客户端。而对于post阶段抛出异常的情况下，由error过滤器处理之后
 * 并不会再调用post阶段的请求，自然error.*这些参数也不会被SendErrorFilter消费输出。所以在自定义post过滤器的时候，没有正确处理异常，会出现日志中
 * 没有异常但是响应内容为空的问题。
 *
 * 解决：
 *  创建一个继承SendErrorFilter的过滤器，复用run方法，重写他的类型、顺序以及执行条件，实现对原有逻辑的复用。如本类。
 *
 *  但是，如何判断异常的过滤器来自什么阶段？shouldFilter方法该如何实现，对于这个问题，我们寄希望于请求上下文RequestContext对象，可是查阅文档
 *  和源码没有发现由存储异常来源的内容，所以我们不得不扩展原来的过滤器处理逻辑。当有异常抛出时，记录下抛出异常的过滤器，这样就可以在ErrorExtFilter
 *  过滤器的shouldFilter方法中异常来源。
 *
 *  Zuul过滤器的核心处理器：com.netflix.zuul.filterProcessor.该类中定义了过滤器调用和处理的相关核心方法。
 *  可直接扩展processZuulFilter（ZuulFilter filter），当过滤器执行抛出异常时，捕获它，并向请求上下中记录一些信息，
 *  因此创建一个FilterProcessor的子类（gatewayFilterProcessor），并重写processZuulFilter方法。
 */
@Component
public class ErrorExtFilter extends SendErrorFilter {

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 30;// 大于ErrorFilter的值
    }

    @Override
    public boolean shouldFilter() {
        // 判断仅来自post过滤器引起的异常
        RequestContext context = RequestContext.getCurrentContext();
        ZuulFilter failedFilter = (ZuulFilter) context.get("failed.filter");
        if(failedFilter != null && failedFilter.filterType().equals("post")){
            return true;
        }
        return false;
        // 在应用主类中，调用FilterProcessor.setProcessor(new GatewayFilterProcessor());
    }


}
