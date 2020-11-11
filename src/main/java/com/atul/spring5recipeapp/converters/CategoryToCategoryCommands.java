package com.atul.spring5recipeapp.converters;

import com.atul.spring5recipeapp.commands.CategoryCommands;
import com.atul.spring5recipeapp.model.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryCommands implements Converter<Category, CategoryCommands> {

    @Synchronized
    @Nullable
    @Override
    public CategoryCommands convert(Category category) {
        if (category==null){
            return null;
        }

        final CategoryCommands categoryCommands=new CategoryCommands();
        categoryCommands.setId(category.getId());
        categoryCommands.setDescription(category.getDescription());

        return categoryCommands;
    }
}
