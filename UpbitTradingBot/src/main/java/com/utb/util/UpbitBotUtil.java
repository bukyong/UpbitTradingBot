package com.utb.util;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.utb.config.UpbitBotConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Component
@RequiredArgsConstructor
@Log
public class UpbitBotUtil {
	private final UpbitBotConfig upbitBotConfig;
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	public String generateAuthenticationToken() {
		String ACCESS_KEY = upbitBotConfig.getACCESS_KEY();
		String SECRET_KEY = upbitBotConfig.getSECRET_KEY();
		
		log.info(ACCESS_KEY);
		log.info(SECRET_KEY);
		
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        String jwtToken = JWT.create()
            .withClaim("access_key", ACCESS_KEY)
            .withClaim("nonce", UUID.randomUUID().toString())
            .sign(algorithm);

        return "Bearer " + jwtToken;
		
	}
}
