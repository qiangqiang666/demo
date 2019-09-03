# 项目介绍 #
本项目采用springboot,maven管理jar包,直接下载项目,下载完依赖,就可以运行,里面包含<br>
1.AES加解密请求demo<br>
2.RSA加解密请求demo<br>
3.recaptcha V2版本demo<br>
4.recaptcha V3版本demo<br>
5.日志记录,采用自定义注解+aop实现<br>
6.敏感词工具类<br>
7.邮箱:发送邮件(一般用于验证码验证或者链接验证)<br>
8.导出:上传文件,导出TXT文件<br>
9.导出: 导出大量数据的excel(超速)<br>
10.查询IP地址: 自动切换查询源,目前已经有5个左右查询源!!!<br>
## 运行后访问以下链接,然后debug就可以查看如何实现以下功能 ##
1.(aes加解密双向)http://localhost:8080/aes<br>
2.(rsa加解密双向)http://localhost:8080/rsa<br>
3.(混合加解密双向)http://localhost:8080/rsaAes<br>
4.(混合加解密双向+recaptchaV2验证)http://localhost:8080/summary<br>
5.(V2)http://localhost:8080/recaptchaV2<br>
6.(V3)http://localhost:8080/recaptchaV3<br>
7.测试日志记录,访问第5或6或4点的链接即可在控制台查看效果<br>
8.(敏感词)http://localhost:8080/checkStr?str=Monkey大傻X<br>
9.(发送邮件)http://localhost:8080/sendEmail?email=446372479@qq.com<br>
10.(导出TXT)http://localhost:8080/txt<br>
11.(导出大量数据的excel(超速))http://localhost:8080/excel<br>
12.(查询IP地址)http://localhost:8080/location<br>
