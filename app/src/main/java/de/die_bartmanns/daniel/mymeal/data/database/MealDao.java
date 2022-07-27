package de.die_bartmanns.daniel.mymeal.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.die_bartmanns.daniel.mymeal.data.Meal;
import de.die_bartmanns.daniel.mymeal.data.MealType;

@Dao
public interface MealDao {

    @Insert
    void insertMeals(List<Meal> meals);

    @Insert
    long insertMeal(Meal meal);

    @Query("SELECT * FROM Meals WHERE id = :id")
    Meal getMeal(long id);

    @Query("SELECT * FROM Meals")
    List<Meal> getAllMeals();

    @Query("SELECT m.id FROM Meals m ORDER BY m.name ASC")
    List<Long> getAllMealIds();

    @Query("SELECT m.id FROM Meals m WHERE m.type = :type ORDER BY m.name ASC")
    List<Long> getAllMealIds(MealType type);

    @Query("SELECT m.id FROM Meals m WHERE m.ownCategory = :category ORDER BY m.name ASC")
    List<Long> getAllMealIdsWithCategory(String category);

    @Update
    void updateMeal(Meal meal);

    @Delete
    void deleteMeal(Meal meal);
}
