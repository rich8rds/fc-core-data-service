package com.favourite.collections.infrastructure.notification.api;

import com.favourite.collections.commons.useradmin.data.OtpMessageRequest;
import com.favourite.collections.infrastructure.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/states")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<String> sendEmailTest(@RequestBody OtpMessageRequest otpMessageRequest) {
        this.notificationService.mailSentSuccessfully(otpMessageRequest);
        return ResponseEntity.ok("Mail Sent, I Guess!");

    }

    @PostMapping("/product-service")
    public ResponseEntity<String> productService(@RequestBody OtpMessageRequest otpMessageRequest) {
        this.notificationService.sendToProductService(otpMessageRequest);
        return ResponseEntity.ok("Mail Sent to productService, I Guess!");

    }
}
