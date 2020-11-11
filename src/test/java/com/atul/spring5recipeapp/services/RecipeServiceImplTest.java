package com.atul.spring5recipeapp.services;

import com.atul.spring5recipeapp.commands.RecipeCommands;
import com.atul.spring5recipeapp.converters.RecipeCommandsToRecipe;
import com.atul.spring5recipeapp.converters.RecipeToRecipeCommands;
import com.atul.spring5recipeapp.exceptions.NotFoundException;
import com.atul.spring5recipeapp.model.Recipe;
import com.atul.spring5recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeCommandsToRecipe recipeCommandsToRecipe;

    @Mock
    RecipeToRecipeCommands recipeToRecipeCommands;

    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        recipeService=new RecipeServiceImpl(recipeRepository, recipeCommandsToRecipe, recipeToRecipeCommands);
    }

    @Test
    void getRecipesTest() throws Exception{
        Recipe recipe=new Recipe();
        HashSet<Recipe> recipiesData=new HashSet();
        recipiesData.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipiesData);

        Set<Recipe> recipeSet=recipeService.getRecipes();
        assertEquals(recipeSet.size(),1);
        verify(recipeRepository,times(1)).findAll();
        verify(recipeRepository,never()).findById(anyLong());
    }

    @Test
    void getRecipeByIdTest() {
        //GIVEN
        Recipe recipe=new Recipe();
        recipe.setId(1L);
        Optional<Recipe> optionalRecipe=Optional.of(recipe);

        //WHEN
        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);
        Recipe returnedRecipe=recipeService.findById(1L);

        //then
        assertNotNull(returnedRecipe,"Null Recipe Returned");
        verify(recipeRepository,times(1)).findById(anyLong());
        verify(recipeRepository,never()).findAll();
    }
    @Test
    void getRecipeCommandByIdTest() {
        //given
        Recipe recipe=new Recipe();
        recipe.setId(1L);
        Optional<Recipe> optionalRecipe=Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);

        RecipeCommands recipeCommands=new RecipeCommands();
        recipeCommands.setId(1L);

        //when
        when(recipeToRecipeCommands.convert(any())).thenReturn(recipeCommands);
        RecipeCommands findedRecipeCommands=recipeService.findCommandById(1L);

        //then
        assertNotNull(findedRecipeCommands);
        verify(recipeRepository,times(1)).findById(anyLong());
        verify(recipeToRecipeCommands,times(1)).convert(any());
        verify(recipeRepository,never()).findAll();
        assertEquals(recipe.getId(),findedRecipeCommands.getId());
    }

    @Test
    void deleteRecipeByIdTest() {
        //given
        Long idToBeDeleted=new Long(2L);

        //when

        //HERE, NO WHEN IS USED, AS THE METHOD IS NOT RETURNING ANYTHING. HERE, THE METHOD IS recipeRepository.deleteById(anyLong());
        recipeService.deleteRecipeById(idToBeDeleted);

        //then
        verify(recipeRepository,times(1)).deleteById(anyLong());
    }

    @Test
    void testGetRecipeByIdNotFound() {
        //given
        Optional<Recipe> optionalRecipe=Optional.empty();

        //when
        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);

        //then
        assertThrows(NotFoundException.class,()->{
            recipeService.findById(1L);
        });
//        Recipe returnedRecipe=recipeService.findById(1L);
    }
}