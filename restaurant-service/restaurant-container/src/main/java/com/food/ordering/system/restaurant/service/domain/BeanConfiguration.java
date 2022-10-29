package com.food.ordering.system.restaurant.service.domain;

import com.food.ordering.system.restaurant.service.RestaurantDomainService;
import com.food.ordering.system.restaurant.service.RestaurantDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public RestaurantDomainService restaurantDomainService() {
        return new RestaurantDomainServiceImpl();
    }

}
