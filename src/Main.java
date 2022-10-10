import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public static void main(String[] args) throws Exception {
        Main obj = new Main();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido a la calculadora de divisas\nA que divisa desea convertir? (eur, usd, eth, btc, bnb, etc)");
        String divisa = scanner.nextLine().toLowerCase();
        System.out.println("Que divisa desea convertir? (eur, usd, eth, btc, bnb, etc)");
        String divisa2 = scanner.nextLine().toLowerCase();
        System.out.println("Cuanto desea convertir?");
        String euros = scanner.nextLine();

        double resultado = obj.hacerCalculo(divisa, divisa2, euros);
        System.out.println("El resultado es: " + resultado + " " + divisa.toUpperCase());

    }
    private Map sendGet(String divisa) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/" + divisa.toLowerCase() + ".json"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println("Status code: " + response.statusCode());

        Gson gson = new Gson();
        Object obj = gson.fromJson(response.body(), Object.class);

        return (Map<String, Object>) obj;
    }

    private Double hacerCalculo(String divisa, String divisa2, String euros) {
        Main obj = new Main();
        Object eur = null;
        try {
            eur = obj.sendGet(divisa2).get(divisa2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Map<String, Object> eurMap = (Map<String, Object>) eur;

        double exchange = (double) eurMap.get(divisa);
        float eurosFloat = Float.parseFloat(euros);
        return eurosFloat * exchange;
    }



}

