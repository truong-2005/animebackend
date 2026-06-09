package com.qtanime.animebackend.controller;

import com.qtanime.animebackend.dto.common.MessageResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @GetMapping
    public MessageResponse getNotifications() {

        return MessageResponse.builder()
                .message("Danh sách thông báo")
                .build();
    }

    @PostMapping("/send")
    public MessageResponse sendNotification() {

        return MessageResponse.builder()
                .message("Gửi thông báo thành công")
                .build();
    }
}