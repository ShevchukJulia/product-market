package market.service;

import market.repository.model.Product;

import java.util.List;

public interface ProductService {

    Product create(Product product);

    Product update(Product product);

    Product findById(Long id);

    List<Product> findAll();

    void delete(Long id);

}
