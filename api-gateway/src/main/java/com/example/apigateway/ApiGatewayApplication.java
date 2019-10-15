package com.example.apigateway;

import com.example.apigateway.config.GatewayErrorAttributes;
import com.example.apigateway.filter.AccessFilter;
import com.example.apigateway.filter.GatewayFilterProcessor;
import com.netflix.zuul.FilterProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);

        // 启动自定义的核心处理器以完成优化目标
        FilterProcessor.setProcessor(new GatewayFilterProcessor());
    }


    @Bean
    public AccessFilter accessFilter() {
        return new AccessFilter();
    }

    /***
     * 自动化创建类似/v1/userservice/**的路由匹配规则
     * @return
     */
    @Bean
    public PatternServiceRouteMapper serviceRouteMapper(){

        /****
         * new PatternServiceRouteMapper构造参数中，servicePattern参数用来匹配服务名称是否符合该自定义规则的正则表达式
         *  第二个参数routePattern定义根据服务名中定义的内容转换出的路径表达式规则。只要符合第一个参数定义规则的服务名，都会优先使用该实现构建出来的
         *  路径表达式，若没有匹配上的服务则还是使用默认的路由映射规则，即采用完成服务名作为前缀的路径表达式。
         *
         *  Ant通配符：
         *  ?:匹配任意单个字符
         *  *：匹配任意数量的字符
         *  **：匹配任意数量的字符，支持多级目录
         *
         *
         *  由于路由规则的保存是有序的（通过LinkHashMap保存），内容的加载是通过遍历配置文件中路由规则依次加入的，所以导致当一个URL会匹配到多个路由
         *  规则的时候，会根据配置文件的顺序匹配，而不能认为设置。
         *  解决：由于properties文件内容无法保证有序，为了保证路由的优先顺序，需要使用YAML文件来配置，以实现有序的路由规则
         */
        return new PatternServiceRouteMapper("(?<name>^.+)-(?<version>V.+$)","${version}/${name}");
    }

    /**
     * 使自定义错误信息生效
     * @return
     */
    @Bean
    public DefaultErrorAttributes errorAttributes(){
        return new GatewayErrorAttributes();
    }
}
