package com.example.taco_cloud_client.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class Shaverma {
   
    private Long id;

    private Date createdAt=new Date();
    @NotNull
    @Size(min=4,message = "Name must be at least 4 chars ")
    private  String name;

    @NotNull
    @Size(min=3,message = "Shaverma at least has to contain 3 ingredients")
    private List<Ingredient> ingredients;

    public Shaverma(String name, List<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }
    public Shaverma(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
