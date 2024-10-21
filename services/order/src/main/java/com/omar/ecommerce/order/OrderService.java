package com.omar.ecommerce.order;

import com.omar.ecommerce.customer.CustomerClient;
import com.omar.ecommerce.exception.BusinessException;
import com.omar.ecommerce.kafka.OrderConfirmation;
import com.omar.ecommerce.kafka.OrderProducer;
import com.omar.ecommerce.orderline.OrderLineRequest;
import com.omar.ecommerce.orderline.OrderLineService;
import com.omar.ecommerce.payment.PaymentClient;
import com.omar.ecommerce.payment.PaymentRequest;
import com.omar.ecommerce.item.ItemClient;
import com.omar.ecommerce.item.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final ItemClient itemClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;
    @Transactional
    public Integer createOrder(OrderRequest request){
        // check the customer
        // purchase the items --> item - microservice
        // persist order lines
        // start payment process
        // send the order confirmation --> notification-ms (kafka)
        var customer = this.customerClient.findCustomerById(request.customerId())
            .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exists with the provided ID:"));
        var purchasedItems = this.itemClient.purchaseItems(request.items());
        var order = this.repository.save(mapper.toOrder(request));
        for (PurchaseRequest purchaseRequest: request.items()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.itemId(),
                            purchaseRequest.quantity()
                    )
            );
        }
        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedItems
                )
        );
        return order.getId();
    }

    public List<OrderResponse> findAllOrders() {
        return this.repository.findAll()
                .stream()
                .map(this.mapper::fromOrder)
                .collect(Collectors.toList());
    }
    public OrderResponse findById(Integer id) {
        return this.repository.findById(id)
                .map(this.mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID: %d", id)));
    }

}
