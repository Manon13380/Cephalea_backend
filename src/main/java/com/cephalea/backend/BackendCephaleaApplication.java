package com.cephalea.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class BackendCephaleaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BackendCephaleaApplication.class, args);
	}

	public void run(String... args) {
		System.out.println("Bienvenue sur Cephalea 🚀");
	}
}
