package com.favourite.collections.infrastructure.notification.service;

import com.favourite.collections.commons.core.config.RabbitMQConfig;
import com.favourite.collections.commons.useradmin.data.OtpMessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final RabbitTemplate rabbitTemplate;

    @Async
    @Override
    public void mailSentSuccessfully(OtpMessageRequest otpMessageRequest) {
        log.info("Sending mail from Core Service to rabbitmq : {}", otpMessageRequest);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.OTP_ROUTING_KEY, otpMessageRequest);
    }

    @Async
    @Override
    public void sendToProductService(OtpMessageRequest otpMessageRequest) {
        log.info("Sending mail from Core Service to ProductService : {}", otpMessageRequest);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.PRODUCT_SERVICE_ROUTING_KEY, otpMessageRequest);
    }
}
