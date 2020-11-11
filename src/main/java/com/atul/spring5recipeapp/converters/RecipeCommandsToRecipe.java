package com.atul.spring5recipeapp.converters;

import com.atul.spring5recipeapp.commands.RecipeCommands;
import com.atul.spring5recipeapp.model.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandsToRecipe implements Converter<RecipeCommands, Recipe>{

    private final NotesCommandsToNotes notesConverter;
    private final IngredientCommandsToIngredient ingredientConverter;
    private final CategoryCommandsToCategory categoryConverter;

    public RecipeCommandsToRecipe(NotesCommandsToNotes notesConverter, IngredientCommandsToIngredient ingredientConverter, CategoryCommandsToCategory categoryConverter) {
        this.notesConverter = notesConverter;
        this.ingredientConverter = ingredientConverter;
        this.categoryConverter = categoryConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommands recipeCommands) {
        if (recipeCommands == null){
            return null;
        }
        final Recipe recipe=new Recipe();
        recipe.setId(recipeCommands.getId());
        recipe.setDescription(recipeCommands.getDescription());
        recipe.setPrepTime(recipeCommands.getPrepTime());
        recipe.setCookTime(recipeCommands.getCookTime());
        recipe.setServings(recipeCommands.getServings());
        recipe.setSource(recipeCommands.getSource());
        recipe.setUrl(recipeCommands.getUrl());
        recipe.setDirections(recipeCommands.getDirections());
        recipe.setDifficulty(recipeCommands.getDifficulty());
        recipe.setNotes(notesConverter.convert(recipeCommands.getNotes()));

        if (recipeCommands.getIngredients()!=null && recipeCommands.getIngredients().size()>0){
            recipeCommands.getIngredients().forEach(ingredient -> recipe.getIngredients().add(ingredientConverter.convert(ingredient)));
//            recipeCommands.getIngredients().forEach(ingredient -> recipe.addIngredient(ingredientConverter.convert(ingredient)));
        }

        if (recipeCommands.getCategories()!=null && recipeCommands.getCategories().size()>0){
            recipeCommands.getCategories().forEach(category -> recipe.getCategories().add(categoryConverter.convert(category)));
        }

        return recipe;
    }
}
