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
import com.monkey.springboot.demo.HttpRequest;
import com.monkey.springboot.demo.annotation.SecurityParameter;
import com.monkey.springboot.demo.annotation.RequestParam;
import com.monkey.springboot.demo.domain.Persion;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈测试加解密〉
 *
 * @author 44637
 * @create 2018/10/24
 * @since 1.0.0
 */
@Controller
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    /*
     * AES测试返回数据，会自动加密
     * @return
     */
    @GetMapping("/get")
    @ResponseBody
    public Object get() {
        Persion info = new Persion();
        info.setName("好看");
        return info;
    }

    /*
     * AES自动解密，并将返回信息加密
     * @param info
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public Object save(@RequestBody Persion info) {
        System.out.println(info.getName());
        return info;
    }

    /**
     * 跳转RSA+AES双重加密页面
     *
     * @return
     */

    @RequestMapping("/test")
    public String goTest() {
        return "recaptcha2";
    }

    /**
     * RSA+AES双重加密测试
     *
     * @return object
     */
    @RequestMapping("/testEncrypt")
    @ResponseBody
 //   @SecurityParameter
    public Persion testEncrypt(@RequestBody Persion info) {
        System.out.println(info.getName());
        String content = "内容";
        info.setName(content);
        return info;
    }

    @RequestMapping("/check")
    @ResponseBody
    public String check(HttpServletRequest request) {
        String checkCode = request.getParameter("g-recaptcha-response");
        Map<String, Object> map = new HashMap<>();
        // 私钥
        map.put("secret", "6LemoXcUAAAAAPTxnJjg6y5ygYOvMixY1ThhRFQ-");
        map.put("response", checkCode);
        String json = HttpRequest.sendPost("https://www.recaptcha.net/recaptcha/api/siteverify", map, "UTF-8");
        return json;
    }

    @ResponseBody
 //   @SecurityParameter
    @ApiOperation(value = "创建用户", notes = "根据Persion对象创建用户")
    @ApiImplicitParam(name = "Persion", value = "用户详细实体Persion", required = true, dataType = "Persion")
    @RequestMapping(value = "/check2", method = RequestMethod.POST)
    public Persion check2(
            @RequestBody Persion info, @RequestParam("pcode") String pcode
    ) {
        try {
            String checkCode = info.getCode();
            Map<String, Object> checkMap = new HashMap<>();
            // 私钥
            checkMap.put("secret", "6Lc7qXcUAAAAAH_3fhtzGp3MME3O2LC");
            checkMap.put("response", checkCode);
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
                logger.info("对方法method :【check2】进行图形验证不通过,返回结果是: " + errorInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("参数【g-recaptcha-response】验证过程中异常！");
        }
        System.out.println(pcode);
        System.out.println(info.getName());
        System.out.println(info.getPassword());
        return info;
    }

    @ResponseBody
    @SecurityParameter
    @RequestMapping(value = "/check3", method = RequestMethod.POST)
    public Persion check3(@RequestParam("name")String name, @RequestParam("password")String password, @RequestParam(required = false,defaultValue = "1") String size){
        Persion persion=new Persion();
        persion.setName(name);
        return persion;
    }

}