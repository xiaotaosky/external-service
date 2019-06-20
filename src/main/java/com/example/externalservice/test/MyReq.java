package com.example.externalservice.test;

import com.example.externalservice.anotation.ExternalMap;
import com.example.externalservice.anotation.ExternalService;

import java.util.List;
import java.util.Map;

@ExternalService(url = "test.url", service = "test")
public interface MyReq {

    @ExternalMap(url = "/t1")
    List<Foo> funA();

    @ExternalMap(url = "/t2")
    List<Map<String,String>> funB(Map<String,String> a);

    @ExternalMap(url = "t3")
    String funC();

    @ExternalMap(url = "t4")
    Foo funcD();

    @ExternalMap(url = "t5")
    List<String> funcE();

    @ExternalMap(url = "t6")
    double funcF();
}
