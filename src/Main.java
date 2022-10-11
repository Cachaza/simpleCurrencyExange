import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public static void main(String[] args) {
        Main obj = new Main();
        Scanner scanner = new Scanner(System.in);
        int i;
        do {
            System.out.println("Para utilizar el programa, pulse 1, para salir pulse 2");
            i = Integer.parseInt(scanner.nextLine());

            if (i == 1) {
                System.out.println("Bienvenido a la calculadora de divisas\n\nA que divisa desea convertir? (eur, usd, eth, btc, bnb, etc)");
                String divisa = scanner.nextLine().toLowerCase();
                System.out.println("Que divisa desea convertir? (eur, usd, eth, btc, bnb, etc)");
                String divisa2 = scanner.nextLine().toLowerCase();
                System.out.println("Cuanto desea convertir?");
                String euros = scanner.nextLine();

                double resultado = obj.hacerCalculo(divisa, divisa2, euros);
                System.out.println("El resultado es: " + resultado + " " + divisa.toUpperCase() + "\n\n");
            } else if (i == 2) {
                System.out.println("Hasta pronto!");
            } else {
                System.out.println("Opcion no valida");
            }
        } while (i != 2);


    }
    private JsonObject sendGet(String divisa) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/" + divisa.toLowerCase() + ".json"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        //System.out.println("Status code: " + response.statusCode());


        JsonObject obj = new Gson().fromJson(response.body(), JsonObject.class);

        //System.out.println(obj.get(divisa));
        return obj;



    }

    private Double hacerCalculo(String divisa, String divisa2, String euros) {
        Main obj = new Main();
        JsonElement eur;

        try {
            eur = obj.sendGet(divisa2).get(divisa2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        double exchange = eur.getAsJsonObject().get(divisa).getAsDouble();
        float eurosFloat = Float.parseFloat(euros);
        return eurosFloat * exchange;
    }



}

