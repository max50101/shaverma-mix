package com.example.taco_cloud_client.repository.impl;

import com.example.taco_cloud_client.model.Ingredient;
import com.example.taco_cloud_client.repository.IngredientRepository;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class RestIngredientRepository implements IngredientRepository {
    private RestTemplate rest;
    private String apiBaseUrl;
    public RestIngredientRepository(RestTemplate restTemplate, String apiBaseUrl){
        this.rest=restTemplate;
        this.apiBaseUrl=apiBaseUrl;

    }


    @Override
    public Iterable<Ingredient> findAll() {
        return Arrays.asList(
                rest.getForObject(apiBaseUrl + "/api/v1/ingredients", Ingredient[].class)
        );
    }

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
         return rest.postForObject(
                 apiBaseUrl + "/api/ingredients",
                ingredient,
                Ingredient.class);
    }

    @Override
    public Optional<Ingredient> findById(String id) {
        Ingredient ingredient= rest
                .getForObject(apiBaseUrl+"/api/v1/ingredients/{id}",Ingredient.class,id);
        return Optional.ofNullable(ingredient);
    }

}
