package com.utb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Configuration
public class UpbitBotConfig {
	
	// Upbit의 OPEN API 키 및 서버 주소 설정을 위한 Config 클래스
	
	@Value("${UPBIT_API_ACCESS_KEY}")
	private String ACCESS_KEY;
	
	@Value("${UPBIT_API_SECRET_KEY}")
	private String SECRET_KEY;
	
    @Value("${UPBIT_API_SERVER_URL}")
    private String SERVER_URL;
}
