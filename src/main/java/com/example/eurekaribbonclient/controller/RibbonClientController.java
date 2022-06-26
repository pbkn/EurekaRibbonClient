package com.example.eurekaribbonclient.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RibbonClientController {

	@Autowired
	LoadBalancerClient loadBalancerClient;

	@GetMapping(value = { "/fetch/{localeId}", "/fetch" })
	public String getGreetings(@PathVariable(name = "localeId", required = false) String localeId)
			throws RestClientException, IOException {
		String langCode = StringUtils.isEmpty(localeId) ? "en" : localeId;
		ServiceInstance servInstance = loadBalancerClient.choose("greetings");
		String baseUrl = servInstance.getUri().toString();

		log.info("Ribbon Client Controller lba server Instance {}", baseUrl);

		baseUrl = baseUrl + "/welcome/" + langCode;
		log.info("Ribbon Client base url {}", baseUrl);

		ResponseEntity<String> response = null;
		RestTemplate restTemplate = new RestTemplate();
		try {
			response = restTemplate.exchange(baseUrl, HttpMethod.GET, getHeaders(), String.class);
		} catch (Exception e) {
			log.warn(e.getMessage());
		}
		log.info("Load Balancing Client Response {}", response != null ? response.getBody() : "");
		String result = response != null ? response.getBody() : "No Instances Available";
		return result + " from port: " + servInstance.getPort();
	}

	private static HttpEntity<?> getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return new HttpEntity<>(headers);
	}

}
