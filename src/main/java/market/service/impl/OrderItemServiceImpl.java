package market.service.impl;

import market.exeption.InvalidDataException;
import market.exeption.ItemNotFoundException;
import market.repository.OrderItemRepository;
import market.repository.model.Order;
import market.repository.model.OrderItem;
import market.repository.model.Product;
import market.service.OrderItemService;
import market.service.OrderService;
import market.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private OrderItemRepository repository;
    private ProductService productService;
    private OrderService orderService;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepository repository, ProductService productService, OrderService orderService) {
        this.repository = repository;
        this.productService = productService;
        this.orderService = orderService;
    }

    @Override
    public OrderItem create(OrderItem item) {
        if (item.getProduct() == null || item.getQuantity() == null) {
            throw new InvalidDataException("Product and quantity can not be null");
        }

        if (item.getProduct() != null) {
            Product product = productService.findById(item.getProduct().getId());
            item.setProduct(product);
        }

        if (item.getOrder() != null) {
            Order order = orderService.findById(item.getOrder().getId());
            orderService.addOrderItem(order, item);
        }

        return repository.save(item);
    }

    @Override
    public OrderItem update(OrderItem item) {
        OrderItem itemToUpdate = findById(item.getId());

        if (item.getQuantity() != null) {
            itemToUpdate.setQuantity(item.getQuantity());
        }

        if (item.getProduct() != null) {
            Product product = productService.findById(item.getProduct().getId());
            itemToUpdate.setProduct(product);
        }

        if (itemToUpdate.getOrder() != null) {
            Order order = orderService.findById(item.getOrder().getId());
            orderService.addOrderItem(order, item);
        }

        return repository.save(itemToUpdate);
    }

    @Override
    public OrderItem findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(MessageFormat.format(
                        "Order Item with id {0} does not exist", id)));
    }

    @Override
    public List<OrderItem> findAll() {
        return (List<OrderItem>) repository.findAll();
    }

    @Override
    public void delete(Long id) {
        OrderItem orderItem = findById(id);
        orderItem.getOrder().getOrderItems().remove(orderItem);
        orderService.updateTotalAmount(orderItem.getOrder());
        repository.delete(orderItem);
    }

}
