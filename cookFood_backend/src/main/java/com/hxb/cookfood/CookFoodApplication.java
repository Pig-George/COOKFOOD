package com.hxb.cookfood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication
public class CookFoodApplication {
    public static void main(String[] args) {
        SpringApplication.run(CookFoodApplication.class, args);
    }

}
