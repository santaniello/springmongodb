package com.example.springbootmongodbclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SpringbootmongodbclientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootmongodbclientApplication.class, args);
    }
}
