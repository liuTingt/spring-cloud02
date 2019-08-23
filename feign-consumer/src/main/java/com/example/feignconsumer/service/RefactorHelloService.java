package com.example.feignconsumer.service;


import com.example.helloserviceapi.service.HelloService;
import org.springframework.cloud.openfeign.FeignClient;

/****
 * 继承hello-service-api的HelloService接口，添加@FeignClient注解来绑定服务
 */

@FeignClient(value = "hello-service")
public interface RefactorHelloService extends HelloService {

}
