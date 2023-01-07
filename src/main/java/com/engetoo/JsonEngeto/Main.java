package com.engetoo.JsonEngeto;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static Map<String, Country> countryMap = new HashMap<>();
    static LinkedHashMap<Country, Integer> sortedMap;
    static Map<Country, Integer> countryHashMap = new HashMap<>();


    //Metoda pro vypis zeme podle zkratky
    public static void printCountry(String abr) {
        System.out.println(countryMap.get(abr));
    }

    // Metoda pro vypis států s nejnižší a nejvyšší sazbou
    public static void highLow() {
        System.out.println("Státy s nejnižší sazbou");
        for (int i = 0; i < 3; i++) {
            System.out.println(sortedMap.entrySet().stream().skip(i).findFirst().get().getKey());
        }
        System.out.println("státy s nejvyšší sazbou");
        for (int i = (sortedMap.size() - 3); i < sortedMap.size(); i++) {
            System.out.println(sortedMap.entrySet().stream().skip(i).findFirst().get().getKey());
        }
    }

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        List<Country> countryList = new ArrayList<>();
        List<Country> country2 = new ArrayList<>();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI("https://euvatrates.com/rates.json"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


        JsonObject jsonObject = new JsonParser().parse(response.body()).getAsJsonObject();
        JsonObject countries = (JsonObject) jsonObject.get("rates");

        for (String countryAbr : countries.keySet()) {
            Country country = new Country((JsonObject) countries.get(countryAbr), countryAbr);
            countryList.add(country);
            BigDecimal bd = country.rate;
            int integerRate = bd.intValueExact();


            countryMap.put(countryAbr, country);
            countryHashMap.put(country, integerRate);
        }

        sortedMap = countryHashMap.entrySet().stream().sorted((e1, e2) -> {
            return (e1.getValue() - e2.getValue());
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new)); 
        //3 státy s min. a max. sazbou
        //highLow();
        // vypis zemí podle zkratky
        // printCountry();

        try (PrintWriter printWriter = new PrintWriter(new FileWriter("Json.txt"))) {
            for (Country country :countryList ) {
                printWriter.println(
                        country.name+ ";"
                                +country.abbreviation+ ";"
                                +country.rate+ ";"
                                +country.reduceRate+ ";"
                                +country.reducedRateAlt+ ";"
                                +country.superReducedRate+ ";"
                                +country.parkingRate
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
