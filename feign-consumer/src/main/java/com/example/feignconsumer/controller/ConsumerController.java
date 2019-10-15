package com.example.feignconsumer.controller;

import com.example.feignconsumer.entity.User;
import com.example.feignconsumer.service.RefactorHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.feignconsumer.service.HelloService;

/***
 * 实现对Feign客户端的调用
 * 一个项目中不能使用注解@FeignClient对同一个服务绑定两次
 *
 * 通过访问feign-consumer,可以得到之前Ribbon实现时一样的效果，，根据控制台的输出，依然是利用Ribbon维护了针对hello-service的服务列表
 *  通过轮询实现了客户端负载均衡。
 *
 *  通过Feign我们只需要定义服务绑定接口，以声明式的方法，实现服务调用
 *  实现步骤：
 *      1、引入依赖
 *      2、创建接口，在接口上使用@FeignClient(value = "服务名称")，注意：不可使用@GetMapping，一般实在api服务端上
 *      3、入口程序添加注解@EnableFeignClients和EnableDiscoveryClient
 *      4、在Controller注入第二步创建的接口，调用接口方法实现调用
 */
@RestController
public class ConsumerController {

    @Autowired
    HelloService helloService;

    @Autowired
    RefactorHelloService refactorHelloService;

    @RequestMapping(value = "/feign-consumer", method = RequestMethod.GET)
    public String helloConsumer() {
        System.out.println("----------------------------------");
        return helloService.hello();
    }

    @RequestMapping(value = "/feign-consumer2", method = RequestMethod.GET)
    public String helloConsumer2() {
        StringBuilder sb = new StringBuilder();
        sb.append(helloService.hello()).append("\n");
        sb.append(helloService.hello("Carol")).append("\n");
        sb.append(helloService.hello("Carol", 34)).append("\n");
        sb.append(helloService.hello(new User("Carol", 34))).append("\n");
        return sb.toString();
    }

   @RequestMapping(value = "/feign-consumer3", method = RequestMethod.GET)
    public String helloConsumer3() throws Exception {
        System.out.println("-----------中国-----------------");
        StringBuilder sb = new StringBuilder();
        sb.append(refactorHelloService.hello("DIDI")).append("\n");
        sb.append(refactorHelloService.hello("DIDI", 34)).append("\n");
        sb.append(refactorHelloService.hello(new com.example.helloserviceapi.dto.User("DIDI",33))).append("\n");
        return sb.toString();
    }


    /*
    *

   后端使用什么语言开发，用什么架构开发，底层使用那种关系型数据库，那种非关系型数据库
    前端使用那种框架开发
   为满足客户需求，实现软件高性能和高效率

    * */
}
