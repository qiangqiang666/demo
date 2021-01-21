package com.monkey.springboot.demo.utils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

/**
 * 描述:
 * 〈爬虫工具类〉
 * ps:
 * 1.关于超时问题: 最好设置为预期的一倍,比如你业务上是3s超时,那么这儿的timeOut就设置为6000
 * 参考文章: https://blog.csdn.net/wangjun5159/article/details/95312749
 * 2.关于一些 请求返回错误的问题
 * 完善header信息,或者是请求参数格式问题等等
 * 3. 关于返回值Document类
 * 参考文章: https://www.cnblogs.com/zhangyinhua/p/8037599.html
 * @author Monkey
 * @create 2019/9/2 15:11
 */
public class JsoupUtil {

    /**
     * GET请求
     * @param url 路径
     * @param timeOut 超时时间单位毫秒
     * @return
     * @throws IOException
     */
    public static Document sendGet(String url, Integer timeOut) throws IOException {
        return Jsoup.connect(url).timeout(timeOut).ignoreContentType(true)
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .header("Accept-Encoding", "gzip, deflate, sdch")
                .header("Accept-Language", "zh-CN,zh;q=0.8")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
                .get();
    }

    /**
     * POST请求,请求参数格式为: application/x-www-form-urlencoded
     * @param url 路径
     * @param timeOut 超时时间单位毫秒
     * @param map 请求参数
     * @return
     * @throws IOException
     */
    public static Document sendPost(String url, Integer timeOut, Map<String, String> map) throws IOException {
        return Jsoup.connect(url).timeout(timeOut).ignoreContentType(true)
                .header("Accept", "application/json, text/javascript, */*; q=0.01")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept-Language", "zh-CN,zh;q=0.9")
                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.96 Safari/537.36")
                .data(map)
                .post();
    }

    /**
     * POST请求,请求参数格式为: application/json
     * @param url 路径
     * @param timeOut 超时时间单位毫秒
     * @param json 请求参数json字符串
     * @return
     * @throws IOException
     */
    public static Document sendPostByBody(String url, Integer timeOut, String json) throws IOException {
        return Jsoup.connect(url).timeout(timeOut).ignoreContentType(true)
                .header("Accept", "application/json, text/javascript, */*; q=0.01")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept-Language", "zh-CN,zh;q=0.9")
                .header("Content-Type", "application/json;charset=utf-8")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.96 Safari/537.36")
                .requestBody(json)
                .post();
    }

    /**
     * POST请求,请求参数格式为: application/json
     * @param url  路径
     * @param timeOut 超时时间单位毫秒
     * @param json 请求参数json字符串
     * @param headerMap 自定义请求头信息
     * @return
     * @throws IOException
     */
    public static Document sendPostByBody(String url, Integer timeOut, String json, Map<String, String> headerMap) throws IOException {
        Connection connection = Jsoup.connect(url).timeout(timeOut).ignoreContentType(true)
                .header("Accept", "application/json, text/javascript, */*; q=0.01")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept-Language", "zh-CN,zh;q=0.9")
                .header("Content-Type", "application/json;charset=utf-8")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.96 Safari/537.36")
                .requestBody(json);
        if (headerMap.size() > 0) {
            for (String key : headerMap.keySet()) {
                connection.header(key, headerMap.get(key));
            }
        }
        return connection.post();
    }

    /**
     *  POST请求,请求参数格式为: application/json
     *  ps: 一般用于需要判断请求码的业务
     * @param url 路径
     * @param timeOut 超时时间单位毫秒
     * @param json 请求参数json字符串
     * @return
     * @throws IOException
     */
    public static Connection.Response sendPostByBodyToSatusCode(String url, Integer timeOut,String json) throws IOException {
        return Jsoup.connect(url).timeout(timeOut).ignoreContentType(true)
                .header("Accept", "application/json, text/javascript, */*; q=0.01")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept-Language", "zh-CN,zh;q=0.9")
                .header("Content-Type", "application/json;charset=utf-8")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.96 Safari/537.36")
                .requestBody(json)
                .method(Connection.Method.POST)
                .execute();
    }

    public static void main(String[] args) throws IOException {
        String url = "https://www.baidu.com";
        Document document = sendGet(url, 10000);
        System.out.println(document.toString());
    }
}