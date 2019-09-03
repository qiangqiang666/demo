package com.monkey.springboot.demo.utils.ip;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.monkey.springboot.demo.utils.JsoupUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * 描述:
 * 〈淘宝IP查询〉
 *
 * @author Monkey
 * @create 2019/9/2 14:25
 */
@Slf4j
public class TBUtil {

    private static final  String REQUEST_URL = "http://ip.taobao.com/service/getIpInfo.php?ip=";

    private static final  Integer TIME_OUT = 30000;

    public static String  selectLocation(String ip){
        if (StringUtils.isEmpty(ip)){
            return "";
        }
        try {
            Document document = JsoupUtil.sendGet(REQUEST_URL + ip, TIME_OUT);
            if (null != document){
                Elements body = document.getElementsByTag("body");
                if (null == body){
                    return "";
                }
                String data = body.text();
                if (StringUtils.isNotEmpty(data)){
                    ResultTB resultTB = new Gson().fromJson(data, new TypeToken<ResultTB>() {
                    }.getType());
                    //System.out.println(resultTB);
                    if (null != resultTB){
                        return resultTB.getData().getLocation();
                    }
                }
            }
        }catch (Exception e){
            log.error("|【TB,Select IP】|Find exception|Reason{}",e);
            return "";
        }
        return "";
    }
}

@Data
class ResultTB{
    private Integer code;
    private TBEntity data;
}

@Data
class TBEntity{
    private String ip;
    private String country;
    private String region;
    private String city;
    private String  isp;

    public String getLocation(){
        StringBuffer stringBuffer = new StringBuffer();
        if (StringUtils.isNotBlank(this.country)){
            stringBuffer.append(this.country);
        }
        if (StringUtils.isNotBlank(this.region)){
            stringBuffer.append(this.region);
        }
        if (StringUtils.isNotBlank(this.city)){
            stringBuffer.append(this.city);
        }
        if (StringUtils.isNotBlank(this.isp)){
            stringBuffer.append(" "+this.isp);
        }
        return stringBuffer.toString();
    }
}
