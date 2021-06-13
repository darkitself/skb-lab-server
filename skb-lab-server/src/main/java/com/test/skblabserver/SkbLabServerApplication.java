package com.test.skblabserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SkbLabServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkbLabServerApplication.class, args);
    }

}
