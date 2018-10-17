package market.service.impl;

import market.exeption.ItemNotFoundException;
import market.repository.OrderRepository;
import market.elasticsearch.repository.OrderSearchRepository;
import market.repository.model.Order;
import market.repository.model.OrderItem;
import market.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository repository;
    private OrderSearchRepository elasticsearchRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository repository, OrderSearchRepository elasticsearchRepository) {
        this.repository = repository;
        this.elasticsearchRepository = elasticsearchRepository;
    }

    @Override
    public Order create(Order order) {
        Order savedOrder = repository.save(order);
        elasticsearchRepository.save(savedOrder);
        return savedOrder;
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

        repository.save(order);
        elasticsearchRepository.save(order);
        return order;
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
    public Page<Order> findByProductName(String name, Pageable pageable) {
        return elasticsearchRepository.findByOrderItemsProductNameContainsIgnoreCase(name, pageable);
    }

    @Override
    public void delete(Long id) {
        Order order = findById(id);

        elasticsearchRepository.delete(order);
        repository.delete(order);
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
