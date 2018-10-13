package market.service;

import market.repository.model.OrderItem;

import java.util.List;

public interface OrderItemService {

    OrderItem create(OrderItem item);

    OrderItem update(OrderItem item);

    OrderItem findById(Long id);

    List<OrderItem> findAll();

    void delete(Long id);
}
