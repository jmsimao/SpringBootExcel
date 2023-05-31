package br.com.josemarcelo.SpringBootExcel.AppConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
	
	@Bean
	public String home() {
		return "C:/Stage/";
	}
}
