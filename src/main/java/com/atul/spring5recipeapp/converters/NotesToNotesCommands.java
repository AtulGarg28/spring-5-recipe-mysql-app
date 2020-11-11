package com.atul.spring5recipeapp.converters;

import com.atul.spring5recipeapp.commands.NotesCommands;
import com.atul.spring5recipeapp.model.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesCommands implements Converter<Notes, NotesCommands> {

    @Synchronized
    @Nullable
    @Override
    public NotesCommands convert(Notes notes) {
        if (notes==null){
            return null;
        }

        final NotesCommands notesCommands=new NotesCommands();
        notesCommands.setId(notes.getId());
        notesCommands.setRecipeNotes(notes.getRecipeNotes());

        return notesCommands;
    }
}
