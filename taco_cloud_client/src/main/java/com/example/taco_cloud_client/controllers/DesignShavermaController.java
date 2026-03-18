package com.example.taco_cloud_client.controllers;


import com.example.taco_cloud_client.model.Ingredient;
import com.example.taco_cloud_client.model.Shaverma;
import com.example.taco_cloud_client.model.ShavermaOrder;
import com.example.taco_cloud_client.repository.IngredientRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("shavermaOrder")
public class DesignShavermaController {

    private final IngredientRepository ingredientRepo;


    @Autowired
    public DesignShavermaController(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = StreamSupport
                .stream(ingredientRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());
         Ingredient.Type[] types = Ingredient.Type.values();
        for (Ingredient.Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }

    @ModelAttribute(name = "shavermaOrder")
    public ShavermaOrder order() {
        return new ShavermaOrder();
    }

    @ModelAttribute(name = "shaverma")
    public Shaverma shaverma() {
        return new Shaverma();
    }

    @GetMapping
    public String showShavermaDesignForm() {
        return "design";
    }

    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients.stream()
                .filter(it -> it.getType().equals(type))
                .collect(Collectors.toList());
    }

    @PostMapping
    public String processShaverma(@Valid Shaverma shaverma,
                                  Errors erros, @ModelAttribute ShavermaOrder order) {
        if (erros.hasErrors()) {
            erros.getFieldErrors().forEach(e ->
                    log.error("Field error: {} -> {}", e.getField(), e.getDefaultMessage())
            );
            return "design";
        }

        order.addShaverma(shaverma);
        log.info("Processing shaverma", shaverma);
        return "redirect:/orders/current";
    }
}

