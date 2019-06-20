package com.example.externalservice.service;

import com.example.externalservice.commom.ExternalServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Retryable;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public abstract class AbstractExternalService {
   private static Map<String,Class<? extends AbstractExternalService>> keyServiceMaps = new ConcurrentHashMap<>();

    public static AbstractExternalService getService(String key) {
//        Map<String, AbstractExternalService> services = SpringApplicationContextHolder.getContext().getBeansOfType(AbstractExternalService.class);
//        Optional<AbstractExternalService> service = services.values().stream().filter(item -> key.equals(item.key)).findFirst();
//        if (service.isPresent()) {
//            return service.get();
//        }
        AbstractExternalService service =SpringApplicationContextHolder.getContext().getBean(keyServiceMaps.get(key));
        if(service == null) {
            log.error("fail to get external service, key:{}", key);
            throw new ExternalServiceException();
        }
        return service;
    }

    public abstract void init();

    public void init(String key, Class<? extends AbstractExternalService> clazz) {
        keyServiceMaps.put(key,clazz);
    }

    /**
     * 发起请求
     *
     * @param url
     * @param params
     * @return
     */
    @Retryable(value = Exception.class, maxAttempts = 3)
    public abstract String post(String url, Object[] params);

    /**
     * 从retBody中解析出返回的对象（默认json格式,其它在parseRetBody(String,Type)中转换），主要处理返回格式、解密等
     *
     * @param retBody
     * @return
     */
    public String parseRetBody(String retBody) {
        return retBody;
    }

    /**
     * 设置为false时，parseRetBody(String)方法将返回结果解析成json格式，然后回转换成对应类型的返回值；
     * 如果为true，在parseRetBody(String , Type)方法中将返回结果直接解析成对应的返回类型
     *
     * @return
     */
    public boolean useDefaultParserRetBodyStrategy() {
        return false;
    }

    /**
     * 将返回结果转换成方法返回的类型
     * @param retBody
     * @param <T>
     * @return
     */
    public <T> T parseRetBody(String retBody, Method method) {
        return null;
    }
}
