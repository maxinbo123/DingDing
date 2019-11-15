package com.example.dingding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource({ "classpath*:application*.xml"})
@SpringBootApplication
public class DingdingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DingdingApplication.class, args);
	}

}
