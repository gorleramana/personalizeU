/**
 * 
 */
package com.rg.web.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

/**
 * @author gorle
 */
@Configuration
public class OpenAPIConfiguration {
	@Bean
	public OpenAPI defineOpenApi() {
		Server server = new Server().url("http://localhost:8080").description("Development");

		Contact contact = new Contact().name("Ramana Gorli").email("gorleramana@email.com");

		Info info = new Info().title("Saving Personal Info System API").version("1.0")
				.description("This API exposes endpoints to save personal information.").contact(contact);

		return new OpenAPI().info(info).servers(List.of(server));
	}
}
