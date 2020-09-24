package io.microservice.thi.productstock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.microservice.thi.productstock.DAO.ProductStockDAO;
import io.microservice.thi.productstock.model.ProductStock;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.*;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/service")
public class ProductStockService {

    @Inject
    private ProductStockDAO productDAO;


    /**
     * This method creates a new product from the submitted data (name and description) by the user.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response addNewProductStock(String Json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            ProductStock input = mapper.readValue(Json, ProductStock.class);
            if(productDAO.readProduct(input.getId()) != null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Product already exists").build();
            }
            productDAO.createProduct(input);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    /**
     * This method updates a new Product Stock from the submitted data (name, stock, id) by the user.
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response updateProduct(@FormParam("product") String name,
                                  @FormParam("stock") Integer stock,
                                  @PathParam("id") int id) {
        ProductStock Prod = productDAO.readProduct(id);
        if(Prod == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Product does not exist").build();
        }
        Prod.setProduct(name);
        Prod.setStock(stock);

        productDAO.updateProduct(Prod);
        return Response.status(Response.Status.OK).build();
    }
    /**
     * This method returns a specific existing/stored product in Json format
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public JsonObject getProduct(@PathParam("id") int id) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        ProductStock product = productDAO.readProduct(id);
        if(product != null) {
            builder.add("name", product.getProduct()).add("stock", product.getStock())
                    .add("id", product.getId());
        }
        return builder.build();
    }

    /**
     * This method returns the existing/stored products in Json format
     */
    @GET
    @RolesAllowed({"admin", "asd"})
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public JsonArray getProducts() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonArrayBuilder finalArray = Json.createArrayBuilder();
        for (ProductStock product : productDAO.readAllProducts()) {
            builder.add("name", product.getProduct()).add("stock", product.getStock())
                    .add("id", product.getId());
            finalArray.add(builder.build());
        }
        return finalArray.build();
    }
}
