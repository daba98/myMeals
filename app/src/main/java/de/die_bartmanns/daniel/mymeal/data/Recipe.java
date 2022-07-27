package de.die_bartmanns.daniel.mymeal.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 04.01.2017.
 */
public class Recipe implements Serializable{

    public static final int DEFAULT_NBO_PERSONS = 4;

    private int persons;

    private List<Ingredient> ingredients;
    private List<String> instructions;


    public Recipe(List<Ingredient> ingredients, List<String> instructions){
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public Recipe(List<Ingredient> ingredients, List<String> instructions, int persons){
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.persons = persons;
    }

    public Recipe(){
        persons = DEFAULT_NBO_PERSONS;
        ingredients = new ArrayList<>();
        instructions = new ArrayList<>();
    }

    public int getPersons() {
        return persons;
    }

    public void setPersons(int persons) {
        this.persons = persons;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients){ this.ingredients = ingredients;}

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String> instructions){
        this.instructions = instructions;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Recipe){
            Recipe r = (Recipe) obj;
            return instructions.equals(r.instructions) && ingredients.equals(r.ingredients);
        }
        return false;
    }
}
