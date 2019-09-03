package com.monkey.springboot.demo.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

/**
 * 描述:
 * 〈爬虫工具类〉
 *
 * @author Monkey
 * @create 2019/9/2 15:11
 */
public class JsoupUtil {

    public static Document sendGet(String url,Integer timeOut) throws IOException {
        return Jsoup.connect(url).timeout(timeOut).ignoreContentType(true)
                .header("Accept", "application/json, text/javascript, */*; q=0.01")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept-Language", "zh-CN,zh;q=0.9")
                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.96 Safari/537.36")
                .get();
    }

    public static Document sendPost(String url, Integer timeOut, Map<String,String> map) throws IOException {
        return Jsoup.connect(url).timeout(timeOut).ignoreContentType(true)
                .header("Accept", "application/json, text/javascript, */*; q=0.01")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept-Language", "zh-CN,zh;q=0.9")
                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.96 Safari/537.36")
                .data(map)
                .post();
    }
}