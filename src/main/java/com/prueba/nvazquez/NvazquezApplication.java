package com.prueba.nvazquez;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.prueba.nvazquez.entity")
public class NvazquezApplication {
	public static void main(String[] args) {
		SpringApplication.run(NvazquezApplication.class, args);
	}
}
