package com.business;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class BusinessProjectApplication {
    private BusinessProjectApplication() {
        // Private constructor to prevent instantiation
        throw new UnsupportedOperationException("Utility class");
    }


	public static void main(String[] args)
	{
		SpringApplication.run(BusinessProjectApplication.class, args);
	
	}

}
