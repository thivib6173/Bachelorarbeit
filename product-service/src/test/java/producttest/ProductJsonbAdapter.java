package producttest;

import io.microservice.thi.product.model.Product;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

public class ProductJsonbAdapter implements JsonbAdapter<Product, JsonObject> {
 
    @Override
    public JsonObject adaptToJson(final Product product) throws Exception {
        return Json.createObjectBuilder()
        .add("category", product.getCategory())
        .add("name", product.getName())
        .add("description", product.getDescription()).build();
    }

    @Override
    public Product adaptFromJson(final JsonObject jsonObject) throws Exception {
        final Product product = new Product();
        product.setCategory(jsonObject.getString("category"));
        product.setName(jsonObject.getString("name"));
        product.setDescription(jsonObject.getString("description"));
        return product;
    }
}
