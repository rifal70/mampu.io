package org.example.service;

import org.example.dto.inquiryRequest;
import org.example.dto.inquiryResponse;
import org.example.dto.wdRequest;
import org.example.dto.wdResponse;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EwalletService {

    private static final Logger log = LoggerFactory.getLogger(EwalletService.class);
    @Autowired
    private UserRepository walletRepository;

    public inquiryResponse getBalance(inquiryRequest req) {
        Optional<User> wallet = walletRepository.findById(req.getId());

        log.info("id {}", req.getId());
        log.info("wallet {}", wallet);

        String userId = wallet.map(User::getUserId).orElse(null);
        double balance = wallet.map(User::getBalance).orElse(0.0);

        inquiryResponse response = new inquiryResponse();
        if (userId != null) {
            response.setResponseCode("00");
            response.setResponseMessage("inquiryBalance Success");
            response.setUserId(userId);
            response.setBalance(balance);
        } else {
            response.setResponseCode("01");
            response.setResponseMessage("User not found");
        }
        return response;
    }

    public wdResponse withdrawFunds(wdRequest req) {
        Integer id = req.getId();
        Optional<User> wallet = walletRepository.findById(id);
        wdResponse response = new wdResponse();

        if (wallet.isPresent()) {
            User userWallet = wallet.get();
            double currentBalance = userWallet.getBalance();

            if (currentBalance >= req.getAmount()) {
                double balance = currentBalance - req.getAmount();
                userWallet.setBalance(balance);
                walletRepository.save(userWallet);

                response.setResponseCode("00");
                response.setResponseMessage("Withdrawal Successful");
                response.setUserId(userWallet.getUserId());
                response.setBalance(balance);
            } else {
                response.setResponseCode("01");
                response.setResponseMessage("Insufficient Balance");
                response.setUserId(userWallet.getUserId());
                response.setBalance(currentBalance);
            }
        } else {
            response.setResponseCode("02");
            response.setResponseMessage("User not found");
        }
        return response;
    }
}
