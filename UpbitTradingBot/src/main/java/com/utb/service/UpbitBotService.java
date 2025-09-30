package com.utb.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.utb.config.UpbitBotConfig;
import com.utb.util.UpbitBotUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
public class UpbitBotService {
	private final UpbitBotConfig upbitBotConfig;
	private final UpbitBotUtil upbitBotUtil;
	
	private final RestTemplate restTemplate;
	
	// 보유한 화폐들의 수량
    // 계좌 조회
    public String getAccounts() {
        String url = upbitBotConfig.getSERVER_URL() + "/v1/accounts";

        RestTemplate restTemplate = upbitBotUtil.restTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", upbitBotUtil.generateAuthenticationToken());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        return response.getBody();
    }

    // 매수 주문 (market: KRW-BTC, ord_type: limit/price/market)
    public String placeBuyOrder(String market, String volume, String price, String ordType) {
        String url = upbitBotConfig.getSERVER_URL() + "/v1/orders";

        RestTemplate restTemplate = upbitBotUtil.restTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", upbitBotUtil.generateAuthenticationToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = String.format(
                "{\"market\":\"%s\",\"side\":\"bid\",\"volume\":\"%s\",\"price\":\"%s\",\"ord_type\":\"%s\"}",
                market, volume, price, ordType
        );

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                String.class
        );

        return response.getBody();
    }

    // 매도 주문
    public String placeSellOrder(String market, String volume, String price, String ordType) {
        String url = upbitBotConfig.getSERVER_URL() + "/v1/orders";

        RestTemplate restTemplate = upbitBotUtil.restTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", upbitBotUtil.generateAuthenticationToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = String.format(
                "{\"market\":\"%s\",\"side\":\"ask\",\"volume\":\"%s\",\"price\":\"%s\",\"ord_type\":\"%s\"}",
                market, volume, price, ordType
        );

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                String.class
        );

        return response.getBody();
    }
}
