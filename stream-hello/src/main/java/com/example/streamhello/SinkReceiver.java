package com.example.streamhello;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@EnableBinding(value = {Sink.class, SinkSender.class})
public class SinkReceiver {

    /****
     * 启动报错
     * org.springframework.beans.factory.BeanDefinitionStoreException: Invalid bean definition with name 'input' defined in com.example.streamhello.SinkSender: bean definition with this name already exists
     *
     * 分析原因：
     *    @EnableBinding(value = {Sink.class, SinkSender.class})中的Sink和SinkSender中。@Input和@Output都定义了相同的Sink.INPUT导致
     * 处理办法
     *      1、application.properties配置文件添加如下
     *          spring.cloud.stream.bindings.input.destination=raw-sensor-data
     *          spring.cloud.stream.bindings.output.destination=raw-sensor-data
     *      2、SinkSender修改@Output(Source.OUTPUT)
     */
    private static Logger logger = LoggerFactory.getLogger(SinkReceiver.class);

    @StreamListener(Sink.INPUT)
    public void receiver(Object payload) {
        logger.info("Received:" + payload);
    }
}
