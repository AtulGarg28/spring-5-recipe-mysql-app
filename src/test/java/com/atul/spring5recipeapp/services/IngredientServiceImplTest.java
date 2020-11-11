package com.atul.spring5recipeapp.services;

import com.atul.spring5recipeapp.commands.IngredientCommands;
import com.atul.spring5recipeapp.converters.IngredientCommandsToIngredient;
import com.atul.spring5recipeapp.converters.IngredientToIngredientCommands;
import com.atul.spring5recipeapp.converters.UnitOfMeasureCommandsToUnitOfMeasure;
import com.atul.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommands;
import com.atul.spring5recipeapp.model.Ingredient;
import com.atul.spring5recipeapp.model.Recipe;
import com.atul.spring5recipeapp.repositories.RecipeRepository;
import com.atul.spring5recipeapp.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

    private final IngredientToIngredientCommands ingredientToIngredientCommands;
    private final IngredientCommandsToIngredient ingredientCommandsToIngredient;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    IngredientService ingredientService;

    public IngredientServiceImplTest() {
        this.ingredientCommandsToIngredient = new IngredientCommandsToIngredient(new UnitOfMeasureCommandsToUnitOfMeasure());
        this.ingredientToIngredientCommands = new IngredientToIngredientCommands(new UnitOfMeasureToUnitOfMeasureCommands());
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ingredientService=new IngredientServiceImpl(recipeRepository,ingredientToIngredientCommands, ingredientCommandsToIngredient, unitOfMeasureRepository);
    }

    @Test
    public void findByRecipeIdAndId() throws Exception {
    }

    @Test
    void findByRecipeIdAndIngredientIdTest() throws Exception {
        //given
        Recipe recipe=new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1=new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2=new Ingredient();
        ingredient2.setId(2L);

        Ingredient ingredient3=new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        Optional<Recipe> recipeOptional=Optional.of(recipe);

        //when
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        IngredientCommands ingredientCommands=ingredientService.findByRecipeIdAndIngredientId(1L,3L);

        //then
        assertEquals(Long.valueOf(3L),ingredientCommands.getId());

        //ERROR.... NOT STORING THE RECIPE ID IN INGREDIENT.
        assertEquals(Long.valueOf(1L),ingredientCommands.getRecipeId());
        verify(recipeRepository,times(1)).findById(anyLong());
    }

    @Test
    public void saveIngredientCommandTest() throws Exception {
        IngredientCommands ingredientCommands=new IngredientCommands();
        ingredientCommands.setId(3L);
        ingredientCommands.setRecipeId(2L);

        Optional<Recipe> optionalRecipe=Optional.of(new Recipe());

        Recipe savedRecipe=new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3L);

        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        IngredientCommands savedCommands=ingredientService.saveIngredientCommand(ingredientCommands);

        assertEquals(Long.valueOf(3L),savedCommands.getId());
        verify(recipeRepository,times(1)).findById(anyLong());
        verify(recipeRepository,times(1)).save(any(Recipe.class));
    }

    @Test
    public void deleteIngredientByIdTest() throws Exception {
        //given
        Recipe recipe=new Recipe();
        recipe.setId(1L);

        Ingredient ingredient=new Ingredient();
        ingredient.setId(2L);

        recipe.addIngredient(ingredient);
//        ingredient.setRecipe(recipe);

        Optional<Recipe> optionalRecipe=Optional.of(recipe);

        //when
        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);

        //then
        ingredientService.deleteIngredientById(1L,2L);

        verify(recipeRepository,times(1)).findById(anyLong());
        verify(recipeRepository,times(1)).save(any(Recipe.class));
    }
}