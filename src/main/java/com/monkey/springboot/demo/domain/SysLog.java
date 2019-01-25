package com.monkey.springboot.demo.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志记录
 */
@Data
public class SysLog implements Serializable {
    private Long id;
 
    private String username; //用户名
 
    private String operation; //操作
 
    private String method; //方法名
 
    private String params; //参数
 
    private String ip; //ip地址
 
    private Date createDate; //操作时间
}