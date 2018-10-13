package market.service.impl;

import market.exeption.InvalidDataException;
import market.exeption.ItemNotFoundException;
import market.repository.ProductRepository;
import market.repository.model.Category;
import market.repository.model.Product;
import market.service.CategoryService;
import market.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private CategoryService categoryService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Override
    public Product create(Product product) {
        if (productRepository.findBySku(product.getSku()).isPresent()) {
            throw new InvalidDataException(MessageFormat.format(
                    "Product with sku {0} has already existed", product.getSku()));
        }

        Category category = categoryService.findById(product.getCategory().getId());
        categoryService.addProduct(category, product);

        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        Product productToUpdate = findById(product.getId());

        if (!productToUpdate.getSku().equals(product.getSku())
                && productRepository.findBySku(product.getSku()).isPresent()) {
            throw new InvalidDataException(MessageFormat.format(
                    "Product with sku {0} has already existed", product.getSku()));
        }

        if (!StringUtils.isEmpty(product.getName())) {
            productToUpdate.setName(product.getName());
        }

        if (product.getPrice() != null) {
            productToUpdate.setPrice(product.getPrice());
        }

        if (!StringUtils.isEmpty(product.getSku())) {
            productToUpdate.setSku(product.getSku());
        }

        if (product.getCategory() != null) {
            Category category = categoryService.findById(product.getCategory().getId());
            categoryService.addProduct(category, productToUpdate);
        }

        return productRepository.save(productToUpdate);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(MessageFormat.format(
                        "Product with id {0} does not exist", id)));
    }

    @Override
    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        productRepository.delete(findById(id));
    }

}
