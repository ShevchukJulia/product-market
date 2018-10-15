package market.service.impl;

import market.repository.OrderRepository;
import market.repository.model.Order;
import market.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private OrderRepository orderRepository;

    @Autowired
    public ReportServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Map<LocalDate, BigDecimal> findOrdersByDate(LocalDate from, LocalDate to) {
        List<Order> orders = orderRepository.findAllByCreationDateBetween(from, to);

        return orders.stream()
                .collect(Collectors.groupingBy(Order::getCreationDate,
                        Collectors.reducing(BigDecimal.ZERO, Order::getTotalAmount, BigDecimal::add)));
    }

}
