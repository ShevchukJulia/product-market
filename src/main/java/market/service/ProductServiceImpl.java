package market.service;

import market.exeption.InvalidDataException;
import market.exeption.ItemNotFoundException;
import market.repository.ProductRepository;
import market.repository.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(Product product) {
        if (productRepository.findBySku(product.getSku()).isPresent()) {
            throw new InvalidDataException(MessageFormat.format(
                    "Product with sku {0} has already existed", product.getSku()));
        }

        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        Product productToUpdate = findById(product.getId());

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
            productToUpdate.setCategory(product.getCategory());
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
