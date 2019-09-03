package com.monkey.springboot.demo.utils.ip;

import com.monkey.springboot.demo.utils.JsoupUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;

/**
 * 描述:
 * 〈IP网站查询IP〉
 *
 * @author Monkey
 * @create 2019/9/2 14:36
 */
@Slf4j
public class IPSiteUtil {
    private static final  String REQUEST_URL = "https://ip.cn/?ip=";

    //这个网址,设置超时时间5秒以下,请求超时错误概率会比较大,所以设置为10s
    private static final  Integer TIME_OUT = 10000;

    public static String  selectLocation(String ip){
        if (StringUtils.isEmpty(ip)){
            return "";
        }
        try {
            Document document = JsoupUtil.sendGet(REQUEST_URL + ip, TIME_OUT);
            if (null != document){
                String resultStr = document.toString();
                // 因为需要的数据不再html中,而是在js代码中,所以无法利用节点获取,只能用'蠢'方法...
                if (resultStr.contains("您查询的")){
                    String subStr = resultStr.substring(resultStr.indexOf("您查询的"), resultStr.indexOf("</p></div>');"));
                    String[] split = subStr.split("</code>");
                    if (split.length == 3){
                        return split[1].substring(split[1].indexOf("code")+5);
                    }
                }
            }
        }catch (Exception e){
            log.error("|【IPSite,Select IP】|Find exception|Reason{}",e);
            return "";
        }
        return "";
    }
}