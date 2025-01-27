package com.raon.example.batch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.prometheus.client.exporter.PushGateway;

@SpringBootApplication
public class BatchExampleApplication {
	public static void main(String[] args) {
		SpringApplication.run(BatchExampleApplication.class, args);
	}

	@Bean
	public PushGateway pushGateway(
		@Value("${prometheus.pushgateway.url:localhost:9091}") String url) {
		return new PushGateway(url);
	}
}
