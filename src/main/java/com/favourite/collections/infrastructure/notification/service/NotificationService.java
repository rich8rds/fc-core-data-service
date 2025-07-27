package com.favourite.collections.infrastructure.notification.service;


import com.favourite.collections.commons.useradmin.data.OtpMessageRequest;

public interface NotificationService {

    void mailSentSuccessfully(OtpMessageRequest otpMessageRequest);

    void sendToProductService(OtpMessageRequest otpMessageRequest);
}
