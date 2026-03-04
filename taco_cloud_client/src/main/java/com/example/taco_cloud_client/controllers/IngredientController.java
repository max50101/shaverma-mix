package com.example.taco_cloud_client.controllers;

import com.example.taco_cloud_client.model.Ingredient;
import com.example.taco_cloud_client.service.IngredientService;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/")
public class IngredientController {
    private IngredientService ingredientService;
    public IngredientController(IngredientService ingredientService){
        this.ingredientService=ingredientService;
    }

    @GetMapping
    public String getIngredients(Model model){
        List<Ingredient> ingredients = StreamSupport
                .stream(ingredientService.findAll().spliterator(),false)
                .collect(Collectors.toList());

        model.addAttribute("ingredients", ingredients);

        // 2) форма + список типов для select
        model.addAttribute("ingredientForm", new IngredientForm());
        model.addAttribute("types", Ingredient.Type.values());
        return "ingredients";
    }

    @PostMapping
    public String createIngredient(@ModelAttribute("ingredientForm") IngredientForm form) {
        Ingredient ingredient = new Ingredient(form.getId(), form.getName(), form.getType());
        ingredientService.addIngredient(ingredient);
        return "redirect:/ingredients";
    }

    @Data
    public static class IngredientForm {
        private String id;
        private String name;
        private Ingredient.Type type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Ingredient.Type getType() {
            return type;
        }

        public void setType(Ingredient.Type type) {
            this.type = type;
        }
    }
}
