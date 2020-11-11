package com.atul.spring5recipeapp.converters;

import com.atul.spring5recipeapp.commands.UnitOfMeasureCommands;
import com.atul.spring5recipeapp.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureCommandsToUnitOfMeasureTest {

    public static final String DESCRIPTION="description";
    public static final Long LONG_VALUE =new Long(1L);

    UnitOfMeasureCommandsToUnitOfMeasure unitOfMeasureConverter;

    @BeforeEach
    void setUp() {
        unitOfMeasureConverter=new UnitOfMeasureCommandsToUnitOfMeasure();
    }

    @Test
    public void testNullParameter(){
        assertNull(unitOfMeasureConverter.convert(null));
    }

    @Test
    public void testEmptyObject(){
        assertNotNull(unitOfMeasureConverter.convert(new UnitOfMeasureCommands()));
    }

    @Test
    void convert() {
        UnitOfMeasureCommands unitOfMeasureCommands=new UnitOfMeasureCommands();
        unitOfMeasureCommands.setId(LONG_VALUE);
        unitOfMeasureCommands.setDescription(DESCRIPTION);

        UnitOfMeasure uom=unitOfMeasureConverter.convert(unitOfMeasureCommands);

        assertNotNull(uom);
        assertEquals(LONG_VALUE,uom.getId());
        assertEquals(DESCRIPTION,uom.getDescription());
    }
}