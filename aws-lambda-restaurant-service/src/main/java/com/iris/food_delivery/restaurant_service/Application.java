package com.iris.food_delivery.restaurant_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
// We use direct @Import instead of @ComponentScan to speed up cold starts
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}