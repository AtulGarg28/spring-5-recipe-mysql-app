package com.atul.spring5recipeapp.converters;

import com.atul.spring5recipeapp.commands.CategoryCommands;
import com.atul.spring5recipeapp.model.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandsToCategory implements Converter<CategoryCommands, Category> {

    @Synchronized
    @Nullable
    @Override
    public Category convert(CategoryCommands categoryCommands) {
        if (categoryCommands==null){
            return null;
        }

        final Category category=new Category();
        category.setId(categoryCommands.getId());
        category.setDescription(categoryCommands.getDescription());

        return category;
    }
}
