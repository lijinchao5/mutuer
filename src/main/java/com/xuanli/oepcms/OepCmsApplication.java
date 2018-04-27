package com.xuanli.oepcms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

import com.xuanli.oepcms.config.CustomBeanNameGenerator;

@SpringBootApplication
@ComponentScan(nameGenerator=CustomBeanNameGenerator.class)
@ServletComponentScan
public class OepCmsApplication {
	public static void main(String[] args) {
		SpringApplication.run(OepCmsApplication.class, args);
	}
}
