package com.github.abhinavrohatgi30;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
@EnableConfigurationProperties
@EnableAutoConfiguration
public class CentralizedAuthGatewayApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(CentralizedAuthGatewayApplication.class);
	}
}