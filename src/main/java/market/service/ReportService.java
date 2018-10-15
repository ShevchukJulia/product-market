package market.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public interface ReportService {

    Map<LocalDate, BigDecimal> findOrdersByDate(LocalDate from, LocalDate to);
}
