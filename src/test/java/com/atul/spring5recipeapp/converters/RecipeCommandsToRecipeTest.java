package com.atul.spring5recipeapp.converters;

import com.atul.spring5recipeapp.commands.*;
import com.atul.spring5recipeapp.model.Difficulty;
import com.atul.spring5recipeapp.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RecipeCommandsToRecipeTest {

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

    RecipeCommandsToRecipe recipeConverter;

//    @Mock
//    NotesCommandsToNotes notesConverter;
//
//    @Mock
//    IngredientCommandsToIngredient ingredientConverter;
//
//    @Mock
//    CategoryCommandsToCategory categoryConverter;

//    notesConverter,ingredientConverter,categoryConverter

    @BeforeEach
    void setUp() {
        recipeConverter=new RecipeCommandsToRecipe(new NotesCommandsToNotes(),new IngredientCommandsToIngredient(new UnitOfMeasureCommandsToUnitOfMeasure()),new CategoryCommandsToCategory());
    }

    @Test
    public void testNullParameter(){
        assertNull(recipeConverter.convert(null));
    }

    @Test
    public void testEmptyObject(){
        assertNotNull(recipeConverter.convert(new RecipeCommands()));
    }

    @Test
    void convert() {

        //given
        RecipeCommands recipeCommands=new RecipeCommands();
        recipeCommands.setId(RECIPE_ID);
        recipeCommands.setDescription(DESCRIPTION);
        recipeCommands.setPrepTime(PREP_TIME);
        recipeCommands.setCookTime(COOK_TIME);
        recipeCommands.setServings(SERVINGS);
        recipeCommands.setSource(SOURCE);
        recipeCommands.setUrl(URL);
        recipeCommands.setDirections(DIRECTIONS);
        recipeCommands.setDifficulty(DIFFICULTY);

        NotesCommands notesCommands = new NotesCommands();
        notesCommands.setId(NOTES_ID);

        recipeCommands.setNotes(notesCommands);

        IngredientCommands ingredientCommands1=new IngredientCommands();
        ingredientCommands1.setId(INGRED_ID_1);

        IngredientCommands ingredientCommands2=new IngredientCommands();
        ingredientCommands2.setId(INGRED_ID_2);

        recipeCommands.getIngredients().add(ingredientCommands1);
        recipeCommands.getIngredients().add(ingredientCommands2);

        CategoryCommands categoryCommands1=new CategoryCommands();
        categoryCommands1.setId(CAT_ID_1);

        CategoryCommands categoryCommands2=new CategoryCommands();
        categoryCommands2.setId(CAT_ID2);

        recipeCommands.getCategories().add(categoryCommands1);
        recipeCommands.getCategories().add(categoryCommands2);

        //when
        Recipe recipe=recipeConverter.convert(recipeCommands);

        assertNotNull(recipe);
        assertEquals(RECIPE_ID,recipe.getId());
        assertEquals(DESCRIPTION,recipe.getDescription());
        assertEquals(PREP_TIME,recipe.getPrepTime());
        assertEquals(COOK_TIME,recipe.getCookTime());
        assertEquals(SERVINGS,recipe.getServings());
        assertEquals(SOURCE,recipe.getSource());
        assertEquals(URL,recipe.getUrl());
        assertEquals(DIRECTIONS,recipe.getDirections());
        assertEquals(DIFFICULTY,recipe.getDifficulty());
        assertEquals(NOTES_ID,recipe.getNotes().getId());
        assertEquals(2,recipe.getIngredients().size());
        assertEquals(2,recipe.getCategories().size());
    }
}