package com.atul.spring5recipeapp.services;

import com.atul.spring5recipeapp.model.Recipe;
import com.atul.spring5recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImage(Long recipe_id, MultipartFile file) {
        log.debug("Recieved an image.");

        try {
            Recipe recipe=recipeRepository.findById(recipe_id).get();

            Byte[] imageBytes=new Byte[file.getBytes().length];

            int i=0;

            for (byte b:file.getBytes()){
                imageBytes[i++]=b;
            }

            recipe.setImage(imageBytes);
            recipeRepository.save(recipe);
        }
        catch (IOException e){
            log.error("error occurred"+e);
            e.getStackTrace();
        }
    }
}
