package com.example.ribbonconsumer.hystrixCommand;

import com.example.ribbonconsumer.entity.User;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

/**
 * Hystrix命令通过继承实现
 */
public class UserCommand extends HystrixCommand<User> {

    private RestTemplate restTemplate;
    private Long id;

    public UserCommand(Setter setter, RestTemplate restTemplate, Long id) {
        super(setter);
        this.restTemplate = restTemplate;
        this.id = id;
    }

    @Override
    protected User run() throws Exception {
        return restTemplate.getForObject("http://user-service/users/{1}",User.class,id);
    }

    /**
     *  通过@HystrixCommand注解实现Hystrix命令
     *  异步执行方法
     * @param id
     * @return
     */
    public Future<User> getUserByIdAsync(final String id){
        return new AsyncResult<User>() {
            @Override
            public User invoke() {
                return restTemplate.getForObject("http://user-service/users/{1}",User.class, id);
            }
        };
    }
    public static void main(String[] args) {
        // 同步执行
        //User u = new UserCommand(restTemplate,1L).execute();

        // 异步执行：Future<User> furureUser = new UserCommand(restTemplate,1L).queue();


    }
}
