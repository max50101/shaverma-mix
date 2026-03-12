package com.example.taco_cloud_client.repository;

import com.example.taco_cloud_client.model.Ingredient;

import java.util.Optional;

public interface IngredientRepository {
    Iterable<Ingredient> findAll();
    Ingredient addIngredient(Ingredient ingredient);
    Optional<Ingredient> findById(String id);
}
