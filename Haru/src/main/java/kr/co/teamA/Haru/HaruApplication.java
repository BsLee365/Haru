package kr.co.teamA.Haru;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class HaruApplication {
	public static void main(String[] args) {
		SpringApplication.run(HaruApplication.class, args);
	}

	@Value("${server-port-url}")
	private String serverPortUrl;

	@Bean
	public WebMvcConfigurer corsConfigure() {
		return new WebMvcConfigurer() {

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins(serverPortUrl)
						.allowedHeaders("*")
						.allowCredentials(true)
						.allowedMethods("*").maxAge(3600);
			}

		};
	}



}
