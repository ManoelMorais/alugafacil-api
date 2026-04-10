package com.manoelalmorais.alugafacil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AlugafacilApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlugafacilApplication.class, args);
	}

}
