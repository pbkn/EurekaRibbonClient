package com.example.eurekaribbonclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

import com.example.eurekaribbonclient.config.RibbonConfiguration;

import lombok.extern.slf4j.Slf4j;

@RibbonClient(name = "ribbonclient", configuration = RibbonConfiguration.class)
@SpringBootApplication
@EnableEurekaClient
@Slf4j
public class EurekaRibbonClientApplication {

	@Value("${server.port}")
	private String serverPort;

	private static String serverPortStatic;

	@Value("${server.port}")
	public void setServerPortStatic(String serverPort) {
		EurekaRibbonClientApplication.serverPortStatic = serverPort;
	}

	public static void main(String[] args) {
		SpringApplication.run(EurekaRibbonClientApplication.class, args);
		log.info("Eureka Ribbon Client runnig on port: {}", serverPortStatic);
	}

}
