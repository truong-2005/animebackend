package com.qtanime.animebackend.controller;

import com.qtanime.animebackend.dto.common.MessageResponse;
import com.qtanime.animebackend.dto.payment.VnPayRequest;
import com.qtanime.animebackend.service.VnPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vnpay")
public class VnPayController {

    private final VnPayService vnPayService;

    // =========================
    // CREATE PAYMENT URL
    // =========================

    @PostMapping("/create-payment")
    public MessageResponse createPayment(
            @RequestBody VnPayRequest request
    ) {

        String paymentUrl =
                vnPayService.createPaymentUrl(request);

        return MessageResponse.builder()
                .message(paymentUrl)
                .build();
    }

    // =========================
    // CALLBACK
    // =========================

    @GetMapping("/payment-callback")
    public MessageResponse paymentCallback(

            @RequestParam String vnp_ResponseCode,

            @RequestParam String vnp_TransactionNo,

            @RequestParam String vnp_TxnRef
    ) {

        vnPayService.paymentCallback(
                vnp_ResponseCode,
                vnp_TransactionNo,
                vnp_TxnRef
        );

        return MessageResponse.builder()
                .message("Thanh toán thành công")
                .build();
    }
}