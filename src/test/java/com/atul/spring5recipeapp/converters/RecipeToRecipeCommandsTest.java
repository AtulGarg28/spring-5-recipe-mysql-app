package com.atul.spring5recipeapp.converters;

import com.atul.spring5recipeapp.commands.RecipeCommands;
import com.atul.spring5recipeapp.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeToRecipeCommandsTest {

    public static final Long RECIPE_ID = 1L;
    public static final Integer COOK_TIME = Integer.valueOf("5");
    public static final Integer PREP_TIME = Integer.valueOf("7");
    public static final String DESCRIPTION = "My Recipe";
    public static final String DIRECTIONS = "Directions";
    public static final Difficulty DIFFICULTY = Difficulty.EASY;
    public static final Integer SERVINGS = Integer.valueOf("3");
    public static final String SOURCE = "Source";
    public static final String URL = "Some URL";
    public static final Long CAT_ID_1 = 1L;
    public static final Long CAT_ID2 = 2L;
    public static final Long INGRED_ID_1 = 3L;
    public static final Long INGRED_ID_2 = 4L;
    public static final Long NOTES_ID = 9L;
    RecipeToRecipeCommands recipeConverter;

    @BeforeEach
    void setUp() {
        recipeConverter=new RecipeToRecipeCommands(new NotesToNotesCommands(),new IngredientToIngredientCommands(new UnitOfMeasureToUnitOfMeasureCommands()),new CategoryToCategoryCommands());
    }

    @Test
    public void testNullObject() throws Exception {
        recipeConverter.convert(null);
    }


    @Test
    public void testEmptyObject() throws Exception {
        recipeConverter.convert(new Recipe());
    }

    @Test
    void convert() {
        //given
        Recipe recipe=new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setDescription(DESCRIPTION);
        recipe.setPrepTime(PREP_TIME);
        recipe.setCookTime(COOK_TIME);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);
        recipe.setDirections(DIRECTIONS);
        recipe.setDifficulty(DIFFICULTY);

        Notes notes=new Notes();
        notes.setId(NOTES_ID);
        recipe.setNotes(notes);

//        Category category1=new Category();
//        category1.setId(CAT_ID_1);
//
//        Category category2=new Category();
//        category2.setId(CAT_ID2);
//
//        recipe.getCategories().add(category1);
//        recipe.getCategories().add(category2);

        Category category = new Category();
        category.setId(CAT_ID_1);

        Category category2 = new Category();
        category2.setId(CAT_ID2);

        recipe.getCategories().add(category);
        recipe.getCategories().add(category2);

        Ingredient ingredient1=new Ingredient();
        ingredient1.setId(INGRED_ID_1);

        Ingredient ingredient2=new Ingredient();
        ingredient2.setId(INGRED_ID_2);

        recipe.getIngredients().add(ingredient1);
        recipe.getIngredients().add(ingredient2);

        //when
        RecipeCommands recipeCommands=recipeConverter.convert(recipe);

        //then
        assertNotNull(recipeCommands);
        assertEquals(RECIPE_ID,recipeCommands.getId());
        assertEquals(DESCRIPTION,recipeCommands.getDescription());
        assertEquals(PREP_TIME,recipeCommands.getPrepTime());
        assertEquals(COOK_TIME,recipeCommands.getCookTime());
        assertEquals(SERVINGS,recipeCommands.getServings());
        assertEquals(SOURCE, recipeCommands.getSource());
        assertEquals(URL,recipeCommands.getUrl());
        assertEquals(DIRECTIONS,recipeCommands.getDirections());
        assertEquals(DIFFICULTY,recipeCommands.getDifficulty());
        assertEquals(NOTES_ID,recipeCommands.getNotes().getId());
        assertEquals(2, recipeCommands.getCategories().size());
        assertEquals(2,recipeCommands.getIngredients().size());
    }
}