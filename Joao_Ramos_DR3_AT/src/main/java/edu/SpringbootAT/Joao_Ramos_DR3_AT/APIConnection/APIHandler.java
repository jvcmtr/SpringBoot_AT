package edu.SpringbootAT.Joao_Ramos_DR3_AT.APIConnection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.SpringbootAT.Joao_Ramos_DR3_AT.model.Pokemon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;


@Slf4j
public class APIHandler {
    private HashMap<Integer, Pokemon> RequestCache;
    @Autowired
    final private int maxDexNumber;
    final private String APIRoot = "https://pokeapi.co/api/v2/pokemon/";

    public APIHandler(int maxDexNumber){
        this.maxDexNumber = maxDexNumber;
        this.RequestCache = new HashMap<Integer, Pokemon>();

        for (int i = 1; i <= this.maxDexNumber; i++) {
            String response = makeAPIRequest(APIRoot + i);
            RequestCache.put(i, JsonToPokemon(response));
        }
    }

    public Pokemon[] getAll(){
        return RequestCache.values().toArray(new Pokemon[0]);
    }

    private Pokemon JsonToPokemon(String json){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ApiPokemonModel apiPokemon = null;
        try {
            apiPokemon = mapper.readValue(json, ApiPokemonModel.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        return apiPokemon.ToPokemon();
    }
    private String makeAPIRequest(String requestURI){
        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(new URI(requestURI))
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() >= 200 && response.statusCode()<300){
                //log.info("API responded with status:200 for the following request: " + requestURI);
                return response.body();
            }

            String message = "Server responded with status:"+response.statusCode();
            log.error(message + " for the following request: " + requestURI);
            return message;

        }catch (Exception e){
            log.error("An error ocourred while parsing the following request : " + requestURI);
            log.error(e.getMessage());
            return "An error ocourred while parsing the following request : " + requestURI;
        }
    }


}