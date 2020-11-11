package com.atul.spring5recipeapp.converters;

import com.atul.spring5recipeapp.commands.RecipeCommands;
import com.atul.spring5recipeapp.model.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommands implements Converter<Recipe, RecipeCommands> {

    private final NotesToNotesCommands notesConverter;
    private final IngredientToIngredientCommands ingredientConverter;
    private final CategoryToCategoryCommands categoryConverter;

    public RecipeToRecipeCommands(NotesToNotesCommands notesConverter, IngredientToIngredientCommands ingredientConverter, CategoryToCategoryCommands categoryConverter) {
        this.notesConverter = notesConverter;
        this.ingredientConverter = ingredientConverter;
        this.categoryConverter = categoryConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommands convert(Recipe recipe) {
        if (recipe==null){
            return null;
        }

        final RecipeCommands recipeCommands=new RecipeCommands();
        recipeCommands.setId(recipe.getId());
        recipeCommands.setDescription(recipe.getDescription());
        recipeCommands.setPrepTime(recipe.getPrepTime());
        recipeCommands.setCookTime(recipe.getCookTime());
        recipeCommands.setServings(recipe.getServings());
        recipeCommands.setSource(recipe.getSource());
        recipeCommands.setUrl(recipe.getUrl());
        recipeCommands.setDirections(recipe.getDirections());
        recipeCommands.setDifficulty(recipe.getDifficulty());
        recipeCommands.setImage(recipe.getImage());
        recipeCommands.setNotes(notesConverter.convert(recipe.getNotes()));

        if (recipe.getIngredients()!=null && recipe.getIngredients().size()>0){
            recipe.getIngredients().forEach(ingredient -> recipeCommands.getIngredients().add(ingredientConverter.convert(ingredient)));
        }

        if (recipe.getCategories()!=null && recipe.getCategories().size()>0){
            recipe.getCategories().forEach(category -> recipeCommands.getCategories().add(categoryConverter.convert(category)));
        }

        return recipeCommands;
    }
}
