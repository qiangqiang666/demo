package com.monkey.springboot.demo.config;

import com.monkey.springboot.demo.advice.DecodeRequestBodyAdvice;
import com.monkey.springboot.demo.advice.EncodeResponseBodyAdvice;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@EnableAutoConfiguration
@EnableConfigurationProperties
public class EncryptAutoConfiguration {

	/**
	 * 配置请求解密
	 *
	 * @return
	 */
	//@Bean
	//public DecodeRequestBodyAdvice encryptResponseBodyAdvice() {
	//	return new DecodeRequestBodyAdvice();
	//}

	/**
	 * 配置请求加密
	 *
	 * @return
	 */
	//@Bean
	//public EncodeResponseBodyAdvice encryptRequestBodyAdvice() {
	//	return new EncodeResponseBodyAdvice();
	//}
}
