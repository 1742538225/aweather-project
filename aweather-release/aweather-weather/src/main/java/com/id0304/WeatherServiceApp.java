package com.id0304;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableEurekaClient
//@EnableScheduling
public class WeatherServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(WeatherServiceApp.class,args);
    }

    @Bean
//    @LoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
