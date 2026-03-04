package com.example.taco_cloud_client.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE,force = true)
public class Ingredient {
    private  String id;
    private  String name;
    private  Type type;
    public Ingredient(String id, String name, Type type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
    public enum Type{
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }
}


