package market.service;

import market.repository.model.Category;
import market.repository.model.Product;

import java.util.List;

public interface CategoryService {

    Category create(Category category);

    Category update(Category category);

    Category findById(Long id);

    List<Category> findAll();

    void delete(Long id);

    void addProduct(Category category, Product product);

}
