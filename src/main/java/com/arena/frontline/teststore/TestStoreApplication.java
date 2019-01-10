package com.arena.frontline.teststore;

import com.arena.frontline.teststore.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestStoreApplication {
 @Autowired

    public static void main(String[] args) {
        SpringApplication.run(TestStoreApplication.class, args);


    }
}
