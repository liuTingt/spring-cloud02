package com.example.feignconsumer.service;

import com.example.feignconsumer.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 通过@FeignClient注解指定服务名来绑定服务
 */
@FeignClient(value = "hello-service")
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
