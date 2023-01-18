package org.zerock.b01.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CustomServletConfig implements WebMvcConfigurer {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		/* '/js/**'로 호출하는 자원은 '/static/js/' 폴더 아래에서 찾는다. */ 
        registry.addResourceHandler("/js/**")
        .addResourceLocations("classpath:/static/js/"); 
		/* '/css/**'로 호출하는 자원은 '/static/css/' 폴더 아래에서 찾는다. */ 
        registry.addResourceHandler("/css/**")
        .addResourceLocations("classpath:/static/css/"); 
		/* '/assets/**'로 호출하는 자원은 '/static/assets/' 폴더 아래에서 찾는다. */ 
        registry.addResourceHandler("/assets/**")
        .addResourceLocations("classpath:/static/assets/"); 
		/* '/fonts/**'로 호출하는 자원은 '/static/fonts/' 폴더 아래에서 찾는다. */ 
        registry.addResourceHandler("/fonts/**")
        .addResourceLocations("classpath:/static/fonts/");
	}
}
