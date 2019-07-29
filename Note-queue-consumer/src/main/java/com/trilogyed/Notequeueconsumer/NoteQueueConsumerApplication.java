package com.trilogyed.Notequeueconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NoteQueueConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoteQueueConsumerApplication.class, args);
	}

}
