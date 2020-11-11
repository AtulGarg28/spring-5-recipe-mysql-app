package com.atul.spring5recipeapp.services;

import com.atul.spring5recipeapp.model.Recipe;
import com.atul.spring5recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ImageServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    ImageService imageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        imageService=new ImageServiceImpl(recipeRepository);
    }

    @Test
    void saveImageTest() throws IOException {

        //given
        Long id=1L;
        Recipe recipe=new Recipe();
        recipe.setId(id);

        MultipartFile multipartFile=new MockMultipartFile("imagefile","testing.txt","text/plain","Hello Atul here..".getBytes());

        Optional<Recipe> optionalRecipe=Optional.of(recipe);

        when(recipeRepository.findById(id)).thenReturn(optionalRecipe);

        ArgumentCaptor<Recipe> recipeArgumentCaptor=ArgumentCaptor.forClass(Recipe.class);

        //when
        imageService.saveImage(id,multipartFile);

        //then
        verify(recipeRepository,times(1)).save(recipeArgumentCaptor.capture());
        Recipe savedRecipe=recipeArgumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length,savedRecipe.getImage().length);
    }
}