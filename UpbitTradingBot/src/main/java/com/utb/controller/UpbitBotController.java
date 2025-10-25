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
    
    // 시세 조회 API
    // ex) GET /ticker?market=KRW-DOGE
    @GetMapping("/ticker")
    public String getTicker(@RequestParam("market") String market) {
        return upbitBotService.getTicker(market);
    }

    // 계좌 조회 API
    // ex) GET /accounts
    @GetMapping("/accounts")
    public String getAccounts() {
        return upbitBotService.getAccounts();
    }

    // 매수 주문 API
    // ex) POST /buy?market=KRW-DOGE&volume=100&price=300&ord_type=limit
    @PostMapping("/buy")
    public String buyOrder(
            @RequestParam("market") String market,
            @RequestParam("volume") String volume,
            @RequestParam("price") String price,
            @RequestParam("ord_type") String ordType
    ) throws Exception {
        return upbitBotService.placeBuyOrder(market, volume, price, ordType);
    }

    // 매도 주문 API
    // ex) POST /sell?market=KRW-DOGE&volume=100&price=300&ord_type=limit
    @PostMapping("/sell")
    public String sellOrder(
            @RequestParam("market") String market,
            @RequestParam("volume") String volume,
            @RequestParam("price") String price,
            @RequestParam("ord_type") String ordType
    ) throws Exception {
        return upbitBotService.placeSellOrder(market, volume, price, ordType);
    }
    
    // JWT 토큰만 생성 (Postman 테스트용)
//    @PostMapping("/token")
//    public ResponseEntity<String> generateJwtToken() {
//        String token = upbitBotService.createJwtToken();
//        return ResponseEntity.ok(token);
//    }
    
}
