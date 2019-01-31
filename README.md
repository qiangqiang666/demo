# 项目介绍 #
本项目采用springboot,maven管理jar包,直接下载项目,下载完依赖,就可以运行,里面包含<br>
1.AES加解密请求demo<br>
2.RSA加解密请求demo<br>
3.recaptcha V2版本demo<br>
4.recaptcha V3版本demo<br>
5.日志记录,采用自定义注解+aop实现<br>
6.敏感词工具类<br>
## 运行后访问6个页面,然后debug就可以查看如何实现以6种功能点 ##
1.(aes加解密双向)http://localhost:8080/aes<br>
2.(rsa加解密双向)http://localhost:8080/rsa<br>
3.(混合加解密双向)http://localhost:8080/rsaAes<br>
4.(混合加解密双向+recaptchaV2验证)http://localhost:8080/summary<br>
5.(V2)http://localhost:8080/recaptchaV2<br>
6.(V3)http://localhost:8080/recaptchaV2<br>
7.测试日志记录,访问第5或6或4点的链接即可在控制台查看效果<br>
8.(敏感词)http://localhost:8080/checkStr,即可在控制台看效果
