package com.barclays.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * README
 * I'm ignoring the fact, that there might be more countries with the same value of standard VAT.
 */
@SpringBootApplication
public class BarclaysTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(BarclaysTestApplication.class, args);
	}

}
