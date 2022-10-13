import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

public class Main {
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


        URL url = new URL("https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/" + divisa + ".json");
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        JsonObject jsonObject = new Gson().fromJson(in, JsonObject.class);

        return jsonObject;

    }

    private Double hacerCalculo(String divisa, String divisa2, String euros) {
        JsonElement eur;

        try {
            eur = sendGet(divisa2).get(divisa2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        double exchange = eur.getAsJsonObject().get(divisa).getAsDouble();
        float eurosFloat = Float.parseFloat(euros);
        return eurosFloat * exchange;
    }



}

