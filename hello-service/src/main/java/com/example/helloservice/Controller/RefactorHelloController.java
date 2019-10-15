package com.example.helloservice.Controller;

import com.example.helloserviceapi.dto.User;
import com.example.helloserviceapi.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

/**
 * 构建了hello-service-api项目后，新增控制类继承HelloService接口，不用包含请求映射
 */
@Slf4j
@RestController
public class RefactorHelloController implements HelloService {

    @Autowired
    private DiscoveryClient client; // 服务发现客户端

    @Autowired
    private Registration registration;// 服务注册

    @Override
    public String hello(@RequestParam("name") String name){
       ServiceInstance instance = serviceInstance();
       // 测试超时
        int sleepTime = new Random().nextInt(3000);
        log.info("----------sleepTime:"+sleepTime);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("/hello,host:"+instance.getHost() + ",serviceId:"+instance.getServiceId());
        return "Hello "+name;
    }

    @Override
    public User hello(@RequestHeader("name") String name, @RequestHeader("age") Integer age) {
        return new User(name,age);
    }

    @Override
    public String hello(@RequestBody User user) {
        return "Hello " + user.getName() + ", " + user.getAge();
    }


    public ServiceInstance serviceInstance() {
        List<ServiceInstance> list = client.getInstances(registration.getServiceId());
        if(list != null && list.size() > 0){
            for (ServiceInstance itm: list) {
                System.out.println("serviceId is :"+ itm.getServiceId());
                if((itm.getPort() == 8081) || (itm.getPort() == 8082) || (itm.getPort() == 8080)){
                    return itm;
                }
            }
        }
        return null;
    }
}
