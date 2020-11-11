package com.atul.spring5recipeapp.converters;

import com.atul.spring5recipeapp.commands.UnitOfMeasureCommands;
import com.atul.spring5recipeapp.model.Recipe;
import com.atul.spring5recipeapp.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureToUnitOfMeasureCommandsTest {

    public static final Long LONG_VALUE = new Long(1L);
    public static final String DESCRIPTION = "description";
    UnitOfMeasureToUnitOfMeasureCommands unitOfMeasureConverter;

    @BeforeEach
    void setUp() {
        unitOfMeasureConverter=new UnitOfMeasureToUnitOfMeasureCommands();
    }

    @Test
    public void testNullObject() throws Exception {
        unitOfMeasureConverter.convert(null);
    }


    @Test
    public void testEmptyObject() throws Exception {
        unitOfMeasureConverter.convert(new UnitOfMeasure());
    }

    @Test
    void convert() {
        //given
        UnitOfMeasure unitOfMeasure=new UnitOfMeasure();
        unitOfMeasure.setId(LONG_VALUE);
        unitOfMeasure.setDescription(DESCRIPTION);

        //when
        UnitOfMeasureCommands unitOfMeasureCommands=unitOfMeasureConverter.convert(unitOfMeasure);

        //then
        assertNotNull(unitOfMeasureCommands);
        assertEquals(LONG_VALUE,unitOfMeasureCommands.getId());
        assertEquals(DESCRIPTION,unitOfMeasureCommands.getDescription());
    }
}