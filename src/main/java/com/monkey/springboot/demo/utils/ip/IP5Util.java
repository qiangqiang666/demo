package com.monkey.springboot.demo.utils.ip;

import com.monkey.springboot.demo.utils.JsoupUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述:
 * 〈https://q.ip5.me 查询IP地址〉
 *
 * @author Monkey
 * @create 2019/9/2 16:21
 */
@Slf4j
public class IP5Util {

    private static final  String REQUEST_URL ="https://q.ip5.me/q.php";

    private static final  Integer TIME_OUT = 30000;

    // 注意: 这个参数,有可能有变化,如果不可用之后,可以直接去https://q.ip5.me网站查看源代码,查找隐藏的vcode属性值
    private static final  String V_CODE = "h2o2";

    public static String  selectLocation(String ip){
        if (StringUtils.isEmpty(ip)){
            return "";
        }
        try {
            Map<String,String> map = new HashMap<>();
            map.put("s", ip);
            map.put("vcode", V_CODE);
            Document document = JsoupUtil.sendPost(REQUEST_URL, TIME_OUT, map);
            if (null != document){
                //System.out.println(document.toString());
                Element ip_pos = document.getElementById("ip_pos");
                return ip_pos.text();
            }
        }catch (Exception e){
            log.error("|【IP5Util,Select IP】|Find exception|Reason{}",e);
            return "";
        }
        return "";
    }
}