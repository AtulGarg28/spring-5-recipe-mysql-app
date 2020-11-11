package com.atul.spring5recipeapp.converters;

import com.atul.spring5recipeapp.commands.IngredientCommands;
import com.atul.spring5recipeapp.model.Category;
import com.atul.spring5recipeapp.model.Ingredient;
import com.atul.spring5recipeapp.model.Recipe;
import com.atul.spring5recipeapp.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientToIngredientCommandsTest {

    public static final Recipe RECIPE = new Recipe();
    public static final BigDecimal AMOUNT = new BigDecimal("1");
    public static final String DESCRIPTION = "Cheeseburger";
    public static final Long UOM_ID = new Long(2L);
    public static final Long ID_VALUE = new Long(1L);

    IngredientToIngredientCommands ingredientConverter;

    @BeforeEach
    void setUp() {
        ingredientConverter=new IngredientToIngredientCommands(new UnitOfMeasureToUnitOfMeasureCommands());
    }

    @Test
    public void testNullObject() throws Exception {
        ingredientConverter.convert(null);
    }

    @Test
    public void testEmptyObject() throws Exception {
        ingredientConverter.convert(new Ingredient());
    }

    @Test
    void convertWithUOM() {
        //given
        Ingredient ingredient=new Ingredient();
        ingredient.setId(ID_VALUE);
        ingredient.setAmount(AMOUNT);
        ingredient.setDescription(DESCRIPTION);

        UnitOfMeasure uom=new UnitOfMeasure();
        uom.setId(UOM_ID);
        ingredient.setUom(uom);

        ingredient.setRecipe(RECIPE);

        //when
        IngredientCommands ingredientCommands=ingredientConverter.convert(ingredient);

        //then
        assertNotNull(ingredientCommands);
        assertEquals(ID_VALUE,ingredientCommands.getId());
        assertEquals(DESCRIPTION,ingredientCommands.getDescription());
        assertEquals(AMOUNT,ingredientCommands.getAmount());
        assertEquals(UOM_ID,ingredientCommands.getUom().getId());
    }

    @Test
    void convertWithoutUOM() {
        //given
        Ingredient ingredient=new Ingredient();
        ingredient.setId(ID_VALUE);
        ingredient.setAmount(AMOUNT);
        ingredient.setDescription(DESCRIPTION);

//        UnitOfMeasure uom=new UnitOfMeasure();
//        uom.setId(UOM_ID);
        ingredient.setUom(null);

        ingredient.setRecipe(RECIPE);

        //when
        IngredientCommands ingredientCommands=ingredientConverter.convert(ingredient);

        //then
        assertNotNull(ingredientCommands);
        assertNull(ingredientCommands.getUom());
        assertEquals(ID_VALUE,ingredientCommands.getId());
        assertEquals(DESCRIPTION,ingredientCommands.getDescription());
        assertEquals(AMOUNT,ingredientCommands.getAmount());
    }
}