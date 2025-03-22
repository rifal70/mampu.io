package org.example.controller;

import org.example.dto.inquiryRequest;
import org.example.dto.inquiryResponse;
import org.example.dto.wdRequest;
import org.example.dto.wdResponse;
import org.example.repository.UserRepository;
import org.example.service.EwalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ewallet")
public class EwalletController {

    @Autowired
    private EwalletService walletService;
    @Autowired
    private UserRepository userWalletRepository;

    @PostMapping("/balance")
    public inquiryResponse getBalance(@RequestBody inquiryRequest req) {
        return walletService.getBalance(req);
    }

    @PostMapping("/withdraw")
    public wdResponse withdraw(@RequestBody wdRequest req) {
        return walletService.withdrawFunds(req);
    }
}
