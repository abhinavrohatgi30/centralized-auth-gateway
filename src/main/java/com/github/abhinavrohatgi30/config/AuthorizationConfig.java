package com.github.abhinavrohatgi30.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.github.abhinavrohatgi30.models.AuthorizationPolicy;
import com.github.abhinavrohatgi30.utils.YamlPropertySourceFactory;

@Configuration
@PropertySource(factory = YamlPropertySourceFactory.class,value="classpath:authorization.yml")
@ConfigurationProperties(prefix="authorization")
public class AuthorizationConfig {

	private List<AuthorizationPolicy> policies;

	public List<AuthorizationPolicy> getPolicies() {
		return policies;
	}

	public void setPolicies(List<AuthorizationPolicy> policies) {
		this.policies = policies;
	}
}
