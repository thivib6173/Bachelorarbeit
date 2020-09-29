package producttest;

import io.microservice.thi.product.model.Product;


// Java
import java.net.URI;
import java.io.StringReader;

// JSON
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

// JSON-B
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
// JAX-RS

// Format data for the POST request
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.client.Entity;

// Client
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

// JUnit
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.microservice.thi.product.model.Product;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class Test_GetAuthors {

    @ParameterizedTest(name = "{index} => name=''{0},{1},{2},{3},{4},{5},{6}")
    @CsvSource({      
            "guest,Vitalij Bojatschkin,guest,guest,Productservice-token,password,product-auth"
    })  

    public void testAuthGetAuthor(
            final String nameAuthor, 
            final String expectedResult, 
            final String user,
            final String password,
            final String realm,
            final String grant_type,
            final String client_id
            ) {

        final String realmName = realm;
        final String tokenPath = "/protocol/openid-connect/token";
        final String authServerBaseUrl = "http://localhost:8080/auth/realms/";

        // Prepare test

        // 1. Get token from Keycloak
        // Build data for auth
        final Form formData = new Form();
        formData.param("username", user)
                .param("password", password)
                .param("realm", realm)
                .param("grant_type", grant_type)
                .param("client_id", client_id);
        // Build client
        final Client client = ClientBuilder.newBuilder().build();
        final WebTarget target = client
                .target(UriBuilder.fromUri(authServerBaseUrl).path(realmName).path(tokenPath).build());
        // Get token
        final String keycloakToken = this.getToken(target, formData);
        System.out.println("[JUNIT-TEST] -> keycloakToken : " + keycloakToken);

    }
    

    private String getAuthorAuthorized(final String token, final String nameAuthor) {
        final Client client = ClientBuilder.newBuilder().build();
        client.register(new KeycloackAuthRequestFilter(token));

        final String BASE_URL = "http://localhost:9081/product";
        final URI baseURI = URI.create(BASE_URL);
        final String AUTHOR_PATH = "/service";

        WebTarget target = client.target(UriBuilder.fromUri(baseURI).path(AUTHOR_PATH).build());
        target = target.queryParam("name", nameAuthor);
        final Response response = target.request().accept(MediaType.APPLICATION_JSON_TYPE).get();

        System.out.println("[JUNIT-TEST] -> getAuthorAuthorized response status: " + response.getStatus());
        System.out.println("[JUNIT-TEST] -> getAuthorAuthorized response has entity: " + response.hasEntity());

        if (response.hasEntity() == true) {
            final String string_response = response.readEntity(String.class);
            System.out.println("[JUNIT-TEST] -> Get Author response has 'data': " + string_response);
            return string_response;
        } else {
            return "No data from author";
        }
    }

    private String getToken(final WebTarget target, final Form formData) {     
        System.out.println("[JUNIT-TEST] -> Start get Token");
        final Response keycloakTokenResponse = target.request()
                .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE).post(Entity.form(formData));
        System.out.println("[JUNIT-TEST] -> Keycloak getToken response has entity: " + keycloakTokenResponse.hasEntity());

        if (keycloakTokenResponse.hasEntity() == true) {
            final String string_response = keycloakTokenResponse.readEntity(String.class);
            final JsonObject jsonObject = stringToJson(string_response);
            final String accessToken = jsonObject.getString("access_token");        
            // System.out.println("[JUNIT-TEST] -> Keycloak  'access_token': " + accessToken);
            
            return accessToken;
        } else {
            return "{'Error':'No access-token from keycloak'}";
        }
    }

    private static JsonObject stringToJson(final String thestring) {

        final JsonReader jsonReader = Json.createReader(new StringReader(thestring));
        final JsonObject object = jsonReader.readObject();
        jsonReader.close();
        // System.out.println("[JUNIT-TEST] -> stringToJson object : " + object);  
        return object;
    }
}
