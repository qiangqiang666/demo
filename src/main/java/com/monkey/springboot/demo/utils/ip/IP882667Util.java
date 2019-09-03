package com.monkey.springboot.demo.utils.ip;

import com.monkey.springboot.demo.utils.JsoupUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * 描述:
 * 〈www.882667.com 查询IP〉
 *
 * @author Monkey
 * @create 2019/9/2 16:08
 */
@Slf4j
public class IP882667Util {

    private static final String REQUEST_URL = "http://www.882667.com/ip_";

    private static final Integer TIME_OUT = 30000;

    public static String selectLocation(String ip) {
        if (StringUtils.isEmpty(ip)) {
            return "";
        }
        try {
            Document document = JsoupUtil.sendGet(REQUEST_URL + ip+".html", TIME_OUT);
            //System.out.println(document.toString());
            if (null != document){
                Elements elements = document.getElementsByClass("shenlansezi");
                for (int i = 0; i < elements.size(); i++) {
                    if (i == 2){
                        return elements.get(i).text();
                    }
                }
            }
        } catch (Exception e) {
            log.error("|【IP882667Util,Select IP】|Find exception|Reason{}", e);
            return "";
        }
        return "";
    }
}