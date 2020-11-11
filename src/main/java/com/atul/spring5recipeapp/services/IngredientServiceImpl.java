package com.atul.spring5recipeapp.services;

import com.atul.spring5recipeapp.commands.IngredientCommands;
import com.atul.spring5recipeapp.converters.IngredientCommandsToIngredient;
import com.atul.spring5recipeapp.converters.IngredientToIngredientCommands;
import com.atul.spring5recipeapp.model.Ingredient;
import com.atul.spring5recipeapp.model.Recipe;
import com.atul.spring5recipeapp.repositories.RecipeRepository;
import com.atul.spring5recipeapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService{

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommands ingredientToIngredientCommands;
    private final IngredientCommandsToIngredient ingredientCommandsToIngredient;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommands ingredientToIngredientCommands, IngredientCommandsToIngredient ingredientCommandsToIngredient, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommands = ingredientToIngredientCommands;
        this.ingredientCommandsToIngredient = ingredientCommandsToIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommands findByRecipeIdAndIngredientId(Long recipe_id, Long ingredient_id) {
        Optional<Recipe> optionalRecipe=recipeRepository.findById(recipe_id);

        if (!optionalRecipe.isPresent()){
            log.error("recipe id: "+recipe_id+" not found");
        }

        Recipe recipe=optionalRecipe.get();

//        System.out.println("re:id "+recipe.getIngredients());

        Optional<IngredientCommands> optionalIngredientCommands=recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredient_id))
                .map(ingredient -> ingredientToIngredientCommands.convert(ingredient)).findFirst();

        System.out.println(optionalIngredientCommands.get().getId());

        if (!optionalIngredientCommands.isPresent()){
            log.error("Ingredient of id: "+ingredient_id+" not found.");
        }


        return optionalIngredientCommands.get();
    }

    @Override
    public IngredientCommands saveIngredientCommand(IngredientCommands command) {
        Optional<Recipe> optionalRecipe=recipeRepository.findById(command.getRecipeId());
        if (!optionalRecipe.isPresent()){
            //todo toss error if not found!
            log.error("recipe of id:"+command.getRecipeId()+" not found.");
            return new IngredientCommands();
        }
        else {
            Recipe recipe=optionalRecipe.get();
            Optional<Ingredient> optionalIngredient=recipe.getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            if (optionalIngredient.isPresent()){
                Ingredient ingredientFounded=optionalIngredient.get();
                ingredientFounded.setDescription(command.getDescription());
                ingredientFounded.setAmount(command.getAmount());
                ingredientFounded.setUom(unitOfMeasureRepository
                        .findById(command.getUom().getId())
                        .orElseThrow(()->new RuntimeException("UOM NOT FOUND")));       //todo address this
            }
            else {
                //adding new ingredient if ingredient is not present.
                recipe.addIngredient(ingredientCommandsToIngredient.convert(command));
            }
            Recipe savedRecipe=recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional=savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients->recipeIngredients.getId().equals(command.getId()))
                    .findFirst();

            if (!savedIngredientOptional.isPresent()){
                savedIngredientOptional= savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(command.getUom().getId()))
                        .findFirst();
            }

            //to do check for fail
            return ingredientToIngredientCommands.convert(savedIngredientOptional.get());
        }
    }

    @Override
    public void deleteIngredientById(Long recipe_id, Long ingredient_id) {

        Optional<Recipe> optionalRecipe= recipeRepository.findById(recipe_id);

        if (optionalRecipe.isPresent()){
            Recipe recipe=optionalRecipe.get();
            log.debug("Recipe found for deletion.");

            Optional<Ingredient> optionalIngredient= recipe.getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredient_id))
                    .findFirst();

            if (optionalIngredient.isPresent()){
                log.debug("Ingredient found for deletion");
                Ingredient ingredient=optionalIngredient.get();
                ingredient.setRecipe(null);
                recipe.getIngredients().remove(ingredient);
                recipeRepository.save(recipe);
            }
        }
        else log.debug("recipe of id:"+recipe_id+" not found.");
    }
}
