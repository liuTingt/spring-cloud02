package com.example.feignconsumer.fallback;

import com.example.feignconsumer.entity.User;
import com.example.feignconsumer.service.HelloService;
import org.springframework.stereotype.Component;

/**
 * 服务降级配置：为Feign客户端的定义接口编写一个具体的接口实现类。重写方法的实现逻辑用来定义相应的降级逻辑，如本类
 * 在服务绑定接口HelloService中，通过@FeignClient注解的fallback属性来指定对应的服务降级实现类
 */
@Component
public class HelloServiceFallback implements HelloService {
    @Override
    public String hello() {
        return "error";
    }

    @Override
    public String hello(String name) {
        return "error";
    }

    @Override
    public User hello(String name, Integer age) {
        return new User("未知", 0);
    }

    @Override
    public String hello(User user) {
        return "error";
    }
}
