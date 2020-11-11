package com.atul.spring5recipeapp.converters;

import com.atul.spring5recipeapp.commands.IngredientCommands;
import com.atul.spring5recipeapp.commands.UnitOfMeasureCommands;
import com.atul.spring5recipeapp.model.Ingredient;
import com.atul.spring5recipeapp.model.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class IngredientCommandsToIngredient implements Converter<IngredientCommands, Ingredient> {

    private final UnitOfMeasureCommandsToUnitOfMeasure unitOfMeasureConverter;

    public IngredientCommandsToIngredient(UnitOfMeasureCommandsToUnitOfMeasure unitOfMeasureConverter) {
        this.unitOfMeasureConverter = unitOfMeasureConverter;
    }

//    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommands ingredientCommands) {
        if (ingredientCommands==null){
            return null;
        }

        final Ingredient ingredient=new Ingredient();
        ingredient.setId(ingredientCommands.getId());

        if (ingredientCommands.getRecipeId()!=null){
            Recipe recipe=new Recipe();
            recipe.setId(ingredientCommands.getRecipeId());
            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
        }
        ingredient.setDescription(ingredientCommands.getDescription());
        ingredient.setAmount(ingredientCommands.getAmount());
        ingredient.setUom(unitOfMeasureConverter.convert(ingredientCommands.getUom()));

        return ingredient;
    }
}
