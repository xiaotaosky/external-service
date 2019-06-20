package com.example.externalservice.test;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TestService {
    @Autowired
    private MyReq myReq;

    public void test() {
        List<Foo> a = myReq.funA();
        log.info("funA :" + a.get(0).getZzz().get(2));
        Map<String, String> tt = new HashMap<>();
        tt.put("aaa", "wwer");
        List<Map<String, String>> b = myReq.funB(tt);
        log.info("funB :" + JSONObject.toJSONString(b));
        myReq.funcE();
        myReq.funcD();
        String c = myReq.funC();
        log.info("funC :" + c);
        myReq.funcF();
    }
}
