package com.atul.spring5recipeapp.converters;

import com.atul.spring5recipeapp.commands.UnitOfMeasureCommands;
import com.atul.spring5recipeapp.model.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasureCommands implements Converter<UnitOfMeasure, UnitOfMeasureCommands> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasureCommands convert(UnitOfMeasure unitOfMeasure) {
        if (unitOfMeasure==null){
            return null;
        }

        final UnitOfMeasureCommands unitOfMeasureCommands=new UnitOfMeasureCommands();
        unitOfMeasureCommands.setId(unitOfMeasure.getId());
        unitOfMeasureCommands.setDescription(unitOfMeasure.getDescription());

        return unitOfMeasureCommands;
    }
}
