package org.zerock.b01.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RootConfig {
	
	//@Bean으로, ModelMapper를 스프링의 빈으로 설정
	@Bean
	public ModelMapper getMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
			.setFieldMatchingEnabled(true)
			.setFieldAccessLevel(org.modelmapper.config.Configuration
									.AccessLevel.PRIVATE)
			.setMatchingStrategy(MatchingStrategies.LOOSE);
		return modelMapper;	
	}
}
