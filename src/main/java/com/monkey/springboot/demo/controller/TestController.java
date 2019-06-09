/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: TestController
 * Author:   44637
 * Date:     2018/10/24 19:20
 * Description: 测试加解密
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.monkey.springboot.demo.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.monkey.springboot.demo.utils.*;
import com.monkey.springboot.demo.annotation.AesSecurityParameter;
import com.monkey.springboot.demo.annotation.MyLog;
import com.monkey.springboot.demo.annotation.RsaSecurityParameter;
import com.monkey.springboot.demo.annotation.SecurityParameter;
import com.monkey.springboot.demo.domain.Persion;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈测试加解密和谷歌验证〉
 *
 * @author 44637
 * @create 2018/10/24
 * @since 1.0.0
 */
@Controller
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private SendEmailUtils sendEmailUtils;

    // 跳转rsa页面
    @GetMapping("/rsa")
    public Object getRsa() {
        return "rsa";
    }

    // 跳转aes页面
    @GetMapping("/aes")
    public Object getAes() {
        return "aes";
    }

    // 跳转rsa+aes页面
    @RequestMapping("/rsaAes")
    public Object getRsaAes() {
        return "rsaAes";
    }

    // 跳转summary页面
    @RequestMapping("/summary")
    public Object getSummary() {
        return "summary";
    }

    // 跳转recaptchaV2页面
    @RequestMapping("/recaptchaV2")
    public Object getRecaptchaV2() {
        return "recaptchaV2";
    }

    // 跳转recaptchaV3页面
    @RequestMapping("/recaptchaV3")
    public Object getRecaptchaV3() {
        return "recaptchaV3";
    }

    // 跳转TXT页面
    @RequestMapping("/txt")
    public Object getTxt() {
        return "txt";
    }

    // 跳转excel页面
    @RequestMapping("/excel")
    public Object getExcel() {
        return "excel";
    }

    /**
     * AES加密测试
     *
     * @return object
     */
    @RequestMapping("/testAesEncrypt")
    @ResponseBody
    @AesSecurityParameter
    public Persion testAesEncrypt(@RequestBody Persion info) {
        return info;
    }

    /**
     * RSA加密测试
     *
     * @return object
     */
    @RequestMapping("/testRsaEncrypt")
    @ResponseBody
    @RsaSecurityParameter
    public Persion testRsaEncrypt(@RequestBody Persion info) {
        return info;
    }

    /**
     * RSA+AES双重加密测试
     *
     * @return object
     */
    @RequestMapping("/testEncrypt")
    @ResponseBody
    @SecurityParameter
    public Persion testEncrypt(@RequestBody Persion info) {
        return info;
    }

    /**
     * 综合测试
     *
     * @param persion
     * @return
     */
    @MyLog(value = "综合测试记录")
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    @SecurityParameter
    @ResponseBody
    public Persion check(@RequestBody Persion persion) {
        try {
            Map<String, Object> checkMap = new HashMap<>();
            // 私钥
            checkMap.put("secret", "6Lc7qXcUAAAAAH_3fhtzGp3MME3O2LC4QO3phFHS");
            checkMap.put("response", persion.getCode());
            String json = HttpRequest.sendPost("https://www.recaptcha.net/recaptcha/api/siteverify", checkMap, "UTF-8");
            Map<String, Object> resultMap = new Gson().fromJson(json, new TypeToken<Map<String, Object>>() {
            }.getType());
            System.out.println(json);
            boolean resultCode = (boolean) resultMap.get("success");
            if (!resultCode) {
                String errorCode = resultMap.get("error-codes").toString();
                String errorInfo = null;
                if (StringUtils.isEmpty(errorCode)) {
                    errorInfo = errorCode;
                } else if (errorCode.contains("missing-input-secret")) {
                    errorInfo = "私钥参数丢失了。";
                } else if (errorCode.contains("invalid-input-secret")) {
                    errorInfo = "私钥参数无效或格式不正确。";
                } else if (errorCode.contains("missing-input-response")) {
                    errorInfo = "响应参数缺失。";
                } else if (errorCode.contains("invalid-input-response")) {
                    errorInfo = "响应参数无效或格式不正确。";
                } else if (errorCode.contains("bad-request")) {
                    errorInfo = "请求无效或格式不正确。";
                }
                logger.info("对方法method :【check】进行图形验证不通过,返回结果是: " + errorInfo);
            }
            return persion;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("参数【g-recaptcha-response】验证过程中异常！");
        }
    }

    @MyLog(value = "谷歌recaptchaV2验证记录")
    @RequestMapping("/checkV2")
    @ResponseBody
    public String checkV2(String token) {
        Map<String, Object> map = new HashMap<>();
        // 私钥
        map.put("secret", "6Lc7qXcUAAAAAH_3fhtzGp3MME3O2LC4QO3phFHS");
        map.put("response", token);
        String json = HttpRequest.sendPost("https://www.recaptcha.net/recaptcha/api/siteverify", map, "UTF-8");
        return json;
    }

    @MyLog(value = "谷歌recaptchaV3验证记录")
    @RequestMapping("/checkV3")
    @ResponseBody
    public String checkV3(String token) {
        Map<String, Object> map = new HashMap<>();
        // 私钥
        map.put("secret", "6LdBgIoUAAAAAMyFObm3oUzGUot7VJwfMCtu15fr");
        map.put("response", token);
        String json = HttpRequest.sendPost("https://www.recaptcha.net/recaptcha/api/siteverify", map, "UTF-8");
        return json;
    }

    @MyLog(value = "敏感词验证记录")
    @RequestMapping("/checkStr")
    @ResponseBody
    public String checkStr(String str) {
        System.out.println("替换敏感词: " + BadWordUtil.replaceBadWord(str, 2, "*"));
        System.out.println("是否包含敏感词: " + BadWordUtil.isContaintBadWord(str, 2));
        Set<String> set = BadWordUtil.getBadWord(str, 2);
        System.out.println("敏感词汇个数: " + set.size());
        System.out.println("敏感词汇: " + BadWordUtil.getBadWord(str, 2));
        return str;
    }

    @MyLog(value = "发送邮件记录")
    @GetMapping("/sendEmail")
    @ResponseBody
    public void sendEmail(String email) throws Exception {
        sendEmailUtils.send(email);
    }

    @MyLog(value = "导出TXT文件")
    @PostMapping(value = "/exportTxt")
    public void exportTxt(@RequestParam("file") MultipartFile file, HttpServletResponse response) {
        try {
            InputStream inputStream = file.getInputStream();
            /* 读入TXT文件 */
            InputStreamReader reader = new InputStreamReader(
                    inputStream, "GBK"); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            StringBuffer stringBuffer = new StringBuffer();
            String line = "";
            boolean flag = true;
            while (flag) {
                line = br.readLine(); // 一次读入一行数据
                if (StringUtils.isEmpty(line)) {
                    flag = false;
                } else {
                    stringBuffer.append(line + "\r\n");
                }
            }
            //导出
            ExportTextUtil.writeToTxt(response, stringBuffer.toString(), "XXX");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @MyLog(value = "导出数据量大的Excel文件")
    @PostMapping(value = "/exportExcel")
    public void exportExcel(@RequestParam("file") MultipartFile file, HttpServletResponse response) {
        try {
            List<Persion> list = new ArrayList<>();
            for (int i = 0; i < 10000; i++) {
                Persion persion = new Persion();
                persion.setName("Monkey");
                persion.setPassword("111111");
                persion.setCode("11111");
                list.add(persion);
            }
            /**
             * 导出
             */
            Map<String,String> map = new HashMap<>();
            map.put("name", "姓名");
            map.put("password", "密码");
            ExportExcelUtil.outPutToBigExcel(list,"人",map, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}