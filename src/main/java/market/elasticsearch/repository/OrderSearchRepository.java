package market.elasticsearch.repository;

import market.repository.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderSearchRepository extends ElasticsearchCrudRepository<Order, Long> {

    Page<Order> findByOrderItemsProductNameContainsIgnoreCase(String name, Pageable pageable);

}
