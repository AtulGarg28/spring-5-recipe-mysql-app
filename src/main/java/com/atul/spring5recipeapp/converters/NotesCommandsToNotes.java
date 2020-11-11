package com.atul.spring5recipeapp.converters;

import com.atul.spring5recipeapp.commands.NotesCommands;
import com.atul.spring5recipeapp.model.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesCommandsToNotes implements Converter<NotesCommands, Notes> {

    @Synchronized
    @Nullable
    @Override
    public Notes convert(NotesCommands notesCommands) {
        if (notesCommands==null){
            return null;
        }

        final Notes notes=new Notes();
        notes.setId(notesCommands.getId());
        notes.setRecipeNotes(notesCommands.getRecipeNotes());

        return notes;
    }
}
