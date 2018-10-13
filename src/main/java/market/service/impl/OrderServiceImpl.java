package market.service.impl;

import market.exeption.ItemNotFoundException;
import market.repository.OrderRepository;
import market.repository.model.Order;
import market.repository.model.OrderItem;
import market.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository repository;

    @Autowired
    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order create(Order order) {
        return repository.save(order);
    }

    @Override
    public Order update(Order order) {
        Order orderToUpdate = findById(order.getId());

        if (!order.getOrderItems().isEmpty()) {
            orderToUpdate.getOrderItems().addAll(order.getOrderItems());
            orderToUpdate.getOrderItems().retainAll(order.getOrderItems());
        }

        if (order.getTotalAmount() != null) {
            orderToUpdate.setTotalAmount(order.getTotalAmount());
        }

        return repository.save(order);
    }

    @Override
    public Order findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(MessageFormat.format(
                        "Order with id {0} does not exist", id)));
    }

    @Override
    public List<Order> findAll() {
        return (List<Order>) repository.findAll();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void addOrderItem(Order order, OrderItem orderItem) {
        Set<OrderItem> orderItems = order.getOrderItems();
        orderItems.add(orderItem);
        orderItem.setOrder(order);

        updateTotalAmount(order);
    }

    @Override
    public void updateTotalAmount(Order order) {
        BigDecimal amount = order.getOrderItems()
                .stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(amount);
    }

}
