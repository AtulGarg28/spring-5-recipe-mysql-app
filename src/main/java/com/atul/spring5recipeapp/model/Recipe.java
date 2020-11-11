package com.atul.spring5recipeapp.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    private String name;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    @Lob
    private String directions;

    @Enumerated(value = EnumType.STRING)        // EnumType has two i.e. Original and String, In Original it is default i.e. gives numbers, but in String it gives strings i.e. like EASY, HARD...
    private Difficulty difficulty;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "recipe")               //recipe is the target property on the ingredient class.
    private Set<Ingredient> ingredients=new HashSet<>();

    @Lob
    private Byte[] image;

    @OneToOne(cascade = CascadeType.ALL)            //Here, cascade means, if we delete recipe then it will also delete notes, but we don't need that if we delete notes then it will delete recipe. So, that's why we don't need cascade three i.e. in Notes.
    private Notes notes;

    @ManyToMany
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories=new HashSet<>();

    public void setNotes(Notes notes) {
        if (notes!=null){
            this.notes = notes;
            notes.setRecipe(this);
        }
    }

    public Recipe addIngredient(Ingredient ingredient){
        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }

}

