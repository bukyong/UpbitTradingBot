package com.utb.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utb.service.UpbitBotService;
import com.utb.strategy.UpbitBotStrategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// UpbitBotScheduler
// 5초마다 업비트 시세를 조회하고 설정한 전략에 따라 매수 · 매도 신호 발생 시 주문 수행

@Component
@RequiredArgsConstructor
@Slf4j
public class UpbitBotScheduler {

    private final UpbitBotService upbitBotService;
    private final UpbitBotStrategy upbitBotStrategy;

    // 모니터링 대상 코인 목록
    private final List<String> marketList = Arrays.asList("KRW-ETH", "KRW-SOL", "KRW-XRP", "KRW-DOGE");

    // 코인별 시세 데이터 저장
    private final Map<String, List<Double>> priceHistory = new HashMap<>();

    // 10초마다 시세 조회 및 전략 판단
    @Scheduled(fixedRate = 10000)
    public void monitorMarkets() {
        log.info("=== 업비트 알트코인 시세 모니터링 시작 ===");

        for (String market : marketList) {
            try {
                double currentPrice = upbitBotService.getCurrentPrice(market);
                priceHistory.putIfAbsent(market, new ArrayList<>());

                List<Double> prices = priceHistory.get(market);
                prices.add(currentPrice);
                if (prices.size() > 100) prices.remove(0); // 메모리 관리

                String action = upbitBotStrategy.decideAction(market, prices);
                log.info("[{}] 전략 판단 결과 → {}", market, action);

            } catch (Exception e) {
                log.error("[{}] 시세 조회 실패: {}", market, e.getMessage());
            }
        }

        log.info("=== 모니터링 종료 ===");
    }
}

