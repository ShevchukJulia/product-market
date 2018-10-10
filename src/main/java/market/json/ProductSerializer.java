package market.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import market.repository.model.Product;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class ProductSerializer extends JsonSerializer<Product> {

    @Override
    public void serialize(Product product, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", product.getId());
        jsonGenerator.writeNumberField("price", product.getPrice());
        jsonGenerator.writeStringField("sku", product.getSku());
        jsonGenerator.writeStringField("name", product.getName());
        jsonGenerator.writeNumberField("categoryId", product.getCategory().getId());
        jsonGenerator.writeEndObject();
    }

}
