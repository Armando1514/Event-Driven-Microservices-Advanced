package com.food.ordering.system.order.service.messaging.publisher.kafka;

import com.food.ordering.system.kafka.order.avro.model.PaymentRequestAvroModel;
import com.food.ordering.system.kafka.producer.service.KafkaProducer;
import com.food.ordering.system.order.service.domain.config.OrderServiceConfigData;
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import com.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CancelOrderKafkaMessagePublisher implements OrderCancelledPaymentRequestMessagePublisher {
    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderServiceConfigData orderServiceConfigData;
    private final KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer;

    private final OrderKafkaMessageHelper orderKafkaMessageHelper;

    public CancelOrderKafkaMessagePublisher(OrderMessagingDataMapper orderMessagingDataMapper, OrderServiceConfigData orderServiceConfigData, KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer, OrderKafkaMessageHelper orderKafkaMessageHelper) {
        this.orderMessagingDataMapper = orderMessagingDataMapper;
        this.orderServiceConfigData = orderServiceConfigData;
        this.kafkaProducer = kafkaProducer;
        this.orderKafkaMessageHelper = orderKafkaMessageHelper;
    }


    @Override
    public void publish(OrderCancelledEvent domainEvent) {
        String orderId = domainEvent.getOrder().getId().getValue().toString();
        log.info("Received orderCreatedEvent for order id : {} ", orderId);

        try {
            PaymentRequestAvroModel paymentRequestAvroModel = orderMessagingDataMapper
                    .orderCancelledEventToPaymentRequestAvroModel(domainEvent);

            kafkaProducer.send(orderServiceConfigData.getPaymentRequestTopicName(),
                    orderId,
                    paymentRequestAvroModel,
                    orderKafkaMessageHelper.getKafkaCallback(orderServiceConfigData.getPaymentResponseTopicName(),
                            paymentRequestAvroModel,
                            orderId,
                            "paymentRequestAvroModel")
            );

            log.info("PaymentRequestAvroModel sent to Kafka for order id: {}", paymentRequestAvroModel.getOrderId());

        } catch (Exception e) {
            log.error("Error while sending PaymentRequestAvroModel sent to Kafka" +
                    " for order id: {}, with message: {}", orderId, e.getMessage());
        }
    }
}
