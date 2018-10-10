package market.repository;

import market.repository.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository  extends CrudRepository<Product, Long> {

    Optional<Product> findBySku(String sku);
}
