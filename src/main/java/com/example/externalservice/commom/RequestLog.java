package com.example.externalservice.commom;

import lombok.Data;

import java.util.Date;

@Data
public class RequestLog {
    private String requestId; //请求Id
    private String url; //请求url
    private String id; //请求主键（幂等校验）
    private String partnerId; //服务提供商Id
    private String serviceId; //服务编号
    private Integer retryTimes; //请求次数
    private String rawRequestContent; //原始请求报文,即发送出去的报文
    private String requestContent;  //请求内容
    private Date requestTime;  //请求时间
    private String rawResponseContent; //原始响应报文，即获取的报文
    private String responseContent; //响应内容
    private Date responseTime; //响应时间
    private String httpStatus;  //http状态
    private String status; //请求结果
}
