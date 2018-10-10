package market.controller;

import market.repository.model.Product;
import market.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Product product) {
        return service.create(product);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Product updateProduct(@RequestBody Product product) {
        return service.update(product);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public Product getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Product> findAll() {
        return service.findAll();
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public void removeProduct(@PathVariable Long id) {
        service.delete(id);
    }
}
