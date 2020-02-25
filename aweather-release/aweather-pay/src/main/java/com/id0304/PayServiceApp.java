package com.id0304;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PayServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(PayServiceApp.class);
    }
}
