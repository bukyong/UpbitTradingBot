package com.utb.controller;

import java.io.IOException;

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
    @GetMapping("/upbit/accounts")
    public String getAccounts() throws IOException {
        return upbitBotService.getAccounts();
    }
    
    // 매수 기능
//    @PostMapping("/buy")
//    public JsonNode buyOrder(
//            @RequestParam String market,
//            @RequestParam String volume,
//            @RequestParam String price) throws Exception {
//        return upbitBotService.buyOrder(market, volume, price);
//    }
    
    // 매도 기능
//    @PostMapping("/sell")
//    public JsonNode sellOrder(
//            @RequestParam String market,
//            @RequestParam String volume,
//            @RequestParam String price) throws Exception {
//        return upbitBotService.sellOrder(market, volume, price);
//    }
    
}
