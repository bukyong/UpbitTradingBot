package com.utb.strategy;

import java.util.List;

import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

// 현재 시세를 입력받아 매수/매도 여부를 판단하는 Strategy (전략) 클래스
// 이후 이동평균선, RSI, 거래량 기반 전략 등으로 확장 가능

@Component
@Slf4j
public class UpbitBotStrategy {

    // 이동평균선을 계산하는 메서드
    public double calculateMovingAverage(List<Double> prices, int period) {
        if (prices.size() < period) return 0;
        return prices.subList(prices.size() - period, prices.size())
                     .stream()
                     .mapToDouble(Double::doubleValue)
                     .average()
                     .orElse(0);
    }

    // 매매 판단 로직
    public String decideAction(String market, List<Double> prices) {
        double shortMA = calculateMovingAverage(prices, 5);
        double longMA = calculateMovingAverage(prices, 20);
        double currentPrice = prices.get(prices.size() - 1);

        log.info("[{}] 현재가: {}, 단기MA(5): {}, 장기MA(20): {}", market, currentPrice, shortMA, longMA);

        if (shortMA == 0 || longMA == 0) return "대기";

        if (shortMA > longMA && currentPrice > shortMA) {
            return "매수 신호";
        } else if (shortMA < longMA && currentPrice < longMA) {
            return "매도 신호";
        } else {
            return "유지";
        }
    }
}

