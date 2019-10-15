package com.example.feignconsumer.service;

import com.example.feignconsumer.config.DisableHystrixConfiguration;
import com.example.feignconsumer.entity.User;
import com.example.feignconsumer.fallback.HelloServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 通过@FeignClient注解指定服务名来绑定服务，再使用Spring MVC的注解来绑定具体该服务提供的REST接口
 *
 * 注：服务名不区分大小写
 *
 * 再2个Feign接口类中定义相同的服务名，@FeignClient(value = "名字相同")，会出现报错，如下
 * 报错：The bean 'hello-service.FeignClientSpecification', defined in null, could not be registered. A bean with that name has already been defined in null and overriding is disabled.
 *
 * 解决办法，相同的名字也就是跨服务调用，同一个服务的接口，不要分散在多个接口类中，而是放到同一个接口类中定义调用
 */
@FeignClient(value = "hello-service", configuration = DisableHystrixConfiguration.class, fallback= HelloServiceFallback.class)
//@FeignClient(value = "hello")

// 引入configuration属性实现关闭Hystrix;引入fallback属性指定对应的服务降级实现类
// @FeignClient(value = "hello-service", configuration = DisableHystrixConfiguration.class,fallback=HelloServiceFallback.class)
public interface HelloService {

    @RequestMapping("/hello")
    String hello();

    // 这里 @RequestParam、@RequestHeader的value属性不能少，否则会报错
    @RequestMapping(value = "/hello1", method = RequestMethod.GET)
    String hello(@RequestParam("name") String name);

    @RequestMapping(value = "/hello2", method = RequestMethod.GET)
    User hello(@RequestHeader("name") String name, @RequestHeader("age") Integer age);

    @RequestMapping(value = "/hello3", method = RequestMethod.POST)
    String hello(@RequestBody User user);
}
