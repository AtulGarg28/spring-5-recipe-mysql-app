package com.atul.spring5recipeapp.services;

import com.atul.spring5recipeapp.commands.UnitOfMeasureCommands;
import com.atul.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommands;
import com.atul.spring5recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService{

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommands unitOfMeasureToUnitOfMeasureCommands;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommands unitOfMeasureToUnitOfMeasureCommands) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommands = unitOfMeasureToUnitOfMeasureCommands;
    }

    @Override
    public Set<UnitOfMeasureCommands> listAllUoms() {
        return StreamSupport.stream(unitOfMeasureRepository.findAll()
        .spliterator(),false)
                .map(unitOfMeasureToUnitOfMeasureCommands::convert)
                .collect(Collectors.toSet());
    }
}
