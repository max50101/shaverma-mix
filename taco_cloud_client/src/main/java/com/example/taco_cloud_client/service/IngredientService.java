package com.example.taco_cloud_client.service;

import com.example.taco_cloud_client.model.Ingredient;

public interface IngredientService {
    Iterable<Ingredient> findAll();
    Ingredient addIngredient(Ingredient ingredient);
}
