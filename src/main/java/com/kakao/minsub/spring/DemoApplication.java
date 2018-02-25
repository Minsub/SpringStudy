package com.kakao.minsub.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@EnableCaching
@SpringBootApplication(
		scanBasePackages = {
				"com.kakao.minsub.spring.config","com.kakao.minsub.spring.controller"
})
@ComponentScan({
		"com.kakao.minsub.spring"
})
public class DemoApplication extends SpringBootServletInitializer{


	public static void main(String[] args) {
		if (System.getProperty("spring.profiles.active") == null) {
			System.setProperty("spring.profiles.active", "development");
		}

		SpringApplication.run(DemoApplication.class, args);
	}
}
