package com.example.taco_cloud_client.model.converters;


import com.example.taco_cloud_client.model.Ingredient;
import com.example.taco_cloud_client.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {
    private final IngredientRepository ingredientRepo;

    @Autowired
    public IngredientByIdConverter(IngredientRepository ingredientRepo){
        this.ingredientRepo=ingredientRepo;
    }

    @Override
    public Ingredient convert(String id) {
        return  StreamSupport
                .stream(ingredientRepo.findAll().spliterator(), false)
                .toList().stream().filter(it-> Objects.equals(it.getId(), id)).findFirst().get();
    }


}


