package com.example.externalservice.test;

import com.alibaba.fastjson.JSONObject;
import com.example.externalservice.service.AbstractExternalService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class TestExternalService extends AbstractExternalService {
    @PostConstruct
    public void init() {
       this.init("test",this.getClass());
    }

    @Override
    public String post(String url, Object[] params) {
        Foo f1 = new Foo();
        f1.setXxx("x1");
        f1.setYyy(123);
        f1.setZzz(Arrays.asList("z1", "z2", "z4"));
        Foo f2 = new Foo();
        f2.setXxx("x1");
        f2.setYyy(1223);
        f2.setZzz(Arrays.asList("z1", "z5", "z4"));

        if (url.contains("t1")) {  //假设第三方的/t1接口的返回信息为List<Foo>
            List<Foo> a = new ArrayList<>();
            a.add(f1);
            a.add(f2);
            return JSONObject.toJSONString(a);
        }

        if (url.contains("t2")) {  // /t2接口返回
            Map<String, Object> data = (Map) params[0];
            data.put("test", "this is a test");
            return "[" + JSONObject.toJSONString(data) + "]";
        }
        if (url.contains("t3")) {
            return "this is a test";
        }
        if (url.contains("t4")) {
            return JSONObject.toJSONString(f1);
        }
        if (url.contains("t5")) {
            return "[\"\",\"\"]";
        }
        if (url.contains("t6")) {
            return "2.7";
        }
        throw new IllegalArgumentException("");
    }

    @Override
    public String parseRetBody(String retBody) {
        return retBody;
    }
}
