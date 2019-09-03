package com.monkey.springboot.demo.utils.ip;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.monkey.springboot.demo.utils.JsoupUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述:
 * 〈matools IP查询〉
 *
 * @author Monkey
 * @create 2019/9/2 14:32
 */
@Slf4j
public class MTUtil {

    private static final  String REQUEST_URL ="http://www.matools.com/ip";

    private static final  Integer TIME_OUT = 30000;

    public static String  selectLocation(String ip){
        if (StringUtils.isEmpty(ip)){
            return "";
        }
        try {
            Map<String,String> map = new HashMap<>();
            map.put("inputIP", ip);
            Document document = JsoupUtil.sendPost(REQUEST_URL, TIME_OUT, map);
            if (null != document){
                Elements body = document.getElementsByTag("body");
                if (null == body){
                    return "";
                }
                String data = body.text();
                if (StringUtils.isNotEmpty(data)){
                    ResultMT resultMT = new Gson().fromJson(data, new TypeToken<ResultMT>() {
                    }.getType());
                    if (null != resultMT){
                        return resultMT.getAdr().substring(3);
                    }
                }
            }
        }catch (Exception e){
            log.error("|【MT,Select IP】|Find exception|Reason{}",e);
            return "";
        }
        return "";
    }
}

@Data
class ResultMT{
    private String ip;
    private String adr;
}