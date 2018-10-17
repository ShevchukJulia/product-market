package market.service;

import market.repository.model.Order;
import market.repository.model.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    Order create(Order item);

    Order update(Order item);

    Order findById(Long id);

    List<Order> findAll();

    Page<Order> findByProductName(String name, Pageable pageable);

    void delete(Long id);

    void addOrderItem(Order order, OrderItem items);

    void updateTotalAmount(Order order);

}
