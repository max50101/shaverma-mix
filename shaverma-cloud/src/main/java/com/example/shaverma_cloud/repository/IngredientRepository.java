package com.example.shaverma_cloud.repository;

import com.example.shaverma_cloud.model.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;


import java.util.Optional;

public interface IngredientRepository extends CrudRepository<Ingredient,String> {


}
