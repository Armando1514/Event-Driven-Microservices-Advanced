# EVENT DRIVEN MICROSERVICES  ADVANCED

## CLEAN ARCHITECTURE FOR ORDER-SERVICE

![orderService](./doc/orderService.jpg)

## DOMAIN DRIVEN DESIGN (DDD) FOR ORDER-SERVICE

![orderServiceDDD](./doc/orderServiceDDD.png)

## ORDER STATE TRANSITIONS

![Order state transitions](./doc/orderStateTransitions.png)

## OUTBOX PATTERN

![outbox pattern](./doc/outbox.png)

### OUTBOX HAPPY FLOW

![outbox happy flow](./doc/outbox-happy-flow.png)

### OUTBOX PAYMENT FAILURE

![outbox payment failure](./doc/outbox-payment-failure.png)

### OUTBOX APPROVAL FAILURE

![outbox approval failure](./doc/outbox-approval-failure.png)

## CQRS

![CQRS](./doc/CQRS.jpg)

Yes, there is not strong consistency between local database operations and data publishing operation for the customer service, outbox pattern implementation can fix that.

## API USAGE

1. POST request to http://localhost:8184/customers with JSON body:

```json
{
    "customerId":"d215b5f8-0249-4dc5-89a3-51fd148cfb41",
    "username": "user_1",
    "firstName": "Armando",
    "lastName": "Maradona"
}
```

2. POST request to http://localhost:8181/orders  request to with JSON body:

```json
{
  "customerId": "d215b5f8-0249-4dc5-89a3-51fd148cfb41",
  "restaurantId": "d215b5f8-0249-4dc5-89a3-51fd148cfb45",
  "address": {
    "street": "street_1",
    "postalCode": "1000AB",
    "city": "Amsterdam"
  },
  "price": 200.00,
  "items": [
    {
      "productId": "d215b5f8-0249-4dc5-89a3-51fd148cfb48",
      "quantity": 1,
      "price": 50.00,
      "subTotal": 50.00
    },
    {
      "productId": "d215b5f8-0249-4dc5-89a3-51fd148cfb48",
      "quantity": 3,
      "price": 50.00,
      "subTotal": 150.00
    }
  ]
}
```

3. Get the orderTrackingId from the response and query the result with a GET operation to http://localhost:8181/orders/toChangewithOrderTrackingId

You will see that first is PAID (payment-service replied), and roughly after 10 seconds, it is APPROVED (restaurant-service confirmed) if you continue to perform GET operation. Notice that if you perform the previous POST operation multiple times, it will fail, because there are not enough funds, and this can be an example of bad path.
