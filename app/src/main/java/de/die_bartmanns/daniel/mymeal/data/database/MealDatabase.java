package de.die_bartmanns.daniel.mymeal.data.database;

import android.content.Context;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.DeleteColumn;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.AutoMigrationSpec;

import de.die_bartmanns.daniel.mymeal.data.Meal;
import de.die_bartmanns.daniel.mymeal.data.OwnCategory;
import de.die_bartmanns.daniel.mymeal.data.UnitCount;
import de.die_bartmanns.daniel.mymeal.data.typeConverter.DifficultyTypeConverter;
import de.die_bartmanns.daniel.mymeal.data.typeConverter.MealTypeTypeConverter;
import de.die_bartmanns.daniel.mymeal.data.typeConverter.RecipeTypeConverter;

@Database(entities = {Meal.class, UnitCount.class, OwnCategory.class}, version = 6, exportSchema = true,
        autoMigrations = {@AutoMigration (from = 2, to = 3),
                @AutoMigration (from = 3, to = 4),
                @AutoMigration (from = 4, to = 5),
                @AutoMigration (from = 5, to = 6, spec = MealDatabase.AutoMigrationSpecDeleteBitmapStringColumn.class)})
@TypeConverters({DifficultyTypeConverter.class, MealTypeTypeConverter.class, RecipeTypeConverter.class})
public abstract class MealDatabase extends RoomDatabase {

    private static MealDatabase INSTANCE;
    private static final String DB_NAME = "meals.db";


    public static MealDatabase getDatabase(Context context) {
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MealDatabase.class, DB_NAME)
                    .allowMainThreadQueries() // nur zu testzwecken
                    .fallbackToDestructiveMigration()
                    .build();

        return INSTANCE;
    }

    public abstract MealDao mealDao();

    public abstract UnitCountDao unitCountDao();

    public abstract OwnCategoryDao ownCategoryDao();


    @DeleteColumn(tableName = "Meals", columnName = "bitmapString")
    public static class AutoMigrationSpecDeleteBitmapStringColumn implements AutoMigrationSpec{}
}
