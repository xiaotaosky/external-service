package com.example.externalservice;

import com.example.externalservice.registar.ExternalServiceRegistar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableRetry
@Import({ExternalServiceRegistar.class})
public class ExternalServiceInit {
}
