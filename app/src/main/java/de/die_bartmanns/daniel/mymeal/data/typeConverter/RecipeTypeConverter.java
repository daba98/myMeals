package de.die_bartmanns.daniel.mymeal.data.typeConverter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import de.die_bartmanns.daniel.mymeal.data.Recipe;

public class RecipeTypeConverter {

    private static final Gson gson = new Gson();

    @TypeConverter
    public static Recipe toRecipe(String data) {
        if(data == null)
            return null;

        return gson.fromJson(data, Recipe.class);
    }

    @TypeConverter
    public static String toString(Recipe recipe) {
        return gson.toJson(recipe);
    }
}
