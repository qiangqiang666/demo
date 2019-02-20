package com.monkey.springboot.demo.utils;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 邮件工具类
 * smtp服务器,支持非ssl协议25端口,和ssl协议465端口
 * 如果硬件服务器可以采用25端口,来发送邮件
 * 如果采用阿里云ESC服务器,默认25端口是不开放,添加安全组规则25端口也没用,必须采用465端口
 * 如果有问题,请查看https://www.cnblogs.com/hoojjack/p/8025284.html网址
 * @author Monkey
 * @version 1.0
 * @date 2019年02月20日上午15:01:15
 */
@Component
public class SendEmailUtils {

    //这一套采用25端口
//	public static final String HOST = "smtp.163.com";
//	public static final String PROTOCOL = "smtp";
//	public static final String PORT = "25";
//	public static final String FROM = "xxx@163.com";// 发件人的email
//	public static final String PWD = "xxx";// 发件人密码
    /*
     * 这一套采用465端口
	 */
    //邮箱主服务器地址
    @Value("${spring.mail.host}")
    private String EMA_HOST;

    //邮箱端口
    @Value("${spring.mail.port}")
    private String EMA_PORT;

    //邮箱发件人
    @Value("${spring.mail.username}")
    private String EMA_FROM;

    //邮箱发件人密码
    @Value("${spring.mail.password}")
    private String EMA_PWD;

    //邮箱发件协议
    @Value("${spring.mail.properties.mail.smtp.socketFactory.class}")
    private String EMA_SSL_FACTORY;



    /**
     * 获取Session
     *
     * @return
     */
    private Session getSession() throws Exception{
        Properties props = new Properties();
        //这一套是25端口
//		props.put("mail.smtp.host", HOST);// 设置服务器地址
//		props.put("mail.store.protocol", PROTOCOL);// 设置协议
//		props.put("mail.smtp.port",PORT);// 设置端口
//		props.put("mail.smtp.auth", "true");//注意是字符串的true,不是boolean类型的true

        //这一套是465端口
        props.setProperty("mail.smtp.host", EMA_HOST);// 设置服务器地址
        props.setProperty("mail.smtp.socketFactory.class", EMA_SSL_FACTORY);// 设置协议
        props.setProperty("mail.smtp.socketFactory.fallback", "false");//注意是字符串的false,不是boolean类型的false
        props.setProperty("mail.smtp.port", EMA_PORT);// 设置端口
        props.setProperty("mail.smtp.socketFactory.port", EMA_PORT);
        props.put("mail.smtp.auth", "true");//注意是字符串的true,不是boolean类型的true
        props.put("mail.smtp.ssl.enable", "true");//注意是字符串的true,不是boolean类型的true
        Authenticator authenticator = new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMA_FROM, EMA_PWD);
            }

        };
        Session session = Session.getDefaultInstance(props, authenticator);

        return session;
    }

    /**
     * @param toEmail
     * @return void
     * @throws Exception
     * @Title: send
     * @Description: TODO 发送邮箱验证码
     */
    public void send(String toEmail) throws Exception {
        //获取验证码
        String captcha = String.valueOf((int) (Math.random() * (999999 - 100000 + 1)) + 100000);//产生100000-999999的随机数
        try {
            /// 邮件的内容
            StringBuffer sb = new StringBuffer();
            sb.append("&nbsp;&nbsp;&nbsp;您好:</br>");
            sb.append("&nbsp;&nbsp;&nbsp;【XX】验证码：<span style='color: blue;font-size: 18px;'>" + captcha
                    + "</span>。有效时间5分钟!");
            sb.append("</br>");
            sb.append("&nbsp;&nbsp;&nbsp; 验证码仅用于【XX】XXX，如果非本人操作，请不要在任何地方输入该验证码并立即修改登录密码！。</br>");
            sb.append("&nbsp;&nbsp;&nbsp;为防止非法诈骗，请勿将帐号、密码、验证码在除XX外任何其他第三方网站输入。。</br>");
            sb.append("&nbsp;&nbsp;&nbsp;" + DateFormatUtil.Now());
            Session session = getSession();
            // Instantiate a message
            Message msg = new MimeMessage(session);
            // Set message attributes
            msg.setFrom(new InternetAddress(EMA_FROM));
            InternetAddress[] address = {new InternetAddress(toEmail)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject("【XXX】通行证");
            msg.setSentDate(new Date());
            msg.setContent(sb.toString(), "text/html;charset=utf-8");
            // Send the message
            Transport.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("请替换配置文件中的主机名称和账号密码,目前写的公共账号,暂时发送不了!!!");
        }
    }
}