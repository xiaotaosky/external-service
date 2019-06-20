package com.example.externalservice.registar;

import com.example.externalservice.anotation.ExternalService;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ExternalServiceAnnotationFactoryBean<T> implements FactoryBean<T> {
    private String className;
    private Class<?> clazz;

    public void setClassName(String className) throws ClassNotFoundException {
        this.className = className;
        this.clazz = ExternalService.class.getClassLoader().loadClass(className);
    }

    @Override
    public T getObject() {
        ClassLoader classLoader = ExternalService.class.getClassLoader();
        Class[] interfaces = new Class[] { this.clazz };
        InvocationHandler proxyInstance = new ExternalServiceProxy();
        return (T) Proxy.newProxyInstance(classLoader, interfaces, proxyInstance);
    }

    @Override
    public Class<?> getObjectType() {
        return this.clazz;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
