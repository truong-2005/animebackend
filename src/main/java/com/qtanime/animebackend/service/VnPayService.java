package com.qtanime.animebackend.service;

import com.qtanime.animebackend.dto.payment.VnPayRequest;

public interface VnPayService {

    String createPaymentUrl(VnPayRequest request);

    void paymentCallback(
            String vnpResponseCode,
            String vnpTransactionNo,
            String vnpTxnRef
    );
}