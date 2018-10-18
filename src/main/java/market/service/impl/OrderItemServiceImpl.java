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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
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
            orderService.saveForSearch(order);
        }

        return repository.save(item);
    }

    @Override
    @Transactional
    public OrderItem update(OrderItem item) {
        OrderItem itemToUpdate = findById(item.getId());

        if (item.getQuantity() != null) {
            itemToUpdate.setQuantity(item.getQuantity());
        }

        if (item.getProduct() != null) {
            Product product = productService.findById(item.getProduct().getId());
            itemToUpdate.setProduct(product);
        }

        if (item.getOrder() != null && item.getOrder().getId() != null) {
            Order order = orderService.findById(item.getOrder().getId());
            orderService.addOrderItem(order, itemToUpdate);
            orderService.saveForSearch(order);
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
    @Transactional
    public void delete(Long id) {
        OrderItem orderItem = findById(id);
        orderItem.getOrder().getOrderItems().remove(orderItem);
        orderService.updateTotalAmount(orderItem.getOrder());
        orderService.saveForSearch(orderItem.getOrder());
        repository.delete(orderItem);
    }

}
