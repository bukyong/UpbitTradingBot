package com.utb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Configuration
public class UpbitBotConfig {
	
	// Upbit의 OPEN API 키 및 서버 주소 설정을 위한 Config 클래스
	
	@Value("${UPBIT_OPEN_API_ACCESS_KEY}")
	private String ACCESS_KEY;
	
	@Value("${UPBIT_OPEN_API_SECRET_KEY}")
	private String SECRET_KEY;
	
	public static final String SERVER_URL = "https://api.upbit.com";
}
