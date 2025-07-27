package com.favourite.collections.rabbitmq;

import com.favourite.collections.commons.core.config.RabbitMQConfig;
import com.favourite.collections.commons.useradmin.data.EmailNotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class RabbitMQListener {


    @RabbitListener(queues = RabbitMQConfig.USER_SERVICE_QUEUE)
    public void receiveMessage(EmailNotificationRequest request) {
        log.info("Received notification request (OTP): {}", request.getType());
//        this.productClientService.sendNotificationToProductService(request);
    }

}
