package market.controller;

import market.repository.model.Category;
import market.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private CategoryService service;

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Category createCategory(@RequestBody Category category) {
        return service.create(category);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Category updateCategory(@RequestBody Category category) {
        return service.update(category);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public Category getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Category> findAll() {
        return service.findAll();
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public void removeCategory(@PathVariable Long id) {
        service.delete(id);
    }

}
