package com.example.apigateway.config;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * 自定义异常信息:设计符合系统的响应格式
 * 自定义类，继承DefaultErrorAttributes，然后重写getErrorAttributes方法。如果要从原来的结果中将
 * exception移除，具体实现如下。
 *
 * 最后为了让自定义错误信息生成逻辑生效，需要在主类加入代码，为其创建实例来替代默认的实现。
 *
 *
 */
public class GatewayErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> result = super.getErrorAttributes(webRequest, includeStackTrace);
        result.remove("exception");
        return result;
    }
}
