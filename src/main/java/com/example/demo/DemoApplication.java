package com.example.demo;

import com.example.demo.conf.JWTFilter;
import com.example.demo.service.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages= {"com.example.demo"} )
public class DemoApplication {
	@Autowired
	UserTokenService userTokenService;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<JWTFilter> tokenFilter(){
		FilterRegistrationBean<JWTFilter> registrationBean = new FilterRegistrationBean<>();
		JWTFilter jwtFilter = new JWTFilter();
		jwtFilter.setUserTokenService(userTokenService);

		registrationBean.setFilter(jwtFilter);
		registrationBean.addUrlPatterns("/token/*");

		return registrationBean;
	}
}

