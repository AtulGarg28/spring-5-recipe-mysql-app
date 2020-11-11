package com.atul.spring5recipeapp.converters;

import com.atul.spring5recipeapp.commands.UnitOfMeasureCommands;
import com.atul.spring5recipeapp.model.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureCommandsToUnitOfMeasure implements Converter<UnitOfMeasureCommands, UnitOfMeasure> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommands unitOfMeasureCommands) {
        if (unitOfMeasureCommands==null){
            return null;
        }

        final UnitOfMeasure unitOfMeasure=new UnitOfMeasure();
        unitOfMeasure.setId(unitOfMeasureCommands.getId());
        unitOfMeasure.setDescription(unitOfMeasureCommands.getDescription());

        return unitOfMeasure;
    }
}
