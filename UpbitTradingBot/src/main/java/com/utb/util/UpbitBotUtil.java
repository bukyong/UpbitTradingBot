package com.utb.util;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.common.hash.Hashing;
import com.utb.config.UpbitBotConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Component
@RequiredArgsConstructor
@Log
public class UpbitBotUtil {
	
	// 자주 쓰는 것, 공통으로 쓰는 것들을 모아놓은 Util 클래스
	
	private final UpbitBotConfig upbitBotConfig;
	
	// 인증 토큰 생성 메서드
	public String generateAuthenticationToken(Map<String, String> params) {
	    String ACCESS_KEY = upbitBotConfig.getACCESS_KEY();
	    String SECRET_KEY = upbitBotConfig.getSECRET_KEY();

	    // 파라미터 → 쿼리스트링으로 변환
        String queryString = params.entrySet().stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("&"));

	    // SHA512 해시 생성
	    // MessageDigest -> Guava로 변경
        String queryHash = Hashing.sha512()
                .hashString(queryString, StandardCharsets.UTF_8)
                .toString();

	    // JWT 생성
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        String jwtToken = JWT.create()
                .withClaim("access_key", ACCESS_KEY)
                .withClaim("nonce", UUID.randomUUID().toString())
                .withClaim("query_hash", queryHash)
                .withClaim("query_hash_alg", "SHA512")
                .sign(algorithm);

	    return "Bearer " + jwtToken;
	}
	
    // API URL 반환
    public String getSERVER_URL() {
        return upbitBotConfig.getSERVER_URL();
    }
}
