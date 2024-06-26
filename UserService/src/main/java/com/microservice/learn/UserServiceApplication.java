package com.microservice.learn;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
//@EnableEurekaClient // latest version e eta dite hoyna
@EnableFeignClients
public class UserServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		try {
		    InetAddress localHost = InetAddress.getLocalHost();
		    String localIpAddress = localHost.getHostAddress();
		    System.out.println("Local IP Address: " + localIpAddress);
		} catch (UnknownHostException e) {
		    e.printStackTrace();
		}

		
	}

}
