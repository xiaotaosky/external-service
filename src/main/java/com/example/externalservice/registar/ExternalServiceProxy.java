package com.example.externalservice.registar;

import com.alibaba.fastjson.JSONObject;
import com.example.externalservice.anotation.ExternalMap;
import com.example.externalservice.anotation.ExternalService;
import com.example.externalservice.commom.ExternalServiceException;
import com.example.externalservice.service.AbstractExternalService;
import com.example.externalservice.service.SpringApplicationContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class ExternalServiceProxy implements InvocationHandler {
    private static Logger log = LoggerFactory.getLogger(ExternalServiceProxy.class);

    public Object invoke(Object proxy, Method method, Object[] args) {
        ExternalService classAnnotation = method.getDeclaringClass().getAnnotation(ExternalService.class);
        ExternalMap methodAnnotation = method.getAnnotation(ExternalMap.class);
        if (classAnnotation != null && methodAnnotation != null) {
            AbstractExternalService service = AbstractExternalService.getService(classAnnotation.service());
            String url = getUrl(classAnnotation.url(), methodAnnotation.url());

            log.info("post start, url:{}, params:{}", url, JSONObject.toJSONString(args));
            String retBody = service.post(url, args); // 各个服务具体处理如何取请求（网络配置、参数处理等）
            log.info("post return body :{}", retBody);
            if (service.useDefaultParserRetBodyStrategy()) { //在service类处理返回结果
                return service.parseRetBody(retBody, method);
            }
            String retData = service.parseRetBody(retBody);// 从返回结果中提取返回数据（数据转换、解密等）

            Type genericReturnType = method.getGenericReturnType();
            Class<?> returnType = method.getReturnType();
            if (genericReturnType.getTypeName().equals("void")) {
                return null;
            }
            if (genericReturnType.getTypeName().equals("java.lang.String")) {
                return retData;
            }
            if ((genericReturnType instanceof Class) && (returnType.isPrimitive())) {
                try {
                    Class boxClazz = Class.forName("java.lang." + returnType.getName().substring(0, 1).toUpperCase() + returnType.getName().substring(1));
                    Method valueOf = boxClazz.getMethod("valueOf", new Class[]{String.class});
                    return valueOf.invoke(boxClazz, retData);
                } catch (Exception e) {
                    log.error("get valueOf error", e);
                    throw new ExternalServiceException(e.getMessage());
                }
            } else {
                return JSONObject.parseObject(retData, genericReturnType);
            }
        }
        return null;
    }

    private String getUrl(String parentUrlConfig, String childUrl) {
        Environment env = (Environment) SpringApplicationContextHolder.getContext().getBean("environment");
        String parentUrl = env.getProperty(parentUrlConfig);
        boolean purlTag = parentUrl.endsWith("/");
        boolean curlTag = childUrl.startsWith("/");
        if (purlTag && curlTag) {
            return parentUrl + childUrl.substring(1);
        } else if (purlTag || curlTag) {
            return parentUrl + childUrl;
        } else {
            return parentUrl + "/" + childUrl;
        }
    }
}
