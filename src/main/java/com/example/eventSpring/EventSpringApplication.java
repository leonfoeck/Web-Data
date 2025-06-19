package com.example.eventSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@Controller
public class EventSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventSpringApplication.class, args);
    }
}