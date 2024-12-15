package com.threeping.syncday;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
@EnableScheduling
@EnableAsync(proxyTargetClass = true)
public class SyncdayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SyncdayApplication.class, args);
    }

}
