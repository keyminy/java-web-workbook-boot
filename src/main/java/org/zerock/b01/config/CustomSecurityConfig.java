package org.zerock.b01.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.zerock.b01.security.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class CustomSecurityConfig {
	
	//주입 필요
	private final DataSource dataSource;
	private final CustomUserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		log.info("---------configure-------------");
		http.formLogin()
			.loginPage("/member/login"); // post 로그인 처리 요청도 해당 경로로 수행된다
		http.csrf().disable();
		
		//자동 로그인 구현
	       http.rememberMe()
           .key("12345678")
           .tokenRepository(persistentTokenRepository())
           .userDetailsService(userDetailsService)
           .tokenValiditySeconds(60*60*24*30);//30일
		
		return http.build();	
	}
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		log.info("---------web configure-------------");
		return (web) -> web.ignoring()
							.requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(dataSource);
		return repo;
	}
}