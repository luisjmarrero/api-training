package marrero.client;

import marrero.model.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpHelper {
    private static final CloseableHttpClient httpClient = HttpClients.createMinimal();

    public static HttpResponse execute (CloseableHttpClient httpClient, HttpUriRequest httpRequestBase) throws IOException {
        return execute(httpClient, httpRequestBase, null);
    }

    private static HttpResponse execute(CloseableHttpClient httpClient, HttpUriRequest httpRequest, HttpContext httpContext) throws IOException {
        try (CloseableHttpResponse response = httpClient.execute(httpRequest, httpContext)){
            return new HttpResponse(getHttpStatusCode(response), getHttpResponseAsString(response));
        }
    }

    private static String getHttpResponseAsString(CloseableHttpResponse response) throws IOException {
        if (response != null){
            return EntityUtils.toString(response.getEntity());
        }
        return null;
    }

    private static int getHttpStatusCode(CloseableHttpResponse response) {
        if (response != null && response.getStatusLine() != null){
            return response.getStatusLine().getStatusCode();
        }
        return -1;
    }
}
