package com.atul.spring5recipeapp.converters;

import com.atul.spring5recipeapp.commands.NotesCommands;
import com.atul.spring5recipeapp.model.Notes;
import com.atul.spring5recipeapp.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotesToNotesCommandsTest {

    public static final Long ID_VALUE = new Long(1L);
    public static final String RECIPE_NOTES = "Notes";

    NotesToNotesCommands notesConverter;

    @BeforeEach
    void setUp() {
        notesConverter=new NotesToNotesCommands();
    }

    @Test
    public void testNull() throws Exception {
        notesConverter.convert(null);
    }

    @Test
    public void testEmptyObject() throws Exception {
        notesConverter.convert(new Notes());
    }

    @Test
    void convert() {
        //given
        Notes notes=new Notes();
        notes.setId(ID_VALUE);
        notes.setRecipeNotes(RECIPE_NOTES);
//        notes.setRecipe(new Recipe());

        //when
        NotesCommands notesCommands=notesConverter.convert(notes);

        //then
        assertNotNull(notesCommands);
        assertEquals(ID_VALUE,notesCommands.getId());
        assertEquals(RECIPE_NOTES,notesCommands.getRecipeNotes());
    }
}