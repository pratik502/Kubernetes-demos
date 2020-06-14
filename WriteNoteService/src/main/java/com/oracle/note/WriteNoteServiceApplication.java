package com.oracle.note;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients   //If disabled then path-service-proxy bean will not be created and error: Cannot find bean in the WriteNoteController
public class WriteNoteServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WriteNoteServiceApplication.class, args);
	}

}
