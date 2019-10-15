package com.example.configclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope // 开启更新功能
@RestController
public class TestController {

    @Value("${from}")
    private String from;

    @RequestMapping(value = "/from1")
    public String from(){
        return this.from;
    }


    @Autowired
    private Environment environment;

    /***
     * 除了@Value注解绑定注入之外，也可以通过Environment对象来获取配置属性
     * @return
     */
    @RequestMapping(value = "/from2")
    public String from1() {
        return environment.getProperty("from","undefined");
    }
}
