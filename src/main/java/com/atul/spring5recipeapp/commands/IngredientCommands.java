package com.atul.spring5recipeapp.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommands {
    private Long id;
    private String description;
    private Long recipeId;
    private BigDecimal amount;
    private UnitOfMeasureCommands uom;
}
