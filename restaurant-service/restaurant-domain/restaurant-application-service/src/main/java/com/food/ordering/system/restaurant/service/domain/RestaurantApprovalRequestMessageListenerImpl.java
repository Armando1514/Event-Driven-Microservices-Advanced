package com.food.ordering.system.restaurant.service.domain;


import com.food.ordering.system.restaurant.service.domain.dto.RestaurantApprovalRequest;
import com.food.ordering.system.restaurant.service.domain.event.OrderApprovalEvent;
import com.food.ordering.system.restaurant.service.domain.event.OrderApprovedEvent;
import com.food.ordering.system.restaurant.service.domain.event.OrderRejectedEvent;
import com.food.ordering.system.restaurant.service.domain.ports.input.message.listener.RestaurantApprovalRequestMessageListener;
import com.food.ordering.system.restaurant.service.domain.ports.output.message.publisher.OrderApprovedMessagePublisher;
import com.food.ordering.system.restaurant.service.domain.ports.output.message.publisher.OrderRejectedMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RestaurantApprovalRequestMessageListenerImpl implements RestaurantApprovalRequestMessageListener {

    private final RestaurantApprovalRequestHelper restaurantApprovalRequestHelper;

    private final OrderApprovedMessagePublisher orderApprovedMessagePublisher;

    private final OrderRejectedMessagePublisher orderRejectedMessagePublisher;

    public RestaurantApprovalRequestMessageListenerImpl(RestaurantApprovalRequestHelper restaurantApprovalRequestHelper, OrderApprovedMessagePublisher orderApprovedMessagePublisher, OrderRejectedMessagePublisher orderRejectedMessagePublisher) {
        this.restaurantApprovalRequestHelper = restaurantApprovalRequestHelper;
        this.orderApprovedMessagePublisher = orderApprovedMessagePublisher;
        this.orderRejectedMessagePublisher = orderRejectedMessagePublisher;
    }

    @Override
    public void approveOrder(RestaurantApprovalRequest restaurantApprovalRequest) {

        OrderApprovalEvent orderApprovalEvent =
                restaurantApprovalRequestHelper.persistOrderApproval(restaurantApprovalRequest);

        fireEvent(orderApprovalEvent);
    }

    private void fireEvent(OrderApprovalEvent orderApprovalEvent) {

        log.info("Publishing orderApprovalEvent event with payment id: {} and order id: {}",
                orderApprovalEvent.getOrderApproval().getId().getValue(),
                orderApprovalEvent.getOrderApproval().getOrderId().getValue());

        if (orderApprovalEvent instanceof OrderApprovedEvent) {
            orderApprovedMessagePublisher.publish((OrderApprovedEvent) orderApprovalEvent);
        } else if (orderApprovalEvent instanceof OrderRejectedEvent) {
            orderRejectedMessagePublisher.publish((OrderRejectedEvent) orderApprovalEvent);
        }
    }
}
