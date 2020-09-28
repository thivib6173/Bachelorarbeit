package io.microservice.thi.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.microservice.thi.product.DAO.ProductDAO;
import io.microservice.thi.product.model.Product;
import org.eclipse.microprofile.jwt.JsonWebToken;


import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.*;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.Principal;

@ApplicationScoped
@Path("/service")
public class ProductService {

    @Inject
    private ProductDAO productDAO;
    @Inject
    private JsonWebToken jsonWebToken;
    @Inject
    private Principal principal;
    /**
     * This method creates a new product from the submitted data (name and description) by the user.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response addNewProduct(String Json) {
        String name = "", description = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            Product input = mapper.readValue(Json, Product.class);
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
     * This method updates a new Product from the submitted data (name, description, id) by the user.
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response updateProduct(@FormParam("name") String name,
                                  @FormParam("description") String description,
                                  @PathParam("id") int id) {
        Product Prod = productDAO.readProduct(id);
        if(Prod == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Product does not exist").build();
        }
        Prod.setName(name);
        Prod.setDescription(description);

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
        Product product = productDAO.readProduct(id);
        if(product != null) {
            builder.add("name", product.getName()).add("description", product.getDescription())
                    .add("id", product.getId());
        }
        return builder.build();
    }

    /**
     * This method returns the existing/stored products in Json format
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public JsonArray getProducts() {
        //System.out.println("Name JWT: " + principal.getName());
        //System.out.println("Admin" + jsonWebToken.getClaim( "adminstrator_level").toString());



        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonArrayBuilder finalArray = Json.createArrayBuilder();
        for (Product product : productDAO.readAllProducts()) {
            builder.add("name", product.getName()).add("description", product.getDescription())
                    .add("id", product.getId()).add("category", product.getCategory()).add("image", product.getImage() );
            finalArray.add(builder.build());
        }
        return finalArray.build();
    }
}
