package com.cephalea.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

@SpringBootTest
@ActiveProfiles("test")
class BackendCephaleaApplicationTests {


	@Autowired
	private org.springframework.core.env.Environment env;

	@Test
	void contextLoads() {
		System.out.println("Active profiles: " + Arrays.toString(env.getActiveProfiles()));
	}

}
