package com.atul.spring5recipeapp.converters;

import com.atul.spring5recipeapp.commands.IngredientCommands;
import com.atul.spring5recipeapp.commands.NotesCommands;
import com.atul.spring5recipeapp.commands.UnitOfMeasureCommands;
import com.atul.spring5recipeapp.model.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientCommandsToIngredientTest {

    public static final Long ID_VALUE = new Long(1L);
    public static final String DESCRIPTION = "Cheeseburger";
    public static final BigDecimal AMOUNT = new BigDecimal("1");
    public static final Long UOM_ID = new Long(2L);

    IngredientCommandsToIngredient ingredientConverter;

    @BeforeEach
    void setUp() {
        ingredientConverter=new IngredientCommandsToIngredient(new UnitOfMeasureCommandsToUnitOfMeasure());
    }

    @Test
    public void testNullParameter(){
        assertNull(ingredientConverter.convert(null));
    }

    @Test
    public void testEmptyObject(){
        assertNotNull(ingredientConverter.convert(new IngredientCommands()));
    }

    @Test
    void convert() {

        //given
        IngredientCommands ingredientCommands=new IngredientCommands();
        ingredientCommands.setId(ID_VALUE);
        ingredientCommands.setDescription(DESCRIPTION);
        ingredientCommands.setAmount(AMOUNT);

        UnitOfMeasureCommands unitOfMeasureCommands=new UnitOfMeasureCommands();
        unitOfMeasureCommands.setId(UOM_ID);
        ingredientCommands.setUom(unitOfMeasureCommands);

        //when
        Ingredient ingredient=ingredientConverter.convert(ingredientCommands);

        //then
        assertNotNull(ingredient);
        assertEquals(ID_VALUE,ingredient.getId());
        assertEquals(DESCRIPTION,ingredient.getDescription());
        assertEquals(AMOUNT,ingredient.getAmount());
        assertEquals(UOM_ID,ingredient.getUom().getId());
    }

    @Test
    void convertWithNullUOM() {

        //given
        IngredientCommands ingredientCommands=new IngredientCommands();
        ingredientCommands.setId(ID_VALUE);
        ingredientCommands.setDescription(DESCRIPTION);
        ingredientCommands.setAmount(AMOUNT);

        UnitOfMeasureCommands unitOfMeasureCommands=new UnitOfMeasureCommands();
//        ingredientCommands.setUom(unitOfMeasureCommands);

        Ingredient ingredient=ingredientConverter.convert(ingredientCommands);
        assertNotNull(ingredient);
        assertNull(ingredient.getUom());
        assertEquals(ID_VALUE,ingredient.getId());
        assertEquals(DESCRIPTION,ingredient.getDescription());
        assertEquals(AMOUNT,ingredient.getAmount());
    }
}