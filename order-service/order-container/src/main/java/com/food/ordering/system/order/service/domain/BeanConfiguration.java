package com.food.ordering.system.order.service.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    /*
    In the domain core there is no spring dependency (TO KEEP IT PURE)
    SO the OrderDomainService IS NOT MARK as spring BEAN, but
    I still want to use it as a spring Bean, so the reason of this method.
    I REGISTER IT AS A BEAN IN THE BEAN CONFIGURATION.
    OrderDomainServiceImpl when the app starts will be registered as a Spring bean of OrderDomainService
    */
    @Bean
    public OrderDomainService orderDomainService() {
        return new OrderDomainServiceImpl();
    }
}
