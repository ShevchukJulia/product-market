package market.controller;

import market.repository.model.OrderItem;
import market.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderItems")
public class OrderItemController {

    private OrderItemService service;

    @Autowired
    public OrderItemController(OrderItemService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderItem createOrderItem(@RequestBody OrderItem orderItem) {
        return service.create(orderItem);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public OrderItem updateOrderItem(@RequestBody OrderItem orderItem) {
        return service.update(orderItem);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public OrderItem getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<OrderItem> findAll() {
        return service.findAll();
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public void removeOrderItem(@PathVariable Long id) {
        service.delete(id);
    }

}
