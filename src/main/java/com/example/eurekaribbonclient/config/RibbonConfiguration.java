package com.example.eurekaribbonclient.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import com.netflix.loadbalancer.WeightedResponseTimeRule;

public class RibbonConfiguration {

	@Autowired
	IClientConfig ribbonClientConfig;

	@Bean
	IPing ribbonPing(IClientConfig config) {
		return new PingUrl();
	}

	@Bean
	IRule ribbonRule(IClientConfig config) {
		return new WeightedResponseTimeRule();
	}
}
