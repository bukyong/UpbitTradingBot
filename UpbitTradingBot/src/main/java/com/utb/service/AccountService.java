package com.utb.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.utb.config.UpbitBotConfig;
import com.utb.util.UpbitBotUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
	private final UpbitBotUtil upbitBotUtil;
	private final RestTemplate restTemplate;
	
	// 보유한 화폐들의 수량
    public String getAccounts() {
    	Map<String, Double> balances = new HashMap<>();
    	
        String authenticationToken = upbitBotUtil.generateAuthenticationToken();

        HttpHeaders headers = new HttpHeaders();
        
        headers.set("Authorization", authenticationToken);
        headers.set("Content-Type", "application/json");

        HttpEntity entity = new HttpEntity(headers);

        String url = UpbitBotConfig.SERVER_URL + "/v1/accounts";

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        return response.getBody();
    }
}
