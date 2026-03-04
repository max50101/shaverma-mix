package com.example.shaverma_cloud;

import com.example.shaverma_cloud.model.Ingredient;
import com.example.shaverma_cloud.model.Ingredient.Type;
import com.example.shaverma_cloud.repository.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DesignControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    IngredientRepository ingredientRepository;

    private static final List<Ingredient> TEST_INGREDIENTS = List.of(
            new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
            new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
            new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
            new Ingredient("CARN", "Carnitas", Type.PROTEIN),
            new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
            new Ingredient("LETC", "Lettuce", Type.VEGGIES),
            new Ingredient("CHED", "Cheddar", Type.CHEESE),
            new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
            new Ingredient("SLSA", "Salsa", Type.SAUCE),
            new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
    );

    @BeforeEach
    void setUp(){
        ingredientRepository.deleteAll();
        ingredientRepository.saveAll(TEST_INGREDIENTS);
    }

    @Test
    void showDesignForm() throws Exception{
        mockMvc.perform(get("/design"))
                .andExpect(status().isOk())
                .andExpect(view().name("design"))
                .andExpect(model().attributeExists("wrap"))
                .andExpect(model().attributeExists("protein"))
                .andExpect(model().attributeExists("veggies"))
                .andExpect(model().attributeExists("cheese"))
                .andExpect(model().attributeExists("sauce"))
                .andExpect(model().attributeExists("shaverma"))
                .andExpect(model().attributeExists("shavermaOrder"));
    }

    @Test
    void showPrcocessShavermaAndRedirect() throws Exception{
        mockMvc.perform(post("/design").param("name","Shaverma").param("ingredients","FLTO","GRBF","CHED"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders/current"))
                .andExpect(request().sessionAttribute("shavermaOrder", notNullValue()));
    }

    @Test
    void showPrcocessShavermaAndError() throws Exception{
        mockMvc.perform(post("/design").param("ingredients","FLTO","GRBF","CHED"))
                .andExpect(status().isOk())
                .andExpect(view().name("/design"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("shaverma","name"));

    }

}
