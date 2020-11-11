package com.atul.spring5recipeapp.services;

import com.atul.spring5recipeapp.commands.RecipeCommands;
import com.atul.spring5recipeapp.converters.RecipeCommandsToRecipe;
import com.atul.spring5recipeapp.converters.RecipeToRecipeCommands;
import com.atul.spring5recipeapp.model.Recipe;
import com.atul.spring5recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class RecipeServiceIT {

    public static final String NEW_DESCRIPTION = "New Description";

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeToRecipeCommands recipeToRecipeCommands;

    @Autowired
    RecipeCommandsToRecipe recipeCommandsToRecipe;

    @Transactional
    @Test
    void testSaveOfDescription() {
        //given
        Iterable<Recipe> recipes=recipeRepository.findAll();
        Recipe testRecipe=recipes.iterator().next();
        RecipeCommands recipeCommands=recipeToRecipeCommands.convert(testRecipe);

        //when
        recipeCommands.setDescription(NEW_DESCRIPTION);
        RecipeCommands savedRecipeCommands=recipeService.saveRecipeCommand(recipeCommands);

        //then
        assertNotNull(savedRecipeCommands);
        assertEquals(NEW_DESCRIPTION,savedRecipeCommands.getDescription());
        assertEquals(testRecipe.getId(),savedRecipeCommands.getId());
        assertEquals(testRecipe.getCategories().size(),savedRecipeCommands.getCategories().size());
        assertEquals(testRecipe.getIngredients().size(),savedRecipeCommands.getIngredients().size());
    }
}