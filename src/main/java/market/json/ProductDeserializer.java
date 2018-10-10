package market.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import market.repository.model.Category;
import market.repository.model.Product;
import market.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.math.BigDecimal;

@JsonComponent
public class ProductDeserializer extends JsonDeserializer<Product> {

    private CategoryService categoryService;

    @Autowired
    public ProductDeserializer(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public Product deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);

        Product product = new Product();
        if (treeNode.get("id") != null) {
            product.setId(((JsonNode) treeNode.get("id")).asLong());

        }

        if (treeNode.get("name") != null) {
            product.setName(((JsonNode) treeNode.get("name")).asText());

        }

        if (treeNode.get("sku") != null) {
            product.setSku(((JsonNode) treeNode.get("sku")).asText());
        }

        if (treeNode.get("price") != null) {
            product.setPrice(BigDecimal.valueOf(((JsonNode) treeNode.get("price")).asDouble()));
        }

        if (treeNode.get("categoryId") != null) {
            Category category = categoryService.findById(((JsonNode) treeNode.get("categoryId")).asLong());
            category.addProduct(product);
        }

        return product;
    }

}
