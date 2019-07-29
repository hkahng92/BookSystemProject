package com.company.eurekaserviceregistrybooksystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer

public class EurekaServiceRegistryBookSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServiceRegistryBookSystemApplication.class, args);
	}

}
