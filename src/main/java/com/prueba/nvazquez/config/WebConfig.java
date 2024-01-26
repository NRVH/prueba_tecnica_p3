package com.prueba.nvazquez.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("*") // Permite todos los métodos HTTP
                .allowedHeaders("*") // Permite todos los headers
                .allowCredentials(true); // Permite credenciales, como cookies y autorización HTTP
    }
}
