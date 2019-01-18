package com.monkey.springboot.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.monkey.springboot.demo.annotation.RequestParam;
import com.monkey.springboot.demo.annotation.SecurityParameter;
import com.monkey.springboot.demo.utils.AesEncryptUtils;
import com.monkey.springboot.demo.utils.RSAUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.annotation.Annotation;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * 获取用户的session dto
 */
public class MyMethodArgumentResolver implements HandlerMethodArgumentResolver {
    private static final String SERVER_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIn2zWqU7K/2qm5pOpq5bp9R+3MTnStWTfJU9nC/Vo7UKH9dITPvrELCTK+qlqpx5Fes+l0GY7n6u4n4jyiw4ejsvkZYQ5ww477yLOn2FcoEGuZEwPgSCmfTST0OFUgQqn+/J11k9L92jEHyieE3qmhMkMt0UsVUSJwx/nZxo30ZAgMBAAECgYBD3YHigeuEC4R+14iaf8jo2j0kuGtB3Cxvnlez0otTqw1YyYkBsU49cLKkXvfKVEgM0Ow/QltgKvSBxCE31PrrDka5TygVMqqA/IM7NrDvjUcGLjyoeNmLA8660fWcDxUTlAGN5kxIvUATayVwKVflpWPWu0FPKsWrZustnEo+4QJBAMCmYsWqAKWYMVRXFP3/XGRfio8DV793TOckyBSN9eh8UhgoZyT3u7oeHmDJEwm4aNMHlg1Pcdc6tNsvi1FRCiUCQQC3VNzfF4xOtUgX7vWPL8YVljLuXmy12iVYmg6ofu9l31nwM9FLQ1TRFglvF5LWrIXTQb07PgGd5DJMAQWGsqLlAkAPE7Z9M73TN+L8b8hDzJ1leZi1cpSGdoa9PEKwYR/SrxAZtefEm+LEQSEtf+8OfrEtetWCeyo0pvKKiOEFXytFAkEAgynL/DC0yXsZYUYtmYvshHU5ayFTVagFICbYZeSrEo+BoUDxdI9vl0fU6A5NmBlGhaZ65G+waG5jLc1tTrlvoQJAXBEoPcBNAosiZHQfYBwHqU6mJ9/ZacJh3MtJzGGebfEwJgtln5b154iANqNWXpySBLvkK+Boq7FYRiD83pqmUg==";

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        // content-type不是json的不处理
        if (!request.getContentType().contains("application/json")) {
            return null;
        }
        String bodyString = null;
        RepeatedlyReadRequestWrapper requestWrapper;
        if (request instanceof RepeatedlyReadRequestWrapper) {
            requestWrapper = (RepeatedlyReadRequestWrapper) request;
           bodyString = getBodyString(requestWrapper);

        }

        // 利用fastjson转换为对应的类型
        JSONObjectWrapper jsonObjectWrapper = new JSONObjectWrapper(JSON.parseObject(bodyString));
        JSONObject jsonObject = jsonObjectWrapper.getJSONObject();
        // 密文
        String data = (String) jsonObject.get("requestData");
        // 加密的ase秘钥
        String encrypted = (String) jsonObject.get("encrypted");
        String aseKey = RSAUtils.decryptDataOnJava(encrypted, SERVER_PRIVATE_KEY);
        String content = AesEncryptUtils.decrypt(data, aseKey);
        Map<String,String> requestMap = new Gson().fromJson(content,new TypeToken<Map<String,String>>() {
        }.getType());
        String value = requestMap.get(parameter.getParameterName());
        Annotation[] methodAnnotations = parameter.getParameter().getAnnotations();
        for (int i = 0; i < methodAnnotations.length; i++) {
            if (methodAnnotations[i] instanceof RequestParam){
                RequestParam requestParam = (RequestParam) methodAnnotations[i];
                boolean required = requestParam.required();
                if (required){
                    if (StringUtils.isEmpty(value)){
                        throw  new RuntimeException("参数异常");
                    }else{
                        return convertIfTypeName(parameter.getParameterType().getName(),value);
                    }
                }else{
                    if (StringUtils.isEmpty(value)){
                        return convertIfTypeName(parameter.getParameterType().getName(),requestParam.defaultValue());
                    }else{
                        return convertIfTypeName(parameter.getParameterType().getName(),value);
                    }
                }
            }
        }
        throw  new RuntimeException("参数异常");
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        SecurityParameter annotation = parameter.getMethod().getAnnotation(SecurityParameter.class);
        return annotation != null;
        //return parameter.hasParameterAnnotation(SecurityParameter.class);
    }

    public Object convertIfTypeName(String name,String value){
        if (StringUtils.isEmpty(name)){
            throw new RuntimeException("获取参数类型失败");
        }
        if (name.equals("java.lang.Double")){
            return Double.parseDouble(value);
        }else if(name.equals("java.lang.Integer")){
            return Integer.parseInt(value);
        }else{
            return value;
        }
    }
    /**
     * 获取请求Body
     *
     * @param request
     *
     * @return
     */
    public  String getBodyString(final ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = cloneInputStream(request.getInputStream());
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    /**
     * Description: 复制输入流</br>
     *
     * @param inputStream
     *
     * @return</br>
     */
    public  InputStream cloneInputStream(ServletInputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buffer)) > -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byteArrayOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        return byteArrayInputStream;
    }
}