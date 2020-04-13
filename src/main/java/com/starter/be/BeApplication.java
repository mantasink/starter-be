package com.starter.be;

import com.starter.be.util.MockUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BeApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(BeApplication.class, args);
		context.getBean(MockUtils.class).mockData();
	}
}
