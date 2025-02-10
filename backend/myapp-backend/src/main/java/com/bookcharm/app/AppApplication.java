package com.bookcharm.app;

import com.bookcharm.app.utils.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

	// create a bean of Webclient builder
	@Bean
	public WebClient.Builder createWebClientBuilder(){
		return WebClient.builder();
	}

	@Bean
	public JwtUtil createJwtUtil(){
		return new JwtUtil();
	}


}
