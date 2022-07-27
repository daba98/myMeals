package de.die_bartmanns.daniel.mymeal.data.database;

import java.util.List;

import de.die_bartmanns.daniel.mymeal.data.Meal;
import de.die_bartmanns.daniel.mymeal.data.MealType;
import de.die_bartmanns.daniel.mymeal.data.OwnCategory;
import de.die_bartmanns.daniel.mymeal.data.Unit;

public interface DatabaseService {

    void insertMeal(Meal meal);

    void insertMeals(List<Meal> meals);

    void updateMeal(Meal meal);

    void updateMeals(List<Meal> meals);

    Meal getMeal(long mealId);

    List<Meal> getAllMeals();

    List<Long> getAllMealIds();

    List<Long> getAllMealIdsWithType(MealType type);

    List<Long> getAllMealIdsWithCategory(String category);

    void deleteMeal(Meal meal);

    void deleteMeal(long id);

    void addCount(String ingredient, Unit unit);

    Unit getMostLikelyUnit(String ingredient);

    void insertCategory(String name);

    OwnCategory getCategory(String name);

    List<String> getAllCategoryNames();

    void deleteCategory(OwnCategory category);
}
