package com.example.shaverma_cloud.controllers;

import com.example.shaverma_cloud.model.Ingredient;
import com.example.shaverma_cloud.model.Ingredient.Type;
import com.example.shaverma_cloud.model.Shaverma;
import com.example.shaverma_cloud.model.ShavermaOrder;
import com.example.shaverma_cloud.repository.IngredientRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
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

    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream()
                .filter(it -> it.getType().equals(type))
                .collect(Collectors.toList());
    }

    @PostMapping
    public String processShaverma(@Valid Shaverma shaverma,
                                  Errors erros, @ModelAttribute ShavermaOrder order) {
        if (erros.hasErrors()) {
            log.info("Error");
            return "/design";
        }
        order.addShaverma(shaverma);
        log.info("Processing shaverma", shaverma);
        return "redirect:/orders/current";
    }
}

