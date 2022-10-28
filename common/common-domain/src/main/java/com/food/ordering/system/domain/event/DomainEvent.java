package com.food.ordering.system.domain.event;


// mark class to specify an event object with the type of entity that will fire that event from the domain.
// E.G. OrderCreatedEvent will set the generic type as Order, which is the entity that this event is originated from.
public interface DomainEvent<T> {

}
