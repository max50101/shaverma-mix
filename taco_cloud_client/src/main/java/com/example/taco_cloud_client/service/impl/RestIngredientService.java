package com.example.taco_cloud_client.service.impl;

import com.example.taco_cloud_client.model.Ingredient;
import com.example.taco_cloud_client.service.IngredientService;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

public class RestIngredientService implements IngredientService {
    private RestTemplate rest;
    public RestIngredientService(String accessTocken){
        this.rest=new RestTemplate();
        if(accessTocken!=null){
            this.rest.getInterceptors().add(getBearerTokenInterceptor(accessTocken));
        }
    }
    private ClientHttpRequestInterceptor
    getBearerTokenInterceptor(String accessToken) {
        ClientHttpRequestInterceptor interceptor =
                new ClientHttpRequestInterceptor() {
                    @Override
                    public ClientHttpResponse intercept(
                            HttpRequest request, byte[] bytes,
                            ClientHttpRequestExecution execution) throws IOException {
                        request.getHeaders()
                                .add("Authorization",
                                        "Bearer " + accessToken);
                        return execution.execute(request, bytes);
                    }
                };
        return interceptor;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return Arrays.asList(
                rest.getForObject("http://localhost:8080/api/v1/ingredients",Ingredient[].class)
        );
    }

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
         return rest.postForObject(
                "http://localhost:8080/api/ingredients",
                ingredient,
                Ingredient.class);
    }

}
