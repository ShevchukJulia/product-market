package market.service.impl;

import market.exeption.InvalidDataException;
import market.exeption.ItemNotFoundException;
import market.repository.model.Category;
import market.repository.CategoryRepository;
import market.repository.model.Product;
import market.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository repository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Category create(Category category) {
        if (StringUtils.isEmpty(category.getName())) {
            throw new InvalidDataException("Category name can not be empty");
        }

        if (repository.findByNameIgnoreCase(category.getName()).isPresent()) {
            throw new InvalidDataException("Category name is duplicated");
        }

        return repository.save(category);
    }

    @Override
    @Transactional
    public Category update(Category category) {
        Optional<Category> categoryOptional = repository.findById(category.getId());
        if (!categoryOptional.isPresent()) {
            throw new ItemNotFoundException(
                    MessageFormat.format("Category with id {0} does not exist", category.getId()));
        }

        Category categoryToUpdate = categoryOptional.get();
        categoryToUpdate.setName(category.getName());
        return repository.save(categoryToUpdate);
    }

    @Override
    public Category findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(MessageFormat.format(
                        "Category with id {0} does not exist", id)));
    }

    @Override
    public List<Category> findAll() {
        return (List<Category>) repository.findAll();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(MessageFormat.format(
                        "Category with id {0} does not exist", id)));
        repository.delete(category);
    }

    public void addProduct(Category category, Product product) {
        category.getProducts().add(product);
        product.setCategory(category);
    }
}
