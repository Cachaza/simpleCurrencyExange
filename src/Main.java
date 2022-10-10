import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public static void main(String[] args) throws Exception {
        Main obj = new Main();

        System.out.println("Testing 1 - Send Http GET request");
        obj.sendGet();





    }
    private void sendGet() throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/eur.json"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println("Status code: " + response.statusCode());

        // print response body
        System.out.println(response.body());

    }



}

