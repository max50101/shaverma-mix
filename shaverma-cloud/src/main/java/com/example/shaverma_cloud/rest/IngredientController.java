package com.example.shaverma_cloud.rest;

import com.example.shaverma_cloud.model.Ingredient;
import com.example.shaverma_cloud.repository.IngredientRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ingredients")
public class IngredientController {
    private final IngredientRepository ingredientRepository;
    public IngredientController(IngredientRepository ingredientRepository){
        this.ingredientRepository=ingredientRepository;
    }

    @GetMapping
    public Iterable<Ingredient> all(){
        return ingredientRepository.findAll();
    }
}
