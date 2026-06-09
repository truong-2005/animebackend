package com.qtanime.animebackend.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.qtanime.animebackend.dto.payment.VnPayRequest;
import com.qtanime.animebackend.entity.Order;
import com.qtanime.animebackend.entity.Payment;
import com.qtanime.animebackend.enums.PaymentMethod;
import com.qtanime.animebackend.enums.PaymentStatus;
import com.qtanime.animebackend.exception.ResourceNotFoundException;
import com.qtanime.animebackend.repository.OrderRepository;
import com.qtanime.animebackend.repository.PaymentRepository;
import com.qtanime.animebackend.service.VnPayService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VnPayServiceImpl implements VnPayService {

    private final OrderRepository orderRepository;

    private final PaymentRepository paymentRepository;

    // =========================
    // CREATE PAYMENT URL
    // =========================

    @Override
    public String createPaymentUrl(VnPayRequest request) {

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Đơn hàng không tồn tại"
                        ));

        String txnRef = UUID.randomUUID().toString();

        Payment payment = Payment.builder()
                .order(order)
                .paymentMethod(PaymentMethod.VNPAY)
                .paymentStatus(PaymentStatus.UNPAID)
                .transactionCode(txnRef)
                .amount(request.getAmount())
                .build();

        paymentRepository.save(payment);

        return "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html"
                + "?vnp_TxnRef=" + txnRef
                + "&vnp_Amount=" + request.getAmount();
    }

    // =========================
    // PAYMENT CALLBACK
    // =========================

    @Override
    public void paymentCallback(
            String vnpResponseCode,
            String vnpTransactionNo,
            String vnpTxnRef
    ) {

        Payment payment = paymentRepository
                .findByTransactionCode(vnpTxnRef)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy giao dịch"
                        ));

        if ("00".equals(vnpResponseCode)) {

            payment.setPaymentStatus(PaymentStatus.PAID);

            Order order = payment.getOrder();

            order.setPaymentStatus(PaymentStatus.PAID);

            orderRepository.save(order);

        } else {

            payment.setPaymentStatus(PaymentStatus.FAILED);
        }

        paymentRepository.save(payment);
    }
}