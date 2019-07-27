package com.leon.springDemo;

import com.leon.springDemo.Util.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringDemoApplication {

	public static void main(String[] args) {
		System.out.println("start app...");
		ApplicationContext context =SpringApplication.run(SpringDemoApplication.class, args);
		String[] beanNames =  context.getBeanDefinitionNames();
		System.out.println("beanNames个数："+beanNames.length);
		for(String bn:beanNames){
			System.out.println(bn);
		}
		SpringContextUtil.setApplicationContext(context);
	}

}
