package com.atul.spring5recipeapp.controllers;

import com.atul.spring5recipeapp.commands.IngredientCommands;
import com.atul.spring5recipeapp.commands.RecipeCommands;
import com.atul.spring5recipeapp.services.IngredientService;
import com.atul.spring5recipeapp.services.RecipeService;
import com.atul.spring5recipeapp.services.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IngredientControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    IngredientController ingredientController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ingredientController=new IngredientController(recipeService, ingredientService, unitOfMeasureService);
        mockMvc= MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    void showListOfIngredientsTest() throws Exception {

        //given
        RecipeCommands recipeCommands=new RecipeCommands();
        recipeCommands.setId(2L);

        //when
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommands);

        //then
        mockMvc.perform(get("/recipe/2/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService,times(1)).findCommandById(anyLong());
    }

    @Test
    void ViewIngredientTest() throws Exception {
        //given
        IngredientCommands ingredientCommands=new IngredientCommands();
        //when
        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(),anyLong())).thenReturn(ingredientCommands);
        //then
        mockMvc.perform(get("/recipe/1/ingredient/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));
    }

    @Test
    void testUpdateIngredient() throws Exception {
        //given
        IngredientCommands ingredientCommands=new IngredientCommands();

        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(),anyLong())).thenReturn(ingredientCommands);
        when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());

        //then
        mockMvc.perform(get("/recipe/1/ingredient/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("ingredient/ingredientForm"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));
    }

    @Test
    void addNewIngredientTest() throws Exception {
        //given
        RecipeCommands recipeCommands=new RecipeCommands();
        recipeCommands.setId(1L);

        //when
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommands);
        when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());

        //then
        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("ingredient/ingredientForm"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));

        verify(recipeService,times(1)).findCommandById(anyLong());
    }

    @Test
    void testShowSavedOrUpdateIngredient() throws Exception {
        //given
        IngredientCommands ingredientCommands=new IngredientCommands();
        ingredientCommands.setId(3L);
        ingredientCommands.setRecipeId(2L);

        //when
        when(ingredientService.saveIngredientCommand(any())).thenReturn(ingredientCommands);

        //then
        mockMvc.perform(post("/recipe/2/ingredient")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id","")
        .param("description","some string")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));
    }

    @Test
    void testDeleteIngredient() throws Exception {
        mockMvc.perform(get("/recipe/2/ingredient/3/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredients"));
    }
}