package com.utb.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utb.service.AccountService;
import com.utb.service.UpbitBotService;

@RestController
public class UpbitBotController {

    //private final UpbitBotService upbitBotService;
    private final AccountService accountService;

    public UpbitBotController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/upbit/accounts")
    public String getAccounts() throws IOException {
        return accountService.getAccounts();
    }
}
