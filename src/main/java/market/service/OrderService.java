package market.service;

import market.repository.model.Order;
import market.repository.model.OrderItem;

import java.util.List;

public interface OrderService {

    Order create(Order item);

    Order update(Order item);

    Order findById(Long id);

    List<Order> findAll();

    void delete(Long id);

    void addOrderItem(Order order, OrderItem items);

    void updateTotalAmount(Order order);

}
