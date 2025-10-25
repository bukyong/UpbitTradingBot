package com.utb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// 주기적으로 시세를 모니터링하고 이동평균선 전략(테스트용)에 따라 매수 · 매도 로직을 수행하는 스케줄러

@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final StrategyService strategyService;
    private final RestTemplate restTemplate = new RestTemplate();
    
    // TODO : 실전 주문 모드 추가(테스트 금액만 실제 매수·매도 실행)
    
    // 테스트용 - 주요 알트코인 목록
    private static final String[] ALTCOINS = {
        "KRW-ETH", "KRW-XRP", "KRW-ADA", "KRW-DOGE", "KRW-SOL"
    };

    // 1분마다 실행 (테스트 시 10초로 변경 가능)
    @Scheduled(fixedRate = 60000)
    public void monitorMarket() {
        for (String market : ALTCOINS) {
            try {
                List<Double> prices = getRecentPrices(market);

                boolean buySignal = strategyService.shouldBuy(prices);
                boolean sellSignal = strategyService.shouldSell(prices);

                double latestPrice = prices.get(prices.size() - 1);

                System.out.printf("[모니터링] %s 현재가: %.1f / 매수: %s / 매도: %s%n",
                        market, latestPrice, buySignal, sellSignal);

                // 테스트 모드: 실제 주문 대신 로그만 출력
                if (buySignal) {
                    System.out.println("🚀 [테스트 모드] 매수 시그널 발생 - " + market);
                    // 실제 매수 주문 실행 시:
                    // upbitBotService.placeBuyOrder(market, "10", String.valueOf(latestPrice), "limit");
                } else if (sellSignal) {
                    System.out.println("💣 [테스트 모드] 매도 시그널 발생 - " + market);
                    // 실제 매도 주문 실행 시:
                    // upbitBotService.placeSellOrder(market, "10", String.valueOf(latestPrice), "limit");
                }

            } catch (Exception e) {
                System.err.println("[에러] " + market + " 모니터링 중 오류: " + e.getMessage());
            }
        }
    }

    // 최근 20개 시세 조회
    private List<Double> getRecentPrices(String market) {
        String url = "https://api.upbit.com/v1/candles/minutes/1?market=" + market + "&count=20";
        String response = restTemplate.getForObject(url, String.class);

        JSONArray arr = new JSONArray(response);
        List<Double> prices = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            prices.add(obj.getDouble("trade_price"));
        }

        // 최신 순으로 정렬
        prices.sort(Double::compare);
        return prices;
    }
}

