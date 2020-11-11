package com.atul.spring5recipeapp.services;

import com.atul.spring5recipeapp.commands.RecipeCommands;
import com.atul.spring5recipeapp.converters.RecipeCommandsToRecipe;
import com.atul.spring5recipeapp.converters.RecipeToRecipeCommands;
import com.atul.spring5recipeapp.exceptions.NotFoundException;
import com.atul.spring5recipeapp.model.Recipe;
import com.atul.spring5recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService{

    private final RecipeRepository recipeRepository;
    private final RecipeCommandsToRecipe recipeCommandsToRecipe;
    private final RecipeToRecipeCommands recipeToRecipeCommands;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandsToRecipe recipeCommandsToRecipe, RecipeToRecipeCommands recipeToRecipeCommands) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandsToRecipe = recipeCommandsToRecipe;
        this.recipeToRecipeCommands = recipeToRecipeCommands;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("I am in the service");
        Set<Recipe> recipeSet=new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe findById(Long l) {
        Optional<Recipe> optionalRecipe=recipeRepository.findById(l);
        if (!optionalRecipe.isPresent()){
//            throw new RuntimeException("Recipe Not found");
            throw new NotFoundException("Recipe Not found of id:"+l.toString());
        }
        return optionalRecipe.get();
    }

    @Override
    @Transactional
    public RecipeCommands saveRecipeCommand(RecipeCommands recipeCommands) {
        Recipe recipeDetached=recipeCommandsToRecipe.convert(recipeCommands);
        Recipe savedRecipe=recipeRepository.save(recipeDetached);
        log.debug("Recipe saved of id: "+savedRecipe.getId());
        return recipeToRecipeCommands.convert(savedRecipe);
    }

    @Override
    @Transactional
    public RecipeCommands findCommandById(Long id) {
        return recipeToRecipeCommands.convert(findById(id));
    }

    @Override
    public void deleteRecipeById(Long id){
        recipeRepository.deleteById(id);
    }
}
