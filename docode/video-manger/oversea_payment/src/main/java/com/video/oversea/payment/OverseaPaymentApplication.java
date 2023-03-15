package com.video.oversea.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class OverseaPaymentApplication {

    public static void main(String[] args) {
        SpringApplication.run(OverseaPaymentApplication.class, args);
    }
}
