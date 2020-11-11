package com.atul.spring5recipeapp.converters;

import com.atul.spring5recipeapp.commands.IngredientCommands;
import com.atul.spring5recipeapp.model.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommands implements Converter<Ingredient, IngredientCommands> {

    private final UnitOfMeasureToUnitOfMeasureCommands unitOfMeasureConverter;

    public IngredientToIngredientCommands(UnitOfMeasureToUnitOfMeasureCommands unitOfMeasureConverter) {
        this.unitOfMeasureConverter = unitOfMeasureConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommands convert(Ingredient ingredient) {
        if (ingredient==null){
            return null;
        }

        IngredientCommands ingredientCommands=new IngredientCommands();
        ingredientCommands.setId(ingredient.getId());

        ingredientCommands.setDescription(ingredient.getDescription());

        if (ingredient.getRecipe() != null) {
            ingredientCommands.setRecipeId(ingredient.getRecipe().getId());
        }

        ingredientCommands.setAmount(ingredient.getAmount());
        ingredientCommands.setUom(unitOfMeasureConverter.convert(ingredient.getUom()));

        return ingredientCommands;
    }
}
