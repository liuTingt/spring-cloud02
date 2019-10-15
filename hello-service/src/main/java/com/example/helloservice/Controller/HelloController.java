package com.example.helloservice.Controller;

import com.example.helloservice.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;


@Slf4j
@RestController
public class HelloController {

    @Autowired
    private DiscoveryClient client; // 服务发现客户端

    @Autowired
    private Registration registration;// 服务注册

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String index()  throws InterruptedException{
        // client.getLocalserviceInstance()已经过期
        ServiceInstance instance = serviceInstance();

        // 让处理线程等待几秒
        int sleepTime = new Random().nextInt(3000);
        log.info("sleepTime:"+sleepTime);
        //Thread.sleep(sleepTime);

        log.info("--------------------/hello,host:"+instance.getHost()+",serviceId:"+instance.getServiceId());
        return "Hello Word";
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

    @RequestMapping(value = "/hello1", method = RequestMethod.GET)
    public String hello(@RequestParam String name) {
        return "Hello "+ name;
    }

    @RequestMapping(value = "/hello2", method = RequestMethod.GET)
    public User hello(@RequestHeader String name, @RequestHeader Integer age){
        return new User(name,age);
    }

    @RequestMapping(value = "/hello3", method = RequestMethod.POST)
    public String hello(@RequestBody User user){
        return "Hello "+ user.getName() + "," +user.getAge();
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return "test";
    }
}
