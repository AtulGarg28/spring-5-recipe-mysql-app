package com.atul.spring5recipeapp.converters;

import com.atul.spring5recipeapp.commands.CategoryCommands;
import com.atul.spring5recipeapp.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandsTest {

    public static final Long ID_VALUE = new Long(1L);
    public static final String DESCRIPTION = "descript";
    CategoryToCategoryCommands categoryConverter;

    @BeforeEach
    void setUp() {
        categoryConverter=new CategoryToCategoryCommands();
    }

    @Test
    public void testNullObject() throws Exception {
        categoryConverter.convert(null);
    }

    @Test
    public void testEmptyObject() throws Exception {
        categoryConverter.convert(new Category());
    }

    @Test
    void convert() {
        //given
        Category category=new Category();
        category.setId(ID_VALUE);
        category.setDescription(DESCRIPTION);

        //when
        CategoryCommands categoryCommands=categoryConverter.convert(category);

        //then
        assertNotNull(categoryCommands);
        assertEquals(ID_VALUE,categoryCommands.getId());
        assertEquals(DESCRIPTION,categoryCommands.getDescription());
    }
}