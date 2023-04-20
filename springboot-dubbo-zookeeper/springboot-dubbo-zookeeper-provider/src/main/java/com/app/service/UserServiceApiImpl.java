
package com.app.service;


import com.app.api.UserServiceApi;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * dubbo接口实现
 */
@DubboService
public class UserServiceApiImpl implements UserServiceApi {

    @Override
    public String sayHello(String name) {
        return "Hello, " + name;
    }
}