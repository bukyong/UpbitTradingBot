package com.utb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.utb.service.UpbitBotService;

@RestController
public class UpbitBotController {

    private final UpbitBotService upbitBotService;

    public UpbitBotController(UpbitBotService upbitBotService) {
        this.upbitBotService = upbitBotService;
    }

    // 보유한 화폐 수량 확인 기능 (계좌 조회)
    @GetMapping("/accounts")
    public String getAccounts() {
        return upbitBotService.getAccounts();
    }

    // 매수 주문
    @PostMapping("/buy")
    public String buyOrder(
            @RequestParam String market,
            @RequestParam String volume,
            @RequestParam String price,
            @RequestParam String ordType
    ) {
        return upbitBotService.placeBuyOrder(market, volume, price, ordType);
    }

    // 매도 주문
    @PostMapping("/sell")
    public String sellOrder(
            @RequestParam String market,
            @RequestParam String volume,
            @RequestParam String price,
            @RequestParam String ordType
    ) {
        return upbitBotService.placeSellOrder(market, volume, price, ordType);
    }
    
}
