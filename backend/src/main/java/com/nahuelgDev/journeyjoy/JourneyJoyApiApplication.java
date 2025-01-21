package com.nahuelgDev.journeyjoy;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JourneyJoyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(JourneyJoyApiApplication.class, args);
	}

	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
