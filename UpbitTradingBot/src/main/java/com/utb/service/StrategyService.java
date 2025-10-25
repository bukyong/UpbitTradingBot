package com.utb.service;

import org.springframework.stereotype.Service;
import java.util.List;

// 이동평균선 전략 기반 매매 판단 서비스 (테스트용 전략)

@Service
public class StrategyService {

    // 단기 이동평균선이 장기 이동평균선을 상향 돌파하면 매수
    public boolean shouldBuy(List<Double> prices) {
        if (prices.size() < 10) return false;

        double shortMA = calculateMA(prices, 5);
        double longMA = calculateMA(prices, 10);

        return shortMA > longMA;
    }

    // 단기 이동평균선이 장기 이동평균선을 하향 돌파하면 매도
    public boolean shouldSell(List<Double> prices) {
        if (prices.size() < 10) return false;

        double shortMA = calculateMA(prices, 5);
        double longMA = calculateMA(prices, 10);

        return shortMA < longMA;
    }

    private double calculateMA(List<Double> prices, int period) {
        int start = Math.max(0, prices.size() - period);
        List<Double> subList = prices.subList(start, prices.size());
        return subList.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }
}
