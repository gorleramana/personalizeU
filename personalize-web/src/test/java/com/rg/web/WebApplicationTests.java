package com.rg.web;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


//@SpringBootTest
class WebApplicationTests {

	/**
	 * Best practices:
	 * 
	 * Use @WebMvcTest for controller tests (loads only web layer).
	 * 
	 * Use @DataJpaTest for JPA repository tests (loads only JPA layer).
	 * 
	 * Use @MockBean to mock dependencies as needed.
	 */
	/**
	 * Step Action Add dependency spring-boot-starter-test in pom.xml 
	 * Write test Use @Test from org.junit.jupiter.api 
	 * Spring Boot test Use @SpringBootTest for integration tests 
	 * Test slices Use @WebMvcTest, @DataJpaTest for focused testing 
	 * Run tests Use IDE or mvn test/gradle test
	 */
	//@Disabled
	//@Test
	void contextLoads() {
	}

}
