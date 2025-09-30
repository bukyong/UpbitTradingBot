package com.utb.util;

import java.util.UUID;

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
	
	// 자주 쓰는 것, 공통으로 쓰이는 것들을 모아놓은 Util 클래스
	
	private final UpbitBotConfig upbitBotConfig;
	
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	public String generateAuthenticationToken() {
		String ACCESS_KEY = upbitBotConfig.getACCESS_KEY();
		String SECRET_KEY = upbitBotConfig.getSECRET_KEY();
		
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        String jwtToken = JWT.create()
            .withClaim("access_key", ACCESS_KEY)
            .withClaim("nonce", UUID.randomUUID().toString())
            .sign(algorithm);

        return "Bearer " + jwtToken;
		
	}
}
