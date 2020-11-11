package com.atul.spring5recipeapp.services;

import com.atul.spring5recipeapp.commands.UnitOfMeasureCommands;
import com.atul.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommands;
import com.atul.spring5recipeapp.model.UnitOfMeasure;
import com.atul.spring5recipeapp.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureServiceImplTest {

    UnitOfMeasureService unitOfMeasureService;

    UnitOfMeasureToUnitOfMeasureCommands unitOfMeasureToUnitOfMeasureCommands=new UnitOfMeasureToUnitOfMeasureCommands();

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        unitOfMeasureService=new UnitOfMeasureServiceImpl(unitOfMeasureRepository,unitOfMeasureToUnitOfMeasureCommands);
    }

    @Test
    void listAllUomsTest() {
        //given
        Set<UnitOfMeasure> unitOfMeasures=new HashSet<>();
        UnitOfMeasure uom1=new UnitOfMeasure();
        uom1.setId(1L);
        unitOfMeasures.add(uom1);

        UnitOfMeasure uom2=new UnitOfMeasure();
        uom2.setId(2L);
        unitOfMeasures.add(uom2);

        //when
        when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);

        //then
        Set<UnitOfMeasureCommands> uomCommands=unitOfMeasureService.listAllUoms();
        assertEquals(2,uomCommands.size());
        verify(unitOfMeasureRepository,times(1)).findAll();
    }
}