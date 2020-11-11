package com.atul.spring5recipeapp.services;

import com.atul.spring5recipeapp.commands.RecipeCommands;
import com.atul.spring5recipeapp.model.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
    Recipe findById(Long l);
    RecipeCommands saveRecipeCommand(RecipeCommands recipeCommands);
    RecipeCommands findCommandById(Long id);
    void deleteRecipeById(Long id);
}
