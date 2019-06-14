package com.leon.springDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringDemoApplication {

	public static void main(String[] args) {
		System.out.println("start app...");
		SpringApplication.run(SpringDemoApplication.class, args);
	}

}
