package marrero.client;

import com.google.gson.Gson;
import marrero.model.HttpResponse;
import marrero.model.Person;
import marrero.response.People;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

public class PersonClient {

    private final Gson gson = new Gson();
    private final CloseableHttpClient httpClient = HttpClients.createMinimal();
    private final String rootPath = "http://localhost:8080/";

    public People getAllPeople(){
        String path = "people";
        return execute(path, HttpGet.METHOD_NAME, People.class);
    }

    public People getPeopleByName(String name){
        String path = "people/" + name;
        return execute(path, HttpGet.METHOD_NAME, People.class);
    }

    public People addPerson(Person person){
        String path = "person";
        return execute(path, person, HttpPost.METHOD_NAME,People.class);
    }

    private <T> T execute(String path, String method, Class<T> responseType){
        return execute(path, null, method, responseType, null);
    }

    private <T> T execute(String path, String method, Class<T> responseType, List<NameValuePair> parameters){
        return execute(path, null, method, responseType, parameters);
    }

    private <T, R> T execute(String path, R request, String method, Class<T> responseType) {
        return execute(path, request, method, responseType, null);
    }

    private <T, R> T execute(String path, R request, String method, Class<T> responseType, List<NameValuePair> parameters) {
        try {
            HttpUriRequest httpRequest = RequestBuilder
                    .create(method)
                    .setUri(getRequestURI(path, parameters))
                    .setEntity(getJsonRequestEntity(request))
                    .build();

            System.out.println(httpRequest.getURI());
            HttpResponse httpResponse = HttpHelper.execute(httpClient, httpRequest);
            T wsResponse = gson.fromJson(httpResponse.getResponseBody(), responseType);
            return wsResponse;
        } catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }

     private URI getRequestURI(String path, List<NameValuePair> parameters){
        try {
            return new URIBuilder(rootPath + path)
                    .addParameters(parameters != null ? parameters : Collections.emptyList())
                    .build();
        } catch (URISyntaxException e){
            throw new RuntimeException(e.getMessage(), e);
        }
     }

     private <T> HttpEntity getJsonRequestEntity(T request){
         return new StringEntity(gson.toJson(request), ContentType.APPLICATION_JSON);
     }

    public static void main(String[] args) {
        PersonClient client = new PersonClient();

//        client.getAllPeople().getPeople().stream().forEach(p -> System.out.println(p.getName()));
//        client.getPeopleByName("jose").getPeople().stream().forEach(p -> System.out.println(p.getLastname()));
//        client.addPerson(new Person(5, "Jose", "Rodriguez"));

    }

}
