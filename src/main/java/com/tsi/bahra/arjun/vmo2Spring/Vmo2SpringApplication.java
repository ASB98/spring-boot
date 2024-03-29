package com.tsi.bahra.arjun.vmo2Spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class Vmo2SpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(Vmo2SpringApplication.class, args);
	}

	//method produces a bean to be managed by the Spring container.
	//handles CORS requests across the entire application
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		//allow cookies and authentication headers to be included in cross origin requests.
		config.setAllowCredentials(true);
		config.addAllowedOriginPattern("*"); //allows all origins
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

}
