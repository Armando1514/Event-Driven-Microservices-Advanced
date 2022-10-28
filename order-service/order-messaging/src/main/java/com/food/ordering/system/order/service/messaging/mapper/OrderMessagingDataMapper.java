package com.food.ordering.system.order.service.messaging.mapper;

import com.food.ordering.system.kafka.order.avro.model.*;
import com.food.ordering.system.order.service.domain.dto.message.PaymentResponse;
import com.food.ordering.system.order.service.domain.dto.message.RestaurantApprovalResponse;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderMessagingDataMapper {

    public PaymentRequestAvroModel orderCreatedEventToPaymentRequestAvroModel(OrderCreatedEvent orderCreatedEvent){
        Order order = orderCreatedEvent.getOrder();
        return PaymentRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID())
                .setSagaId(UUID.randomUUID())
                .setCustomerId(order.getCustomerId().getValue())
                .setOrderId(order.getId().getValue())
                .setPrice(order.getPrice().getAmount())
                .setCreatedAt(orderCreatedEvent.getCreatedAt().toInstant())
                .setPaymentOrderStatus(PaymentOrderStatus.PENDING)
                .build();

    }

    public PaymentRequestAvroModel orderCancelledEventToPaymentRequestAvroModel(OrderCancelledEvent orderCancelledEvent){
        Order order = orderCancelledEvent.getOrder();
        return PaymentRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID())
                .setSagaId(UUID.randomUUID())
                .setCustomerId(order.getCustomerId().getValue())
                .setOrderId(order.getId().getValue())
                .setPrice(order.getPrice().getAmount())
                .setCreatedAt(orderCancelledEvent.getCreatedAt().toInstant())
                .setPaymentOrderStatus(PaymentOrderStatus.CANCELLED)
                .build();

    }

    public RestaurantApprovalRequestAvroModel orderPaidEventToRestaurantApprovalRequestAvroModel(OrderPaidEvent orderPaidEvent){
        Order order = orderPaidEvent.getOrder();
        return RestaurantApprovalRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID())
                .setSagaId(UUID.randomUUID())
                .setOrderId(order.getId().getValue())
                .setRestaurantId(order.getRestaurantId().getValue())
                .setOrderId(order.getId().getValue())
                .setRestaurantOrderStatus(com.food.ordering.system.kafka.order.avro.model.RestaurantOrderStatus
                        .valueOf(order.getOrderStatus().name()))
                .setProducts(order.getItems().stream().map(orderItem ->
                        com.food.ordering.system.kafka.order.avro.model.Product.newBuilder()
                                .setId(orderItem.getProduct().getId().getValue().toString())
                                .setQuantity(orderItem.getQuantity())
                                .build()).collect(Collectors.toList()))
                .setPrice(order.getPrice().getAmount())
                .setCreatedAt(orderPaidEvent.getCreatedAt().toInstant())
                .setRestaurantOrderStatus(RestaurantOrderStatus.PAID)
                .build();
    }

    public PaymentResponse paymentResponseAvroModelToPaymentResponse(PaymentResponseAvroModel
                                                                             paymentResponseAvroModel) {
        return PaymentResponse.builder()
                .id(paymentResponseAvroModel.getId().toString())
                .sagaId(paymentResponseAvroModel.getSagaId().toString())
                .paymentId(paymentResponseAvroModel.getPaymentId().toString())
                .customerId(paymentResponseAvroModel.getCustomerId().toString())
                .orderId(paymentResponseAvroModel.getOrderId().toString())
                .price(paymentResponseAvroModel.getPrice())
                .createdAt(paymentResponseAvroModel.getCreatedAt())
                .paymentStatus(com.food.ordering.system.domain.valueobject.PaymentStatus.valueOf(
                        paymentResponseAvroModel.getPaymentStatus().name()))
                .failureMessages(paymentResponseAvroModel.getFailureMessages())
                .build();
    }

    public RestaurantApprovalResponse
    approvalResponseAvroModelToApprovalResponse(RestaurantApprovalResponseAvroModel
                                                        restaurantApprovalResponseAvroModel) {
        return RestaurantApprovalResponse.builder()
                .id(restaurantApprovalResponseAvroModel.getId().toString())
                .sagaId(restaurantApprovalResponseAvroModel.getSagaId().toString())
                .restaurantId(restaurantApprovalResponseAvroModel.getRestaurantId().toString())
                .orderId(restaurantApprovalResponseAvroModel.getOrderId().toString())
                .createdAt(restaurantApprovalResponseAvroModel.getCreatedAt())
                .orderApprovalStatus(com.food.ordering.system.domain.valueobject.OrderApprovalStatus.valueOf(
                        restaurantApprovalResponseAvroModel.getOrderApprovalStatus().name()))
                .failureMessages(restaurantApprovalResponseAvroModel.getFailureMessages())
                .build();
    }


}
