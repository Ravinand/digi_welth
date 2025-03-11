package com.app.digi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class DigiWealthBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigiWealthBeApplication.class, args);
		System.out.println("Start Application Digi Wealth..................!");
	}

}
