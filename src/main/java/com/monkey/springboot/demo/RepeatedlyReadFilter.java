package com.monkey.springboot.demo;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 复制请求数据包body
 * 以提供 拦截器下 可数次获取Body数据包
 */
public class RepeatedlyReadFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest serveltRequst = (HttpServletRequest) request;
        String url = serveltRequst.getRequestURI();
        // 不拦截url包含export的请求
        if (url.contains("export")){
            chain.doFilter(request, response);
        }else{
            ServletRequest requestWrapper = null;
            if (request instanceof HttpServletRequest) {
                requestWrapper = new RepeatedlyReadRequestWrapper(serveltRequst);
            }
            if (null == requestWrapper) {
                chain.doFilter(request, response);
            } else {
                chain.doFilter(requestWrapper, response);
            }
        }
    }

    @Override
    public void destroy() {

    }
}