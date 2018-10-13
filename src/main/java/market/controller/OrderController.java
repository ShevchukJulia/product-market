package market.controller;

import market.repository.model.Order;
import market.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService service;

    @Autowired
    public OrderController(OrderService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@RequestBody Order order) {
        return service.create(order);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Order updateOrder(@RequestBody Order order) {
        return service.update(order);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public Order getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Order> findAll() {
        return service.findAll();
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public void removeOrder(@PathVariable Long id) {
        service.delete(id);
    }

}
