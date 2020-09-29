package producttest;

import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientRequestContext;
import java.io.IOException;

class KeycloackAuthRequestFilter implements
        ClientRequestFilter {

    private final String accessToken;

    public KeycloackAuthRequestFilter(String theaccessToken) {
        accessToken = theaccessToken;
    }

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        requestContext.getHeaders().putSingle("Authorization", "Bearer " +
                accessToken);
    }
}
