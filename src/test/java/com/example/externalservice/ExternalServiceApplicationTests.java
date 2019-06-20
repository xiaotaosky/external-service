package com.example.externalservice;

import com.alibaba.fastjson.JSONObject;
import com.example.externalservice.test.TestExternalService;
import com.example.externalservice.test.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Configuration
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableRetry
public class ExternalServiceApplicationTests {
    @Autowired
    private TestService testService;

    @Autowired
    private TestExternalService testExternalService;

    @Test
    public void test1() {
        testExternalService.init();
        testService.test();
    }

}
