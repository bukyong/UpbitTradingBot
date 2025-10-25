package com.utb.service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.utb.util.UpbitBotUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

// 업비트 API와 통신하는 Service 클래스

@Service
@RequiredArgsConstructor
@Log
public class UpbitBotService {
	
	private final UpbitBotUtil upbitBotUtil;
	private final RestTemplate restTemplate = new RestTemplate();
	
    // 시세 조회 (테스트 용)
	// - 코인 시세를 조회
    // - @param market 예: KRW-BTC, KRW-DOGE 등등
    public String getTicker(String market) {
        try {
            String url = upbitBotUtil.getSERVER_URL() + "/v1/ticker?markets=" + market;

            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/json");

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response =
                    restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            return response.getBody();
        } catch (Exception e) {
            log.severe("시세 조회 실패 : " + e.getMessage());
            return "시세 조회 실패 : " + e.getMessage();
        }
    }
    
    // 시세 조회
	// - 코인 시세를 조회
	public double getCurrentPrice(String market) {
	    String url = upbitBotUtil.getSERVER_URL() + "/v1/ticker?markets=" + market;
	    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

	    JSONArray jsonArray = new JSONArray(response.getBody());
	    return jsonArray.getJSONObject(0).getDouble("trade_price");
	}
	
    // 계좌 조회
	// - 보유 중인 코인 및 원화 잔고를 조회
    public String getAccounts() {
        String url = upbitBotUtil.getSERVER_URL() + "/v1/accounts";
        
        // WebSocket 및 파라미터 또는 본문이 없는 REST API인증 토큰 생성
        String authToken = upbitBotUtil.generateAuthenticationToken(Collections.emptyMap());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    // 매수 주문
    // - market: KRW-BTC, ord_type: limit/price/market , side : bid (매수)
    // - 지정가(limit), 시장가(price, market) 등 다양한 유형의 매수 주문을 수행
    public String placeBuyOrder(String market, String volume, String price, String ordType) {
        String url = upbitBotUtil.getSERVER_URL() + "/v1/orders";
        
        // 삽입 순서를 보장하기 위한 LinkedHashMap
        // 실제 전송 순서와 해시 생성 순서를 일치시키기 위함
        Map<String, String> params = new LinkedHashMap<>();
        params.put("market", market);
        params.put("side", "bid"); // 매수
        params.put("volume", volume);
        params.put("price", price);
        params.put("ord_type", ordType);
        
        // 파라미터 또는 본문(Body)이 있는 REST API 인증 토큰 생성
        String authToken = upbitBotUtil.generateAuthenticationToken(params);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authToken);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        return response.getBody();
    }

    // 매도 주문
    // - market: KRW-BTC, ord_type: limit/price/market , side : ask (매도)
    // - 지정가(limit), 시장가(price, market) 등 다양한 유형의 매도 주문을 수행
    public String placeSellOrder(String market, String volume, String price, String ordType) {
        String url = upbitBotUtil.getSERVER_URL() + "/v1/orders";
        
        // 삽입 순서를 보장하기 위한 LinkedHashMap
        // 실제 전송 순서와 해시 생성 순서를 일치시키기 위함
        Map<String, String> params = new LinkedHashMap<>();
        params.put("market", market);
        params.put("side", "ask"); // 매도
        params.put("volume", volume);
        params.put("price", price);
        params.put("ord_type", ordType);
        
        // 파라미터 또는 본문(Body)이 있는 REST API 인증 토큰 생성
        String authToken = upbitBotUtil.generateAuthenticationToken(params);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authToken);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        return response.getBody();
    }
    
    // Postman에서 토큰만 생성할 때 사용 (Postman 테스트용)
//    public String createJwtToken() {
//        return upbitBotUtil.generateAuthenticationToken();
//    }
}
