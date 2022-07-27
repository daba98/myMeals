package de.die_bartmanns.daniel.mymeal.data.onlineDatabase;

import android.os.Build;
import android.util.Base64;


import de.die_bartmanns.daniel.mymeal.data.Meal;
import de.die_bartmanns.daniel.mymeal.data.MealType;

public class MealToOnlineMealConverter {

    public static OnlineMeal toOnlineMeal(Meal meal){
        OnlineMeal onlineMeal = new OnlineMeal();
        onlineMeal.setName(meal.getName());
        onlineMeal.setTimeToCook(meal.getTimeToCook());
        onlineMeal.setDifficulty(meal.getDifficulty());
        onlineMeal.setRating(meal.getRating());
        onlineMeal.setNboRatings(1);
        onlineMeal.setPhoto(arrayToString(meal.getPhotoByteArray()));
        onlineMeal.setRecipe(meal.getRecipe());
        onlineMeal.setCreationTimestamp(System.currentTimeMillis());
        onlineMeal.setType(meal.getType());
        onlineMeal.setWithMeat(meal.isWithMeat());
        onlineMeal.setWithVegetables(meal.isWithVegetables());
        onlineMeal.setWithFish(meal.isWithFish());
        onlineMeal.setWithNoodles(meal.isWithNoodles());
        onlineMeal.setWithPotatoes(meal.isWithPotatoes());
        onlineMeal.setWithRice(meal.isWithRice());
        onlineMeal.setDocumentId(meal.getDocumentId());

        return onlineMeal;
    }

    public static Meal toMeal(OnlineMeal onlineMeal){
        Meal meal = new Meal();
        meal.setName(onlineMeal.getName());
        meal.setTimeToCook(onlineMeal.getTimeToCook());
        meal.setDifficulty(onlineMeal.getDifficulty());
        meal.setRating((int) onlineMeal.getRating());
        meal.setPhotoByteArray(stringToArray(onlineMeal.getPhoto()));
        meal.setRecipe(onlineMeal.getRecipe());
        meal.setWithMeat(onlineMeal.isWithMeat());
        meal.setWithVegetables(onlineMeal.isWithVegetables());
        meal.setWithFish(onlineMeal.isWithFish());
        meal.setWithNoodles(onlineMeal.isWithNoodles());
        meal.setWithPotatoes(onlineMeal.isWithPotatoes());
        meal.setWithRice(onlineMeal.isWithRice());
        meal.setType(onlineMeal.getType());
        meal.setDocumentId(onlineMeal.getDocumentId());

        return meal;
    }


    private static String arrayToString(byte[] photo){
        if(photo == null)
            return null;

        return new String(Base64.encode(photo, Base64.DEFAULT));
    }


    private static byte[] stringToArray(String photo){
        if(photo == null)
            return null;

        return Base64.decode(photo, Base64.DEFAULT);
    }


}
