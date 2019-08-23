package com.example.helloserviceapi.service;

import com.example.helloserviceapi.dto.User;
import org.springframework.web.bind.annotation.*;

/**
 * 通过@FeignClient注解指定服务名来绑定服务
 */

@RequestMapping("/refactor")
public interface HelloService {


    // 这里 @RequestParam、@RequestHeader的value属性不能少，否则会报错
    @RequestMapping(value = "/hello4", method = RequestMethod.GET)
    String hello(@RequestParam("name") String name);

    @RequestMapping(value = "/hello5", method = RequestMethod.GET)
    User hello(@RequestHeader("name") String name, @RequestHeader("age") Integer age);

    @RequestMapping(value = "/hello6", method = RequestMethod.POST)
    String hello(@RequestBody User user);
}
