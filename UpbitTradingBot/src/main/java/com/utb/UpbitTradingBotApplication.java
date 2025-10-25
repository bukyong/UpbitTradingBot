package com.utb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableScheduling
public class UpbitTradingBotApplication {
	public static void main(String[] args) {
		SpringApplication.run(UpbitTradingBotApplication.class, args);
	}

}
