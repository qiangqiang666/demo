package com.monkey.springboot.demo.utils.ip;

import lombok.extern.slf4j.Slf4j;

/**
 * 描述:
 * 〈新浪IP查询〉
 *ps: 时好时坏,不伺候了
 * @author Monkey
 * @create 2019/9/2 15:43
 */
@Slf4j
public class XLUtil {

    private static final  String REQUEST_URL ="http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=";

    private static final  Integer TIME_OUT = 10000;

    public static String  selectLocation(String ip){
//        if (StringUtils.isEmpty(ip)){
//            return "";
//        }
//        try {
//            Document document = JsoupUtil.sendGet(REQUEST_URL+ip, TIME_OUT);
//            System.out.println(document.toString());
//        }catch (Exception e){
//            log.error("|【MT,Select IP】|Find exception|Reason{}",e);
//            return "";
//        }
        return "";
    }
}