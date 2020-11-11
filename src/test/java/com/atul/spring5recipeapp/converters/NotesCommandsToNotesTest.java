package com.atul.spring5recipeapp.converters;

import com.atul.spring5recipeapp.commands.NotesCommands;
import com.atul.spring5recipeapp.commands.RecipeCommands;
import com.atul.spring5recipeapp.model.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotesCommandsToNotesTest {

    public static final Long ID_VALUE = new Long(1L);
    public static final String RECIPE_NOTES = "Notes";
    NotesCommandsToNotes notesConverter;

    @BeforeEach
    void setUp() {
        notesConverter=new NotesCommandsToNotes();
    }

    @Test
    public void testNullParameter(){
        assertNull(notesConverter.convert(null));
    }

    @Test
    public void testEmptyObject(){
        assertNotNull(notesConverter.convert(new NotesCommands()));
    }

    @Test
    void convert() {
        //given
        NotesCommands notesCommands=new NotesCommands();
        notesCommands.setId(ID_VALUE);
        notesCommands.setRecipeNotes(RECIPE_NOTES);

        //when
        Notes notes=notesConverter.convert(notesCommands);

        //then
        assertNotNull(notes);
        assertEquals(ID_VALUE,notes.getId());
        assertEquals(RECIPE_NOTES,notes.getRecipeNotes());
    }
}