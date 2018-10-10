package market.service;

import market.repository.model.Category;

import java.util.List;

public interface CategoryService {

    Category create(Category category);

    Category update(Category category);

    Category findById(Long id);

    List<Category> findAll();

    void delete(Long id);

}
