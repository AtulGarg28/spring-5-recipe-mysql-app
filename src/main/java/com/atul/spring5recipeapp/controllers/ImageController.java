package com.atul.spring5recipeapp.controllers;

import com.atul.spring5recipeapp.commands.RecipeCommands;
import com.atul.spring5recipeapp.services.ImageService;
import com.atul.spring5recipeapp.services.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {

    private final RecipeService recipeService;
    private final ImageService imageService;

    public ImageController(RecipeService recipeService, ImageService imageService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
    }

    @GetMapping("/recipe/{recipe_id}/image")
    public String showUploadForm(@PathVariable String recipe_id, Model model){
        model.addAttribute("recipe",recipeService.findCommandById(Long.valueOf(recipe_id)));
        return "recipe/image/imageUploadForm";
    }

    @PostMapping("/recipe/{recipe_id}/image")
    public String changeImage(@PathVariable String recipe_id, @RequestParam("imagefile") MultipartFile file){
        imageService.saveImage(Long.valueOf(recipe_id),file);
        return "redirect:/recipe/"+recipe_id+"/show";
    }

    @GetMapping("/recipe/{recipe_id}/recipeImage")
    public void getImageFromDBOnPage(@PathVariable String recipe_id, HttpServletResponse response) throws IOException {
        RecipeCommands recipeCommands=recipeService.findCommandById(Long.valueOf(recipe_id));

        if (recipeCommands.getImage()!=null){
            byte[] byteArray=new byte[recipeCommands.getImage().length];

            int i=0;

            for (Byte b:recipeCommands.getImage()){
                byteArray[i++]=b;
            }

            response.setContentType("image.jpeg");
            InputStream inputStream=new ByteArrayInputStream(byteArray);
            IOUtils.copy(inputStream,response.getOutputStream());
        }

    }
}
