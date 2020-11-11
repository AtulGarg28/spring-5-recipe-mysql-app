package com.atul.spring5recipeapp.converters;

import com.atul.spring5recipeapp.commands.CategoryCommands;
import com.atul.spring5recipeapp.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryCommandsToCategoryTest {

    public static final Long ID_VALUE = new Long(1L);
    public static final String DESCRIPTION = "description";

    CategoryCommandsToCategory categoryConverter;

    @BeforeEach
    void setUp() {
        categoryConverter=new CategoryCommandsToCategory();
    }

    @Test
    public void testNullParameter(){
        assertNull(categoryConverter.convert(null));
    }

    @Test
    public void testEmptyObject(){
        assertNotNull(categoryConverter.convert(new CategoryCommands()));
    }

    @Test
    void convert() {
        //given
        CategoryCommands categoryCommands=new CategoryCommands();
        categoryCommands.setId(ID_VALUE);
        categoryCommands.setDescription(DESCRIPTION);

        //when
        Category category=categoryConverter.convert(categoryCommands);

        //then
        assertNotNull(category);
        assertEquals(ID_VALUE,category.getId());
        assertEquals(DESCRIPTION,category.getDescription());
    }
}