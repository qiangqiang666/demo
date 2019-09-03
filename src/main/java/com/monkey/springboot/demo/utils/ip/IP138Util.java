package com.monkey.springboot.demo.utils.ip;

import com.monkey.springboot.demo.utils.JsoupUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 描述:
 * 〈ip138查询IP〉
 *
 * @author Monkey
 * @create 2019/9/2 15:53
 */
@Slf4j
public class IP138Util {

    private static final String REQUEST_URL = "http://www.ip138.com/ips138.asp?action=2&ip=";

    private static final Integer TIME_OUT = 30000;

    public static String selectLocation(String ip) {
        if (StringUtils.isEmpty(ip)) {
            return "";
        }
        try {
            Document document = JsoupUtil.sendGet(REQUEST_URL + ip, TIME_OUT);
            //System.out.println(document.toString());
            if (null != document){
                Elements uls = document.getElementsByTag("ul");
                for (Element ul : uls) {
                    Elements lis = ul.getElementsByTag("li");
                    for (Element li : lis) {
                        return li.text().substring(5);
                    }
                }
            }
        } catch (Exception e) {
            log.error("|【IP138Util,Select IP】|Find exception|Reason{}", e);
            return "";
        }
        return "";
    }
}